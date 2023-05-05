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
    public void add(PersonelKurumTuru kurumTuru) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void delete(PersonelKurumTuru kurumTuru) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void update(PersonelKurumTuru kurumTuru) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    @Transactional
    public List<PersonelKurumTuru> getPersonelKurumTuruList() {
        return personelKurumTuruDao.getPersonelKurumTuruList();
    }
    
}
