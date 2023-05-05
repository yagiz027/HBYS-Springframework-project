package com.example.application.JdbcTemplateExample.Personel.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonelKurumTuru {
    @Id
    @Column(name = "kurumturuId")
    private String kurumTuruId;
    
    @Column(name = "kurumAdi")
    private String kurumTuruAd;
}
