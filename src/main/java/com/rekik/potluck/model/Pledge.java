package com.rekik.potluck.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pledge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
}
