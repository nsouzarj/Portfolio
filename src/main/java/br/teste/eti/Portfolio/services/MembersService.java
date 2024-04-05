package br.teste.eti.Portfolio.services;

import br.teste.eti.Portfolio.domain.Members;
import br.teste.eti.Portfolio.domain.MembersId;

import br.teste.eti.Portfolio.repositorys.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembersService {

    @Autowired
    private MembersRepository membersRepository;

    public Members createMember(Members member) {
        return membersRepository.save(member);
    }

    public Members getMember(Long idProjeto, Long idPessoa) {
        return (Members) membersRepository.findById(new MembersId(idProjeto, idPessoa)).orElse(null);
    }

    public Members updateMember(Members member) {
        return membersRepository.save(member); // Atualiza se existir, cria se não existir
    }

    public void deleteMember(Long idProjeto, Long idPessoa) {
        membersRepository.deleteById(new MembersId(idProjeto, idPessoa));
    }

    // Outros métodos para lógica de negócios específica
}