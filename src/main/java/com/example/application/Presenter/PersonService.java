package com.example.application.Presenter;

import java.util.List;

import com.example.application.Models.SamplePerson;

public interface PersonService {
    void add(SamplePerson person);
    void delete(SamplePerson person);
    void update(SamplePerson person);
    List<SamplePerson> getAllPersons();
}
