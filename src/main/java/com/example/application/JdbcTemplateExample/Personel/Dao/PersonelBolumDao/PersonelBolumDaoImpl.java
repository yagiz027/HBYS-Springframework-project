package com.example.application.JdbcTemplateExample.Personel.Dao.PersonelBolumDao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelBolum;
import com.example.application.JdbcTemplateExample.Personel.PersonelRowMappers.PersonelBolumRowMapper;

@Repository
public class PersonelBolumDaoImpl implements PersonelBolumDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PersonelBolumDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<PersonelBolum> getPersonelBolumList() {
        String selectQuery = "Select pb.bolumId,pb.personelBolumAdi from personelBolum pb;";
        return namedParameterJdbcTemplate.query(selectQuery, new PersonelBolumRowMapper());
    }

    @Override
    public PersonelBolum getPersonelBolumById(int personelBolumId) {
        String selectQuery = "Select pb.bolumId,pb.personelBolumAdi from personelBolum pb where pb.bolumId="
                + personelBolumId + ";";
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("bolumId", personelBolumId);
        return namedParameterJdbcTemplate.queryForObject(selectQuery, parameterSource, new PersonelBolumRowMapper());
    }

}
