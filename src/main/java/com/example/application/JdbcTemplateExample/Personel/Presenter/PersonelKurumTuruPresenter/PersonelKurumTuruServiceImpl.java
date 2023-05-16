package com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelKurumTuruPresenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.application.JdbcTemplateExample.Personel.Dao.PersonelKurumTuruDao.PersonelKurumTuruDao;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurumTuru;

@Service
public class PersonelKurumTuruServiceImpl implements PersonelKurumTuruService {

    @Autowired
    private PersonelKurumTuruDao personelKurumTuruDao;

    @Override
    @Transactional
    public List<PersonelKurumTuru> getPersonelKurumTuruList() {
        return personelKurumTuruDao.getPersonelKurumTuruList();
    }

    @Override
    public PersonelKurumTuru getPersonelKurumTuruById(String kurumTuruId) {
        return personelKurumTuruDao.getPersonelKurumTuruById(kurumTuruId);
    }
    
}
