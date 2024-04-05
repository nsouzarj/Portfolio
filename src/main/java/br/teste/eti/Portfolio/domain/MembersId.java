package br.teste.eti.Portfolio.domain;


import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Data
@Getter
@Setter
public class MembersId implements Serializable {
    @Id
    private Long idprojeto;
    @Id
    private Long idpessoa;

    public MembersId (Long idProjeto, Long idPessoa) {
    }

    public MembersId () {
    }
    // Construtor, getters e setters (se necessário)
}