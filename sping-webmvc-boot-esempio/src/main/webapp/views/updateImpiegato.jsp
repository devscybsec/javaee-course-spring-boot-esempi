<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
  <head>
    <title>Spring MVC / Boot Application</title>
  </head>
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
	.inline > label {
		width: 120px;
	}
	.incol {
		display: flex;
		flex-direction: column;
	}
</style>
  <body>
  	<h2>Aggiornamento</h2>
  	<form id="aggiornamento"> 
        <input type="hidden" name="id" value="${impiegato.id}"/>
        <div class="inline">
			<label for="nome">Nome: </label><input type="text" name="nome" value="${impiegato.nome}" />
		</div>
		<div class="inline">
			<label for="cognome">Cognome: </label><input type="text" name="cognome" value="${impiegato.cognome}" />
		</div>
		<div class="inline">
		<label for="settore">Settore: </label><input type="text" name="settore" value="${impiegato.settore}" />
		</div>
		<div class="inline">
			<input type="submit" value="Aggiorna"/>
		</div>
		<a href="./">
			<input type="button" value="Go to impiegati" />
		</a>
    </form>
  </body>
  <script>
	function aggiornaImpiegato() {
		var xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = function() {
	    	if (this.readyState === 4)
	    		location.reload(true);
	    }
		xhttp.open('put', '/api/impiegati/'${impiegato.id}, true);
		xhttp.setRequestHeader("Content-Type", "application/json");  
	    xhttp.send(JSON.stringify({
	    	nome: document.getElementById('nome').value,
	    	cognome: document.getElementById('cognome').value,
	    	settore: document.getElementById('settore').value
	    }));
	}
	document.getElementById("aggiornamento").addEventListener("submit", function(event){
		  event.preventDefault();
		  aggiornaImpiegato();
		});
</script>
</html>