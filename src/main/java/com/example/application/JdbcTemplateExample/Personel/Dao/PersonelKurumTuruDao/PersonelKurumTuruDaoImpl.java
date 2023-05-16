package com.example.application.JdbcTemplateExample.Personel.Dao.PersonelKurumTuruDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurumTuru;
import com.example.application.JdbcTemplateExample.Personel.PersonelRowMappers.PersonelKurumTuruRowMapper;

@Repository
public class PersonelKurumTuruDaoImpl implements PersonelKurumTuruDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<PersonelKurumTuru> getPersonelKurumTuruList() {
        String selectQuery="SELECT * FROM hastanekurumturleri;";
        return namedParameterJdbcTemplate.query(selectQuery,new PersonelKurumTuruRowMapper());
    }

    @Override
    public PersonelKurumTuru getPersonelKurumTuruById(String kurumTuruId) {
        String findquery="SELECT kt.* FROM hastanekurumturleri kt WHERE kt.kurumturuId='"+kurumTuruId+"';";
        SqlParameterSource parameterSource=new MapSqlParameterSource(findquery,kurumTuruId);
        return namedParameterJdbcTemplate.queryForObject(findquery, parameterSource,new PersonelKurumTuruRowMapper());
    }
}
