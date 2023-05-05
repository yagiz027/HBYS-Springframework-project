package com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelKurumTuruPresenter;

import java.util.List;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurumTuru;

public interface PersonelKurumTuruService {
    void add(PersonelKurumTuru kurumTuru);

    void delete(PersonelKurumTuru kurumTuru);

    void update(PersonelKurumTuru kurumTuru);

    List<PersonelKurumTuru> getPersonelKurumTuruList();
}
