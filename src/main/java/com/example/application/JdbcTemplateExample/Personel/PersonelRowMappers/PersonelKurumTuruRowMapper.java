package com.example.application.JdbcTemplateExample.Personel.PersonelRowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurumTuru;

public class PersonelKurumTuruRowMapper implements RowMapper<PersonelKurumTuru>{

    @Override
    public PersonelKurumTuru mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PersonelKurumTuru(
            rs.getString("kurumTuruId"),
            rs.getString("kurumTuruAd"));
    }    
}
