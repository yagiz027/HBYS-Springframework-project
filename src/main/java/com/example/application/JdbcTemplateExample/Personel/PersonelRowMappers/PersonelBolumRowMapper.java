package com.example.application.JdbcTemplateExample.Personel.PersonelRowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;

public class PersonelBolumRowMapper implements RowMapper<PersonelBolum>{

    @Override
    public PersonelBolum mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PersonelBolum(
            rs.getInt("bolumId"),
            rs.getString("personelBolumAdi")
        );
    }

}
