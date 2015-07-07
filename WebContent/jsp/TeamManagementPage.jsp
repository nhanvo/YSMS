<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css"
	href="${pageContext.request.contextPath}/css/jquery-ui-1.8.16.custom.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/team.css">
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script language="javascript" type="text/javascript"
	src="${pageContext.request.contextPath}/js/Duang.js"></script>
<script language="javascript" type="text/javascript">
	$(function() {
		path="${pageContext.request.contextPath}";
		$("#menubtn_back")
				.click(
						function() {
							$('#ui-datepicker-div').remove();
							window.location.href = "${pageContext.request.contextPath}/signup.html";
						});

		$("#a_signuptoleague")
				.click(
						function() {
							$("#team_frame")
									.attr("src",
											"${pageContext.request.contextPath}/team/leagueteam.html?team_id=${teamId}");
						});
		
		$("#a_team_members")
				.click(
						function() {
							$("#team_frame")
									.attr("src",
											"${pageContext.request.contextPath}/team/configteam.html?team_id=${teamId}");
						});

		if("${signedup}" == true){}
		else{
			$("#a_team_members").click();
		}
					
	});
</script>
<title>运动员管理</title>
</head>
<body>
	<div class="operation ">
		<ul class="a_team">
			<li id="menubtn_back"><div>
					<img src="${pageContext.request.contextPath}/images/a_fanhui.png" />
				</div>
				<p>联赛列表</p></li>
			<!--点击返回联赛列表页-->
			<!-- 此处用于在已经报名的情况下依然显示 -->
			<!-- <c:if test="${signedup==false}">
				<li id="a_team_members"><div>
						<img src="${pageContext.request.contextPath}/images/list_coach.png" />
					</div>
					<p>人员安排</p></li>
			</c:if> -->
			<li id="a_team_members"><div>
						<img src="${pageContext.request.contextPath}/images/list_coach.png" />
					</div>
					<p>人员安排</p></li>
			<li id="a_signuptoleague" class="border_bo"><div>
					<img src="${pageContext.request.contextPath}/images/a_signup.png" />
				</div>
				<p>报名参赛</p></li>
		</ul>
	</div>
	<div></div>
	<iframe id="team_frame" class="main_content" style="padding-top:10px;"
		src="${pageContext.request.contextPath}/team/leagueteam.html?team_id=${teamId}">
	</iframe>
</body>
</html>