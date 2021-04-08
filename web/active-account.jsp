<%-- 
    Document   : active-account
    Created on : Jan 25, 2021, 10:46:46 PM
    Author     : BlankSpace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Active Account</title>
	<link rel="stylesheet" href="assets/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/css/customize.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>

    <body>
	<!--nav bar-->
	<div class="navbar navbar-dark navbar-expand-sm bg-dark">
	    <ul class="navbar-nav mr-0 text-center">
		<li class="nav-item">
		    <a class="nav-link active" href="home">Home</a>
		</li>
	    </ul>
	    <ul class="navbar-nav ml-auto">
		<li class="nav-item">
		    <a href="logout" class="btn btn-secondary">Logout</a>
		</li>
	    </ul>
	</div>

	<c:set var="account" value="${sessionScope.ACCOUNT}" />
	<div class="container h-100 text-center">
	    <div class="row h-100 justify-content-center align-items-center">
		<div class="col-8 col-md-6 col-lg-4 text-center">

		    <h1>Active Account</h1>
		    <!--confirm form-->
		    <form action="active-acount" method="POST">
			<label>Enter 6 digits send to your email (${account.email})</label><br>
			<input type="text" name="txtConfirmCode" value="" class="form-control" />
			<input type="submit" value="Active" class="btn btn-primary my-2" name="btAction" />
		    </form>

		    <!--resend active code-->
		    <a href="sendActivationCode">Resend activation code</a>
		</div>

	    </div>
	</div>



	<c:set var="errors" value="${requestScope.ERRORS}" />
	<c:if test="${not empty errors}">
	    <!-- The Modal -->
	    <div class="modal fade" id="errorModal">
		<div class="modal-dialog">
		    <div class="modal-content">

			<!-- Modal Header -->
			<div class="modal-header">
			    <img src="assets/images/warning.png" alt="warning" width="40" height="40"><br/>
			    <h4 class="modal-title">Warning</h4>
			    <button type="button" class="close" data-dismiss="modal">×</button>
			</div>

			<!-- Modal body -->
			<div class="modal-body">
			    <c:if test="${not empty errors.activeCodeError}">
				<p class="text-danger">${errors.activeCodeError}</p>
			    </c:if>
			</div>

			<!-- Modal footer -->
			<div class="modal-footer">
			    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
			</div>

		    </div>
		</div>
	    </div>

	    <script>
		$(document).ready(function () {
		    $("#errorModal").modal();
		});
	    </script>
	</c:if>
	<script src="assets/js/bootstrap.min.js"></script>
    </body>

</html>