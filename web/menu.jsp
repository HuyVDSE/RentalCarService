
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="user" value="${sessionScope.ACCOUNT}" />
<c:set var="cart" value="${sessionScope.CUS_CART}" />

<div class="nav-bar navbar-dark navbar-expand-sm bg-success py-2">
    <ul class="navbar-nav ml-auto">
	<c:if test="${not empty user}">
	    <ul class="navbar-nav ml-auto">
		<li class="nav-item">
		    <div class="text-white">Welcome, ${user.fullname}</div>
		</li>
		<li class="nav-item">
		    <a href="logout" class="btn btn-light btn-sm my-2 my-sm-0 mx-3">Logout</a>
		</li>
	    </ul>
	</c:if>
	<c:if test="${empty user}">
	    <ul class="navbar-nav ml-auto text-center">
		<li class="nav-item">
		    <a href="login-page" class="btn btn-light btn-sm my-2 my-sm-0 mx-3">login</a>
		</li>
	    </ul>
	</c:if>
    </ul>
</div>

<div class="jumbotron text-center" style="margin-bottom: 0">
    <h1>Car Rental Shop</h1>
    <p>All You Need Just Drive</p>
</div>

<!--nav bar-->
<div class="navbar navbar-dark navbar-expand-sm bg-dark">
    <ul class="navbar-nav mr-0 text-center">
	<li class="nav-item">
	    <a class="nav-link active" href="home">Home</a>
	</li>
	<c:if test="${not empty user}">
	    <c:if test="${user.role ne 'ADMIN'}">
		<li class="nav-item">
		    <a class="nav-link" href="view-cart"><i class="fa fa-shopping-cart"></i> Cart</a>
		</li>
		<c:if test="${not empty cart.itemList}">
		    <li class="nav-item">
			<span class="badge badge-danger">${cart.totalItems}</span>
		    </li>
		</c:if>
		<c:if test="${empty cart.itemList}">
		    <li class="nav-item">
			<span class="badge badge-danger">0</span>
		    </li>
		</c:if>
	    </c:if>
	    <li class="nav-item">
		<a class="nav-link" href="track-rental-order">Rental History</a>
	    </li>
	</c:if>
    </ul>
</div>
