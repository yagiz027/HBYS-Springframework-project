package com.example.application.JdbcTemplateExample.Hasta.Dao.HastaDao;

import java.util.List;

import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;

public interface HastaDao {
    void addHasta(Hasta hasta);

    void deleteHasta(Long hastakimlikno);

    void updateHasta(Hasta hasta);

    List<Hasta> findHastaByName(String name);

    List<Hasta> getAllHasta();

    Hasta getHastaById(Long id);

    boolean isUnique(Long hastakimlikno);
}
