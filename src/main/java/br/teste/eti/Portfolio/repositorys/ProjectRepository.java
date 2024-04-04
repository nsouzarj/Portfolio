package br.teste.eti.Portfolio.repositorys;

import br.teste.eti.Portfolio.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Adicione consultas personalizadas, se necessário
}