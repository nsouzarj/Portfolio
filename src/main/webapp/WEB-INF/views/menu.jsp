<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Menu do Projeto</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" th:href="@{/webjars/bootstrap/5.1.3/css/bootstrap.min.css}"/>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light" style="background-color: #e3f2fd;" >
        <div class="container">
            <a class="navbar-brand" href="#">Menu Projeto</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">Início</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/api/projeto/listaprojetos">Projetos</a>
                    </li>
                     <li class="nav-item">
                        <a class="nav-link" href="/api/pessoa/lista">Recursos</a>
                     </li>

                </ul>
            </div>
        </div>
    </nav>

    <!-- Conteúdo da página -->
    <div class="container mt-4">
        <h1>Bem-vindo ao Gerenciador de Projetos</h1>
        <h2>Sistema de controle de projetos</h2>
    </div>


</body>
</html>
