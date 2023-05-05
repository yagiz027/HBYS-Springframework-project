package com.example.application.JdbcTemplateExample.Randevu.Dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Randevu.Model.Randevu;

@Repository
public class RandevuDaoImpl implements RandevuDao{

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void addRandevu(Randevu randevu) {
        String query=""
        +"INSERT INTO hastarandevu("
        +"                  randevuBaslangicTarih,"
        +"                  randevuBitisTarih,"
        +"                  randevuAlanHastaTC,"
        +"                  randevuVerenDoktorId,"
        +"                  randevuTutar,"
        +"                  randevuStatu)"
        +"VALUES("     
        +"                  :randevuBaslangicTarihi,"
        +"                  :randevuBitisTarih,"
        +"                  :randevuAlanHasta,"
        +"                  :randevuVerenDoktor,"
        +"                  :randevuTutar,"
        +"                  :randevuStatu);";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("randevuBaslangicTarihi", randevu.getRandevuBaslangicTarih());
        parameterSource.addValue("randevuBitisTarih", randevu.getRandevuBitisTarih());
        parameterSource.addValue("randevuAlanHasta",randevu.getRandevuAlanHasta());
        parameterSource.addValue("randevuVerenDoktor", randevu.getRandevuVerenDoktor().getPersonelId());
        parameterSource.addValue("randevuTutar",randevu.getRandevuTutar());
        parameterSource.addValue("randevuStatu",randevu.getRandevuStatu());

        namedParameterJdbcTemplate.execute(query, parameterSource, ps->ps.executeUpdate());
    }

    @Override
    public void updateRandevu(Randevu randevu) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRandevu'");
    }

    @Override
    public void deleteRandevu(Randevu randevu) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRandevu'");
    }

    @Override
    public Randevu findRandevu(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findRandevu'");
    }

    @Override
    public List<Randevu> findAllRandevu() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllRandevu'");
    }

    @Override
    public List<Randevu> findRandevuByBolumAdı(PersonelBolum randevuBolum) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findRandevuByBolumAdı'");
    }

    @Override
    public List<Randevu> findRandevuByStatu(Character randevuStatu) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findRandevuByStatu'");
    }
    
}
