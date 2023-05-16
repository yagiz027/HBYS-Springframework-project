package com.example.application.JdbcTemplateExample.Randevu.Dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Randevu.Model.Randevu;
import com.example.application.JdbcTemplateExample.Randevu.RandevuRowMapper.RandevuRowMapper;

@Repository
public class RandevuDaoImpl implements RandevuDao {

    private JdbcTemplate jdbcTemplate;

    public RandevuDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addRandevu(Randevu randevu) {
        String query = ""
                + "INSERT INTO hastarandevu("
                + "                  randevuBaslangicTarih,"
                + "                  randevuBitisTarih,"
                + "                  randevuAlanHastaTC,"
                + "                  randevuVerenDoktorId,"
                + "                  randevuStatu) "
                + "VALUES(?,?,?,?,?);";

        jdbcTemplate.update(query,
                randevu.getRandevuBaslangicTarih(),
                randevu.getRandevuBitisTarih(),
                randevu.getRandevuAlanHastaTC(),
                randevu.getRandevuVerenDoktor().getPersonelId(),
                randevu.getRandevuStatu());
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
        String query = "SELECT r.*,p.*,pb.*,pk.*" 
        +"      FROM hastarandevu r " 
        +"          INNER JOIN personeljdb p ON r.randevuVerenDoktorId = p.personelId " 
        +"          INNER JOIN personelbolum pb ON p.personelBolum = pb.bolumId " 
        +"          INNER JOIN personelkurum pk ON p.personelKurumId = pk.kurumId"
        +"      WHERE r.randevuVerenDoktorId IS NOT NULL;";

        
        List<Randevu> randevuList = jdbcTemplate.query(query, new RandevuRowMapper());
        
        return randevuList;
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
