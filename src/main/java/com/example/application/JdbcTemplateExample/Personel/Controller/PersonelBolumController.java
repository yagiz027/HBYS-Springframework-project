package com.example.application.JdbcTemplateExample.Personel.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelBolumPresenter.PersonelBolumService;

@Component
public class PersonelBolumController {
    @Autowired
    private PersonelBolumService personelBolumService;
    
    public List<PersonelBolum> getPersonelBolumList(){
        return personelBolumService.getPersonelBolumList();
    }

    public PersonelBolum getPersonelBolumById(int id){
        return personelBolumService.getPersonelBolumById(id);
    }   
}
