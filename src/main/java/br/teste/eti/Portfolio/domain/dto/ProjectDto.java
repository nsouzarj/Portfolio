package br.teste.eti.Portfolio.domain.dto;

import java.time.LocalDate;

//ProjectDTO
public class ProjectDto {
    private Long id;
    private String nome;
    private LocalDate data_inicio;
    private LocalDate data_previsao_fim;
    private LocalDate data_fim;
    private String descricao;
    private String status;
    private Float orcamento;
    private String risco;
    private Long idgerente;

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

    public LocalDate getData_inicio () {
        return data_inicio;
    }

    public void setData_inicio (LocalDate data_inicio) {
        this.data_inicio = data_inicio;
    }

    public LocalDate getData_previsao_fim () {
        return data_previsao_fim;
    }

    public void setData_previsao_fim (LocalDate data_previsao_fim) {
        this.data_previsao_fim = data_previsao_fim;
    }

    public LocalDate getData_fim () {
        return data_fim;
    }

    public void setData_fim (LocalDate data_fim) {
        this.data_fim = data_fim;
    }

    public String getDescricao () {
        return descricao;
    }

    public void setDescricao (String descricao) {
        this.descricao = descricao;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public Float getOrcamento () {
        return orcamento;
    }

    public void setOrcamento (Float orcamento) {
        this.orcamento = orcamento;
    }

    public String getRisco () {
        return risco;
    }

    public void setRisco (String risco) {
        this.risco = risco;
    }

    public Long getIdgerente () {
        return idgerente;
    }

    public void setIdgerente (Long idgerente) {
        this.idgerente = idgerente;
    }

    // Constructors, getters, and setters (no business logic here)
}