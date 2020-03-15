<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel='stylesheet' href='<c:url value="/resources/css/style.css" />' type='text/css' media='all' /> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>New Game</title>
</head>
<body>
<div class="container">
	<h1>Welcome to Black Jack !!!</h1>
	<div class="row">
		<h3 style="color:red">Player Hand&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Player Score:<c:out value="${playerHand.totalScore}"/></h3>
	</div>
	<div class="row">
		<c:forEach items="${playerHand.currentHand}" var="hand">
			<img src="<c:url value="/resources/images/${hand.rank}${hand.suit}.png"/>" alt="<c:out value="${hand.rank} of ${hand.suit}"/>">
		</c:forEach>
	</div>
	<br/>
	<div class="row">
		<c:if test="${empty result}">
			<button type="button" class="btn" id="hit">Hit</button>
			<button type="button" class="btn" id="stand">Stand</button>
		</c:if>
	</div>
	<div class="row">
		<h3 style="color:red">Dealer Hand&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dealer Score:<c:out value="${dealerHand.totalScore}"/></h3>
	</div>
	<div class="row">
		<c:choose>
		<c:when test="${dealerInitialState}">
			<img src="<c:url value="/resources/images/${dealerHand.currentHand[0].rank}${dealerHand.currentHand[0].suit}.png"/>" alt="<c:out value="${dealerHand.currentHand[0].rank} of ${dealerHand.currentHand[0].suit}"/>">
			<img src="<c:url value="/resources/images/BACKOFCARD.png"/>"/>
		</c:when>
		<c:otherwise>
			<c:forEach items="${dealerHand.currentHand}" var="hand">
				<img src="<c:url value="/resources/images/${hand.rank}${hand.suit}.png"/>" alt="<c:out value="${hand.rank} of ${hand.suit}"/>">
			</c:forEach>
		</c:otherwise>
		</c:choose>
	</div>

	<c:if test="${not empty result}">
		<br/>
		<div class="row">
			<h4 style="color:green"><c:out value="${result}"></c:out></h4>
		</div>
		<br/>
		<div class="row">
			<button type="button" id="newgame" class="btn">Start New Game</button>
			<button type="button" id="exit" class="btn">Exit</button>
		</div>
	</c:if>
</div>
<script>
$(document).ready(function(){
	$("#exit").click(function(){
		window.location.href='<c:url value="/home"/>';
	});
	
	$("#newgame").click(function(){
		window.location.href='<c:url value="/newgame"/>';
	});
	
	$("#hit").click(function(){
		window.location.href='<c:url value="/gameinprogress/hit"/>';
	});
	
	$("#stand").click(function(){
		window.location.href='<c:url value="/gameinprogress/stand"/>';
	});
});
</script>
</body>
</html>