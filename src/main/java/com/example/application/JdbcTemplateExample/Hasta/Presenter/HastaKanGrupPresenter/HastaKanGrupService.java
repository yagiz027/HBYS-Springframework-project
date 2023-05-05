package com.example.application.JdbcTemplateExample.Hasta.Presenter.HastaKanGrupPresenter;

import java.util.List;

import com.example.application.JdbcTemplateExample.Hasta.Model.HastaKanGrup;

public interface HastaKanGrupService {
    List<HastaKanGrup> getAllKans();

    HastaKanGrup getKanGrup(HastaKanGrup hastaKan);
}
