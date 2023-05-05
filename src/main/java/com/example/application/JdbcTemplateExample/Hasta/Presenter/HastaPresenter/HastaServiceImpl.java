package com.example.application.JdbcTemplateExample.Hasta.Presenter.HastaPresenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.example.application.JdbcTemplateExample.Hasta.Dao.HastaDao.HastaDao;
import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;

@Service
public class HastaServiceImpl implements HastaService {

    @Autowired
    private HastaDao hastaDao;

    @Override
    public void addHasta(Hasta hasta) throws DuplicateKeyException {
        boolean isUnique=hastaDao.isUnique(hasta.getHastakimlikno());
        if(isUnique==true){
            this.hastaDao.addHasta(hasta);
        }else{
            throw new DuplicateKeyException("Girilen Hasta TC kimlik numarası sistemde mevcut.\nLütfen farklı bir TC kimlik numarası giriniz. ");
        }
    }

    @Override
    public void deleteHasta(Long hastakimlikno) {
        this.hastaDao.deleteHasta(hastakimlikno);
    }

    @Override
    public void updateHasta(Hasta hasta) {
        this.hastaDao.updateHasta(hasta);
    }

    @Override
    public List<Hasta> findHastaByName(String name) {
        return this.hastaDao.findHastaByName(name);
    }

    @Override
    public List<Hasta> getAllHasta() {
        return this.hastaDao.getAllHasta();
    }

    @Override
    public Hasta getHastaById(Long id) {
        return this.hastaDao.getHastaById(id);
    }

}
