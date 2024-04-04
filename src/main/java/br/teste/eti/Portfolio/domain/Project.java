package br.teste.eti.Portfolio.domain;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Entity
@Table (name = "projeto")
public class Project {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate data_inicio;
    private LocalDate data_previsao_fim;
    private LocalDate data_fim;
    private String descricao;
    private String status;
    private Float orcamento;
    private String risco;
    private Long idgerente; // Representa o ID do gerente (classe Pessoa)
}