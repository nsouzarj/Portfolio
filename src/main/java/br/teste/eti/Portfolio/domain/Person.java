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
@Table (name = "pessoa")
public class Person {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate datanascimento;
    private String cpf;
    private boolean funcionario;
    private boolean gerente;
}