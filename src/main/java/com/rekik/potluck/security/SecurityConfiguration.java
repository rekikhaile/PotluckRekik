package com.rekik.potluck.security;

import com.rekik.potluck.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private SSUserDetailsService userDetailsService;
    @Autowired
    private AppUserRepo appUserRepo;

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUserDetailsService(appUserRepo);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http
                .authorizeRequests()

                //allow to all
                .antMatchers("/h2-console/**", "/css/**", "/images/**","/register").permitAll()
                //allowed only to recruiter
                .antMatchers("/jobs/addjob").hasAuthority("RECRUITER")
                //allowed to all
                .antMatchers("/","/postskill", "/addskill","/jobs/displayjobs","/printletter","/displayresume")
                    .access("hasAuthority('ADMIN') or hasAuthority('RECRUITER') or hasAuthority('APPLICANT') or hasAuthority('EMPLOYER') ")
                //allowed to Applicant and Admin
                .antMatchers("/employerindex","/index","/addpersonal","/postpersonal","/addeducation","/posteducation","/addexperience",
                        "/postexperience", "/addsummary","/addreference","/addview",
                        "/coverletter").access("hasAuthority('ADMIN') or hasAuthority('APPLICANT')")

                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(
                        new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll().permitAll()
                .and()
                .httpBasic();

        //For H2
        http
                .csrf().disable();
        http
                .headers().frameOptions().disable();



    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception{
        auth.inMemoryAuthentication()
                .withUser("admin").password("password").authorities("ADMIN");
        auth
                .userDetailsService(userDetailsServiceBean());
    }

}
