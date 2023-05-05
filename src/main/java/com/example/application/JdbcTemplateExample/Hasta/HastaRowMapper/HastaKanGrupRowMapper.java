package com.example.application.JdbcTemplateExample.Hasta.HastaRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.application.JdbcTemplateExample.Hasta.Model.HastaKanGrup;

public class HastaKanGrupRowMapper implements RowMapper<HastaKanGrup> {

    @Override
    public HastaKanGrup mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new HastaKanGrup(
            rs.getInt("kanid"),
            rs.getString("kan_grup")
        ); 
    }
    
}
