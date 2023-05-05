package com.example.application.JdbcTemplateExample.Personel.PersonelRowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;

public class PersonelBolumKurumRowMapper implements RowMapper<Personel> {

    @Override
    public Personel mapRow(ResultSet rs, int rowNum) throws SQLException {
        Personel personel=new Personel();
        PersonelBolum personelBolum = new PersonelBolum();
        PersonelKurum personelKurum=new PersonelKurum();

        personel.setPersonelId(rs.getInt("personelId"));
        personel.setPersonelAdi(rs.getString("personelAdi"));
        personel.setPersonelSoyadi(rs.getString("personelSoyadi"));
        personel.setPersonelEmail(rs.getString("personelEmail"));
        personel.setPersonelDogumTarihi(rs.getDate("personelDogumTarihi"));
        personel.setPersonelPhone(rs.getString("personelPhone"));

        personelBolum.setBolumId(rs.getInt("bolumId"));
        personelBolum.setPersonelBolumAdi(rs.getString("personelBolumAdi"));

        personelKurum.setKurumId(rs.getInt("kurumId"));
        personelKurum.setKurumAdi(rs.getString("kurumAdi"));
        personelKurum.setKurumTuruId(rs.getString("kurumTuruId"));
        personelKurum.setKurumil(rs.getString("kurumİl"));
        personelKurum.setKurumilce(rs.getString("kurumİlce"));

        personel.setPersonelBolum(personelBolum);
        personel.setPersonelKurum(personelKurum);
        
        return personel;
    }    
}
