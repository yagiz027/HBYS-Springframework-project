package com.example.application.JdbcTemplateExample.Personel.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelPresenter.PersonelService;

@Controller
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

    public void updatePerson(int personId, String firstName, String lastName, String email, String phone,
            Date dateOfBirth, String occupation) {
    }

    public List<Personel> getPersonelByKurumTuru(String kurumTuru){
        return personelService.getPersonelByKurumTuru(kurumTuru);
    }

    public List<Personel> getPersonelByBolum(int bolumId){
        return personelService.getPersonelByBolum(bolumId);
    }
}
