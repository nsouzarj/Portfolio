<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Cadastro de Projetos</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" th:href="@{/webjars/bootstrap/5.1.3/css/bootstrap.min.css}"/>
</head>
<body>
<div class="container">
    <h1>Cadastro de Projetos</h1>
    <form action="/api/projeto/cadastrarProjeto" method="post">
        <div class="form-group">
            <label for="nome">Nome do Projeto:</label>
            <input type="text" class="form-control" id="nome" name="nome" required oninput="this.value = this.value.toUpperCase()">
        </div>
        <div class="form-group">
            <label for="data_inicio">Data de Início:</label>
            <input type="date" class="form-control" id="data_inicio" name="data_inicio" required>
        </div>
        <div class="form-group">
            <label for="data_previsao_fim">Data de Previsão de Término:</label>
            <input type="date" class="form-control" id="data_previsao_fim" name="data_previsao_fim" required>
        </div>
        <div class="form-group">
            <label for="data_fim">Data de Término:</label>
            <input type="date" class="form-control" id="data_fim" name="data_fim">
        </div>
        <div class="form-group">
            <label for="descricao">Descrição:</label>
            <textarea class="form-control" id="descricao" name="descricao" required oninput="this.value = this.value.toUpperCase()" rows="3"></textarea>
        </div>
        <div class="form-group">
            <label for="status">Status:</label>
            <select class="form-control" id="status" name="status">
                <c:forEach var="statusEnum" items="${statusEnum}">
                    <option>${statusEnum.getDescricao()}</option>
                </c:forEach>
            </select>
        </div>


        <div class="form-group">
            <label for="orcamento">Orçamento:</label>
            <input type="number" class="form-control" id="orcamento" name="orcamento" step="0.01">
        </div>
        <div class="form-group">
            <label for="risco">Risco:</label>
            <select class="form-control" id="risco" name="risco">
                <c:forEach var="riscoEnum" items="${riscoEnum}">
                    <option value="${riscoEnum}">${riscoEnum.getDescricao()}</option>
                </c:forEach>
            </select>
        </div>
         <div class="form-group">
             <label for="idgerente">Gerente:</label>
               <select class="form-control" id="idgerente" name="idgerente">
                   <c:forEach var="gerente" items="${listPerson}">
                       <option value="${gerente.id}">${gerente.nome}</option>
                   </c:forEach>
             </select>
         </div>


        <button type="submit" class="btn btn-primary" title="Salva o cadastro" >Cadastrar</button>
        <a href="/api/projeto/listaprojetos" title="Cancela o cadastro e volta pra lista de projetos" class="btn btn-warning">Cancelar</a>
    </form>
</body>
</div>
</html>
