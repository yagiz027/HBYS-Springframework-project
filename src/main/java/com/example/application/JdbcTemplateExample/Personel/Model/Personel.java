package com.example.application.JdbcTemplateExample.Personel.Model;

import java.util.Date;

import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Personel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "personelId")
    private int personelId;

    @Column(name = "personelAdi")
    private String personelAdi;

    @Column(name = "personelSoyadi")
    private String personelSoyadi;
    
    @Column(name = "personelEmail")
    private String personelEmail;

    @Column(name = "personelPhone")
    private String personelPhone;

    @Column(name = "personelDogumTarihi", columnDefinition = "DATETIME")
    private Date personelDogumTarihi;
    
    @Column(name = "personelBolum")
    private PersonelBolum personelBolum; 

    @Column(name="personelKurumId")
    private PersonelKurum personelKurum;
}
