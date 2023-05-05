package com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelBolumPresenter;

import java.util.List;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;

public interface PersonelBolumService {
    void addPersonelBolum(PersonelBolum personelBolum);
    List<PersonelBolum> getPersonelBolumList();
    PersonelBolum getPersonelBolumById(int personelBolumId);
}
