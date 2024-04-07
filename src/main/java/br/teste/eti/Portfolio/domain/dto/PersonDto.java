package br.teste.eti.Portfolio.domain.dto;

import java.time.LocalDate;

//PersonDTO
public class PersonDto {
    private Long id;
    private String nome;
    private LocalDate datanascimento;
    private String cpf;
    private boolean funcionario;
    private boolean gerente;

    public PersonDto () {
    }


    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getNome () {
        return nome;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public LocalDate getDatanascimento () {
        return datanascimento;
    }

    public void setDatanascimento (LocalDate datanascimento) {
        this.datanascimento = datanascimento;
    }

    public String getCpf () {
        return cpf;
    }

    public void setCpf (String cpf) {
        this.cpf = cpf;
    }

    public boolean isFuncionario () {
        return funcionario;
    }

    public void setFuncionario (boolean funcionario) {
        this.funcionario = funcionario;
    }

    public boolean isGerente () {
        return gerente;
    }

    public void setGerente (boolean gerente) {
        this.gerente = gerente;
    }
}
