package br.teste.eti.Portfolio.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass (MembersId.class)
public class Members {
    @Id
    private Long idprojeto;

    @Id
    private Long idpessoa;
}
