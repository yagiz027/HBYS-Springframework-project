package com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelPresenter;

import java.util.List;

import com.example.application.JdbcTemplateExample.Personel.Model.Personel;

public interface PersonelService {
    void add(Personel person);

    void delete(int personId);

    void update(Personel person);

    List<Personel> getAllPersons();

    List<Personel> getPersonelByKurumTuru(String kurumTuru);

    List<Personel> getPersonelByBolum(int bolumId);
}
