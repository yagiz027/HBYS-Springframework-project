package com.example.application.JdbcTemplateExample.Personel.Dao.PersonelBolumDao;

import java.util.List;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;

public interface PersonelBolumDao {

    List<PersonelBolum> getPersonelBolumList();

    PersonelBolum getPersonelBolumById(int personelBolumId);
}
