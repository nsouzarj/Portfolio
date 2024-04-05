package br.teste.eti.Portfolio.repositorys;

import br.teste.eti.Portfolio.domain.Members;
import br.teste.eti.Portfolio.domain.MembersId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembersRepository extends JpaRepository<Members, Long> {
    void deleteById (MembersId membersId);

    Optional<Object> findById (MembersId membersId);
    // Adicione consultas personalizadas aqui, se necessário
}