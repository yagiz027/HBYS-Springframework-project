package com.example.application.JdbcTemplateExample.Personel.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;
import com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelKurumPresenter.PersonelKurumService;

@Component
public class PersonelKurumController {
    @Autowired
    private PersonelKurumService personelKurumService;

    public List<PersonelKurum> getPersonelKurumList() {
        return personelKurumService.getKurumList();
    }

    public PersonelKurum getKurumByPersonelKurumId(int personelKurumId){
        return personelKurumService.getKurumByPersoneKurumId(personelKurumId);
    }
}
