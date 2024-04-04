package br.teste.eti.Portfolio.enums;

// Enumeração para classificação de risco
public enum Risco {
    BAIXO("Baixo risco"),
    MEDIO("Médio risco"),
    ALTO("Alto risco"),
    DESCONHECIDO("Risco desconhecido");

    private final String descricao;

    Risco(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}