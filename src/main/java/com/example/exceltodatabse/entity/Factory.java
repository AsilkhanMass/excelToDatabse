package com.example.exceltodatabse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="factory")
@ToString
public class Factory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String invNumber;
    private String serialNumber;
    private Date dateOfAcceptance;
    private Date dateOfAcceptanceResponsibleStuff;
    private String responsiblePerson;
    private String realResponsiblePerson;
    private String post;
    private String stuff;
    private String basisTransfer;
    private String note;
    private double percentagePerYearAmortization;
    private double income;
    private double priceForBeggingOfTheNextYear;
    private double yearPricePlan;
    private double increasingPriceInFact;
    private double january;
    private double february;
    private double march;
    private double april;
    private double may;
    private double june;
    private double jule;
    private double august;
    private double september;
    private double october;
    private double november;
    private double december;
    private double priceForEndOfPeriod;


}
