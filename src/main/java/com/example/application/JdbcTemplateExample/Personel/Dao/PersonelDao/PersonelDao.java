package com.example.application.JdbcTemplateExample.Personel.Dao.PersonelDao;

import java.util.List;

import com.example.application.JdbcTemplateExample.Personel.Model.Personel;

public interface PersonelDao {
    void add(Personel personel);

    void delete(int personelId);

    void update(Personel personel);

    List<Personel> getAllPersonels();

    List<Personel> getPersonelByKurumTuru(String kurumTuru);

    List<Personel> getPersonelByBolum(int bolumId);
}
