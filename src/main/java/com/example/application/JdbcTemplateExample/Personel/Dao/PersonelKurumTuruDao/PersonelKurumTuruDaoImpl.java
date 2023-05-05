package com.example.application.JdbcTemplateExample.Personel.Dao.PersonelKurumTuruDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
}
