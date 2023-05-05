package com.example.application.JdbcTemplateExample.Hasta.Model;

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
public class HastaKanGrup {
    @Id
    @Column(name="kanid")
    private int kanid;

    @Column(name="kan_grup")
    private String kanGrup;
}
