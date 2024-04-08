<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Agenda De Contatos</title>
<link rel="icon" href="imagens/favicon.png">
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<h1>Editar Contato</h1>
	<form name = "frmContato"action ="update">
		<table>
			<tr>
				<td><input type="text" name="idcon" id="caixa3"readonly value="<%out.print(request.getAttribute("Idcon")); %>"></td>
			</tr>
			<tr>
				<td><input type="text" name="nome" class="Caixa1" value="<%out.print(request.getAttribute("Nome")); %>"></td>
			</tr>
			<tr>
				<td><input type="text" name="fone" class="Caixa2" value="<%out.print(request.getAttribute("Fone")); %>"></td>
			</tr>	
			<tr>
				<td><input type="text" name="email"  class="Caixa1" value="<%out.print(request.getAttribute("Email")); %>"></td>
			</tr>
			
			
		</table>
		<br>
		<input type="submit" value="Adicionar" class="adicionar" onclick="validar()">
		
	</form>
	<script type="text/javascript" src="scripts/validacao.js"></script>
</body>

</html>