package com.example.application.JdbcTemplateExample.Randevu.Dao;

import java.util.List;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Randevu.Model.Randevu;

public interface RandevuDao {
    void addRandevu(Randevu randevu);

    void updateRandevu(Randevu randevu);

    void deleteRandevu(Randevu randevu);

    Randevu findRandevu(int id);

    List<Randevu> findAllRandevu();

    List<Randevu> findRandevuByBolumAdı(PersonelBolum randevuBolum);

    List<Randevu> findRandevuByStatu(Character randevuStatu);
}
