<%-- 
    Document   : home
    Created on : Jan 23, 2021, 5:57:09 PM
    Author     : BlankSpace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Car Rental</title>
	<link rel="stylesheet" href="assets/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/css/customize.css">
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    </head>

    <body>
	<jsp:include page="menu.jsp"></jsp:include>
	    <!-- main body -->
	    <div class="container-fluid my-4">
		<div class="row">
		    <div class="col-sm-12 col-md-3 col-lg-3">
		    <c:set var="categoryList" value="${requestScope.CATEGORY_LIST}" />
		    <div class="card bg-light mb-3">
			<div class="card-header bg-primary text-white text-uppercase"><i class="fa fa-list"></i> Categories</div>
			<ul class="list-group category_block">
			    <c:forEach var="ca" items="${categoryList}">
				<li class="list-group-item text-white">
				    <a href="choose-category?cid=${ca.categoryID}">${ca.categoryName}</a>
				</li>
			    </c:forEach>
			</ul>
		    </div>

		    <div class="card">
			<div class="card-header bg-primary text-white text-uppercase">
			    <i class="fa fa-search"></i> Search Car
			</div>
			<c:set var="searchValue" value="${param.txtSearch}" />
			<c:set var="searchCategory" value="${param.cmbCategory}" />
			<c:set var="rentalDateValue" value="${param.txtRentalDate}" />
			<c:set var="returnDateValue" value="${param.txtReturnDate}" />
			<c:set var="amountValue" value="${param.txtAmount}" />

			<div class="card-body">
			    <form action="search-car" class="form-group" method="POST" onsubmit="return checkField();">
				<label>Name</label>
				<input id="searchValue" type="text" name="txtSearch" class="form-control" value="${searchValue}" placeholder="enter car name" />
				<label>Category</label>
				<select id="CategoryId" name="cmbCategory" class="form-control">
				    <option></option>
				    <c:forEach var="category" items="${categoryList}">
					<option value="${category.categoryID}"
						<c:if test="${category.categoryID eq searchCategory}">
						    selected="true"
						</c:if>>
					    ${category.categoryName}
					</option>
				    </c:forEach>
				</select>
				<label>Rental Date</label>
				<input id="rentalDate" type="date" class="form-control" name="txtRentalDate" value="${rentalDateValue}" />
				<label>Return Date</label>
				<input id="returnDate" type="date" class="form-control" name="txtReturnDate" value="${returnDateValue}" />
				<label>Amount</label>
				<input id="amount" type="number" class="form-control text-center" name="txtAmount" value="${amountValue}" min="1" step="1" />
				<div class="text-center">
				    <input type="submit" value="Search" name="btAction" class="btn btn-success my-3" />
				</div>
			    </form>
			</div>
		    </div>
		</div>
		<!-- end of search form-->

		<!--List of result-->
		<c:set var="result" value="${requestScope.LIST_CAR}" />
		<c:set var="totalPages" value="${requestScope.TOTAL_PAGES}" />
		<c:if test="${not empty result}">
		    <div class="col-sm-12 col-md-9 col-lg-9">
			<h3 class="text-center">Car</h3>
			<!--Display car-->
			<div class="row justify-content-center">
			    <c:forEach var="car" items="${result}">
				<div class="col-sm-12 col-md-5 col-lg-5 h-100 my-4">
				    <div class="card h-100 border border-dark">
					<h6 class="card-header">
					    ${car.name}
					</h6>
					<img src="${car.image}" class="card-img-top img" />
					<div class="card-footer">
					    <div class="text-center">
						<form action="view-detail" method="POST">
						    <input type="hidden" name="carId" value="${car.carID}" />
						    <input type="hidden" name="txtRentalDate" value="${rentalDateValue}" />
						    <input type="hidden" name="txtReturnDate" value="${returnDateValue}" />
						    <input type="submit" value="View Detail" name="btAction" class="btn btn-success" />
						</form>
					    </div>
					</div>
				    </div>
				</div>
			    </c:forEach>
			</div>

			<!-- get current page -->
			<c:set var="curPage" value="${requestScope.DEFAULT_TOTAL_PAGES}" />
			<c:if test="${empty curPage}">
			    <c:set var="curPage" value="${param.page}" />
			</c:if>
			<!-- Page indicator -->
			<nav>
			    <ul class="pagination justify-content-center mt-4">
				<c:forEach var="page" begin="1" end="${totalPages}" step="1">
				    <c:url var="urlPaging" value="search-car">
					<c:param name="txtSearch" value="${searchValue}"/>
					<c:param name="cmbCategory" value="${searchCategory}"/>
					<c:param name="txtRentalDate" value="${rentalDateValue}"/>
					<c:param name="txtReturnDate" value="${returnDateValue}"/>
					<c:param name="txtAmount" value="${amountValue}"/>
					<c:param name="page" value="${page}"/>
				    </c:url>
				    <c:if test="${curPage eq page}">
					<li class="page-item active">
					    <a class="page-link" href="${urlPaging}">${page}</a>
					</li>
				    </c:if>
				    <c:if test="${curPage ne page}">
					<li class="page-item">
					    <a class="page-link" href="${urlPaging}">${page}</a>
					</li>
				    </c:if>
				</c:forEach>
			    </ul>
			</nav>
		    </div>
		</c:if>

		<c:if test="${empty result}">
		    <h2 class="text-center">No Car found!!!</h2>
		</c:if>
	    </div>
	</div>
	<script>
	    function checkField() {
		var searchValue = document.getElementById("searchValue").value;
		var CategoryId = document.getElementById("CategoryId").value;
		var amount = document.getElementById("amount").value;
		var rentalDate = document.getElementById("rentalDate").value;
		var returnDate = document.getElementById("returnDate").value;
		if (searchValue === "" && CategoryId === "") {
		    alert("Please input name or choose category to search!!");
		    return false;
		}
		if (rentalDate === "") {
		    alert("Please choose RentalDate to search");
		    return false;
		}
		if (returnDate === "") {
		    alert("Please choose ReturnDate to search");
		    return false;
		}
		if (amount === "") {
		    alert("Please input amount to search!!");
		    return false;
		}

		var totalDays = calTotalDay(rentalDate, returnDate);
		if (totalDays <= 0) {
		    alert("Return date must greater than Rental date!!");
		    return false;
		}

		return true;
	    }

	    function calTotalDay(date1, date2) {
		dt1 = new Date(date1);
		dt2 = new Date(date2);
		var one_day = (1000 * 60 * 60 * 24);
		return Math.floor((Date.UTC(dt2.getFullYear(), dt2.getMonth(), dt2.getDate())
			- Date.UTC(dt1.getFullYear(), dt1.getMonth(), dt1.getDate())) / one_day);
	    }
	</script>
	<jsp:include page="footer.jsp"></jsp:include>
	<script src="assets/js/bootstrap.min.js"></script>
    </body>

</html>