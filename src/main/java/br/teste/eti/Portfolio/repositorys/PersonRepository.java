package br.teste.eti.Portfolio.repositorys;


import br.teste.eti.Portfolio.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    // Adicione consultas personalizadas, se necessário
}