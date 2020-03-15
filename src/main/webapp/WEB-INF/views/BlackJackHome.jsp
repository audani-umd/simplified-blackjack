<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Black Jack Homepage</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel='stylesheet' href='<c:url value="/resources/css/style.css" />' type='text/css' media='all' /> 
</head>
<body>
<div class = "container">
	<h1>Black Jack</h1>
	<div class="row">
		<button type="button" id="newgame" class="btn btn-info">New Game</button>
		<button type="button" id="showrules" class="btn btn-info">Game Rules</button>
	</div>
	<br/>
	<div class="row">
	<div id="gamerules" style="display:none">
		<ul>
		<li>For each game two cards will be drawn for the player and the dealer from four new decks (52 cards per deck) that will be shuffled.</li>
		<li>The program will deal two cards face up for the player and deal one card face up and one card face down (hidden card) for the dealer</li>
		<li>Whenever the sum of the displayed cards for the player is less than 21, the player can decide whether they will hit (receive another card). The hand continues until the player decides to stand (not receive another card) or they bust (their sum is over 21)</li>
		<li>After that, if the player has not busted, the dealer must hit until the sum of the cards is 17 or more.</li>
		<li>The player wins by not busting and having a total higher than the dealer's. The dealer loses by busting or having a lesser total than the player.</li>
		<li>If you and the dealer have the same total, it is considered a push and it is a tie. If the player busts, the dealer wins and they do not need to draw any cards.</li>
		<li>After a game ends, the player can either quit or start a new game.
		<li>
		
		Use the following rules to calculate the score (total):<br/>
		- Ace : +1 or +11 (The ace is always counted as 11. However, if the Ace makes the total over 21, it is counted as 1.)<br/>
		- 10, jack, queen, king : +10 <br/>
		- 2 to 9 : + its value. <br/>
		</li>
		</ul>
	</div>
	</div>
</div>
<script>
$(document).ready(function(){
	$("#showrules").click(function(){
		$("#gamerules").show();
	});
	
	$("#newgame").click(function(){
		window.location.href='<c:url value="/newgame"/>';
	});
});
</script>
</body>
</html>
