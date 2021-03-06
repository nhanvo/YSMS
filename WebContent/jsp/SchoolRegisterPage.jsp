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
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/dialog.css">
<link type="text/css"
	href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/school.css">
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/ds.dialog.js"></script>
<script language="javascript" type="text/javascript"
	src="${pageContext.request.contextPath}/js/Duang.js"></script>
<script type="text/javascript">
	var checkresult;
	$(function() {
		path="${pageContext.request.contextPath}";
		getJobsAndDistricts();
		$("#school_input").blur(function(){
			blur_schoolname();
			 
		});
		$("#username_input").blur(function(){
			blur_username();
		});
		/* $("#email_input").blur(function(){
			blur_email();
		}); */
		$("#btn_register").click(function(){
			checkresult = true;
			blur_schoolname();
			blur_username();
			//blur_email();
			if(checkresult){
				var school_name = $("#school_input").val();
				var user_name = $("#username_input").val();
				var email = $("#email_input").val();
				var category = $("#category_select")[0].selectedIndex + 1;
				var district_id=$("#district_select").val();
				var school_address=$("#address_input").val();
				var school_contacts=$("#contacts_input").val();
				var school_mobile=$("#mobile_input").val();
				var school_fax=$("#fax_input").val();
				$.ajax({
					type : 'POST',
					url : "${pageContext.request.contextPath}/schoolmanagement/addschool.html",
					data : {
						school_name : school_name,
						user_name : user_name,
						email : email,
						category : category,
						district_id:district_id,
						school_address:school_address,
						school_contacts:school_contacts,
						school_mobile:school_mobile,
						school_fax:school_fax
					},
					dataType : "json",
					success : function(data) {
						if(data.success){
							ds.dialog({
								title : '消息提示',
								content : "学校开户成功，初始密码为123456，请尽快修改密码！",
								onyes : function() {
									window.location.href=window.location.href;
								},
								icon : "../../images/socceralert.png",
							});
							
						}
						else{
							ds.dialog({
								title : '消息提示',
								content : "开户失败！",
								onyes : true,
								icon : "../../images/info.png"
							});
						}
					},
					error : function() {
						ds.dialog({
							title : '消息提示',
							content : "开户失败！请检查网络连接！",
							onyes : true,
							icon : "../../images/info.png"
						});
					}
				});
			}
			else{
				ds.dialog({
					title : '消息提示',
					content : "信息填写有误！",
					onyes : true,
					icon : "../../images/info.png"
				});
			}
		})
	})
	function blur_schoolname(){
		var school_name = $("#school_input").val();
		if(school_name.indexOf(" ")!=-1){
			$("#schoolname_check").css("visibility","visible");
			checkresult = false;
			ds.dialog({
				title : '消息提示',
				content : "学校名中不得包含空格！",
				onyes : true,
				icon : "../../images/info.png"
			});

		}
		if(school_name!=null&&school_name!=""){
			$("#schoolname_check").css("visibility","hidden");
			$.ajax({
				type : 'POST',
				url : "${pageContext.request.contextPath}/schoolmanagement/pinyinusername.html",
				data : {
					school_name : school_name
				},
				dataType : "json",
				success : function(data) {
					$("#username_check").css("visibility","hidden");
					$("#username_input").val(data.pinyin_username);
				},
				error : function() {
					checkresult = false;
				}
			});
		}
		else{
			$("#schoolname_check").css("visibility","visible");
			checkresult = false;
		}
	}
	function blur_username(){
		var user_name = $("#username_input").val();
		if(user_name!=null&&user_name!=""){
			$("#username_check").css("visibility","hidden");
			$.ajax({
				type : 'POST',
				url : "${pageContext.request.contextPath}/schoolmanagement/checkusername.html",
				data : {
					user_name : user_name
				},
				dataType : "json",
				success : function(data) {
					if(data.success){
						$("#username_check").css("visibility","hidden");
					}
					else{
						$("#username_check").css("visibility","visible");
						checkresult = false;
					}
				},
				error : function() {
					checkresult = false;
				}
			});
		}
		else{
			$("#username_check").css("visibility","visible");
			checkresult = false;
		}
	}
	function blur_email(){
		var email = $("#email_input").val();
		if(email!=null&&email!=""){
			var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
			if (!reg.test(email)) {
				checkresult = false;
				$("#email_check").css("visibility","visible");
			}
			else{
				$("#email_check").css("visibility","hidden");
			}
		}
		else{
			checkresult = false;
			$("#email_check").css("visibility","visible");
		}
	}
	
	
	 function getJobsAndDistricts(){
		 $.ajax({
		        async: false,
		        cache: false,
		        type: 'POST',
		        dataType: "json",
		        url: "${pageContext.request.contextPath}/judgemanagement/getjobsanddistrict.html",
					//请求的action路径
					error : function() { //请求失败处理函数
						ds.dialog({
							title : '消息提示',
							content : "请求失败，请联系管理员！",
							onyes : true,
							icon : "../../images/info.png"
						});
					},
					success : function(data) { //请求成功后处理函数。  
						 if(data!=null){
						 
							 $("#district_select").empty();
						 
							 for(var j=0;j<data.district.length;j++){
								 $("#district_select").append("<option value='"+data.district[j].districtId+"'>"+data.district[j].districtName+"</option>")
							 } 
						 }
					}
				})
	 }
