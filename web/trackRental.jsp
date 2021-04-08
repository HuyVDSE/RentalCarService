<%-- 
    Document   : trackRental
    Created on : Feb 10, 2021, 2:29:21 PM
    Author     : BlankSpace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rental Order History</title>
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
	<link rel="stylesheet" href="assets/css/customize.css">
	<link rel="stylesheet" href="assets/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
	    <div class="text-center mt-4">
		<h3>Rental History</h3>
	    </div>
	    <div class="container">
		<!--search form-->
		<div class="row">
		    <div class="col-lg-12 my-4">
			<div class="card">
			    <h5 class="card-header text-center">Search</h5>
			    <div class="card-body">
			    <c:set var="searchValue" value="${param.txtSearchValue}"/>
			    <c:set var="fromDate" value="${param.txtFromDate}"/>
			    <c:set var="toDate" value="${param.txtToDate}"/>
			    <form action="search-order" class="form-group mx-2" method="POST">
				<div class="text-center my-2">
				    <span style="font-weight: bold">Car Name </span>
				</div>
				<input type="text" name="txtSearchValue" value="${searchValue}" class="form-control" placeholder="Enter Car Name..." />
				<div class="text-center my-2">
				    <span style="font-weight: bold">Rental Date </span>
				</div>
				<span style="font-weight: bold">From: </span><input type="date" class="form-control border-0" name="txtFromDate" value="${fromDate}">
				<span style="font-weight: bold">To: </span><input type="date" class="form-control border-0" name="txtToDate" value="${toDate}">
				<div class="text-center">
				    <input type="submit" value="Search" name="btAction" class="btn btn-success my-3" />
				</div>
			    </form>
			</div>

		    </div>

		</div>
	    </div>
	    <!--end search form-->

	    <c:set var="listOrder" value="${requestScope.ORDER_LIST}"/>
	    <c:set var="orderDetailMap" value="${requestScope.ORDER_DETAIL_MAP}"/>
	    <c:if test="${not empty listOrder}">
		<c:forEach var="order" items="${listOrder}">
		    <div class="row">
			<div class="col-lg-10 ml-auto">
			    <div>
				<span style="font-weight: bold">OrderID: </span>${order.orderId}
			    </div>
			    <div>
				<span style="font-weight: bold">UserID: </span>${order.userId}
			    </div>
			    <div>
				<span style="font-weight: bold">Status: </span>
				<c:if test="${order.status eq 'active'}">
				    <span style="bold; color: green">Active</span>
				</c:if>
				<c:if test="${order.status eq 'inactive'}">
				    <span style="bold; color: red">Inactive</span>
				</c:if>
			    </div>
			</div>
			<div class="col-lg-2 mr-0">
			    <form action="delete-order" method="POST">
				<input type="hidden" name="orderId" value="${order.orderId}" />
				<input type="hidden" name="txtSearchValue" value="${searchValue}" />
				<input type="hidden" name="txtFromDate" value="${fromDate}" />
				<input type="hidden" name="txtToDate" value="${toDate}" />
				<input type="submit" class="btn btn-danger" value="Delete" name="btAction" onclick="return confirm('Confirm delete this order?');"/>
			    </form>
			</div>
		    </div>

		    <table class="table text-center table-bordered mb-4">
			<thead class="thead-light">
			    <tr>
				<th scope="col">No.</th>
				<th scope="col">Car Name</th>
				<th scope="col">Quantity</th>
				<th scope="col">Price</th>
				<th scope="col">Total</th>
				<th scope="col">Action</th>
			    </tr>
			</thead>
			<tbody>
			    <c:set var="itemList" value="${orderDetailMap[order.orderId]}"/>
			    <c:forEach var="item" items="${itemList}" varStatus="counter">
				<tr class>
				    <td>
					${counter.count}
				    </td>
				    <td>
					<img src="${item.car.image}" alt="Car Image" width="150" class="img-fluid rounded shadow-sm"></br>
					${item.car.name}
				    </td>
				    <td>
					${item.quantity}
				    </td>
				    <td>
					$ ${item.car.price}/Day
				    </td>
				    <td>
					$ ${item.total}
				    </td>
				    <td>
					<a href="#fb${order.orderId}-${counter.count}" class="btn btn-primary" data-toggle="modal">Feedback</a>
				    </td>
				</tr>
				<!-- Feedback Modal HTML -->
			    <div id="fb${order.orderId}-${counter.count}" class="modal fade">
				<div class="modal-dialog">
				    <div class="modal-content">
					<form action="send-feedback" method="POST">
					    <div class="modal-header">						
						<h4 class="modal-title">Add Feedback</h4>
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					    </div>
					    <div class="modal-body">					
						<div class="form-group">
						    <label>Content</label>
						    <input name="txtContent" type="text" class="form-control" required>
						</div>
						<div class="form-group">
						    <label>Point</label>
						    <select name="cmbPoint" class="form-select" aria-label="Default select example">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
						    </select>
						</div>
					    </div>
					    <div class="modal-footer">
						<input type="hidden" name="orderId" value="${order.orderId}" />
						<input type="hidden" name="carId" value="${item.car.carID}" />
						<input type="hidden" name="txtSearchValue" value="${searchValue}" />
						<input type="hidden" name="txtFromDate" value="${fromDate}" />
						<input type="hidden" name="txtToDate" value="${toDate}" />
						<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
						<input type="submit" class="btn btn-success" value="Add">
					    </div>
					</form>
				    </div>
				</div>
			    </div>
			</c:forEach> 
			<tr class="alert-info text-right" style="font-weight: bold">
			    <td scope="row" colspan="5">Rental Date:</td>
			    <td class="text-center" scope="row">${order.rentalDate}</td>
			</tr>
			<tr class="alert-info text-right" style="font-weight: bold">
			    <td scope="row" colspan="5">Return Date:</td>
			    <td class="text-center" scope="row">${order.returnDate}</td>
			</tr>
			<tr class="alert-primary text-right" style="font-weight: bold">
			    <td scope="row" colspan="5">Cart Total:</td>
			    <td class="text-center" scope="row">$ ${order.totalAmount}</td>
			</tr>
			<tr class="alert-primary text-right" style="font-weight: bold">
			    <td scope="row" colspan="5">Total After Discount:</td>
			    <td class="text-center" scope="row">$ ${order.totalAfterDiscount}</td>
			</tr>
			</tbody>
		    </table>
		</c:forEach>
	    </c:if>
	    <c:if test="${empty listOrder}">
		<h4 class="text-center my-5">
		    No Rental Orders Found!!
		</h4>
	    </c:if>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
