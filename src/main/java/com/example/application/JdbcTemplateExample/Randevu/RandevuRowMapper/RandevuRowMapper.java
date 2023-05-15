package com.example.application.JdbcTemplateExample.Randevu.RandevuRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;
import com.example.application.JdbcTemplateExample.Randevu.Model.Randevu;

public class RandevuRowMapper implements RowMapper<Randevu> {

    @Override
    public Randevu mapRow(ResultSet rs, int rowNum) throws SQLException {
        Randevu randevu = new Randevu();

        randevu.setRandevuId(rs.getLong("randevuId"));
        randevu.setRandevuBaslangicTarih(rs.getDate("randevuBaslangicTarih"));
        randevu.setRandevuBitisTarih(rs.getDate("randevuBitisTarih"));
        randevu.setRandevuAlanHastaTC(rs.getString("randevuAlanHastaTC"));

        Personel personel = new Personel();
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

        randevu.setRandevuVerenDoktor(personel);
        return randevu;
    }

}
