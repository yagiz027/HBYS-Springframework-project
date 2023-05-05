package com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelBolumPresenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.JdbcTemplateExample.Personel.Dao.PersonelBolumDao.PersonelBolumDao;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;

@Service
public class PersonelBolumServiceImpl implements PersonelBolumService {

    @Autowired
    private PersonelBolumDao personelBolumDao;

    @Override
    public void addPersonelBolum(PersonelBolum personelBolum) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addPersonelBolum'");
    }

    @Override
    public List<PersonelBolum> getPersonelBolumList() {
        return personelBolumDao.getPersonelBolumList();    
    }

    @Override
    public PersonelBolum getPersonelBolumById(int personelBolumId) {
        return personelBolumDao.getPersonelBolumById(personelBolumId);
    }
    
}
