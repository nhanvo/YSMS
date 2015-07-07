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
	href="${pageContext.request.contextPath}/css/dialog.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/athlete.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jquery.dataTables.css">
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.dataTables.js"></script>
<script language="javascript" type="text/javascript"
	src="${pageContext.request.contextPath}/js/Duang.js"></script>
<script language="javascript" type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ui.datepicker-zh-CN.js"></script>
<script language="javascript" type="text/javascript"
	src="${pageContext.request.contextPath}/js/ds.dialog.js"></script>
	<!--[if lt IE 9]>
    <script  type="text/javascript" src="../js/json2.js"></script>
<![endif]-->
<title>运动员管理</title>
<style type="text/css">
.neirong_wk {
	height:552px
}

.athlete_filter {
	width: 100%;
}

.athlete_detailed {
	left: 80px;
	padding: 20px;
	background: none repeat scroll 0% 0% #FFF;
	border: 1px solid #0FD46C;
	border-radius: 10px;
	width: 900px;
	z-index: 5;
	position: absolute;
	top: 10px;
	padding-top: 10px;
	padding-bottom: 0px;
}

.tbody_sroll {
	height: 598px;
}

.table_head_left {
	cursor: pointer;
	background: #127419;
}

.table_td {
	table-layout: fixed;
	margin-top: 1%;
	width: 100%;
}

.table_td td {
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	height: 40px;
	border-bottom: 1px solid #a7a7a7;
	text-align: center;
}

.table_td thead {
	background: #160b2d;
	color: #fff;
	font-size: 1.2em;
}
.table_td thead a{
	color: #fff;
}

.table_head_left {
	position: absolute;
	height: 50px;
	background: #127419;
	top: -9px;
	left: -10px;
	line-height: 50px;
}

.dataTables_wrapper {
	position: relative;
	clear: both;
	*zoom: 1;
	zoom: 1;
	width: 94%;
	margin-left: 2%;
}

table.dataTable thead .sorting {
	background: url("../images/sort_both.png") no-repeat center right
		#160B2D;
}

.input_wk {
	height: 30px;
	width: 300px;
	margin: 3px;
}

.input_m {
	float: left;
	height: 34px;
	width: 260px;
	background: url("../images/input_m_thin.png") repeat scroll 0% 0%
		transparent;
	position: relative;
	line-height: 30px;
	cursor: pointer;
}

.input_text {
	text-align: left;
	line-height: 30px;
	height: 30px;
	margin-top: 2px;
	width: 260px;
	border: medium none;
	font-size: 1em;
	color: #251B3B;
	background: none repeat scroll 0% 0% #FAFAFA;
	vertical-align: middle;
	font-family: "Microsoft YaHei", "SimHei", "SimSun";
}

.close {
	right: -10px;
	top: -10px;
}

.black_overlay {
	display: none;
	position: absolute;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: white;
	z-index: 2;
	-moz-opacity: 0.8;
	opacity: .80;
	filter: alpha(opacity = 80);
}
.a_delete{
    cursor: pointer;
 	border-radius: 5px;
 	padding: 2px 2px;
	background: #127419;
	position: absolute;
 	top: 5px;
 	left: 0
}
.a_delete:hover{
  background: #144023;
  
}
</style>



