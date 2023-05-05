package com.example.application.JdbcTemplateExample.Personel.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurumTuru;
import com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelKurumTuruPresenter.PersonelKurumTuruService;

@Component
public class PersonelKurumTuruController {
    @Autowired    
    private PersonelKurumTuruService personelKurumTuruService;

    public List<PersonelKurumTuru> getPersonelKurumTuruList(){
        return personelKurumTuruService.getPersonelKurumTuruList();
    }
}
