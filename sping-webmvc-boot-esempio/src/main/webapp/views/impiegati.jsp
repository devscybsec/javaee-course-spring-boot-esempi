<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<title>Spring MVC / Boot Application</title>
<style>
table {
	border-spacing: 0;
}

td, th {
	padding: 4px;
	border: 1px solid black;
}

.inline {
	display: flex;
	padding: 4px;
}

.inline>label {
	width: 120px;
}

.incol {
	display: flex;
	flex-direction: column;
}
</style>
</head>
<body>
	<h2>Inserimento</h2>
	<form id="inserimento" class="incol">
		<div class="inline">
			<label for="nome">Nome: </label>
			<input id="nome" type="text" name="nome" />
		</div>
		<div class="inline">
			<label for="cognome">Cognome: </label>
			<input id="cognome" type="text" name="cognome" />
		</div>
		<div class="inline">
			<label for="settore">Settore: </label>
			<input id="settore" type="text" name="settore" />
		</div>
		<div class="inline">
			<input type="submit" value="Inserisci" />
		</div>
	</form>

	<h2>Risultati</h2>

	<table>
		<tbody>
			<tr>
				<th>ID</th>
				<th>Nome</th>
				<th>Cognome</th>
				<th>Settore</th>
				<th>Comandi</th>
			</tr>
			<c:forEach var="impiegato" items="${impiegati}">
				<tr>
					<td>${impiegato.id}</td>
					<td>${impiegato.nome}</td>
					<td>${impiegato.cognome}</td>
					<td>${impiegato.settore}</td>
					<td>
						<div class="inline">
							<button onclick="location.href='/update?id=${impiegato.id}'">Update</button>
							<span style="width: 4px"></span>
							<button onclick="deleteImpiegato('${impiegato.id}')">Delete</button>
						</div>
					</td>
				</tr>
			</c:forEach>
	</table>

</body>
<script>
	function deleteImpiegato(id) {
		var xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = function() {
	    	if (this.readyState === 4)
				location.reload(true);
	    }
		xhttp.open('delete', '/api/impiegati/' + id, true);
	    xhttp.send();
	}
	function insertImpiegato() {
		var xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = function() {
	    	if (this.readyState === 4)
	    		location.reload(true);
	    }
		xhttp.open('post', '/api/impiegati', true);
		xhttp.setRequestHeader("Content-Type", "application/json");  
	    xhttp.send(JSON.stringify({
	    	nome: document.getElementById('nome').value,
	    	cognome: document.getElementById('cognome').value,
	    	settore: document.getElementById('settore').value
	    }));
	}
	document.getElementById("inserimento").addEventListener("submit", function(event){
		  event.preventDefault();
		  insertImpiegato();
		});
</script>
</html>