<script type="text/javascript">
	var oTable = null;
	$(function() {
		path="${pageContext.request.contextPath}";
		dofilter();

        $("input:disabled").css('color','#b0b5a0;')
		$("#checkAll").click(function() {
			if (this.checked) {
				$("input[name='checkList']").each(function() {
					this.checked = true;
				});
			} else {
				$("input[name='checkList']").each(function() {
					this.checked = false;
				});
			}
		});
		addyearoption($("#year_filter"));

		$('.close').click(function() {
			$('.athlete_detailed').hide();
			$("#default_photo_img").attr("src", "../images/defaultuser.png");
			$('#fade').hide();
			//dofilter();
			//$('.caipan_apply').hide();
		});

		$("#button_change")
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
							var shoesize_index = $("#shoesize_input").val();
							if (shoesize_index && !regex.test(shoesize_index)) {
								ds.dialog({
									title : '消息提示',
									content : "鞋码格式不符！",
									onyes : true,
									icon : "../../images/info.png"
								});
								return false;
							}

							var address = $("#address_input").val();
							var image = $("#image_input").val();
							var studentid = $("#studentid_input").val();
							var grade_index = $('#grade_select')[0].selectedIndex + 1;//index+1，直接用当前年相减即可得入学年
							var phonenum = $("#phonenum_input").val();
							var position_index = $('#position_select')[0].selectedIndex + 1;//适配数据库
							//var shoesize_index = parseInt($('#shoesize_select')[0].selectedIndex) + 30;
							var shoesize_index = $('#shoesize_select').val();
							var height = $("#height_input").val();
							var weight = $("#weight_input").val();
							var guardian1_name = $("#guardian1name_input")
									.val();
							var guardian1_diploma = $("#guardian1diploma_select")[0].selectedIndex + 1;
							var guardian1_job = $("#guardian1job_select")[0].selectedIndex + 1;
							var guardian1_phone = $("#guardian1phone_input")
									.val();
							var guardian2_name = $("#guardian2name_input")
									.val();
							var guardian2_diploma = $("#guardian2diploma_select")[0].selectedIndex + 1;
							var guardian2_job = $("#guardian2job_select")[0].selectedIndex + 1;
							var guardian2_phone = $("#guardian2phone_input")
									.val();
							var coach_name = $("#coachname_input").val();
							var athleteid = $("#athleteid").val();
							ds.dialog({
								title : '消息提示',
								content : "确定修改运动员信息？",
								yesText : "确定",
								onyes : function() {
									$
									.ajax({
										type : 'POST',
										url : "${pageContext.request.contextPath}/athletemanagement/modifyathlete.html",
										data : {
											athleteid : athleteid,
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
											image : image
										},
										dataType : "json",
										success : function(data) {
											if (data.success) {
												// 加载联赛列表
												ds.dialog({
															title : '消息提示',
															content : "运动员已修改！",
															onyes : true,
															icon : "../../images/socceralert.png"
														});
												getAthleteById(athleteid);
												//$('.athlete_detailed').hide();
												//$("#default_photo_img").attr("src", "../images/defaultuser.png");
												//$('#fade').hide();
												//$('.caipan_apply').hide();
												//$("input").val("");
												//$(".select1 option").eq(0).attr('selected', 'true');
												//$(".select2 option").eq(0).attr('selected', 'true');
											} else {
												ds.dialog({
													title : '消息提示',
													content : "修改运动员失败，请重试",
													onyes : true,
													icon : "../../images/info.png"
												});
											}
										},
										error : function() {ds.dialog({
											title : '消息提示',
											content : "修改运动员失败，请重试",
											onyes : true,
											icon : "../../images/info.png"
										});
										}
									});
								},
								noText : "取消",
								onno : function() {
									this.close();
								},
								icon : "../../images/confirm.png"
							});
						});
	});

	function addyearoption(obj)
	{
		var myDate= new Date();
		for (var i=0;i<=11;i++)
		{
			obj.append("<option value='"+(myDate.getFullYear()-i-1)+"'>"+getSchoolYear(i)+"</option>");
		}
	};
	
	function getSchoolYear(year)
    {
    	switch(year)
    	{
    	case 0:
    		return "一年级";
    	case 1:
    		return "二年级";
    	case 2:
    		return "三年级";
    	case 3:
    		return "四年级";
    	case 4:
    		return "五年级";
    	case 5:
    		return "六年级";
    	case 6:
    		return "七年级";
    	case 7:
    		return "八年级";
    	case 8:
    		return "九年级";
    	case 9:
    		return "高一";
    	case 10:
    		return "高二";
    	case 11:
    		return "高三";
    	default:
    		return "已毕业";
    	}
    };
	
	function deleteALL()
	{
		var str='';
		$("input[name='checkList']:checked").each(function(i,o)
		{
			str+=$(this).val();
			str+=",";
		});
		if(str.length>0){
			var IDS=str.substr(0,str.length-1);
			$.ajax({
		        url: "${pageContext.request.contextPath}/athletemanagement/deleteathlete.html",
		        data: {"id": IDS},
		        type: "post",
		        success: function (backdata) {
		        	if (backdata.returnCode==200) {
		        		oTable.fnDraw();
		            } 
		        	ds.dialog({
						title : '消息提示',
						content : backdata.returnMessage,
						onyes : true,
						icon : "../../images/info.png"
					});
		        }, error: function (error) {
		            console.log(error);
		        }
		    });
		}else
		{
			ds.dialog({
				title : '消息提示',
				content : "至少选一项",
				onyes : true,
				icon : "../../images/info.png"
			});
		}
		$("#checkAll").checked=false;
	};
	
	function delAthleteById(id) {
		ds.dialog({
			title : '消息提示',
			content : "确认删除运动员？",
			yesText : "确认",
			onyes : function() {
				$.ajax({
			        url: "${pageContext.request.contextPath}/athletemanagement/deleteathlete.html",
			        data: {"id": id},
			        type: "post",
			        success: function (backdata) {
			            if (backdata.returnCode==200) {
			            	oTable.fnDraw();
			            } 
			            ds.dialog({
							title : '消息提示',
							content : backdata.returnMessage,
							onyes : true,
							icon : "../../images/info.png"
						});
			        }, error: function (error) {
			            console.log(error);
			        }
			    });
			},
			noText : "取消",
			onno : function() {
			},
			icon : "../../images/info.png"
		});
	};
	
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
        	var form = document.getElementById("formCard");
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
	function getAthleteById(id)
	{
		$.ajax({
	        async: false,
	        cache: false,
			url:"${pageContext.request.contextPath}/athletemanagement/athletebyid.html",
			data:{"id":id},
			type:"get",
			success:function(backdata){
				if(backdata.returnCode==200)
				{
					var tempData= new Date();
					$("#athleteid").val(backdata.athlete.athleteId);
					$("#name_input").val(backdata.athlete.identifiedName);
					$("#gender_input").val(backdata.athlete.identifiedGender==0?"女":"男");
					$("#nation_input").val(backdata.athlete.identifiedNationality);
					$("#birthday_input").val(backdata.athlete.identifiedBirthday);
					$("#identity_input").val(backdata.athlete.identifiedId);
					//$("#shoesize_input").val();
					
					$("#address_input").val(backdata.athlete.identifiedAddress);
					
					$("#studentid_input").val(backdata.athlete.studentId);
					$('#grade_select')[0].selectedIndex =tempData.getFullYear()-backdata.athlete.athleteSchoolyear-1;//index+1，直接用当前年相减即可得入学年
					$("#phonenum_input").val(backdata.athlete.athleteMobile);
					$('#position_select')[0].selectedIndex =backdata.athlete.athletePosition-1;//适配数据库
					$('#shoesize_select').val(backdata.athlete.athleteFootsize);
					//$('#shoesize_select')[0].selectedIndex=-30;
					$("#height_input").val(backdata.athlete.athleteHeight);
					$("#weight_input").val(backdata.athlete.athleteWeight);
					$("#guardian1name_input").val(backdata.athlete.athleteGuardian1);
					$("#guardian1diploma_select")[0].selectedIndex =backdata.athlete.ysmsDiplomaByGuardian1DiplomaId-1;
					$("#guardian1job_select")[0].selectedIndex =backdata.athlete.ysmsJobsByGuardian1JobId-1;
					$("#guardian1phone_input").val(backdata.athlete.guardian1Mobile);
					$("#guardian2name_input").val(backdata.athlete.athleteGuardian2);
					$("#guardian2diploma_select")[0].selectedIndex =backdata.athlete.ysmsDiplomaByGuardian2DiplomaId-1;
					$("#guardian2job_select")[0].selectedIndex =backdata.athlete.ysmsJobsByGuardian2JobId-1;
					$("#guardian2phone_input").val(backdata.athlete.guardian2Mobile);
					$("#coachname_input").val(backdata.athlete.athleteCoach);
					
					if(backdata.athleteatt)
						{
						$("#image_input").val(backdata.athleteatt.attName);
						$("#default_photo_img").attr("src","data:image/jpeg;base64,"+backdata.athleteatt.attName);
						}
					
					$('#fade').show();
					$(".athlete_detailed").show();
				}else
				{
					 ds.dialog({
							title : '消息提示',
							content : backdata.returnMessage,
							onyes : true,
							icon : "../../images/info.png"
						});
				}
			},error:function(error){
				console.log(error);
			}
		});
	};
	
    function retrieveData( sSource, aoData, fnCallback ) {   
        //将客户名称加入参数数组   
        var filterurl="";
		var name=$("#name_filter").val();
		var identifiedGender=$("#sex_filter").val();
		var school_filter=$("#school_filter").val();
		var year_filter=$("#year_filter").val();
		if(name!=null&&name!="")
			aoData.push( { "name": "name_filter", "value": name } );   
		if(identifiedGender!=-1)
			aoData.push( { "name": "sex_filter", "value": identifiedGender } );   
		if(school_filter!=null&&school_filter!=-1)
            aoData.push( { "name": "school_filter", "value": school_filter} );   
		if(year_filter!=null&&year_filter!=-1)
			aoData.push( { "name": "year_filter", "value": year_filter} );
      
        $.ajax( {   
            "type": "GET", 
            "contentType": "application/json",   
            "url": sSource,    
            "dataType": "json",   
            "data": {requestJson:JSON.stringify(aoData)}, //以json格式传递   
            "success": function(resp) {   
            	//alert(resp);
                fnCallback(resp); //服务器端返回的对象的returnObject部分是要求的格式  
            }   
        });
    };
    
    
	
	
	//加载初始化信息
	function dofilter() {
	if (oTable == null) { 
		oTable=$('#athlete_list')
				.dataTable(
						{
						"bAutoWidth": false,                    //不自动计算列宽度   
            			"aoColumns": [                          //设定各列宽度   
                            {"sWidth": "160px","mDataProp":"schoolName","sClass":"table_td"},   
                            {"sWidth": "100px","mDataProp" : "identifiedName","sClass":"table_td"},  
                            {"sWidth": "80px",	"mDataProp" : "identifiedGender","sClass":"table_td"},   
                            {"sWidth": "80px","mDataProp" : "athleteSchoolyear","sClass":"table_td"},
                            {"sWidth": "200px","mDataProp" : "register_id","sClass":"table_td"},
                            {"sWidth": "80px","mDataProp" : "athleteGuardian1","sClass":"table_td"},
                            {"sWidth": "160px","mDataProp" : "guardian1Mobile","sClass":"table_td"},
                            {"mDataProp": "athleteId","sClass":"table_td","sWidth": "30px",
               				 "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                    		  $(nTd).html("<input type='checkbox' name='checkList' value='" + sData + "'>");}},//选择框
                    		  {"mDataProp": "athleteId","sClass":"table_td","sWidth": "80px",
                    				 "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                         		  $(nTd).html("<div class='input_l'></div><div class='input_m detailed' style='width: auto;' onclick='delAthleteById(" + sData + ")'>删除</div><div class='input_r'></div>");}},//删除按钮
                         		 {"mDataProp": "athleteId","sClass":"table_td","sWidth": "80px",
                        			"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                             		$(nTd).html("<div class='input_l'></div><div class='input_m detailed' style='width: auto;' onclick='getAthleteById(" + sData + ")'>详细</div><div class='input_r'></div>");}}//详细按钮
                    		  
                        ],"aoColumnDefs": [{"bSortable": false,"aTargets": [5,6,7,8,9]},
                              {"render":function(data, type, row) {return data==0?"女":"男"; },"targets" : 2},
                              {"render":function(data, type, row) {return getSchoolYear(new Date().getFullYear()-data-1);},"targets" : 3}
                        ],
            			"bProcessing": true, //加载数据时显示正在加载信息   
            			"bServerSide": true, //指定从服务器端获取数据   
            			"bFilter": false,        //不使用过滤功能   
            			"bLengthChange": false, //用户不可改变每页显示数量 
            			"sAjaxDataProp":"athletes",
            			"sAjaxSource": "${pageContext.request.contextPath}/athletemanagement/listpageathlete.html",//获取数据的url   
            			"fnServerData": retrieveData,           //获取数据的处理函数   
						"iDisplayLength" : 10,//默认十条
						"oLanguage" : {"sUrl" : "${pageContext.request.contextPath}/L10n/en_cn.txt"},
							"ajax" : {
								"dataSrc" : "athletes"
							},
							"columns" : [ {
								"data" : "schoolName"
							}, {
								"data" : "identifiedName"
							}, {
								"data" : "identifiedGender"
							}, {
							"data" : "athleteSchoolyear"
							}, {
							"data" : "register_id"
							}, {
								"data" : "athleteGuardian1"
							}, {
								"data" : "guardian1Mobile"
							},{
								"data" : "identifiedId"
							},{
								"data" : "identifiedId"
							},{
								"data" : "identifiedId"
							} ]
						});
						}
				else{
				      oTable.fnDraw();
				}
	};
</script>
</head>
<body>
	<div class="neirong_wk">
		<!--运动员信息查看-->
		<div class="athlete_filter">
		<form action="exportexcel.html" method="post">
			<table class="center_table" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td><label for="namefilter">姓名：</label></td>
					<td><div class="input_wk" style="width:140px;">
							<div class="input_l"></div>
							<div class="input_m" style="width:100px;"><input id="name_filter" name="name_filter" class="input_text" type="text" style="width:100px;"/></div>
							<div class="input_r"></div>
						</div></td>
					<td><label for="sex_filter">性别:</label></td>
					<td><div class="input_wk" style="width:150px;">
							<div class="input_l"></div>
							<div class="input_m" style="width:110px;"><select id="sex_filter"  name="sex_filter" class="input_text" ><option value="-1">--未选择--</option>
							<option value="1">男</option>
							<option value="0">女</option></select></div>
							<div class="input_r"></div></div></td>
							<c:if test="${school_list!=null}">
					<td><label>学校:</label></td>
					<td><div class="input_wk" style="width:150px;">
							<div class="input_l"></div>
							<div class="input_m" style="width:110px;">
							<select id="school_filter"  name="school_filter"  style="width: 110px"  class="input_text">
								<option value="-1">--未选择--</option>
							<c:forEach
								items="${school_list}" var="school" varStatus="loop">
								<option value="${school.schoolId}">${school.schoolName}
								</option>
							</c:forEach>
							</select></div>
							<div class="input_r"></div>
						</div></td>
						</c:if>
						<td><label>年级:</label></td>
					<td><div class="input_wk" style="width:150px;">
							<div class="input_l"></div>
							<div class="input_m" style="width:110px;">
							<select id="year_filter" name="year_filter" style="width: 110px"  class="input_text">
								<option value="-1">--未选择--</option>
							</select></div>
							<div class="input_r"></div>
						</div></td>
					<td style="text-align: center;"><input type="button" id="button_filter"
						onclick="dofilter();" /></td>
						<td style="text-align:center;"><input type="submit" id="button_export"  value=""/></td>
				</tr>
			</table>
			</form>
		</div>
		<table id="athlete_list" class="table_td unselectable" cellpadding="0"
			cellspacing="0" >
		<thead>
			<tr>
				<td width="180px"
					style="border-right: 1px solid rgb(167, 167, 167);"><div
						class="table_head_left" style="width: 180px">学校</div></td>
				<td width="100px">姓名</td>
				<td width="80px">性别</td>
				<td width="80px">年级</td>
				<td width="80px">注册证</td>
				<td width="60px">监护人</td>
				<td width="60px">联系电话</td>
				<td width="30px" ><input type="checkbox" id='checkAll' /></td>
				<td width="80px" style="position: relative;"><a onclick="deleteALL()"
					class="a_delete">批量删除</a></td>
				<td width="80px"></td>
				<td></td>
			</tr>
		</thead>
		</table>
		<!--运动员信息查看-->



		<!--修改运动员信息-->
		<div id="athlete_detailed" class="athlete_detailed"
			style="display: none;z-index:5">
			<div class="neirong">
				<div class="close"></div>
				<form id="formCard">
					<table class="center_table" border="0" cellpadding="0"
						cellspacing="0">
						<tr>

							<td class="text_width">姓名(<font style="color: red;">*</font>)：
							</td>
							<td width="10px" rowspan=8>&nbsp;</td>
							<td><div class="input_wk">
									<div class="input_l"></div>
									<div class="input_m">
										<input id="name_input" type="text" class="input_text"
											name="nameL" disabled="disabled">
									</div>
									<div class="input_r"></div>
								</div></td>
							<td rowspan=5 width="150px">&nbsp;</td>
							<td rowspan=5 width="175px"><img id="default_photo_img"
								width="150px" height="180px" src="../images/defaultuser.png"></td>
								<td style="vertical-align: bottom;" rowspan=5 width="175px"><input type="file" accept="image/*" id="picFile" style="width:175px;" onchange="readFile(this)"/></td>

						</tr>
						<tr>
							<td class="text_width">性别(<font style="color: red;">*</font>)：
							</td>
							<td>
								<div class="input_wk">
									<div class="input_l"></div>
									<div class="input_m">
										<input id="gender_input" type="text" class="input_text"
											name="sexL" disabled="disabled">
									</div>
									<div class="input_r"></div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="text_width">民族(<font style="color: red;">*</font>)：
							</td>
							<td><div class="input_wk">
									<div class="input_l"></div>
									<div class="input_m">
										<input id="nation_input" type="text" class="input_text"
											name="nationL" disabled="disabled">
									</div>
									<div class="input_r"></div>
								</div></td>
						</tr>
						<tr>
							<td class="text_width">生日(<font style="color: red;">*</font>)：
							</td>
							<td><div class="input_wk">
									<div class="input_l"></div>
									<div class="input_m">
										<input id="birthday_input" disabled="disabled" type="text"
											class="input_text" name="bornL">
									</div>
									<div class="input_r"></div>
								</div></td>
						</tr>
						<tr>
							<td class="text_width">身份证号(<font style="color: red;">*</font>)：
							</td>
							<td><div class="input_wk">
									<div class="input_l"></div>
									<div class="input_m">
										<input id="identity_input" type="text" class="input_text"
											name="idnum" disabled="disabled">
									</div>
									<div class="input_r"></div>
								</div></td>
						</tr>
					</table>
					<input id="image_input" type="text" class="input_text"
						name="base64Image" style="display: none;">
					<table class="center_table" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td class="text_width">户籍地址(<font style="color: red;">*</font>)：
							</td>
							<td>
								<div class="input_wk">
									<div class="input_l"></div>
									<div class="input_m">
										<input id="address_input" type="text" class="input_text"
											name="address" disabled="disabled">
									</div>
									<div class="input_r"></div>
								</div>
							</td>
							<td width="30px" rowspan=9>&nbsp;</td>

						</tr>

						<tr>
							<td class="text_width">学籍号：</td>
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
											<div class="select_icon"></div>
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
											<div class="select_icon"></div>
										</div>
									</div>
									<div class="input_r"></div>
								</div>
							</td>
							<td class="text_width">鞋码：</td>
							<td>
								<div class="input_wk">
									<div class="input_l"></div>
									<div class="input_m">
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
											<div class="select_icon"></div>
										</div>
									</div>
									<div class="input_r"></div>
								</div>
							</td>
						</tr>

						<tr>
							<td class="text_width">身高(cm)：</td>
							<td>
								<div class="input_wk">
									<div class="input_l"></div>
									<div class="input_m">
										<input id="height_input" type="text" class="input_text"
											name="default">
									</div>
									<div class="input_r"></div>
								</div>
							</td>
							<td class="text_width">体重(kg)：</td>
							<td>
								<div class="input_wk">
									<div class="input_l"></div>
									<div class="input_m">
										<input id="weight_input" type="text" class="input_text"
											name="default">
									</div>
									<div class="input_r"></div>
								</div>
							</td>
						</tr>

						<tr>
							<td class="text_width">监护人1姓名(<font style="color: red;">*</font>)：
							</td>
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
							<td class="text_width">监护人1学历(<font style="color: red;">*</font>)：
							</td>
							<td>
								<div class="input_wk">
									<div class="input_l"></div>
									<div class="input_m">
										<div class="select_wk">
											<select id="guardian1diploma_select">
												<c:forEach items="${diploma_list}" var="xx" varStatus="loop">
													<option value="${xx.getDiplomaId()}">${xx.getDiplomaName()}</option>
												</c:forEach>
											</select>
											<div class="select_icon"></div>
										</div>
									</div>
									<div class="input_r"></div>
								</div>
							</td>
						</tr>

						<tr>
							<td class="text_width">监护人1职业(<font style="color: red;">*</font>)：
							</td>
							<td>
								<div class="input_wk">
									<div class="input_l"></div>
									<div class="input_m">
										<div class="select_wk">
											<select id="guardian1job_select">
												<c:forEach items="${jobs_list}" var="xx" varStatus="loop">
													<option value="${xx.getJobId()}">${xx.getJobName()}</option>
												</c:forEach>
											</select>
											<div class="select_icon"></div>
										</div>
									</div>
									<div class="input_r"></div>
								</div>
							</td>
							<td class="text_width">监护人1电话(<font style="color: red;">*</font>)：
							</td>
							<td>
								<div class="input_wk">
									<div class="input_l"></div>
									<div class="input_m">
										<input id="guardian1phone_input" type="text"
											class="input_text" name="default">
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
											<select id="guardian2diploma_select">
												<c:forEach items="${diploma_list}" var="xx" varStatus="loop">
													<option value="${xx.getDiplomaId()}">${xx.getDiplomaName()}</option>
												</c:forEach>
											</select>
											<div class="select_icon"></div>
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
											<select id="guardian2job_select">
												<c:forEach items="${jobs_list}" var="xx" varStatus="loop">
													<option value="${xx.getJobId()}">${xx.getJobName()}</option>
												</c:forEach>
											</select>
											<div class="select_icon"></div>
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
										<input id="guardian2phone_input" type="text"
											class="input_text" name="default">
									</div>
									<div class="input_r"></div>
								</div>
							</td>
						</tr>
					</table>
					<table id="btn_table" class="center_table" style="margin-top: 5px;">
						<tr>
							<td width="250px;"><input id="button_change" type="button"
								name="btnChange" /><input id="athleteid" type="text"
								style="display: none;" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<!--编辑修改运动员-->
	</div>

	<div id="fade" class="black_overlay"></div>
	<!-- 分页 -->
	<div id="paging" class="choose_bottom">
		<div class="choose_btn_delete"></div>
	</div>
	<!-- 分页 -->
</body>
</html>