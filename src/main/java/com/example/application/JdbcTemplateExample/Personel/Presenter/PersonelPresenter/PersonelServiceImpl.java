package com.example.application.JdbcTemplateExample.Personel.Presenter.PersonelPresenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.JdbcTemplateExample.Personel.Dao.PersonelDao.PersonelDao;
import com.example.application.JdbcTemplateExample.Personel.Model.Personel;

@Service
public class PersonelServiceImpl implements PersonelService {

    @Autowired
    private PersonelDao personelDao;

    @Override
    public void add(Personel person) {
        personelDao.add(person);
    }

    @Override
    public void update(Personel person) {
        personelDao.update(person);
    }

    @Override
    public List<Personel> getAllPersons() {
        return personelDao.getAllPersons();
    }

    @Override
    public void delete(int personId) {
        personelDao.delete(personId);
    }

    @Override
    public List<Personel> getPersonelByKurumTuru(String kurumTuru) {
        return personelDao.getPersonelByKurumTuru(kurumTuru);
    }

    @Override
    public List<Personel> getPersonelByBolum(int bolumId) {
        return personelDao.getPersonelByBolum(bolumId);
    }
}
