package br.teste.eti.Portfolio.enums;


// Enumeração para status do projeto
public enum StatusProjeto {
    EM_ANALISE("Em análise"),
    ANALISE_REALIZADA("Análise realizada"),
    ANALISE_APROVADA("Análise aprovada"),
    DESCONHECIDO("Status desconhecido"),
    EM_ANDAMENTO("Em Andamento"),
    ENCERRDO("Encerrado"),
    INICIADO("Iniciado"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusProjeto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}