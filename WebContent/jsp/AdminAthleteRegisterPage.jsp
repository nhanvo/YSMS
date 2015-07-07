<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css"
	href="${pageContext.request.contextPath}/css/jquery-ui-1.8.16.custom.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.request.contextPath}/css/dialog.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/athlete.css">

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script language="javascript" type="text/javascript"
	src="${pageContext.request.contextPath}/js/Duang.js"></script>
<script language="javascript" type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ui.datepicker-zh-CN.js"></script>
<script language="javascript" type="text/javascript"
	src="${pageContext.request.contextPath}/js/ds.dialog.js"></script>
<head>
<title>运动员注册</title>

<style type="text/css">
td.title1 {
	width: 15%;
	text-align: center;
}

td.textbox1 {
	width: 35%;
	text-align: left;
}
#shoesize{
	line-height:35px; 
	position:relative;
}
#shoesize img{
	position: absolute;
	top:10px;
	left:-225px;
	z-index:1000;
	width:450px;
	display:none;
}
</style>
<script language="javascript" type="text/javascript">
	$(function() {
		path="${pageContext.request.contextPath}";
		var regex = /^[0-9]*[1-9][0-9]*$/;  //正整数
		$("#CardReader1").css("visibility","hidden");
		$("#birthday_input").datepicker();
		$("#birthday_input").datepicker('option', {
			dateFormat : 'yy年mm月dd日'
		});
		$('#shoesize').hover(function(){$(this).find('img').show()},function(){$(this).find('img').hide()})
		$("#default_photo_img").css("position","absolute");
		$("#default_photo_img").css("left",$("#CardReader1").offset().left);
		$("#default_photo_img").css("top","5px");
		$("#button_reset").click(function() {
			$("input").val("");
			$(".select2 option").eq(0).attr('selected', 'true');
			$("#CardReader1").css("visibility", "hidden");
			$("#default_photo_img").css("visibility", "visible");
			$("#default_photo_img").attr("src", "../images/defaultuser.png")
		});
		$("#button_register")
				.click(
						function() {
							var name = $("#name_input").val();
							if (name == "") {
								ds.dialog({
									title : '消息提示',
									content : "姓名不能为空！",
									onyes : true,
									icon : "../../images/info.png"
								});
								return false;
							}
							var gender = $("#gender_input").val();
							if (gender == "男") {
								gender = "1";
							} else if (gender == "女") {
								gender = "0";
							} else {
								ds.dialog({
									title : '消息提示',
									content : "性别格式不符！",
									onyes : true,
									icon : "../../images/info.png"
								});
								return false;
							}
							var nation = $("#nation_input").val();
							if (nation == "") {
								ds.dialog({
									title : '消息提示',
									content : "民族不能为空！",
									onyes : true,
									icon : "../../images/info.png"
								});
								return false;
							}
							var birthday = $("#birthday_input").val();
							if (birthday == "") {
								ds.dialog({
									title : '消息提示',
									content : "生日不能为空！",
									onyes : true,
									icon : "../../images/info.png"
								});
								return false;
							}
							var identity = $("#identity_input").val();
							if (identity == "" || identity.length != 18) {
								ds.dialog({
									title : '消息提示',
									content : "身份证格式不符！",
									onyes : true,
									icon : "../../images/info.png"
								});
								return false;
							}							
							var address = $("#address_input").val();
							var image = $("#image_input").val();
							var studentid = $("#studentid_input").val();
							if(studentid == ""){
								ds.dialog({
									title : '消息提示',
									content : "学籍号不能为空！",
									onyes : true,
									icon : "../../images/info.png"
								});
								return false;
							}
							var schoolusername = $("#schoolusername_input").val();
							if(schoolusername == ""){
								ds.dialog({
									title : '消息提示',
									content : "学校用户名不能为空！",
									onyes : true,
									icon : "../../images/info.png"
								});
								return false;
							}
							var schoolid = 0
							$.ajax({
								type : 'POST',
								url : "${pageContext.request.contextPath}/athletemanagement/getschoolid.html",
								data : {
									schoolusername : schoolusername
								},
								dataType : "json",
								success : function(data) {
									if (data.schoolid == 0) {
										//不存在该学校用户
										ds.dialog({
											title : '消息提示',
											content : "不存在该学校用户,请检查用户名",
											onyes : true,
											icon : "../../images/info.png"
										});
										return false;
									}
									else{
										schoolid = data.schoolid;
										var grade_index = $('#grade_select')[0].selectedIndex + 1;//index+1，直接用当前年相减即可得入学年
										var phonenum = $("#phonenum_input").val();
										var position_index = $('#position_select')[0].selectedIndex + 1;//适配数据库
										var shoesize_index = $('#shoesize_select').val();
										var height = $("#height_input").val();
										var weight = $("#weight_input").val();
										if(height == null || height == ""){//废话代码，因为可能后面要修改，by macong
											ds.dialog({
												title : '消息提示',
												content : "必须填写身高！！",
												onyes : true,
												icon : "../../images/info.png"
											});
											return false;
										} 
										if(weight == null || weight == ""){//废话代码，因为可能后面要修改，by macong
											ds.dialog({
												title : '消息提示',
												content : "必须填写体重！！",
												onyes : true,
												icon : "../../images/info.png"
											});
											return false;
										} 
											
										if(height != null && height != ""){
											if(!regex.test(height)){
												ds.dialog({
													title : '消息提示',
													content : "身高格式不符！",
													onyes : true,
													icon : "../../images/info.png"
												});
												return false;
											}
										}
										if(weight != null && weight != ""){
											if(!regex.test(weight)){
												ds.dialog({
													title : '消息提示',
													content : "体重格式不符！",
													onyes : true,
													icon : "../../images/info.png"
												});
												return false;
											}
										}
										
										var guardian1_name = $("#guardian1name_input").val();
										if (guardian1_name == null || guardian1_name == "") {
											ds.dialog({
												title : '消息提示',
												content : "监护人1姓名不能为空！",
												onyes : true,
												icon : "../../images/info.png"
											});
											return false;
										}
										var guardian1_diploma = $("#guardian1diploma_select")[0].selectedIndex + 1;
										if (guardian1_diploma == null || guardian1_diploma == "") {
											ds.dialog({
												title : '消息提示',
												content : "监护人1学历不能为空！",
												onyes : true,
												icon : "../../images/info.png"
											});
											return false;
										}
										var guardian1_job = $("#guardian1job_select")[0].selectedIndex + 1;
										if (guardian1_job == null || guardian1_job == "") {
											ds.dialog({
												title : '消息提示',
												content : "监护人1职业不能为空！",
												onyes : true,
												icon : "../../images/info.png"
											});
											return false;
										}
										var guardian1_phone = $("#guardian1phone_input").val();
										if (guardian1_phone == null || guardian1_phone == "") {
											ds.dialog({
												title : '消息提示',
												content : "监护人1联系电话不能为空！",
												onyes : true,
												icon : "../../images/info.png"
											});
											return false;
										}
										var guardian2_name = $("#guardian2name_input").val();
										var guardian2_diploma = $("#guardian2diploma_select")[0].selectedIndex + 1;
										var guardian2_job = $("#guardian2job_select")[0].selectedIndex + 1;
										var guardian2_phone = $("#guardian2phone_input").val();
										var coach_name = $("#coachname_input").val();
										$.ajax({
											type : 'POST',
											url : "${pageContext.request.contextPath}/athletemanagement/adminaddathlete.html",
											data : {
												name : name,
												gender : gender,
												nation : nation,
												birthday : birthday,
												identity : identity,
												address : address,
												studentid : studentid,
												grade_index : grade_index,
												phonenum : phonenum,
												position_index : position_index,
												shoesize_index : shoesize_index,
												height : height,
												weight : weight,
												guardian1_name : guardian1_name,
												guardian1_diploma : guardian1_diploma,
												guardian1_job : guardian1_job,
												guardian1_phone : guardian1_phone,
												guardian2_name : guardian2_name,
												guardian2_diploma : guardian2_diploma,
												guardian2_job : guardian2_job,
												guardian2_phone : guardian2_phone,
												coach_name : coach_name,
												image : image,
												schoolid : schoolid
											},
											dataType : "json",
											success : function(data) {
												if (data.success) {
													// 加载联赛列表
													ds.dialog({
														title : '消息提示',
														content : "运动员已添加！",
														onyes : true,
														icon : "../../images/socceralert.png"
													});
													$("input").val("");
													$(".select2 option").attr('selected', 'true');
													$("#default_photo_img").attr("src", "../images/defaultuser.png")
												} else {
													ds.dialog({
														title : '消息提示',
														content : data.message,
														onyes : true,
														icon : "../../images/info.png"
													});
												}
											},
											error : function() {
												ds.dialog({
													title : '消息提示',
													content : "添加运动员失败，请重试",
													onyes : true,
													icon : "../../images/info.png"
												});
											}
										});
									}
								},
								error : function() {
									ds.dialog({
										title : '消息提示',
										content : "检查学校用户名失败，请修复网络",
										onyes : true,
										icon : "../../images/info.png"
									});
									return false;
								}
							});
							
						});
	});
	function byId(id) {
		return document.getElementById(id);
	}

	var isInit = false;
	
	function readFile(obj){   
        var file = obj.files[0];      
        //判断类型是不是图片  
        if(!/image\/\w+/.test(file.type)){     
        	ds.dialog({
				title : '消息提示',
				content : "请确保为图像类型！",
				onyes : true,
				icon : "../../images/info.png"
			});
                return false;   
        }   
        var reader = new FileReader();   
        reader.readAsDataURL(file);   
        reader.onload = function(e){ 
        	var form = byId("formCard");
        	var _result = this.result;
        	if(_result.length>65535){
        		ds.dialog({
					title : '消息提示',
					content : "图片过大，不支持BASE64转码！请用画图等应用程序调整图片大小后再试！",
					onyes : true,
					icon : "../../images/info.png"
				});
        	}
        	form.base64Image.value = _result.substring(_result.indexOf("base64")+7);
    		$("#default_photo_img").attr("src",_result);
        }   
	}   

	function readCard() {
		var obj = byId("CardReader1");
		var form = byId("formCard");
		if (false == isInit) {
			//设置端口号，1表示串口1，2表示串口2，依此类推；1001表示USB。0表示自动选择
			obj.setPortNum(0);
			isInit = true;
		}
		//使用重复读卡功能
		obj.Flag = 0;
		//obj.BaudRate=115200;
		//读卡
		var rst = obj.ReadCard();
		//获取各项信息
		if (0x90 == rst) {
			form.nameL.value = obj.NameL();
			form.sexL.value = obj.SexL();
			form.nationL.value = obj.NationL();
			form.bornL.value = obj.BornL();
			form.idnum.value = obj.CardNo();
			form.address.value = obj.Address();
			form.base64Image.value = obj.GetImage();
		} else {
			form.nameL.value = "";
			form.sexL.value = "";
			form.nationL.value = "";
			form.bornL.value = "";
			form.idnum.value = "";
			form.address.value ="";
			form.base64Image.value = "";
		}

		$("#CardReader1").css("visibility","visible");
		$("#default_photo_img").css("visibility","hidden");
	}

	function GetState() {
		var obj = byId("CardReader1");
		obj.GetState();
	}
