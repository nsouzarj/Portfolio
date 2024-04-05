<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Projetos</title>
    <head>
        <title>Cadastro de Projetos</title>
        <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css">
        <link rel="stylesheet" th:href="@{/webjars/bootstrap/5.1.3/css/bootstrap.min.css}"/>
    </head>
    <style>
      .d-flex .btn {
        margin-right: 15px;
      }
    </style>

</head>
<body>
    <div class="container">
    <h1>Lista de Projetos</h1>
    <div class="d-flex p-2">
          <a href="/api/projeto/cadprojeto" class="btn btn-primary">Cadastrar Projeto</a>
          <a href="/api/menu" class="btn btn-warning">Sair</a>
    </div>
    <table class="table table-striped table-hover">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Data de Início</th>
                <th>Data de Previsão de Término</th>
                <th>Data de Término</th>
                <th>Descrição</th>
                <th>Status</th>
                <th>Orçamento</th>
                <th>Risco</th>
                <th>ID do Gerente</th>
                <th>Excluir</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${listaproj}" var="projeto">
                <tr>
                    <td id="minha-coluna" style="background-color: #fcf6bd;"><a href="/api/projeto/alterarprojeto/${projeto.id}">${projeto.id}</a></td>
                    <td>${projeto.nome}</td>
                    <td>${projeto.data_inicio}</td>
                    <td>${projeto.data_previsao_fim}</td>
                    <td>${projeto.data_fim}</td>
                    <td>${projeto.descricao}</td>
                    <td>${projeto.status}</td>
                    <td>${projeto.orcamento}</td>
                    <td>${projeto.risco}</td>
                    <td>
                        <c:forEach items="${listPerson}" var="gerente">
                            <c:if test="${gerente.id == projeto.idgerente}">
                                ${gerente.nome}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td><a href="/api/projeto/excluirProjeto/${projeto.id}" class="btn btn-danger">Excluir</a>
                                                          <td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
     <c:if test="${alert == true}">
         <div class="alert alert-danger" role="alert">
             ${msg}
         </div>

     </c:if>
</body>
</html>

