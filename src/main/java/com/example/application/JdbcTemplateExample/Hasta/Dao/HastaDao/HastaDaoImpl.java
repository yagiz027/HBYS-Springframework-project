package com.example.application.JdbcTemplateExample.Hasta.Dao.HastaDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.application.JdbcTemplateExample.Hasta.HastaRowMappers.HastaRowMapper;
import com.example.application.JdbcTemplateExample.Hasta.Model.Hasta;

@Repository
public class HastaDaoImpl implements HastaDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public HastaDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void addHasta(Hasta hasta) {
        String insertQuery = ""
                + "INSERT INTO hasta_jdb( "
                + "         hastakimlikno, "
                + "         hastafirstName, "
                + "         hastaLastName, "
                + "         hastaEmail, "
                + "         hastaTelefon, "
                + "         hastaAdres, "
                + "         educationStatus, "
                + "         hastaDogumTarihi, "
                + "         hastaGender, "
                + "         fkHastaKanId, "
                + "         hastaAge) "
                + "     VALUES( "
                + "        :hastakimlikno, "
                + "        :hastafirstName, "
                + "        :hastaLastName, "
                + "        :hastaEmail, "
                + "        :hastaTelefon, "
                + "        :hastaAdres, "
                + "        :educationStatus, "
                + "        :hastaDogumTarihi, "
                + "        :hastaGender, "
                + "        :hastaKanGrup, "
                + "        hastaAge=YEAR(CURDATE()) - "
                + "             YEAR(:hastaDogumTarihi) - IF(STR_TO_DATE(CONCAT(YEAR(CURDATE()), '-', "
                + "             MONTH(:hastaDogumTarihi), '-', "
                + "             DAY(:hastaDogumTarihi)), '%Y-%c-%e') > CURDATE(), 1, 0));";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hastakimlikno", hasta.getHastakimlikno());
        map.put("hastafirstName", hasta.getHastafirstName());
        map.put("hastaLastName", hasta.getHastaLastName());
        map.put("hastaEmail", hasta.getHastaEmail());
        map.put("hastaTelefon", hasta.getHastaTelefon());
        map.put("hastaAdres", hasta.getHastaAdres());
        map.put("educationStatus", hasta.getEducationStatus());
        map.put("hastaDogumTarihi", hasta.getHastaDogumTarihi());
        map.put("hastaGender", hasta.getHastaGender());
        map.put("hastaKanGrup", hasta.getHastaKanGrup().getKanid());
        map.put("hastaAge", hasta.getHastaAge());

        namedParameterJdbcTemplate.execute(insertQuery, map, new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                try {
                    return ps.executeUpdate();
                } catch (SQLException exception) {
                    return exception;
                }
            }
        });
    }

    @Override
    public void deleteHasta(Long hastakimlikno) {
        String deleteQuery = "DELETE FROM hasta_jdb " +
                "WHERE hasta_jdb.hastakimlikno=:hastakimlikno;";

        SqlParameterSource parameterSource = new MapSqlParameterSource("hastakimlikno", hastakimlikno);
        namedParameterJdbcTemplate.update(deleteQuery, parameterSource);
    }

    @Override
    public void updateHasta(Hasta hasta) {
        String updateQuery = ""
                + "UPDATE hastaotomasyon.hasta_jdb h SET "
                + "				   h.hastafirstName=:hastafirstName, "
                + "				   h.hastaLastName=:hastaLastName, "
                + "				   h.hastaEmail=:hastaEmail, "
                + "                h.hastaTelefon=:hastaTelefon, "
                + "                h.hastaAdres=:hastaAdres, "
                + "                h.educationStatus=:educationStatus, "
                + "                h.hastaDogumTarihi=:hastaDogumTarihi, "
                + "                h.hastaGender=:hastaGender, "
                + "                h.fkHastaKanId=:hastaKanGrup, "
                + "                h.hastaAge= "
                + "                     (YEAR(CURDATE()) - YEAR(:hastaDogumTarihi) - "
                + "                         IF(STR_TO_DATE( "
                + "							    CONCAT(YEAR(CURDATE()), '-', MONTH(:hastaDogumTarihi), '-', "
                + "                                 DAY(:hastaDogumTarihi)), '%Y-%c-%e') > CURDATE(), 1, 0)) "
                + "WHERE h.hastakimlikno=:hastakimlikno;";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("hastakimlikno", hasta.getHastakimlikno());
        parameterSource.addValue("hastafirstName", hasta.getHastafirstName());
        parameterSource.addValue("hastaLastName", hasta.getHastaLastName());
        parameterSource.addValue("hastaEmail", hasta.getHastaEmail());
        parameterSource.addValue("hastaTelefon", hasta.getHastaTelefon());
        parameterSource.addValue("hastaAdres", hasta.getHastaAdres());
        parameterSource.addValue("educationStatus", hasta.getEducationStatus());
        parameterSource.addValue("hastaDogumTarihi", hasta.getHastaDogumTarihi());
        parameterSource.addValue("hastaGender", hasta.getHastaGender());
        parameterSource.addValue("hastaKanGrup", hasta.getHastaKanGrup().getKanid());
        parameterSource.addValue("hastaAge", hasta.getHastaAge());

        namedParameterJdbcTemplate.update(updateQuery, parameterSource);
    }

    @Override
    public List<Hasta> findHastaByName(String name) {
        String findQuery = "Select hasta.hasta_jdb from hasta_jdb hasta" +
                " where hasta_jdb.hastafirstName=" + name + "" +
                " or hasta_jdb.hastaLastName=" + name + ";";

        return namedParameterJdbcTemplate.query(findQuery, new HastaRowMapper());
    }

    @Override
    public List<Hasta> getAllHasta() {
        String getAllQuery = ""
                + "SELECT "
                + "     h.hastakimlikno, "
                + "     h.hastafirstName, "
                + "     h.hastaLastName, "
                + "     h.hastaEmail, "
                + "     h.hastaTelefon, "
                + "     h.hastaAdres, "
                + "     h.educationStatus, "
                + "     h.hastaDogumTarihi, "
                + "     h.hastaGender, "
                + "     h.fkHastaKanId, "
                + "     k.kanid, "
                + "     k.kan_grup, "
                + "     h.hastaAge "
                + "FROM hastaotomasyon.hasta_jdb h, hastaotomasyon.kan k "
                + "WHERE h.fkHastaKanId=k.kanid;";

        return namedParameterJdbcTemplate.query(getAllQuery, new HastaRowMapper());
    }

    @Override
    public Hasta getHastaById(Long id) {
        String findQuery = ""
                + "  SELECT h.*,"
                + "      k.kanid,"
                + "      k.kan_grup"
                + "  FROM hasta_jdb h"
                + "       INNER JOIN kan k on k.kanid=h.fkHastaKanId"
                + "  WHERE h.hastakimlikno=" + id + ";";

        Map<String, Object> params = new HashMap<>();
        params.put("hastakimlikno", id);

        return namedParameterJdbcTemplate.queryForObject(findQuery, params, new HastaRowMapper());
    }
    
    @Override
    public boolean isUnique(Long hastakimlikno) {
        String query = ""
                + "  SELECT COUNT(*)"
                + "  FROM hasta_jdb h "
                + "  WHERE h.hastakimlikno =:hastakimlikno;";

        Map<String, Object> params = new HashMap<>();
        params.put("hastakimlikno", hastakimlikno);
        int count = namedParameterJdbcTemplate.queryForObject(query, params, Integer.class);
        return count == 0;
    }
}
