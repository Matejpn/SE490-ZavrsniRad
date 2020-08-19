<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>

<body style="text-align:center">
<div class="bordered" style=" width: 350px;
            height: 350px;
            padding: 30px;
            border: 1px solid #91917f;
            border-radius: 8px;
            color: black;">
    <img id="donation" src="https://visualmodo.com/wp-content/uploads/2018/05/Build-It-Beautiful-with-The-Best-Shopify-Themes.jpg"
         width="170" height="100">
    <br/>
    <br/>
    <p>Hello ${name},</p>
    <p style="color: black">You have successfully completed registration!</p>
    <p>Please click on the button to verify your email.</p>
    <br>
    <a th:href="">
        <a href="http://localhost:4200/#/verify/${token}"><button style=" background-color: #abcd62;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;">Confirm!
            </button></a>
    </a>
</div>
</body>

</html>