</script>
<title>学校开户</title>
</head>
<body>
	<!--创建学校-->
	<div class="neirong">
		<table id="register_table" cellpadding="0" cellspacing="0" style="margin-top:40px;">
			<tbody>
				<tr>
					<td>学校名称(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="school_input" type="text" class="input_text">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td><img id="schoolname_check" style="visibility: hidden;"
						src="${pageContext.request.contextPath}/images/error_info.png" />
					</td>
				</tr>
				<tr>
					<td width="110px">所属区域(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select id="district_select">
										 
									</select>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div>
					</td>
				</tr>
				
				<tr>
					<td>用户名(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="username_input" type="text" class="input_text">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td><img id="username_check" style="visibility: hidden;"
						src="${pageContext.request.contextPath}/images/error_info.png" />
					</td>
				</tr>
				<tr>
					<td>联系人：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="contacts_input" type="text" class="input_text">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td><img id="schoolcontacts_check" style="visibility: hidden;"
						src="${pageContext.request.contextPath}/images/error_info.png" />
					</td>
				</tr>
				<tr>
					<td>电话：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="mobile_input" type="text" class="input_text">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td><img id="schoolmobile_check" style="visibility: hidden;"
						src="${pageContext.request.contextPath}/images/error_info.png" />
					</td>
				</tr>
				<tr>
					<td>传真：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="fax_input" type="text" class="input_text">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td><img id="schoolfax_check" style="visibility: hidden;"
						src="${pageContext.request.contextPath}/images/error_info.png" />
					</td>
				</tr>
				<tr>
					<td>邮箱：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="email_input" type="text" class="input_text">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td><img id="email_check" style="visibility: hidden;"
						src="${pageContext.request.contextPath}/images/error_info.png" />
					</td>
				</tr>
				<tr>
					<td width="110px">分类(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select id="category_select">
										<option value="1">小学</option>
										<option value="2">初中</option>
										<option value="3">高中</option>
									</select>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td>地址：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="address_input" type="text" class="input_text">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td><img id="schooladdress_check" style="visibility: hidden;"
						src="${pageContext.request.contextPath}/images/error_info.png" />
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<div id="btn_register" class="btn_wk">
							<div class="btn_l btn_l_a_green"></div>
							<div class="btn_m btn_m_a_green">
								<input type="button" class="input_btn"
									style="background: none" value="学校开户">
							</div>
							<div class="btn_r btn_r_a_green"></div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!--创建学校 END-->
</body>
</html>