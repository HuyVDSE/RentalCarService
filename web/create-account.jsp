<%-- 
    Document   : create-account
    Created on : Jan 24, 2021, 9:44:28 AM
    Author     : BlankSpace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up</title>
	<link rel="stylesheet" href="assets/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/css/customize.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
	<c:set var="errors" value="${requestScope.CREATE_ERRORS}"/>
	<div class="container-fluid h-100 bg-custom">
	    <div class="row h-100 justify-content-center align-items-center">
		<div class="card p-5 col-10 col-md-8 col-lg-6">
		    <h1 class="text-center">Sign up</h1>
		    <form action="create-account" method="POST">
			<div class ="row">
			    <div class="col-6">
				<div class="form-group">
				    <label>Email</label>
				    <input type="text" name="txtEmail" value="${param.txtEmail}" class="form-control" placeholder="example@gmail.com" required/>

				    <label>Password</label>
				    <input type="password" name="txtPassword" value="" class="form-control" placeholder="3-50 chars" required/>

				    <label>Confirm Password</label>
				    <input type="password" name="txtConfirm" value="" class="form-control"/>
				</div>
			    </div>

			    <div class="col-6">
				<div class="form-group">
				    <label>Full name</label>
				    <input type="text" name="txtFullname" value="${param.txtFullname}" class="form-control" placeholder="6-50 chars" required/>

				    <label>Phone</label>
				    <input type="text" name="txtPhone" value="${param.txtPhone}" class="form-control" placeholder="6 to 20 digits" required/>

				    <label>Address</label>
				    <input type="text" name="txtAddress" value="${param.txtAddress}" class="form-control"/>
				</div>
			    </div>
			</div>
			<div class="text-center">
			    <input type="submit" value="Create Account" name="btAction" class="btn btn-primary px-5 my-3"/> <br/>
			</div>
		    </form>

		    <c:set var="errors" value="${requestScope.ERRORS}"/>
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
					<c:if test="${not empty errors.emailExisted}">
					    <p class="text-danger">${errors.emailExisted}</p>
					</c:if>
					<c:if test="${not empty errors.phoneLengthError}">
					    <p class="text-danger">${errors.phoneLengthError}</p>
					</c:if>
					<c:if test="${not empty errors.phoneFormatError}">
					    <p class="text-danger">${errors.phoneFormatError}</p>
					</c:if>  
					<c:if test="${not empty errors.passwordLengthError}">
					    <p class="text-danger">${errors.passwordLengthError}</p>
					</c:if>
					<c:if test="${not empty errors.nameLengthError}">
					    <p class="text-danger">${errors.nameLengthError}</p>
					</c:if>
					<c:if test="${not empty errors.addressLengthError}">
					    <p class="text-danger">${errors.addressLengthError}</p>
					</c:if>
					<c:if test="${not empty errors.passwordConfirmNotMatched}">
					    <p class="text-danger">${errors.passwordConfirmNotMatched}</p>
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

		    <c:set var="success" value="${requestScope.SUCCESS}"/>
		    <c:if test="${success}">
			<!-- The Modal -->
			<div class="modal fade" id="successModal">
			    <div class="modal-dialog">
				<div class="modal-content">

				    <!-- Modal Header -->
				    <div class="modal-header">
					<img src="assets/images/success.png" alt="success" width="40" height="40"><br/>
					<h4 class="modal-title">Created account successfully!</h4>
					<button type="button" class="close" data-dismiss="modal">×</button>
				    </div>

				    <!-- Modal body -->
				    <div class="modal-body">
					<h5>Please sign in to activate account!</h5>
				    </div>

				    <!-- Modal footer -->
				    <div class="modal-footer">
					<a href="login-page" class="btn btn-success">Sign in now!</a> <br/>
				    </div>

				</div>
			    </div>
			</div>
			<script>
			    $(document).ready(function () {
				$("#successModal").modal();
			    });
			</script>
		    </c:if>
		    <div class="text-center">
			Already have account? 
			<a href="login-page">Sign in</a>
		    </div>
		</div>
	    </div>
	    <script src="assets/js/bootstrap.min.js"></script>
	</form>
</body>
</html>
