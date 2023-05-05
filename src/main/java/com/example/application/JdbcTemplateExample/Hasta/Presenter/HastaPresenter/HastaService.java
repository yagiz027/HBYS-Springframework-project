package com.example.application.JdbcTemplateExample.Hasta.Presenter.HastaPresenter;

import java.util.List;

import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;

public interface HastaService {
    void addHasta(Hasta hasta) throws Exception;

    void deleteHasta(Long hastakimlikno);

    void updateHasta(Hasta hasta);

    List<Hasta> findHastaByName(String name);

    List<Hasta> getAllHasta();

    Hasta getHastaById(Long id);
}
