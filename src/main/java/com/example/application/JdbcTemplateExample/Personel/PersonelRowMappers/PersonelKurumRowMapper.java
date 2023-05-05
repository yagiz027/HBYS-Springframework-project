package com.example.application.JdbcTemplateExample.Personel.PersonelRowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;

public class PersonelKurumRowMapper implements RowMapper<PersonelKurum> {

    @Override
    public PersonelKurum mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PersonelKurum(
            rs.getInt("kurumId"),
            rs.getString("kurumAdi"),
            rs.getString("kurumTuruId"),
            rs.getString("kurumİl"),
            rs.getString("kurumİlce")
        );  
    }
}
