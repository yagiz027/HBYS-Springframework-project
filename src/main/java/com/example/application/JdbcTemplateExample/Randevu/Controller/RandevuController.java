package com.example.application.JdbcTemplateExample.Randevu.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Randevu.Model.Randevu;
import com.example.application.JdbcTemplateExample.Randevu.Presenter.RandevuService;

@Component
public class RandevuController {

    @Autowired
    private RandevuService randevuService;
    
    public void addRandevu(Randevu randevu) {
        randevu.setRandevuStatu(randevuStatusController(randevu));
        randevuService.addRandevu(randevu);
    }
    private char randevuStatusController(Randevu randevu) {
        if(randevu.getRandevuBaslangicTarih().getTime()<randevu.getRandevuBitisTarih().getTime()){
            return 'T';
        }else{
            return 'F';
        }
    }

    public void updateRandevu(Randevu randevu) {
        randevuService.updateRandevu(randevu);
    }

    public void deleteRandevu(Randevu randevu) {
        randevuService.deleteRandevu(randevu);
    }

    public Randevu findRandevu(int randevuId) {
        return randevuService.findRandevu(randevuId);
    }

    public List<Randevu> findAllRandevu() {
        return randevuService.findAllRandevu();
    }

    public List<Randevu> findRandevuByBolumAdı(PersonelBolum randevuBolum) {
        return randevuService.findRandevuByBolumAdı(randevuBolum);
    }

    public List<Randevu> findRandevuByStatu(Character randevuStatu){
        return randevuService.findRandevuByStatu(randevuStatu);
    }
}
