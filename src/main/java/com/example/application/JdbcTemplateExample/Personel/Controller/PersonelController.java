package com.example.application.JdbcTemplateExample.Personel.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelPresenter.PersonelService;

@Component
public class PersonelController {
    @Autowired
    private PersonelService personelService;

    public void add(Personel person) {
        personelService.add(person);
    }

    public void delete(int personId) {
        personelService.delete(personId);
    }

    public List<Personel> findAllPerson() {
        return personelService.getAllPersons();
    }

    public void updatePerson(Personel person){
        personelService.update(person);
    }

    public List<Personel> getPersonelByKurumTuru(String kurumTuru){
        return personelService.getPersonelByKurumTuru(kurumTuru);
    }

    public List<Personel> getPersonelByBolum(int bolumId){
        return personelService.getPersonelByBolum(bolumId);
    }
}
