package com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelKurumPresenter;

import java.util.List;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;

public interface PersonelKurumService {
    List<PersonelKurum> getKurumList();

    PersonelKurum getKurumByPersoneKurumId(int personelKurumId);
}
