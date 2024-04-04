package br.teste.eti.Portfolio.services;


import br.teste.eti.Portfolio.domain.Person;
import br.teste.eti.Portfolio.repositorys.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Long id, Person updatedPerson) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));

        existingPerson.setNome(updatedPerson.getNome());
        existingPerson.setDatanascimento(updatedPerson.getDatanascimento());
        // ... Atualize outros campos conforme necessário

        return personRepository.save(existingPerson);
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}