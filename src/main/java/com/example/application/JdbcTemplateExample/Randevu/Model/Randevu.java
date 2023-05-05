package com.example.application.JdbcTemplateExample.Randevu.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Randevu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "randevuId")
    private Long randevuId;

    @Column(name = "randeBaslangicTarih", columnDefinition = "DATETIME")
    private Date randevuBaslangicTarih;

    @Column(name = "randevuBitisTarih", columnDefinition = "DATETIME")
    private Date randevuBitisTarih;

    @Column(name = "randevuAlanHastaTC", columnDefinition = "BIGINT")
    private String randevuAlanHasta;

    @Column(name = "randevuVerenDoktorId")
    private Personel randevuVerenDoktor;

    @Column(name = "randevuVerenBolumId")
    private PersonelBolum randevuVerenBolum;

    @Column(name="randevuVerenKurumId")
    private PersonelKurum randevuVerenKurum;

    @Column(name = "randevuTutar", columnDefinition = "BIGINT")
    private Float randevuTutar;

    @Column(name = "randevuStatu", columnDefinition = "CHAR")
    private Character randevuStatu;
}
