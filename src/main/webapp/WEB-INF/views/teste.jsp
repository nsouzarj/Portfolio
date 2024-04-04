<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exemplo JSP</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css">
        <link
              rel="stylesheet" th:href="@{/webjars/bootstrap/5.1.3/css/bootstrap.min.css}"
            />
</head>
<body>
    <h1>Olá, mundo! Esta é uma página JSP de exemplo.</h1>
    <p>Esta página foi criada usando JavaServer Pages (JSP).</p>
    <p>A data e hora atual são: <%= new java.util.Date() %></p>
</body>
</html>