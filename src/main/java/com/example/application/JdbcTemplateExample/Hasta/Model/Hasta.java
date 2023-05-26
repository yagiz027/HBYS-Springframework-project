package com.example.application.JdbcTemplateExample.Hasta.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Hasta {
    @Id
    @Column(name = "hastakimlikno", columnDefinition="BIGINT")
    private Long hastakimlikno;

    @Column(name = "hastafirstName")
    private String hastafirstName;

    @Column(name = "hastaLastName")
    private String hastaLastName;

    @Column(name = "hastaEmail")
    private String hastaEmail;

    @Column(name = "hastaTelefon")
    private String hastaTelefon;

    @Column(name = "hastaAdres")
    private String hastaAdres;

    @Column(name="educationStatus")
    private String educationStatus;

    @Column(name="hastaDogumTarihi", columnDefinition = "DATE")
    private Date hastaDogumTarihi;    

    @Column(name="hastaGender")
    private String hastaGender;

    @Column(name="fkHastaKanId")
    private HastaKanGrup hastaKanGrup;

    @Column(name="hastaAge")
    private int hastaAge;
}
