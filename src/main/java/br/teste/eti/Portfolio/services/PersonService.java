package br.teste.eti.Portfolio.services;

import br.teste.eti.Portfolio.domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> getAllPeople();
    Optional<Person> getPersonById(Long id);
    Person createPerson(Person person);
    Person updatePerson(Long id, Person updatedPerson);
    void deletePerson(Long id);
}