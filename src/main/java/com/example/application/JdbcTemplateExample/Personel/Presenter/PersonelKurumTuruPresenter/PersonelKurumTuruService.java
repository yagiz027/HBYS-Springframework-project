package com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelKurumTuruPresenter;

import java.util.List;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurumTuru;

public interface PersonelKurumTuruService {
    List<PersonelKurumTuru> getPersonelKurumTuruList();

    PersonelKurumTuru getPersonelKurumTuruById(String kurumTuruId);
}