</script>
</head>

<body> 
	<div class="neirong_wk" style="padding-top:0px;">
		<form id="formCard">
			<table class="center_table" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="10px" rowspan=8>&nbsp;</td>
					<td class="text_width">姓名(<font style="color:red;">*</font>)：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="name_input" type="text" class="input_text"
									name="nameL">
							</div>
							<div class="input_r"></div>
						</div></td>
					<td rowspan=5 width="175px">&nbsp;</td>
					<td rowspan=5 width="150px"><object id="CardReader1"
							codebase="<%=basePath%>jsp/FirstActivex.cab#version=1,3,1,1"
							classid="CLSID:F225795B-A882-4FBA-934C-805E1B2FBD1B"
							width="150px" height="180px">
							<param name="_Version" value="65536" />
							<param name="_ExtentX" value="2646" />
							<param name="_ExtentY" value="1323" />
							<param name="_StockProps" value="0" />

						</object> <img id="default_photo_img" width="150px" height="180px"
						src="../images/defaultuser.png"></td>
						<td style="vertical-align: bottom;" rowspan=5 width="175px"><input type="file" accept="image/*" id="picFile" style="width:175px;" onchange="readFile(this)"/></td>
					
				</tr>
				<tr>
					<td class="text_width">性别(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="gender_input" type="text" class="input_text"
									name="sexL">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td class="text_width">民族(<font style="color:red;">*</font>)：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="nation_input" type="text" class="input_text"
									name="nationL">
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td class="text_width">生日(<font style="color:red;">*</font>)：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="birthday_input" type="text"
									class="input_text" name="bornL">
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td class="text_width">身份证号(<font style="color:red;">*</font>)：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="identity_input" type="text" class="input_text"
									name="idnum">
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				
			</table>
			<input id="image_input" type="text" class="input_text"
									name="base64Image" style="display:none;">
			<table class="center_table" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="30px" rowspan=9>&nbsp;</td>
					<td class="text_width">户籍地址(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="address_input" type="text" class="input_text"
									name="address">							
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td width="30px" rowspan=9>&nbsp;</td>
					<td class="text_width">学校用户名(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="schoolusername_input" type="text" class="input_text"
									name="default">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
				</tr>
				
				<tr>
					<td class="text_width">学籍号(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="studentid_input" type="text" class="input_text"
									name="default">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td class="text_width">电话号码：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="phonenum_input" type="text" class="input_text"
									name="default">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
				</tr>

				<tr>
					<td class="text_width">年级：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select class="select1" id="grade_select">
										<option value="1">一年级</option>
										<option value="2">二年级</option>
										<option value="3">三年级</option>
										<option value="4">四年级</option>
										<option value="5">五年级</option>
										<option value="6">六年级</option>
										<option value="7">七年级</option>
										<option value="8">八年级</option>
										<option value="9">九年级</option>
										<option value="10">高一</option>
										<option value="11">高二</option>
										<option value="12">高三</option>
									</select>
									<!-- <div class="select_icon"></div> -->
								</div>
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td class="text_width">教练员姓名：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="coachname_input" type="text" class="input_text"
									name="default">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
				</tr>

				<tr>
					<td class="text_width">位置：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select class="select2" id="position_select">
										<option value="0">未选择</option>
										<option value="1">门将</option>
										<option value="2">后卫</option>
										<option value="3">中场</option>
										<option value="4">前锋</option>
									</select>
									<!-- <div class="select_icon"></div> -->
								</div>
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td class="text_width">鞋码(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk" style="width:150px;float:left">
							<div class="input_l"></div>
							<div class="input_m" style="width:100px;">
								<div class="select_wk">
									<select class="select2" id="shoesize_select">
										<option value="23">23码</option>
										<option value="24">24码</option>
										<option value="25">25码</option>
										<option value="26">26码</option>
										<option value="27">27码</option>
										<option value="28">28码</option>
										<option value="29">29码</option>
										<option value="30">30码</option>
										<option value="31">31码</option>
										<option value="32">32码</option>
										<option value="33">33码</option>
										<option value="34">34码</option>
										<option value="35">35码</option>
										<option value="36">36码</option>
										<option value="37">37码</option>
										<option value="38">38码</option>
										<option value="39">39码</option>
										<option value="40">40码</option>
										<option value="41">41码</option>
										<option value="43">42码</option>
										<option value="43">43码</option>
										<option value="44">44码</option>
									</select>
									<!-- <div class="select_icon"></div>-->
								</div>
							</div>
							<div class="input_r"></div>
						</div>
						<a id="shoesize" href='javascript:void(0)' style="">参考鞋码对照表<img alt="尺码参照表" src="../images/shoesize.png"></a>
					</td>
				</tr>

				<tr>
					<td class="text_width">身高(cm)(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="height_input" type="text" class="input_text"
									name="default" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td class="text_width">体重(kg)(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="weight_input" type="text" class="input_text"
									name="default" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
				</tr>

				<tr>
					<td class="text_width">监护人1姓名(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="guardian1name_input" type="text" class="input_text"
									name="default">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td class="text_width">监护人1学历(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select class="select2" id="guardian1diploma_select">
										<c:forEach items="${diploma_list}" var="xx" varStatus="loop">
											<option value="${xx.getDiplomaId()}">${xx.getDiplomaName()}</option>
										</c:forEach>
									</select>
									<!-- <div class="select_icon"></div>-->
								</div>
							</div>
							<div class="input_r"></div>
						</div>
					</td>
				</tr>

				<tr>
					<td class="text_width">监护人1职业(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select class="select2" id="guardian1job_select">
										<c:forEach items="${jobs_list}" var="xx" varStatus="loop">
											<option value="${xx.getJobId()}">${xx.getJobName()}</option>
										</c:forEach>
									</select>
									<!-- <div class="select_icon"></div> -->
								</div>
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td class="text_width">监护人1电话(<font style="color:red;">*</font>)：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="guardian1phone_input" type="text" class="input_text"
									name="default">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
				</tr>

				<tr>
					<td class="text_width">监护人2姓名：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="guardian2name_input" type="text" class="input_text"
									name="default">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td class="text_width">监护人2学历：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select class="select2" id="guardian2diploma_select">
										<c:forEach items="${diploma_list}" var="xx" varStatus="loop">
											<option value="${xx.getDiplomaId()}">${xx.getDiplomaName()}</option>
										</c:forEach>
									</select>
									<!-- <div class="select_icon"></div> -->
								</div>
							</div>
							<div class="input_r"></div>
						</div>
					</td>
				</tr>

				<tr>
					<td class="text_width">监护人2职业：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select class="select2" id="guardian2job_select">
										<c:forEach items="${jobs_list}" var="xx" varStatus="loop">
											<option value="${xx.getJobId()}">${xx.getJobName()}</option>
										</c:forEach>
									</select>
									<!-- <div class="select_icon"></div> -->
								</div>
							</div>
							<div class="input_r"></div>
						</div>
					</td>
					<td class="text_width">监护人2电话：</td>
					<td>
						<div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<input id="guardian2phone_input" type="text" class="input_text"
									name="default">
							</div>
							<div class="input_r"></div>
						</div>
					</td>
				</tr>
			</table>
			
			<div id="btn_table" style="width:90%;margin: 10px auto">
				<input id="button_read" type="button"
				name="btnRead" onclick="readCard()" style="float:left;width:137px;height:41px" />
				
				<input id="button_reset" type="reset"
				name="btnReset" value="" style="float:right;width:102px;height:41px"/>
				
				<input id="button_register" type="button"
				name="btnRegister" style="float:right;width:100px;height:41px;margin-right:10px;"/>
			</div>
				
		</form>
	</div>
</body>
</html>
