package com.example.application.JdbcTemplateExample.Randevu.Presenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Randevu.Dao.RandevuDao;
import com.example.application.JdbcTemplateExample.Randevu.Model.Randevu;

@Service
public class RandevuServiceImpl implements RandevuService {

    @Autowired
    private RandevuDao randevuDao;

    @Override
    public void addRandevu(Randevu randevu) {
        randevuDao.addRandevu(randevu);
    }

    @Override
    public void updateRandevu(Randevu randevu) {
        randevuDao.updateRandevu(randevu);
    }

    @Override
    public void deleteRandevu(Randevu randevu) {
        randevuDao.deleteRandevu(randevu);
    }

    @Override
    public Randevu findRandevu(int randevuId) {
        return randevuDao.findRandevu(randevuId);
    }

    @Override
    public List<Randevu> findAllRandevu() {
        return randevuDao.findAllRandevu();
    }

    @Override
    public List<Randevu> findRandevuByBolumAdı(PersonelBolum randevuBolum) {
        return randevuDao.findRandevuByBolumAdı(randevuBolum);
    }

    @Override
    public List<Randevu> findRandevuByStatu(Character randevuStatu) {
        return randevuDao.findRandevuByStatu(randevuStatu);
    }
}
