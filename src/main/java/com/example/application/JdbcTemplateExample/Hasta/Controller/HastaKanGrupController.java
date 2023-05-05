package com.example.application.JdbcTemplateExample.Hasta.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.JdbcTemplateExample.Hasta.Model.HastaKanGrup;
import com.example.application.JdbcTemplateExample.Hasta.Presenter.HastaKanGrupPresenter.HastaKanGrupService;

@Component
public class HastaKanGrupController {
    @Autowired
    private HastaKanGrupService kanGrupService;

    public List<HastaKanGrup> getKanGrupList() {
        return kanGrupService.getAllKans();
    }

    public HastaKanGrup getKanGrup(HastaKanGrup hastaKan){
        return kanGrupService.getKanGrup(hastaKan);
    }
    
}
