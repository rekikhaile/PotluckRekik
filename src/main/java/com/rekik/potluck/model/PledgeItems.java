package com.rekik.potluck.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PledgeItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String pledgeName;

    private String itemType;

    private int serving;

    @ManyToMany
    //This needs to be instantiated in the construtor so you can use it to add and remove individual pledges
    Set<AppUser> pusers;

    //own method
   /* public void addUser(AppUser aAppUser)
    {
        this.pusers.add(aAppUser);
    }*/
   //own
    public void addUsertoPledge(AppUser aAppUser)
    {
        this.pusers.add(aAppUser);
    }



    public PledgeItems() {
        this.pusers = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPledgeName() {
        return pledgeName;
    }

    public void setPledgeName(String pledgeName) {
        this.pledgeName = pledgeName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
        this.serving = serving;
    }

    public Set<AppUser> getPusers() {
        return pusers;
    }

    public void setPusers(Set<AppUser> pusers) {
        this.pusers = pusers;
    }

    @Override
    public String toString() {
        return "PledgeItems{" +
                "id=" + id +
                ", pledgeName='" + pledgeName + '\'' +
                ", itemType='" + itemType + '\'' +
                ", serving=" + serving +
                '}';
    }
}
