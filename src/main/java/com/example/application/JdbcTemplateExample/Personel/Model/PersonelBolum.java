package com.example.application.JdbcTemplateExample.Personel.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.vaadin.flow.component.template.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonelBolum {
    @Id
    @Column(name="bolumId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int bolumId;
    
    @Column(name="personelBolumAdi")
    private String personelBolumAdi;
}
