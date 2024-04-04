<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>

    <title>Lista de Pessoas</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" th:href="@{/webjars/bootstrap/5.1.3/css/bootstrap.min.css}"/>
</head>
<form>
<body>
<div class="container">
    <div class="d-flex p-2">
              <a href="/api/pessoa/cadastrarpessoa" class="btn btn-primary">Cadastrar Recurso</a>
              <a href="/api/menu" class="btn btn-warning">Sair</a>
        </div>
    <h3>LISTA DE PESSOAS CADASTRADAS</h3>
    <table class="table table-striped table-hover">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Data de Nascimento</th>
            <th>CPF</th>
            <th>Funcionário</th>
            <th>Gerente</th>
            <th>Excluir</th>
        </tr>
        <c:forEach var="entrada" items="${people}">
             <tr>
                 <td><a href="/api/pessoa/alteracao/${entrada.id}">${entrada.id}</a></td>
                 <td>${entrada.nome}</td>
                 <td>${entrada.datanascimento}</td>
                 <td>${entrada.cpf}</td>
                 <td><c:choose>
                     <c:when test="${entrada.funcionario}">
                        SIM
                     </c:when>
                     <c:otherwise>
                        NAO
                    </c:otherwise>
                    </c:choose></td>
                 <td><c:choose>
                     <c:when test="${entrada.gerente}">
                       SIM
                     </c:when>
                     <c:otherwise>
                        NAO
                    </c:otherwise>
                  </c:choose></td>
                 <td><a href="/api/pessoa/excluirPessoa/${entrada.id}" class="btn btn-danger">Excluir</a><td>
             </tr>
        </c:forEach>
    </table>
    </div>
</body>
</form>
</html>
