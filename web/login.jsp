<%-- 
    Document   : login
    Created on : Jan 24, 2021, 9:07:04 AM
    Author     : BlankSpace
--%>

    <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <!DOCTYPE html>
            <html>

            <head>
                <title>Login</title>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="assets/css/bootstrap.min.css">
                <link rel="stylesheet" href="assets/css/customize.css">
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
                <!-- reCAPTCHA script with English language -->
                <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
            </head>

            <body>
                <div class="container-fluid h-100 bg-custom">
                    <div class="row h-100 justify-content-center align-items-center">
                        <div class="card col-10 col-md-8 col-lg-4 p-5">
                            <h1 class="text-center">Sign in</h1>
                            <form action="login" method="POST" class="form-group">
                                <label>Email</label>
                                <input type="text" name="txtEmail" value="${param.txtEmail}" class="form-control" required/><br/>
                                <label>Password</label>
                                <input type="password" name="txtPassword" value="" class="form-control" required><br/>

                                <div class="text-center">
                                    <!-- reCAPTCHA -->
                                    <div class="g-recaptcha" data-sitekey="6Lfo_zgaAAAAAKC7qTVHEXJffrsN5tOo7w0Ntere"></div>
                                    <div class="text-danger text-bold">${requestScope.CAPTCHA_ERROR}</div>
                                    </br>
                                    <!--login button-->
                                    <input type="submit" value="Login" name="btAction" class="btn btn-primary px-5" />
                                </div>
                            </form>
                            <a href="home">Back to Home</a>
                            <a href="create-account-page">Create an Account</a>
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
                                            <c:if test="${not empty errors.captchaError}">
                                                <h5>${errors.captchaError}</h5>
                                            </c:if>
                                            <c:if test="${not empty errors.invalidUserError}">
                                                <h5>${errors.invalidUserError}</h5>
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
                                $(document).ready(function() {
                                    $("#errorModal").modal();
                                });
                            </script>
                        </c:if>

                    </div>
                </div>
            </body>

            </html>