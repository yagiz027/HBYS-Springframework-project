package com.example.application.JdbcTemplateExample.Hasta.Dao.HastaKanGrupDao;

import java.util.List;

import com.example.application.JdbcTemplateExample.Hasta.Model.HastaKanGrup;

public interface HastaKanGrupDao {
    List<HastaKanGrup> findAllKanGrup();
    
    HastaKanGrup getKanGrup(HastaKanGrup hastaKan);
}
