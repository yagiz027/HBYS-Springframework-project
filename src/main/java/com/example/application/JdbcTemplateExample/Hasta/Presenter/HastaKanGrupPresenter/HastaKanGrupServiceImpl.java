package com.example.application.JdbcTemplateExample.Hasta.Presenter.HastaKanGrupPresenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.JdbcTemplateExample.Hasta.Dao.HastaKanGrupDao.HastaKanGrupDao;
import com.example.application.JdbcTemplateExample.Hasta.Model.HastaKanGrup;

@Service
public class HastaKanGrupServiceImpl implements HastaKanGrupService {

    @Autowired
    private HastaKanGrupDao kanGrupDao;

    @Override
    public List<HastaKanGrup> getAllKans() {
        return kanGrupDao.findAllKanGrup();
    }

    @Override
    public HastaKanGrup getKanGrup(HastaKanGrup hastaKan) {
        return kanGrupDao.getKanGrup(hastaKan);
    }
}
