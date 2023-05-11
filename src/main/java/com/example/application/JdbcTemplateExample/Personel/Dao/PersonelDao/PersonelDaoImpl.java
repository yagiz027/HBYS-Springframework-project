package com.example.application.JdbcTemplateExample.Personel.Dao.PersonelDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.application.JdbcTemplateExample.Personel.Model.Personel;
import com.example.application.JdbcTemplateExample.Personel.PersonelRowMappers.PersonelBolumKurumRowMapper;

@Repository
public class PersonelDaoImpl implements PersonelDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PersonelDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void add(Personel personel) {
        String insertQuery ="INSERT INTO personeljdb("+
                                        "personelAdi,personelSoyadi,personelEmail,"+
                                        "personelPhone,personelDogumTarihi,personelBolum,"+
                                        "personelKurumId) " +
                            "VALUES("+
                                        ":personelAdi,:personelSoyadi,:personelEmail,"+
                                        ":personelPhone,:personelDogumTarihi,:personelBolum,"+
                                        ":personelKurum);";

        MapSqlParameterSource parameterSource=new MapSqlParameterSource();
        parameterSource.addValue("personelAdi", personel.getPersonelAdi());
        parameterSource.addValue("personelSoyadi",personel.getPersonelSoyadi());
        parameterSource.addValue("personelEmail",personel.getPersonelEmail());
        parameterSource.addValue("personelPhone",personel.getPersonelPhone());
        parameterSource.addValue("personelDogumTarihi",personel.getPersonelDogumTarihi());
        parameterSource.addValue("personelBolum",personel.getPersonelBolum().getBolumId());
        parameterSource.addValue("personelKurum",personel.getPersonelKurum().getKurumId());

        namedParameterJdbcTemplate.execute(insertQuery, parameterSource,new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                return ps.executeUpdate();
            }
        });
    }

    @Override
    public void delete(int personelId) {
        String deleteQuery = "DELETE FROM personeljdb WHERE personelId=:personelId";

        SqlParameterSource parameterSource = new MapSqlParameterSource("personelId", Integer.valueOf(personelId));
        namedParameterJdbcTemplate.update(deleteQuery, parameterSource);
    }

    @Override
    public void update(Personel personel) {
        String updateQuery = 
                "UPDATE personeljdb SET " +
                    "personelAdi=:personelAdi," +
                    "personelSoyadi=:personelSoyadi," +
                    "personelEmail=:personelEmail," +
                    "personelPhone=:personelPhone," +
                    "personelDogumTarihi=:personelDogumTarihi," +
                    "personelBolum=:personelBolum, " +
                    "personelKurumId=:personelKurum "+
                "WHERE personelId=:personelId";

            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                parameterSource.addValue("personelAdi", personel.getPersonelAdi());
                parameterSource.addValue("personelSoyadi", personel.getPersonelSoyadi());
                parameterSource.addValue("personelEmail", personel.getPersonelEmail());
                parameterSource.addValue("personelPhone", personel.getPersonelPhone());
                parameterSource.addValue("personelDogumTarihi", personel.getPersonelDogumTarihi());
                parameterSource.addValue("personelBolum", personel.getPersonelBolum().getBolumId());
                parameterSource.addValue("personelKurum", personel.getPersonelKurum().getKurumId());
                parameterSource.addValue("personelId", personel.getPersonelId());
            
        namedParameterJdbcTemplate.update(updateQuery,parameterSource);
    }

    @Override
    public List<Personel> getAllPersonels() {
        String selectAllQuery = "SELECT "+
                                    "p.personelId,"+
                                    "p.personelAdi,"+
                                    "p.personelSoyadi,"+
                                    "p.personelEmail,"+
                                    "p.personelPhone,"+
                                    "p.personelDogumTarihi,"+
                                    "pb.bolumId,"+
                                    "pb.personelBolumAdi,"+
                                    "pk.kurumId,"+
                                    "pk.kurumAdi,"+
                                    "pk.kurumTuruID,"+
                                    "pk.kurumil,"+
                                    "pk.kurumilce "+
                                "FROM personeljdb p, personelBolum pb, personelKurum pk "+
                                "WHERE pb.bolumId=p.personelBolum and pk.kurumId=p.personelKurumId;";
        return namedParameterJdbcTemplate.query(selectAllQuery,new PersonelBolumKurumRowMapper()); 
    }

    @Override
    public List<Personel> getPersonelByKurumTuru(String kurumTuruId) {
        String query = ""
                        + "SELECT p.personelId, "
                        + "		p.personelAdi, "
                        + "		p.personelSoyadi, "
                        + "		p.personelEmail, "
                        + "		p.personelPhone, "
                        + "		p.personelDogumTarihi, "
                        + "	    pb.bolumId,"
                        + "		pb.personelBolumAdi, "
                        + "     pk.kurumId,"
                        + "		pk.kurumAdi, "
                        + "     pk.kurumTuruID,"
                        + "     pk.kurumil,"
                        + "     pk.kurumilce"
                        + " FROM hastaotomasyon.personeljdb p, "
                        + "     hastaotomasyon.personelBolum pb, "
                        + "     hastaotomasyon.personelKurum pk "
                        + " WHERE pb.bolumId=p.personelBolum and "
                        + "     pk.kurumId=p.personelKurumId and "
                        + "     pk.kurumTuruId=:kurumTuruId;";
        MapSqlParameterSource parameterSource= new MapSqlParameterSource().addValue("kurumTuruId", kurumTuruId);
        return namedParameterJdbcTemplate.query(query,parameterSource,new PersonelBolumKurumRowMapper());
    }

    @Override
    public List<Personel> getPersonelByBolum(int bolumId) {
        String query = ""
                        + "SELECT p.personelId, "
                        + "		p.personelAdi, "
                        + "		p.personelSoyadi, "
                        + "		p.personelEmail, "
                        + "		p.personelPhone, "
                        + "		p.personelDogumTarihi, "
                        + "	    pb.bolumId,"
                        + "		pb.personelBolumAdi, "
                        + "     pk.kurumId,"
                        + "		pk.kurumAdi, "
                        + "     pk.kurumTuruID,"
                        + "     pk.kurumil,"
                        + "     pk.kurumilce"
                        + " FROM hastaotomasyon.personeljdb p, "
                        + "     hastaotomasyon.personelBolum pb, "
                        + "     hastaotomasyon.personelKurum pk "
                        + " WHERE pb.bolumId=p.personelBolum and "
                        + "     pk.kurumId=p.personelKurumId and "
                        + "     pb.bolumId=:bolumId;";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource("bolumId", bolumId);
        return namedParameterJdbcTemplate.query(query,parameterSource,new PersonelBolumKurumRowMapper());
    }
}
