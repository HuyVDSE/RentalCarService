<%-- 
    Document   : car-detail
    Created on : Feb 6, 2021, 7:12:13 PM
    Author     : BlankSpace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Detail</title>
	<link rel="stylesheet" href="assets/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/css/customize.css">
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
	<style>
	    .img-big-wrap img {
		height: auto;
		width: 250px;
		display: inline-block;
		cursor: zoom-in;
	    }

	    .img-small-wrap .item-gallery {
		width: 60px;
		height: 60px;
		border: 1px solid #ddd;
		margin: 7px 2px;
		display: inline-block;
		overflow: hidden;
	    }

	    .img-small-wrap {
		text-align: center;
	    }

	    .img-small-wrap img {
		max-width: 100%;
		max-height: 100%;
		object-fit: cover;
		border-radius: 4px;
		cursor: zoom-in;
	    }

	    .img-big-wrap img {
		width: 100% !important;
		height: auto !important;
	    }
	</style>
    </head>

    <body>
	<jsp:include page="menu.jsp"></jsp:include>
	    <!-- main body -->
	    <div class="container-fluid my-4">
		<div class="row">
		    <div class="col-sm-12 col-md-3 col-lg-3">
		    <c:set var="categoryList" value="${requestScope.CATEGORY_LIST}" />
		    <div class="card bg-light mb-3">
			<div class="card-header bg-primary text-white text-uppercase">
			    <i class="fa fa-list"></i> Categories
			</div>
			<ul class="list-group category_block">
			    <c:forEach var="ca" items="${categoryList}">
				<li class="list-group-item text-white">
				    <a href="choose-category?cid=${ca.categoryID}">${ca.categoryName}</a>
				</li>
			    </c:forEach>
			</ul>
		    </div>
		</div>
		<!-- end of category card-->

		<!--detail part-->


		<div class="col-sm-12 col-md-9 col-lg-9">
		    <div class="container">
			<c:set var="item" value="${requestScope.CAR}" />
			<div class="card">
			    <div class="card-header">
				<h3>Information</h3>
			    </div>
			    <div class="row">
				<aside class="col-sm-5">
				    <img src="${item.image}" class="img-fluid rounded shadow-sm">
				</aside>
				<aside class="col-sm-7">
				    <article class="card-body p-5">
					<h3 class="tilte mb-3">${item.name}</h3>

					<p class="price-detail-wrap">
					    <span class="h3 text-danger"> 
						<span class="currency">Price: $</span><span class="num">${item.price}/Day</span>
					    </span>
					</p>

					<div>
					    <span style="font-weight: bold">Category: </span> ${item.categoryID}
					</div>
					<div>
					    <span style="font-weight: bold">Year: </span> ${item.year}
					</div>
					<div>
					    <span style="font-weight: bold">Color: </span> ${item.color}
					</div>
					<div>
					    <span style="font-weight: bold">Quantity: </span> ${item.quantity}
					</div>
					<dl class="item-property">
					    <dt>Description:</dt>
					    <dd>
						<p>${item.description} </p>
					    </dd>
					</dl>
					<a href="rental-car?carID=${item.carID}" class="btn btn-lg btn-outline-primary text-uppercase"> <i class="fa fa-shopping-cart"></i> Add to cart </a>
				    </article>
				</aside>
			    </div>
			</div>

			<h3>Feedback</h3>
			<c:set var="feedbackList" value="${requestScope.FEEDBACK_LIST}"/>
			<c:if test="${not empty feedbackList}">
			    <c:forEach var="feedback" items="${feedbackList}">
				<div class="card my-4">
				    <div class="card-header">
					<span style="font-weight: bold">Customer: </span> ${feedback.username}</br>
				    </div>
				    <div class="card-body">
					<span style="font-weight: bold">Content: </span>${feedback.content}</br>
					<span style="font-weight: bold">Point: </span> ${feedback.point}/10
				    </div>
				</div>
			    </c:forEach>
			</c:if>
			<c:if test="${empty feedbackList}">
			    <h5>No comment</h5>
			</c:if>
		    </div>
		</div>
	    </div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script src="assets/js/bootstrap.min.js"></script>
    </body>

</html>