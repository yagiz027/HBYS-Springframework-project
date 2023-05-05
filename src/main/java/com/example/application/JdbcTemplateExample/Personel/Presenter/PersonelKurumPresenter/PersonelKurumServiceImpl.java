package com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelKurumPresenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.JdbcTemplateExample.Personel.Dao.PersonelKurumDao.PersonelKurumDao;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;

import jakarta.transaction.Transactional;

@Service
public class PersonelKurumServiceImpl implements PersonelKurumService{
    @Autowired
    private PersonelKurumDao personelKurumDao;

    @Override
    @Transactional
    public List<PersonelKurum> getKurumList() {
        return personelKurumDao.getKurumList();
    }

    @Override
    public PersonelKurum getKurumByPersoneKurumId(int personelKurumId) {
        return personelKurumDao.getPersonelKurumById(personelKurumId);
    }

}
