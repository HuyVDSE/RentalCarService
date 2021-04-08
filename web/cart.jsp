<%-- 
    Document   : cart
    Created on : Feb 7, 2021, 1:59:46 PM
    Author     : BlankSpace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
	<link rel="stylesheet" href="assets/css/customize.css">
	<link rel="stylesheet" href="assets/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
	<c:set var="cart" value="${requestScope.CUS_CART}"/>
	<c:if test="${not empty cart}">
	    <div class="text-center mt-4">
		<h3>Cart</h3>
	    </div>
	    <div class="shopping-cart">
		<div class="px-4 px-lg-0">
		    <div class="pb-5">
			<div class="container">
			    <div class="row">
				<div class="col-lg-12 p-5 bg-white rounded shadow-sm mb-5">

				    <!-- Shopping cart table -->
				    <div class="table-responsive">
					<table class="table">
					    <thead>
						<tr>
						    <th scope="col" class="border-0 bg-light">
							<div class="p-2 px-3 text-uppercase">Car Name</div>
						    </th>
						    <th scope="col" class="border-0 bg-light">
							<div class="py-2 text-uppercase">Car Type</div>
						    </th>
						    <th scope="col" class="border-0 bg-light text-center">
							<div class="py-2 text-uppercase">Amount</div>
						    </th>
						    <th scope="col" class="border-0 bg-light">
							<div class="py-2 text-uppercase">Price</div>
						    </th>
						    <th scope="col" class="border-0 bg-light">
							<div class="py-2 text-uppercase">Total</div>
						    </th>
						    <th scope="col" class="border-0 bg-light">
							<div class="py-2 text-uppercase">Delete</div>
						    </th>
						    <th scope="col" class="border-0 bg-light">
							<div class="py-2 text-uppercase">Update</div>
						    </th>
						</tr>
					    </thead>
					    <tbody>
						<c:forEach items="${cart.itemList}" var="item">
						<form action="update-item-quantity" method="POST">
						    <tr>
							<td class="align-middle">
							    <img src="${item.car.image}" alt="" width="150" class="img-fluid rounded shadow-sm"></br>
							    <strong>${item.car.name}</strong>
							</td>
							<td class="align-middle"><strong>${item.car.categoryID}</strong></td>
							<td class="align-middle">
							    <input type="number" class="text-center" name="txtQuantity" value="${item.quantity}" min="1" step="1"/>
							</td>
							<td class="align-middle"><strong>$ ${item.car.price}/Day</strong></td>
							<td class="align-middle"><strong>$ ${item.total}</strong></td>
							<td class="align-middle">
							    <a href="#delete${item.car.carID}" class="delete" data-toggle="modal">
								<button type="button" class="btn btn-danger">Delete</button>
							    </a>
							</td>
							<td class="align-middle">
							    <input type="hidden" name="carId" value="${item.car.carID}" />
							    <button type="summit" class="btn btn-success">Update</button>
							</td>
						    </tr>
						</form>
					    </c:forEach>
					    </tbody>
					</table>
				    </div>
				    <!-- End -->
				</div>
			    </div>

			    <c:set var="errors" value="${requestScope.ERRORS}"/>
			    <div class="row py-5 p-4 bg-white rounded shadow-sm">
				<div class="col-lg-6">
				    <div class="bg-light rounded-pill px-4 py-3 text-uppercase font-weight-bold text-center">Detail</div>
				    <div class="p-4">
					<c:set var="rentalDate" value="${cart.rentalDate}"/>
					<c:set var="returnDate" value="${cart.returnDate}"/>
					<form action="calculate-total-payment" method="POST">
					    <label><strong>Rental date</strong></label>
					    <div class="input-group mb-4 border rounded-pill p-2">
						<input id="rentalDate" type="date" class="form-control border-0" name="rentalDate" value="${rentalDate}">
					    </div>
					    <label><strong>Return date</strong></label>
					    <div class="input-group mb-4 border rounded-pill p-2">
						<input id="returnDate" type="date" class="form-control border-0" name="returnDate" value="${returnDate}">
					    </div>
					    <label><strong>Voucher</strong></label>
					    <div class="input-group mb-4 border rounded-pill p-2">
						<input type="text" name="discountCode" value="" placeholder="Enter Voucher" aria-describedby="button-addon3" class="form-control border-0">
					    </div>
					    <div class="input-group-append border-0 justify-content-center">
						<button id="button-addon3" type="submit" class="btn btn-dark px-4 rounded-pill">Apply</button>
					    </div>
					</form>
				    </div>
				</div>
				<div class="col-lg-6">
				    <c:set var="total" value="${requestScope.TOTAL_PRICE}"/>
				    <c:set var="totalAfterDiscount" value="${requestScope.PRICE_AFTER_DISCOUNT}"/>
				    <div class="bg-light rounded-pill px-4 py-3 text-uppercase font-weight-bold text-center">Payment</div>
				    <div class="p-4">
					<ul class="list-unstyled mb-4">
					    <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Total</strong><strong>$ ${total}</strong></li>
					    <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Apply Discount</strong><strong>$ ${total - totalAfterDiscount}</strong></li>
					    <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Total Payment</strong>
						<h5 class="font-weight-bold">$ ${totalAfterDiscount}</h5>
					    </li>
					</ul>

					<c:if test="${empty errors and total > 0}">
					    <form action="confirm-cart" method="POST">
						<input type="submit" class="btn btn-dark rounded-pill py-2 btn-block" value="Confirm" name="btAction" />
					    </form>
					</c:if>

				    </div>
				</div>
			    </div>

			</div>
		    </div>
		</div>
	    </div>

	    <c:forEach var="item" items="${cart.itemList}">
		<!-- Delete Modal HTML -->
		<div id="delete${item.car.carID}" class="modal fade">
		    <div class="modal-dialog">
			<div class="modal-content">
			    <form action="delete-item" method="POST">
				<div class="modal-header">						
				    <h4 class="modal-title">Delete Item</h4>
				    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">					
				    <p>Are you sure you want to delete <strong>${item.car.name}</strong> from your cart?</p>
				    <p class="text-danger"><small>This action cannot be undone.</small></p>
				</div>
				<div class="modal-footer">
				    <input type="hidden" name="carId" value="${item.car.carID}" />
				    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
				    <input type="submit" class="btn btn-danger" value="Enter">
				</div>
			    </form>
			</div>
		    </div>
		</div>
	    </c:forEach>

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
				<c:if test="${not empty errors.inputRentalDateErr}">
				    <p class="text-danger">${errors.inputRentalDateErr}</p>
				</c:if>	
				<c:if test="${not empty errors.inputReturnDateErr}">
				    <p class="text-danger">${errors.inputReturnDateErr}</p>
				</c:if>	
				<c:if test="${not empty errors.rentalDateErr}">
				    <p class="text-danger">${errors.rentalDateErr}</p>
				</c:if>	
				<c:if test="${not empty errors.returnDateErr}">
				    <p class="text-danger">${errors.returnDateErr}</p>
				</c:if>	
				<c:if test="${not empty errors.notValidDiscountCodeErr}">
				    <p class="text-danger">${errors.notValidDiscountCodeErr}</p>
				</c:if>	
				<c:if test="${not empty errors.expiredDiscountCodeErr}">
				    <p class="text-danger">${errors.expiredDiscountCodeErr}</p>
				</c:if>	
				<c:if test="${not empty errors.quantityErr}">
				    <p class="text-danger">${errors.quantityErr}</p>
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

	</c:if>

	<c:if test="${empty cart}">
	    <h4 class="text-center my-5">
		Your cart is empty!!
	    </h4>
	</c:if>
	<jsp:include page="footer.jsp"></jsp:include>

	<!--	<script>
		    document.getElementById('rentalDate').valueAsDate = new Date();
		</script>-->
    </body>
</html>
