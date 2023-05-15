package com.example.application.JdbcTemplateExample.Hasta.HastaRowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;
import com.example.application.JdbcTemplateExample.Hasta.Model.HastaKanGrup;

public class HastaRowMapper implements RowMapper<Hasta>{

    @Override
    public Hasta mapRow(ResultSet rs, int rowNum) throws SQLException {
        HastaKanGrup hastaKanGrup=new HastaKanGrup();
        Hasta hasta=new Hasta();
        hasta.setHastakimlikno(rs.getLong("hastakimlikno"));
        hasta.setHastafirstName(rs.getString("hastafirstname"));
        hasta.setHastaLastName(rs.getString("hastaLastName"));
        hasta.setHastaEmail(rs.getString("hastaEmail"));
        hasta.setHastaTelefon(rs.getString("hastaTelefon"));
        hasta.setHastaAdres(rs.getString("hastaAdres"));
        hasta.setEducationStatus(rs.getString("educationStatus"));
        hasta.setHastaDogumTarihi(rs.getDate("hastaDogumTarihi"));
        hasta.setHastaGender(rs.getString("hastaGender"));
        hasta.setHastaAge(rs.getInt("hastaAge"));

        hastaKanGrup.setKanid(rs.getInt("kanid"));
        hastaKanGrup.setKanGrup(rs.getString("kan_grup"));

        hasta.setHastaKanGrup(hastaKanGrup);

        return hasta;
    }
}
