package com.example.application.JdbcTemplateExample.Hasta.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;
import com.example.application.JdbcTemplateExample.Hasta.Presenter.HastaPresenter.HastaService;

@Controller
public class HastaController {
    @Autowired
    private HastaService hastaService;

    public void addHasta(Hasta hasta) throws Exception{
        hastaService.addHasta(hasta);
    }

    public void deleteHasta(Long hastakimlikno){
        hastaService.deleteHasta(hastakimlikno);
    }

    public void updateHasta(Hasta hasta){
        hastaService.updateHasta(hasta);
    }

    public List<Hasta> findHastaByName(String name){
        return hastaService.findHastaByName(name);
    }

    public List<Hasta> findAllHasta(){
        return hastaService.getAllHasta();
    }

    public Hasta findById(Long id){
        return hastaService.getHastaById(id);
    }
}
