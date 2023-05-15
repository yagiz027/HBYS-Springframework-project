package com.example.application.JdbcTemplateExample.Hasta.Dao.HastaKanGrupDao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.application.JdbcTemplateExample.Hasta.HastaRowMappers.HastaKanGrupRowMapper;
import com.example.application.JdbcTemplateExample.Hasta.Model.HastaKanGrup;

@Repository
public class HastaKanGrupDaoImpl implements HastaKanGrupDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public HastaKanGrupDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<HastaKanGrup> findAllKanGrup() {
        String selectQuery = "SELECT * from kan";
        return namedParameterJdbcTemplate.query(selectQuery, new HastaKanGrupRowMapper());
    }

    @Override
    public HastaKanGrup getKanGrup(HastaKanGrup hastaKan) {
        String selectQuery="Select k.kanid,k.kan_grup from kan k where k.kanid = "+hastaKan.getKanid()+";";
        SqlParameterSource parameterSource=new MapSqlParameterSource().addValue("kanid", hastaKan.getKanid());
        return namedParameterJdbcTemplate.queryForObject(selectQuery, parameterSource, new HastaKanGrupRowMapper());
    }
}
