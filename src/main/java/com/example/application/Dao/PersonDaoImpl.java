package com.example.application.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.application.Models.SamplePerson;

@Repository
public class PersonDaoImpl implements PersonDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public PersonDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(SamplePerson person) {
        String insertQuery = "insert into person(firstName,lastName,email,phone,dateOfBirth,occupation) "+
        "values(:firstName,:lastName,:email,:phone,:dateOfBirth,:occupation);";

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("firstName", person.getFirstName());
        map.put("lastName", person.getLastName());
        map.put("email", person.getEmail());
        map.put("phone",person.getPhone());
        map.put("dateOfBirth",person.getDateOfBirth());
        map.put("occupation",person.getOccupation());

        namedParameterJdbcTemplate.execute(insertQuery, map, new PreparedStatementCallback<Object> (){
            @Override
            public Object  doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
               return ps.executeUpdate();
            }
        });
    }

    @Override
    public void delete(SamplePerson person) {
        String deleteQuery="Delete from person where person.personId;";

        Map<String, Object> map=new HashMap<String, Object>();
        map.put("personId", person.getPersonId());

        namedParameterJdbcTemplate.execute(deleteQuery, map, new PreparedStatementCallback<Object>() {

            @Override
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                return ps.executeQuery();
            }
        });
    }

    @Override
    public void update(SamplePerson person) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<SamplePerson> getAllPersons() {
        String selectAllQuery="Select * from person p;";

        return jdbcTemplate.query(selectAllQuery,new ResultSetExtractor<List<SamplePerson>>() {
            @Override
            public List<SamplePerson> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<SamplePerson> personList=new ArrayList<SamplePerson>();
                while(rs.next()){
                   SamplePerson p=new SamplePerson();
                   p.setPersonId(rs.getInt(1));
                   p.setFirstName(rs.getString(2)); 
                   p.setLastName(rs.getString(3));
                   p.setEmail(rs.getString(4));
                   p.setPhone(rs.getString(5));
                   p.setDateOfBirth(rs.getDate(6));
                   p.setOccupation(rs.getString(7));
                }
                return personList;
            } 
        });
    }

}
