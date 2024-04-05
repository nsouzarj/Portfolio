package br.teste.eti.Portfolio.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Table(name = "membros")
@Entity
@IdClass(MembersId.class)
public class Members {
    @Transient
    private Long id;
    @Id
    private Long idprojeto;
    @Id
    private Long idpessoa;
}
