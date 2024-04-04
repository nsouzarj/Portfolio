<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Pessoa</title>
    <!-- Adicione o link do Bootstrap via CDN (opcional) -->
      <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css">
        <link
              rel="stylesheet" th:href="@{/webjars/bootstrap/5.1.3/css/bootstrap.min.css}"
            />
</head>
<body>
    <div class="container">
        <h1>Cadastro de Pessoa</h1>
        <form action="/api/pessoa/cadastrarPessoa" method="post">
            <div class="form-group">
                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome"  class="form-control" required  oninput="this.value = this.value.toUpperCase()">
            </div>

            <div class="form-group">
                <label for="dataNascimento">Data de Nascimento:</label>
                <input type="date" id="dataNascimento" name="dataNascimento" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="cpf">CPF:</label>
                <input type="text" id="cpf" name="cpf" class="form-control">
            </div>

            <div class="form-check">
                <input type="checkbox" id="funcionario" name="funcionario" class="form-check-input">
                <label for="funcionario" class="form-check-label">Funcionário</label>
            </div>

            <div class="form-check">
                <input type="checkbox" id="gerente" name="gerente" class="form-check-input">
                <label for="gerente" class="form-check-label">Gerente</label>
            </div>


            <button type="submit" class="btn btn-primary">Cadastrar</button>
            <a href="/api/pessoa/lista" title="Cancela o cadastro e volta pra lista de recursos" class="btn btn-warning">Cancelar</a>
        </form>
    </div>
</body>
</html>
