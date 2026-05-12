package com.nouali.authservice.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String planType;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private String paymentMethod;
    @Column(nullable = false)
    private Timestamp  startDate;
    @Column(nullable = false)
    private Timestamp endDate;
}
