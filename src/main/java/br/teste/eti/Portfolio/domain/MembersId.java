package br.teste.eti.Portfolio.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class MembersId implements Serializable {
    private Long idprojeto;
    private Long idpessoa;
}
