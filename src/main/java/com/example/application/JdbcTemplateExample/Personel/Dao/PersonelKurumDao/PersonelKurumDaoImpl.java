package com.example.application.JdbcTemplateExample.Personel.Dao.PersonelKurumDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.application.JdbcTemplateExample.Personel.Model.PersonelKurum;
import com.example.application.JdbcTemplateExample.Personel.PersonelRowMappers.PersonelKurumRowMapper;

@Repository
public class PersonelKurumDaoImpl implements PersonelKurumDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PersonelKurumDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<PersonelKurum> getKurumList() {
        String selectQuery = "Select * from personelKurum pk;";
        return namedParameterJdbcTemplate.query(selectQuery, new PersonelKurumRowMapper());
    }

    @Override
    public List<PersonelKurum> getKurumListByKurumTuruId(char kurumTuruId) {
        return null;
    }

    @Override
    public PersonelKurum getPersonelKurumById(int personelKurumId) {
        String query="Select * from personelKurum pk "+
                    "where pk.kurumId="+personelKurumId+";";
        SqlParameterSource parameterSource=new MapSqlParameterSource().addValue("kurumId", personelKurumId);
        return namedParameterJdbcTemplate.queryForObject(query, parameterSource,new PersonelKurumRowMapper());
    }
}
