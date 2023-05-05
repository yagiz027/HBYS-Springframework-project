package com.example.application.JdbcTemplateExample.Personel.Model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.vaadin.flow.component.template.Id;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonelKurum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kurumId")
    private Integer kurumId;
    @Column(name = "kurumAdi")
    private String kurumAdi;
    @Column(name = "kurumTuruID")
    private String kurumTuruId;
    @Column(name = "kurumil")
    private String kurumil;
    @Column(name = "kurumilce")
    private String kurumilce;
}
