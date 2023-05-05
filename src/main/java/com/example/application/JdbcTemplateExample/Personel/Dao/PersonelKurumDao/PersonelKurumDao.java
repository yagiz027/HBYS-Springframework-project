package com.example.application.JdbcTemplateExample.Personel.Dao.PersonelKurumDao;

import java.util.List;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;

public interface PersonelKurumDao {

    List<PersonelKurum> getKurumList();

    List<PersonelKurum> getKurumListByKurumTuruId(char kurumTuruId);

    PersonelKurum getPersonelKurumById(int personelKurumId);
}
