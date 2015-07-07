<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>比赛管理</title>
<link type="text/css" href="${pageContext.request.contextPath}/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/css/league.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/css/match.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/css/dialog.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/css/jquery-ui-timepicker-addon.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/js/Duang.js"></script>
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/js/ds.dialog.js"></script>
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js"></script>
 <!--中文-->
<script src="${pageContext.request.contextPath}/js/jquery.ui.datepicker-zh-CN.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<script type="text/javascript">
        $(function () {
            // 时间设置
           $('#time1').datetimepicker({
                dateFormat: "yy-mm-dd"
            });
           $('#time2').datetimepicker({
               dateFormat: "yy-mm-dd"
           });
           
           $("input:disabled").css('color','#b0b5a0;')
        });
 </script>
<title>比赛管理</title>
<style>
.height3 td{height:40px}
</style>
</head>
<body>
	<div class="operation ">
		<ul class="a_caipan">
			<li id="competition_liebiao" class="border_bo"><div>
					<img src="${pageContext.request.contextPath}/images/a_revise.png" />
				</div>
				<p>比赛列表</p></li>
			<li id="add_competition"><div>
					<img src="${pageContext.request.contextPath}/images/a_add.png" />
				</div>
				<p>添加比赛</p></li>
		</ul>
	</div>
	<div class="neirong_wk">
		<div id="match_list">
			<!--查找-->
			<table class="search_table">
				<tbody>
					<tr>
						<td>联赛</td>
						<td><div class="input_wk">
								<div class="input_l"></div>
								<div class="input_m">
									<div class="select_wk">
										<select id="league_search_select">
											<option id="search_league_option_0" value="0">全部</option>
											<c:set var="count_league" value="0"></c:set>
											<c:forEach items="${leagues}" var="xx" varStatus="loop">
												<c:set var="count_level" value="${count_level+1}"></c:set>
												<option id="search_league_option_${xx.getLeagueId()}" value="${count_league}">${xx.getLeagueName()}</option>
											</c:forEach>
										</select>
										<div class="select_icon"></div>
									</div>
								</div>
								<div class="input_r"></div>
							</div></td>
						<td>等级</td>
						<td><div class="input_wk">
								<div class="input_l"></div>
								<div class="input_m">
									<div class="select_wk">
										<select id="zone_search_select">
										</select>
										<div class="select_icon"></div>
									</div>
								</div>
								<div class="input_r"></div>
							</div></td>
						<td>时间</td>
						<td><div class="input_wk">
								<div class="input_l"></div>
								<div class="input_m">
									<div class="select_wk">
										<input type="text" id='search_time' style="height:30px;border:none;background:none">
									</div>
								</div>
								<div class="input_r"></div>
							</div></td>
						<td>
						<!--  <img id="button_filter"
							src="${pageContext.request.contextPath}/images/btn_filter.png">-->
							<input type="button" id="button_filter" style="width: 150px; border-width: 0px; background-image: url(http://localhost:8080/YSMS/images/btn_filter.png);">
						</td>
					</tr>
				</tbody>
			</table>
			<!--查找 END-->
			<!--比赛列表-->
			<table id="competition_list" class="list_info" cellpadding="0"
				cellspacing="0">
				<thead>
					<tr>
						<td width='145px'>
							<div class="table_head_left" style="width: 160px">时&nbsp;
								&nbsp;间</div>
						</td>
						<td width="208px">联赛</td>
						<td width="95px">等级</td>
						<td width="74px">类型</td>
						<td width="195px">主队</td>
						<td width="195px">客队</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="10">
							<div class="tbody_sroll" style='height: 417px'>
								<table id="inner_table" cellpadding="0" cellspacing="0" width='100%'>
								</table>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<!--比赛列表 END-->

			<!--分页-->
			<div id="paging" class="choose_bottom">
				<div class="choose_btn_delete">
					<p style="" id="page_setting" class="choose_kuai">
						<input id='pageIndex' type='text' width='10px' value='1'>
					</p>
				</div>
			</div>
			<!--分页 END-->

			<!--修改列表的弹出框-->
			<div class="edit_match">
				<div class="close"></div>
				<!--内容-->
				<table class="competition_apply">
				<tr>
					<td>所属联赛：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input type="text" disabled="disabled" id="game_league_modify"/>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>主 &nbsp; &nbsp; &nbsp; 裁：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>联赛等级：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input type="text" disabled="disabled" id="game_zone_modify"/>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>第一边裁：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>比赛类型：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input type="text" disabled="disabled" id="game_group_modify"/>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>第二边裁：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>主队学校：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input type="text" disabled="disabled" id="host_team_modify"/>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>统&nbsp; &nbsp; &nbsp;计：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>主队球衣：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select id="host_uniform_modify">
										<option value="1">深</option>
										<option value="2">浅</option>
									</select>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>统&nbsp; &nbsp; &nbsp;计：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>客队学校：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input type="text" disabled="disabled" id="guest_team_modify"/>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>第四官员：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>客队球衣：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select id="guest_uniform_modify">
										<option value="1">深</option>
										<option value="2">浅</option>
									</select>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>现场官员：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>比赛时间：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input type="text" id='time1'>
									<!--时间表-->
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>现场官员：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>比赛地点：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input type="text" id="address_modify">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="4">
						<div class="btn_wk">
							<div class="btn_l btn_l_a_green"></div>
							<div class="btn_m btn_m_a_green">
								<input type="button" class="input_btn" id="modifymatch_btn"
									style="background: none" value="确认修改">
							</div>
							<div class="btn_r btn_r_a_green"></div>
						</div>
					</td>
				</tr>
			</table>

			</div>
			<!--修改列表的弹出框 END-->

			<!--比赛结果 登记-->
			<div class="saishi_results">
				<div class="close"></div>
				<div class="score">
					<table width="100%" style="text-align: center">
						<tr>
							<td><img
								src="${pageContext.request.contextPath}/images/logo.png">
								<p id="record_host_school_name"></p></td>
							<td class="score_score"><input id="record_host_score" type="text" onfocus="if(this.value == '--') this.value = ''" onblur="if(this.value =='') this.value = '--'"></td>
							<td><img
								src="${pageContext.request.contextPath}/images/vs_icon2.png"></td>
							<td class="score_score"><input id="record_guest_score" type="text" onfocus="if(this.value == '--') this.value = ''" onblur="if(this.value =='') this.value = '--'"></td>
							<td><img
								src="${pageContext.request.contextPath}/images/logo.png">
								<p id="record_guest_school_name"></p></td>
						</tr>
					</table>
				</div>

				<div class="xijie">
					<table class="fenlai_2">
						<tr>
							<!--第一个学校-->
							<td class="td_waikuang" style="border-left: 1px solid #eee">
								<span class="school_name" id="record_host_team_name"></span>
								<div class="tab_xijie">
									<span class="span_tab">进球得分</span> <span>红黄牌</span>
								</div>
							
								<table class="honghuangpai" cellpadding="0" cellspacing="0" width="100%">
										<thead>
											<tr>
												<td width="83px">球衣号码</td>
												<td width="130px">运动员</td>
												<td width="113px">犯规类型</td>
												<td width="90px">犯规时间</td>
												<td > </td>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td colspan="6">
													<div class="tbody_sroll" style="height:256px;overflow:auto; ">
														<table id="host_foul_table" cellpadding="0" cellspacing="0" width="100%">
															<tr id="host_foul_bottom_tr"> 
																<td colspan="6" style="text-align:right">
																	<img src="images/add_jilu.png">
																</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
										</tbody>
								</table>
								<table class="personscore" cellpadding="0" cellspacing="0" width="100%" >
									<thead>
										<tr>
											<td width="65px">球衣号码</td>
											<td width="90px">进球球员</td>
											<td width="65px">球衣号码</td>
											<td width="90px">助攻球员</td>
											<td width="90px">进球类型</td>
											<td width="70px">进球时间</td>
											<td > </td>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td colspan="8">
												<div class="tbody_sroll" style="height:256px;overflow:auto; ">
													<table id="host_goal_table" cellpadding="0" cellspacing="0" width="100%">
														<tr id="host_goal_bottom_tr"> 
															<td colspan="8" style="text-align:right">
																<img src="images/add_jilu.png">
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							
							</td>
							<!--第一个学校 END-->
							<!--第二个学校-->
							<td class="td_waikuang"><span class="school_name2" id="record_guest_team_name"></span>
								<div class="tab_xijie">
									<span class="span_tab">进球得分</span><span>红黄牌</span>
								</div>
								<table class="honghuangpai" cellpadding="0" cellspacing="0" width="100%">
											<thead>
												<tr>
													<td width="83px">球衣号码</td>
													<td width="130px">运动员</td>
													<td width="113px">犯规类型</td>
													<td width="90px">犯规时间</td>
													<td > </td>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td colspan="6">
														<div class="tbody_sroll" style="height:256px;overflow:auto; ">
															<table id="guest_foul_table" cellpadding="0" cellspacing="0" width="100%">
																<tr id="guest_foul_bottom_tr"> 
																	<td colspan="6" style="text-align:right">
																		<img src="images/add_jilu.png">
																	</td>
																</tr>
															</table>
														</div>
													</td>
												</tr>
											</tbody>
								</table>
								<table class="personscore" cellpadding="0" cellspacing="0" width="100%" >
									<thead>
										<tr>
											<td width="65px">球衣号码</td>
											<td width="90px">进球球员</td>
											<td width="65px">球衣号码</td>
											<td width="90px">助攻球员</td>
											<td width="90px">进球类型</td>
											<td width="70px">进球时间</td>
											<td > </td>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td colspan="8">
												<div class="tbody_sroll" style="height:256px;overflow:auto; ">
													<table id="guest_goal_table" cellpadding="0" cellspacing="0" width="100%">
														<tr id="guest_goal_bottom_tr"> 
															<td colspan="8" style="text-align:right">
																<img src="images/add_jilu.png">
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
								
							</td>
							<!--第二个学校 END-->
						</tr>
						<tr>
							<td colspan="7"><a id="save_record" href="javascript:void(0)">保
									&nbsp; &nbsp;存 </a> <a id="cancel_record" href="javascript:void(0)">取 &nbsp;
									&nbsp; 消</a></td>
						</tr>
					</table>
				</div>
			</div>
			<!--比赛结果-->
		</div>

		<!--添加比赛-->
		<div id="competition_apply" style="display: none">
			<table class="competition_apply">
				<tr>
					<td>所属联赛：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select id="game_league_select">
										<option value="0">--请选择--</option>
											<c:set var="count_league" value="0"></c:set>
											<c:forEach items="${leagues}" var="xx" varStatus="loop">
												<c:set var="count_level" value="${count_level+1}"></c:set>
												<option id="game_league_option_${xx.getLeagueId()}" value="${count_league}">${xx.getLeagueName()}</option>
											</c:forEach>
									</select>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>主 &nbsp; &nbsp; &nbsp; 裁：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>联赛等级：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select id="game_zone_select">
									</select>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>第一边裁：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>联赛分组：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select id="game_group_select">
										<option value="0">--请选择--</option>
										<option value="1">A组</option>
										<option value="2">B组</option>
										<option value="3">C组</option>
										<option value="4">D组</option>
										<option value="5">淘汰赛</option>
									</select>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>第二边裁：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>主队学校：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select onchange="refresh_select_team_list(this)" id="host_team_select" class="refresh_select">
										<option id="team_select_option_0" value="0">--请选择--</option>
									</select>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>统&nbsp; &nbsp; &nbsp;计：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>主队球衣：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select id="host_uniform_select">
										<option value="0">--请选择--</option>
										<option value="1">深</option>
										<option value="2">浅</option>
									</select>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>统&nbsp; &nbsp; &nbsp;计：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>客队学校：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select onchange="refresh_select_team_list(this)" id="guest_team_select" class="refresh_select">
										<option id="team_select_option_0" value="0">--请选择--</option>
									</select>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>第四官员：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>客队球衣：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<select id="guest_uniform_select">
										<option value="0">--请选择--</option>
										<option value="1">深</option>
										<option value="2">浅</option>
									</select>
									<div class="select_icon"></div>
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>现场官员：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>比赛时间：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input type="text" id='time2'>
									<!--时间表-->
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td>现场官员：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input disabled="disabled" readOnly="readonly" type="text" value="暂不支持">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
				</tr>
				<tr>
					<td>比赛地点：</td>
					<td><div class="input_wk">
							<div class="input_l"></div>
							<div class="input_m">
								<div class="select_wk">
									<input type="text" id="address">
								</div>
							</div>
							<div class="input_r"></div>
						</div></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="4">
						<div class="btn_wk">
							<div class="btn_l btn_l_a_green"></div>
							<div class="btn_m btn_m_a_green">
								<input type="button" class="input_btn" id="addmatch_btn"
									style="background: none" value="确认添加">
							</div>
							<div class="btn_r btn_r_a_green"></div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<!--添加比赛 END-->


	</div>
	<script type="text/javascript">
	var selectedGameId;
	var recordingGameId;
	var hostSelectNumHtml = "<option id='host_num_0' value='0'></option>";
	var guestSelectNumHtml = "<option id='guest_num_0' value='0'></option>";
	var hostSelectNameHtml = "<option id='host_name_0' value='0'></option>";
	var guestSelectNameHtml = "<option id='guest_name_0' value='0'></option>";
	$(function(){
		$('#time').datepicker();
		$('#search_time').datepicker();
		refresh_competitions()
		$('#add_competition').click(function(){
			$('#match_list').hide();
			$('#competition_apply').show();	
		})
		$('#competition_liebiao').click(function(){
			refresh_competitions()
			$('#competition_apply').hide();
			$('#match_list').show();
		})
		//填写记录tab切换
		$('.tab_xijie span').click(function(){
			var this_parent=$(this).parents('.td_waikuang');
			$(this).addClass('span_tab').siblings().removeClass('span_tab');
			if($(this).index()==1)
			{
				$(this_parent).find('.honghuangpai').show();
				$(this_parent).find('.personscore').hide();
			}
			else if($(this).index()==0)
			{
				$(this_parent).find('.honghuangpai').hide();
				$(this_parent).find('.personscore').show();
			}
		})
		//填写记录tab切换中的table的样式
		$('.fenlai_2 table ').find('tr:even').css('background','#eee')

		$('.close').click(function(){
			$('.saishi_results').hide();
			$('.edit_match').hide();	
		})

		$('#cancel_record').click(function(){
			$('.saishi_results').hide();
			$('.edit_match').hide();	
		})
		
		$("#save_record").click(function(){
			var reg = new RegExp("^[0-9]{1,2}$");
			var host_score = $("#record_host_score").val();
			var guest_score = $("#record_guest_score").val();
			if(!reg.test(host_score) || !reg.test(guest_score)){
				ds.dialog({
					title : '消息提示',
					content : "比赛得分必须为1~2位数字!",
					onyes : true,
					icon : "../images/info.png"
				});
				cancel_loading();
				return;
			}
			var host_goal = "";
			var guest_goal = "";
			var host_foul = "";
			var guest_foul = "";
			var every_goal_has_shooter = true;
			var every_goal_has_time = true;
			var every_foul_has_player = true;
			var every_foul_has_time = true;
			loading_juggle_empty();
			$("#host_goal_table tr").each(function(){
				$(this).find(".record_host_name_select").eq(0).each(function(){
					if($(this).get(0).selectedIndex == 0){
						every_goal_has_shooter = false;
					}
					host_goal = host_goal + $(this).find("option:selected").attr("id").substring(10) + "," 
						+ $(this).parent().next().next().find("option:selected").attr("id").substring(10) + ","
						+ $(this).parent().next().next().next().find("select").eq(0).val() + "," 
						+ $(this).parent().next().next().next().next().find("input").eq(0).val() + ";";
				})
				$(this).find(".goal_time").each(function(){
					var time = $(this).val();
					if(!reg.test(time))
						every_goal_has_time = false;
				})
			})
			$("#guest_goal_table tr").each(function(){
				$(this).find(".record_guest_name_select").eq(0).each(function(){
					if($(this).get(0).selectedIndex == 0){
						every_goal_has_shooter = false;
					}
					guest_goal = guest_goal + $(this).find("option:selected").attr("id").substring(11) + "," 
					+ $(this).parent().next().next().find("option:selected").attr("id").substring(11) + ","
					+ $(this).parent().next().next().next().find("select").eq(0).val() + "," 
					+ $(this).parent().next().next().next().next().find("input").eq(0).val() + ";";
				})
				$(this).find(".goal_time").each(function(){
					var time = $(this).val();
					if(!reg.test(time))
						every_goal_has_time = false;
				})
			})
			$("#host_foul_table tr").each(function(){
				$(this).find(".record_host_name_select").eq(0).each(function(){
					if($(this).get(0).selectedIndex == 0){
						every_foul_has_player = false;
					}
					host_foul = host_foul + $(this).find("option:selected").attr("id").substring(10) + "," 
						+ $(this).parent().next().find("select").eq(0).val() + "," 
						+ $(this).parent().next().next().find("input").eq(0).val() + ";";
				})
				$(this).find(".foul_time").each(function(){
					var time = $(this).val();
					if(!reg.test(time))
						every_foul_has_time = false;
				})
			})
			$("#guest_foul_table tr").each(function(){
				$(this).find(".record_guest_name_select").eq(0).each(function(){
					if($(this).get(0).selectedIndex == 0){
						every_foul_has_player = false;
					}
					guest_foul = guest_foul + $(this).find("option:selected").attr("id").substring(11) + "," 
						+ $(this).parent().next().find("select").eq(0).val() + "," 
						+ $(this).parent().next().next().find("input").eq(0).val() + ";";
				})
				$(this).find(".foul_time").each(function(){
					var time = $(this).val();
					if(!reg.test(time))
						every_foul_has_time = false;
				})
			})
			if(!every_goal_has_shooter){
				ds.dialog({
					title : '消息提示',
					content : "必须选择进球者!",
					onyes : true,
					icon : "../images/info.png"
				});
				cancel_loading();
				return;
			}
			if(!every_foul_has_player){
				ds.dialog({
					title : '消息提示',
					content : "必须选择犯规人!",
					onyes : true,
					icon : "../images/info.png"
				});
				cancel_loading();
				return;
			}
			
			if(!every_goal_has_time || !every_foul_has_time){
				ds.dialog({
					title : '消息提示',
					content : "必须以1~2位数字格式填写进球或红黄牌时间!",
					onyes : true,
					icon : "../images/info.png"
				});
				cancel_loading();
				return;
			}
			if(host_goal.length>0){
				host_goal = host_goal.substring(0,host_goal.length-1);
			}
			if(guest_goal.length>0){
				guest_goal = guest_goal.substring(0,guest_goal.length-1);
			}
			if(host_foul.length>0){
				host_foul = host_foul.substring(0,host_foul.length-1);
			}
			if(guest_foul.length>0){
				guest_foul = guest_foul.substring(0,guest_foul.length-1);
			}
			$.ajax({
				type : 'POST',
				url : "${pageContext.request.contextPath}/gamemanagement/record.html",
				data : {
					game_id : recordingGameId,
					host_score : host_score,
					guest_score : guest_score,
					host_goal : host_goal,
					guest_goal : guest_goal,
					host_foul : host_foul,
					guest_foul : guest_foul
				},
				dataType : "json",
				success : function(data) {
					if(data.success){
						ds.dialog({
							title : '消息提示',
							content : "记录已提交！",
							onyes : true,
							icon : "../images/socceralert.png"
						});
						$('.saishi_results').hide();
						$('.edit_match').hide();
					}
					else{
						ds.dialog({
							title : '消息提示',
							content : "记录失败！ ",
							onyes : true,
							icon : "../images/info.png"
						});
					}
					cancel_loading();
				},
				error : function() {
					ds.dialog({
						title : '消息提示',
						content : "记录失败，请检查网络连接！",
						onyes : true,
						icon : "../images/info.png"
					});
					cancel_loading();
				}
			}); 
		})
		
		//添加记录条数
		$("#host_foul_table img[src*='add_jilu.png']").click(function(){
			var st=$(this).parent().parent();
			$(st).before("<tr>"
					+"<td width='83px'>"
					+"<select class='record_host_num_select' onchange='record_host_num_select_onchange(this)'>"
					+hostSelectNumHtml+"</select>"
					+"</td>"
					+"<td width='130px'><select class='record_host_name_select'>"+hostSelectNameHtml+"</select></td>"
					+"<td width='113px'><select><option value='1'>黄牌</option><option value='2'>红牌</option></select></td>"
					+"<td width='90px'><input class='foul_time' type='text' ></td>"
					+"<td onclick='shanchu(this)'><img src='images/shanchu.png'></td>"
					+"</tr>");
			$('.fenlai_2 table ').find('tr:even').css('background','#eee');
		})
		$("#guest_foul_table  img[src*='add_jilu.png']").click(function(){
			var st=$(this).parent().parent();
			$(st).before("<tr>"
					+"<td width='83px'>"
					+"<select class='record_guest_num_select' onchange='record_guest_num_select_onchange(this)'>"
					+guestSelectNumHtml+"</select>"
					+"</td>"
					+"<td width='130px'><select class='record_guest_name_select'>"+guestSelectNameHtml+"</select></td>"
					+"<td width='113px'><select><option value='1'>黄牌</option><option value='2'>红牌</option></select></td>"
					+"<td width='90px'><input class='foul_time' type='text' ></td>"
					+"<td onclick='shanchu(this)'><img src='images/shanchu.png'></td>"
					+"</tr>");
			$('.fenlai_2 table ').find('tr:even').css('background','#eee');
		})
		$("#host_goal_table  img[src*='add_jilu.png']").click(function(){
			var st=$(this).parent().parent();
			$(st).before("<tr>"
					+"<td width='65px'><select class='record_host_num_select' onchange='record_host_num_select_onchange(this)'>"+hostSelectNumHtml+"</select></td>"
					+"<td width='90px'><select class='record_host_name_select'>"+hostSelectNameHtml+"</select></td>"
					+"<td width='65px'><select class='record_host_num_select' onchange='record_host_num_select_onchange(this)'>"+hostSelectNumHtml+"</select></td>"
					+"<td width='90px'><select class='record_host_name_select'>"+hostSelectNameHtml+"</select></td>"
					+"<td width='90px'><select><option value='1'>正常进球</option><option value='2'>点球</option><option value='3'>乌龙球</option></select></td>"
					+"<td width='70px'><input class='goal_time' type='text'></td><td onclick='shanchu(this)'><img  src='images/shanchu.png'></td>"
					+"</tr>");
			$('.fenlai_2 table ').find('tr:even').css('background','#eee');
		})
		$("#guest_goal_table  img[src*='add_jilu.png']").click(function(){
			var st=$(this).parent().parent();
			$(st).before("<tr>"
					+"<td width='65px'><select class='record_guest_num_select' onchange='record_guest_num_select_onchange(this)'>"+guestSelectNumHtml+"</select></td>"
					+"<td width='90px'><select class='record_guest_name_select'>"+guestSelectNameHtml+"</select></td>"
					+"<td width='65px'><select class='record_guest_num_select' onchange='record_guest_num_select_onchange(this)'>"+guestSelectNumHtml+"</select></td>"
					+"<td width='90px'><select class='record_guest_name_select'>"+guestSelectNameHtml+"</select></td>"
					+"<td width='90px'><select><option value='1'>正常进球</option><option value='2'>点球</option><option value='3'>乌龙球</option></select></td>"
					+"<td width='70px'><input class='goal_time' type='text'></td><td onclick='shanchu(this)'><img  src='images/shanchu.png'></td>"
					+"</tr>");
			$('.fenlai_2 table ').find('tr:even').css('background','#eee');
		})
		//删除行

		$("#league_search_select").change(function(){
			var league_id = $("#league_search_select").find('option:selected').attr("id").substr(21);
			$.ajax({
				type : 'POST',
				url : "${pageContext.request.contextPath}/gamemanagement/getzonesbyleague.html",
				data : {
					league_id : league_id
				},
				dataType : "json",
				success : function(data) {
					$("#zone_search_select").empty();
					$("#zone_search_select").append("<option id='search_zone_option_0'>全部</option>");
			        $.each(data.zones, function (i, item) {  
			        	$("#zone_search_select").append("<option id='search_zone_option_" + item.zoneId + "'>" + item.zoneName + "</option>");
			        });  
				},
				error : function() {
				}
			});
		})
		$("#game_league_select").change(function(){
			if($("#game_league_select").get(0).selectedIndex==0){
				$("#game_zone_select").empty();
				return;
			}
			var league_id = $("#game_league_select").find('option:selected').attr("id").substr(19);
			$.ajax({
				type : 'POST',
				url : "${pageContext.request.contextPath}/gamemanagement/getzonesbyleague.html",
				data : {
					league_id : league_id
				},
				dataType : "json",
				success : function(data) {
					$("#game_zone_select").empty();
					$("#game_group_select").get(0).selectedIndex=0;
			        $.each(data.zones, function (i, item) {  
			        	$("#game_zone_select").append("<option id='game_zone_option_" + item.zoneId + "'>" + item.zoneName + "</option>");
			        });  
				},
				error : function() {
				}
			});
		})
		$("#game_group_select").change(function(){
			refresh_select_team_list(null);
		})
		$("#button_filter").click(function(){
			refresh_competitions();
		})
		$("#modifymatch_btn").click(function(){
			var host_uniform = $("#host_uniform_modify").get(0).selectedIndex;
			var guest_uniform = $("#guest_uniform_modify").get(0).selectedIndex;
			var game_time = $("#time1").val();
			var game_location = $("#address_modify").val();
			
			$.ajax({
				type : 'POST',
				url : "${pageContext.request.contextPath}/gamemanagement/modifygame.html",
				data : {
					game_id : selectedGameId,
					game_time : game_time,
					game_location : game_location,
					host_uniform : host_uniform,
					guest_uniform : guest_uniform
				},
				dataType : "json",
				success : function(data) {
					if(data.success){
						ds.dialog({
							title : '消息提示',
							content : "比赛已修改！",
							onyes : true,
							icon : "../images/socceralert.png"
						});
						refresh_competitions();
						$('.saishi_results').hide();
						$('.edit_match').hide();	
					}
					else{
						ds.dialog({
							title : '消息提示',
							content : "修改比赛失败！",
							onyes : true,
							icon : "../images/info.png"
						});
					}
				},
				error : function() {
					ds.dialog({
						title : '消息提示',
						content : "添加比赛失败！请检查网络连接！",
						onyes : true,
						icon : "../images/info.png"
					});
				}
			});
		})
		$("#addmatch_btn").click(function(){
			if($("#game_zone_select").get(0).selectedIndex<0){
				ds.dialog({
					title : '消息提示',
					content : "请选择联赛和联赛等级！",
					onyes : true,
					icon : "../images/info.png"
				});
				return; 
			}
			if($("#game_group_select").get(0).selectedIndex == 0){
				ds.dialog({
					title : '消息提示',
					content : "请选择联赛分组信息！",
					onyes : true,
					icon : "../images/info.png"
				});
				return;
			}
			if($("#host_team_select").get(0).selectedIndex == 0 || $("#guest_team_select").get(0).selectedIndex == 0){
				ds.dialog({
					title : '消息提示',
					content : "请选择联赛双方球队！",
					onyes : true,
					icon : "../images/info.png"
				});
				return;
			}
			if($("#host_uniform_select").get(0).selectedIndex == 0 || $("#guest_uniform_select").get(0).selectedIndex == 0){
				ds.dialog({
					title : '消息提示',
					content : "请选择联赛双方球衣！",
					onyes : true,
					icon : "../images/info.png"
				});
				return;
			}
			var game_time = $("#time2").val();
			var game_location = $("#address").val();
			var zone_id = $("#game_zone_select").find('option:selected').attr("id").substr(17);
			var group_index = $("#game_group_select").find('option:selected').val();
			var game_order = 1; //小组赛
			if(group_index==5){
				game_order = 2; //淘汰赛
			}
			var host_team_id = $("#host_team_select").find("option:selected").attr("id").substr(19);
			var guest_team_id = $("#guest_team_select").find("option:selected").attr("id").substr(19);
			var host_uniform = $("#host_uniform_select").get(0).selectedIndex - 1;
			var guest_uniform = $("#guest_uniform_select").get(0).selectedIndex - 1;
			
			$.ajax({
				type : 'POST',
				url : "${pageContext.request.contextPath}/gamemanagement/addgame.html",
				data : {
					host_team_id : host_team_id,
					guest_team_id : guest_team_id,
					zone_id : zone_id,
					game_time : game_time,
					game_location : game_location,
					game_order : game_order,
					host_uniform : host_uniform,
					guest_uniform : guest_uniform
				},
				dataType : "json",
				success : function(data) {
					if(data.success){
						ds.dialog({
							title : '消息提示',
							content : "比赛已添加！",
							onyes : true,
							icon : "../images/socceralert.png"
						});
						$("#game_league_select").get(0).selectedIndex = 0;
						$("#game_zone_select").get(0).selectedIndex = -1;
						$("#game_group_select").get(0).selectedIndex = 0;
						$("#host_team_select").get(0).selectedIndex = 0;
						$("#host_uniform_select").get(0).selectedIndex = 0;
						$("#guest_team_select").get(0).selectedIndex = 0;
						$("#guest_uniform_select").get(0).selectedIndex = 0;
						$("#time2").val("");
						$("#address").val("");
						$('#competition_apply').hide();
						$('#match_list').show();
						refresh_competitions();
					}
					else{
						ds.dialog({
							title : '消息提示',
							content : "添加比赛失败！",
							onyes : true,
							icon : "../images/info.png"
						});
					}
				},
				error : function() {
					ds.dialog({
						title : '消息提示',
						content : "添加比赛失败！请检查网络连接！",
						onyes : true,
						icon : "../images/info.png"
					});
				}
			});
		})
		
	})
	function nextPage() {
		var index = $("#pageIndex").val();
		$("#pageIndex").val(parseInt(index) + 1)
		refresh_competitions();
	}

	function prePage() {
		var index = $("#pageIndex").val();
		$("#pageIndex").val(parseInt(index) - 1)
		refresh_competitions();
	}
	function refresh_competitions(){
		$("#inner_table").empty();
		var current_page = $("#pageIndex").val();
		var r = /^\+?[1-9][0-9]*$/;
		if (!r.test(current_page)) {
			current_page = "1";
		}
		var league_id = $("#league_search_select").find('option:selected').attr("id").substr(21);
		if(league_id=="0")
			league_id = null;
		var zone_id = null;
		if($("#zone_search_select").get(0).selectedIndex!=-1){
			zone_id = $("#zone_search_select").find('option:selected').attr("id").substr(19);
		}
		if(zone_id=="0")
			zone_id = null;
		var date = $("#search_time").val();
		loading_juggle_empty();
		$.ajax({
			type : 'POST',
			url : "${pageContext.request.contextPath}/gamemanagement/getgames.html",
			data : {
				current_page : current_page,
				league_id : league_id,
				zone_id : zone_id,
				date : date
			},
			dataType : "json",
			success : function(data) {
				$("#page_setting").empty();

				if (data.page.hasPrePage == true) {
					$("#page_setting")
							.append(
									" <label onclick='prePage()'>上一页</label>");
				}
				if (data.page.hasNextPage == true) {
					$("#page_setting")
							.append(
									" <label onclick='nextPage()'>下一页</label>");
				}
				$("#page_setting")
						.append(
								"<span>第</span><input id='pageIndex' type='text' width='10px' value='"
										+ data.page.currentPage
										+ "'>/<span id='pageCount'></span>页 <button id='page_ok' onclick='refresh_competitions()'>跳转</button>");
				$("#pageCount").text(data.page.totalPage)
		        $.each(data.games, function (i, item) {  
		    		$("#inner_table").append("<tr id='gametr_" + item.gamesId + "'>" + 
		    				"<td width='145px'>" + item.gameTime + "</td>" + 
		    				"<td width='208px'>" + item.leagueName + "</td>" + 
		    				"<td width='95px'>" + item.zoneName + "</td>" + 
		    				"<td width='74px'>" + item.orderName + "</td>" + 
		    				"<td width='190px'>" + item.hostSchoolName + "</td>" + 
		    				"<td width='190px'>" + item.guestSchoolName + "</td>" + 
		    				"<td><img class='record' src='${pageContext.request.contextPath}/images/edit_saishi_info.png' alt='记录' onmouseover='mouseover_obj(this)' onmouseout='mouseout_obj(this)'>" + 
		    				"<img class='edit' src='${pageContext.request.contextPath}/images/list_modify_btn.png' alt='修改' onmouseover='mouseover_obj(this)' onmouseout='mouseout_obj(this)'>" + 
		    				"<img class='delete' src='${pageContext.request.contextPath}/images/list_delete_btn.png' alt='删除' onmouseover='mouseover_obj(this)' onmouseout='mouseout_obj(this)'></td>" + 
		    				"</tr>"
		    		);
		    	});
				cancel_loading();
				$('.record').click(function(){
					$("#host_goal_bottom_tr").siblings().remove();
					$("#guest_goal_bottom_tr").siblings().remove();
					$("#host_foul_bottom_tr").siblings().remove();
					$("#guest_foul_bottom_tr").siblings().remove();
					var game_id = $(this).parent().parent().attr("id").substr(7);
					recordingGameId = game_id;
					loading_juggle_empty();
					$.ajax({
						type : 'POST',
						url : "${pageContext.request.contextPath}/gamemanagement/getgameinfo.html",
						data : {
							game_id : game_id
						},
						dataType : "json",
						success : function(data) {
							$("#record_host_school_name").html(data.game.hostSchoolName);
							$("#record_guest_school_name").html(data.game.guestSchoolName);
							var hostTeamName = data.game.hostTeamName;
							var guestTeamName = data.game.guestTeamName;
							if(hostTeamName == null || hostTeamName == "")
								hostTeamName = "球队未命名";
							if(guestTeamName == null || guestTeamName == "")
								guestTeamName = "球队未命名";
							$("#record_host_team_name").html(hostTeamName);
							$("#record_guest_team_name").html(guestTeamName);
							var hostScore = data.game.hostScore;
							var guestScore = data.game.guestScore;
							if(hostScore == null)
								hostScore = "--";
							if(guestScore == null)
								guestScore = "--";
							$("#record_host_score").val(hostScore);
							$("#record_guest_score").val(guestScore);
							hostSelectNumHtml = "<option id='host_num_0' value='0'></option>";
							hostSelectNameHtml = "<option id='host_name_0' value='0'></option>";
							$.each(data.host_athletes, function (i, item) {
								hostSelectNameHtml += "<option id='host_name_" + item.athleteId + "'>" + item.athleteName + "</option>";
								if(item.athleteNumber!=null){
									hostSelectNumHtml += "<option id='host_num_" + item.athleteId + "'>" + item.athleteNumber + "</option>";
								}
				    		});
							guestSelectNumHtml = "<option id='guest_num_0' value='0'></option>";
							guestSelectNameHtml = "<option id='guest_name_0' value='0'></option>";
							$.each(data.guest_athletes, function (i, item) { 
								guestSelectNameHtml += "<option id='guest_name_" + item.athleteId + "'>" + item.athleteName + "</option>";
								if(item.athleteNumber!=null){
									guestSelectNumHtml += "<option id='guest_num_" + item.athleteId + "'>" + item.athleteNumber + "</option>";
								}
				    		});
							$(".record_host_name_select").empty();
							$(".record_host_name_select").append(hostSelectNameHtml);
							$(".record_guest_name_select").empty();
							$(".record_guest_name_select").append(guestSelectNameHtml);
							$(".record_host_num_select").empty();
							$(".record_host_num_select").append(hostSelectNumHtml);
							$(".record_guest_num_select").empty();
							$(".record_guest_num_select").append(guestSelectNumHtml);
							$.each(data.host_goals, function(i, item){
								var appendHtml = "<tr style='background: rgb(238, 238, 238);'>" + 
										"<td width='65px'><select class='record_host_num_select' onchange='record_host_num_select_onchange(this)'>";
								appendHtml += hostSelectNumHtml;
								appendHtml += "</select></td><td width='90px'><select class='record_host_name_select'>";
								appendHtml += hostSelectNameHtml;
								appendHtml += "</select></td><td width='65px'><select class='record_host_num_select' onchange='record_host_num_select_onchange(this)'>";
								appendHtml += hostSelectNumHtml;
								appendHtml += "</select></td><td width='90px'><select class='record_host_name_select'>";
								appendHtml += hostSelectNameHtml;
								appendHtml += "</select></td><td width='90px'><select><option value='1'>正常进球</option><option value='2'>点球</option><option value='3'>乌龙球</option></select>";
								appendHtml += "</select></td><td width='70px'><input class='goal_time' type='text'></td>";
								appendHtml += "<td onclick='shanchu(this)'><img src='images/shanchu.png'></td></tr>";
								$("#host_goal_bottom_tr").before(appendHtml);
								$("#host_goal_bottom_tr").prev().find(".record_host_num_select").eq(0).find("option:contains('" + item.shooterNumber + "')").each(function(){
									 if($(this).text() == item.shooterNumber+""){
										 $(this).attr("selected",true);
									 }
								 })
								$("#host_goal_bottom_tr").prev().find(".record_host_name_select").eq(0).find("option:contains('" + item.shooterName + "')").each(function(){
									 if($(this).attr("id").substring(10) == item.shooterId){
										 $(this).attr("selected",true);
									 }
								 })
								$("#host_goal_bottom_tr").prev().find(".record_host_num_select").eq(1).find("option:contains('" + item.assistantNumber + "')").each(function(){
									 if($(this).text() == item.assistantNumber+""){
										 $(this).attr("selected",true);
									 }
								 })
								$("#host_goal_bottom_tr").prev().find(".record_host_name_select").eq(1).find("option:contains('" + item.assistantName + "')").each(function(){
									 if($(this).attr("id").substring(10) == item.assistantId){
										 $(this).attr("selected",true);
									 }
								 })
								 $("#host_goal_bottom_tr").prev().find("select").eq(4).val(item.style);
								 $("#host_goal_bottom_tr").prev().find("input").eq(0).val(item.time);
							});
							$.each(data.guest_goals, function(i, item){
								var appendHtml = "<tr style='background: rgb(238, 238, 238);'>" + 
										"<td width='65px'><select class='record_guest_num_select' onchange='record_guest_num_select_onchange(this)'>";
								appendHtml += guestSelectNumHtml;
								appendHtml += "</select></td><td width='90px'><select class='record_guest_name_select'>";
								appendHtml += guestSelectNameHtml;
								appendHtml += "</select></td><td width='65px'><select class='record_guest_num_select' onchange='record_guest_num_select_onchange(this)'>";
								appendHtml += guestSelectNumHtml;
								appendHtml += "</select></td><td width='90px'><select class='record_guest_name_select'>";
								appendHtml += guestSelectNameHtml;
								appendHtml += "</select></td><td width='90px'><select><option value='1'>正常进球</option><option value='2'>点球</option><option value='3'>乌龙球</option></select>";
								appendHtml += "</select></td><td width='70px'><input class='goal_time' type='text'></td>";
								appendHtml += "<td onclick='shanchu(this)'><img src='images/shanchu.png'></td></tr>";
								$("#guest_goal_bottom_tr").before(appendHtml);
								$("#guest_goal_bottom_tr").prev().find(".record_guest_num_select").eq(0).find("option:contains('" + item.shooterNumber + "')").each(function(){
									 if($(this).text() == item.shooterNumber+""){
										 $(this).attr("selected",true);
									 }
								 })
								$("#guest_goal_bottom_tr").prev().find(".record_guest_name_select").eq(0).find("option:contains('" + item.shooterName + "')").each(function(){
									if($(this).attr("id").substring(11) == item.shooterId){
										 $(this).attr("selected",true);
									 }
								 })
								$("#guest_goal_bottom_tr").prev().find(".record_guest_num_select").eq(1).find("option:contains('" + item.assistantNumber + "')").each(function(){
									 if($(this).text() == item.assistantNumber+""){
										 $(this).attr("selected",true);
									 }
								 })
								$("#guest_goal_bottom_tr").prev().find(".record_guest_name_select").eq(1).find("option:contains('" + item.assistantName + "')").each(function(){
									 if($(this).attr("id").substring(11) == item.assistantId){
										 $(this).attr("selected",true);
									 }
								 })
								 $("#guest_goal_bottom_tr").prev().find("select").eq(4).val(item.style);
								 $("#guest_goal_bottom_tr").prev().find("input").eq(0).val(item.time);
							}); 
							
							$.each(data.host_fouls, function(i, item){
								var appendHtml = "<tr style='background: rgb(238, 238, 238);'>" + 
										"<td width='83px'><select class='record_host_num_select' onchange='record_host_num_select_onchange(this)'>";
								appendHtml += hostSelectNumHtml;
								appendHtml += "</select></td><td width='130px'><select class='record_host_name_select'>";
								appendHtml += hostSelectNameHtml;
								appendHtml += "</select></td><td width='113px'><select><option value='1'>黄牌</option><option value='2'>红牌</option></select>";
								appendHtml += "</select></td><td width='90px'><input class='goal_time' type='text'></td>";
								appendHtml += "<td onclick='shanchu(this)'><img src='images/shanchu.png'></td></tr>";
								$("#host_foul_bottom_tr").before(appendHtml);
								$("#host_foul_bottom_tr").prev().find(".record_host_num_select").eq(0).find("option:contains('" + item.athleteNumber + "')").each(function(){
									 if($(this).text() == item.athleteNumber+""){
										 $(this).attr("selected",true);
									 }
								 })
								$("#host_foul_bottom_tr").prev().find(".record_host_name_select").eq(0).find("option:contains('" + item.athleteName + "')").each(function(){
									if($(this).attr("id").substring(10) == item.athleteId){
										 $(this).attr("selected",true);
									 }
								 })
								 $("#host_foul_bottom_tr").prev().find("select").eq(2).val(item.foulLevel);
								 $("#host_foul_bottom_tr").prev().find("input").eq(0).val(item.time);
							});
							$.each(data.guest_fouls, function(i, item){
								var appendHtml = "<tr style='background: rgb(238, 238, 238);'>" + 
										"<td width='83px'><select class='record_guest_num_select' onchange='record_guest_num_select_onchange(this)'>";
								appendHtml += guestSelectNumHtml;
								appendHtml += "</select></td><td width='130px'><select class='record_guest_name_select'>";
								appendHtml += guestSelectNameHtml;
								appendHtml += "</select></td><td width='113px'><select><option value='1'>黄牌</option><option value='2'>红牌</option></select>";
								appendHtml += "</select></td><td width='90px'><input class='goal_time' type='text'></td>";
								appendHtml += "<td onclick='shanchu(this)'><img src='images/shanchu.png'></td></tr>";
								$("#guest_foul_bottom_tr").before(appendHtml);
								$("#guest_foul_bottom_tr").prev().find(".record_guest_num_select").eq(0).find("option:contains('" + item.athleteNumber + "')").each(function(){
									 if($(this).text() == item.athleteNumber+""){
										 $(this).attr("selected",true);
									 }
								 })
								$("#guest_foul_bottom_tr").prev().find(".record_guest_name_select").eq(0).find("option:contains('" + item.athleteName + "')").each(function(){
									 if($(this).attr("id").substring(11) == item.athleteId){
										 $(this).attr("selected",true);
									 }
								 })
								 $("#guest_foul_bottom_tr").prev().find("select").eq(2).val(item.foulLevel);
								 $("#guest_foul_bottom_tr").prev().find("input").eq(0).val(item.time);
							});
							cancel_loading();
							
						},
						error : function() {
							ds.dialog({
								title : '消息提示',
								content : "请检查网络连接！",
								onyes : true,
								icon : "../images/info.png"
							});
							cancel_loading();
						}
					});
					$('.saishi_results').show();
				})	
				$('.edit').click(function(){
					var game_id = $(this).parent().parent().attr("id").substr(7);
					selectedGameId = game_id;
						$.ajax({
							type : 'POST',
							url : "${pageContext.request.contextPath}/gamemanagement/getsinglegame.html",
							data : {
								game_id : game_id
							},
							dataType : "json",
							success : function(data) {
								$("#game_league_modify").val(data.game.leagueName);
								$("#game_zone_modify").val(data.game.zoneName);
								$("#game_group_modify").val(data.game.orderName);
								$("#team_modify").val(data.game.hostSchoolName);
								$("#host_team_modify").val(data.game.hostSchoolName);
								$("#guest_team_modify").val(data.game.guestSchoolName);
								$("#time1").val(data.game.gameTime);
							$("#address_modify").val(data.game.gameLocation);
							var host_uniform = data.game.hostUniform;
							var guest_uniform = data.game.guestUniform;
							if(host_uniform == 0){
								$("#host_uniform_modify").get(0).selectedIndex = 0;
							}
							else
								$("#host_uniform_modify").get(0).selectedIndex = 1;
							if(guest_uniform == 0){
								$("#guest_uniform_modify").get(0).selectedIndex = 0;
							}
							else
								$("#guest_uniform_modify").get(0).selectedIndex = 1;
						},
						error : function() {
							ds.dialog({
								title : '消息提示',
								content : "请检查网络连接！",
								onyes : true,
								icon : "../images/info.png"
							});
						}
					});
					$('.edit_match').show();
				})
				$(".delete").click(function(){
					var game_id = $(this).parent().parent().attr("id").substr(7);
					ds.dialog({
						title : '消息提示',
						content : "将删除该比赛，确定删除？",
						yesText : "确定",
						onyes : function() {
							$.ajax({
								type : 'POST',
								url : "${pageContext.request.contextPath}/gamemanagement/deletegame.html",
								data : {
									game_id : game_id
								},
								dataType : "json",
								success : function(data) {
									refresh_competitions();
								},
								error : function() {
									ds.dialog({
										title : '消息提示',
										content : "删除比赛失败！请检查网络连接！",
										onyes : true,
										icon : "../images/info.png"
									});
								}
							});
						},
						noText : "取消",
						onno : function() {
							this.close();
						},
						icon : "../images/confirm.png"
					});
				})
			},
			error : function() {
				ds.dialog({
					title : '消息提示',
					content : "请检查网络连接！",
					onyes : true,
					icon : "../images/info.png"
				});
				cancel_loading();
			}
		});
	}
	function record_host_num_select_onchange(obj){
		var selectedNum = $(obj).find("option:selected").text();
		var nextSelect = $(obj).parent().next().find("select");
		loading_juggle_empty();
		$.ajax({
			type : 'POST',
			url : "${pageContext.request.contextPath}/gamemanagement/getgameinfo.html",
			data : {
				game_id : recordingGameId
			},
			dataType : "json",
			success : function(data) {
				$.each(data.host_athletes, function (i, item) {
					if(item.athleteNumber==selectedNum){
						//why?
						$(nextSelect).find("option:contains('" + item.athleteName + "')").each(function(){
							 $(this).attr("selected",false);
							 if($(this).attr("id") == "host_name_" + item.athleteId){
								 $(this).attr("selected",true);
							 }
						 })
					}
	    		});
				cancel_loading();
			},
			error : function() {
				cancel_loading();
			}
		});
	}
	function record_guest_num_select_onchange(obj){
		var selectedNum = $(obj).find("option:selected").text();
		var nextSelect = $(obj).parent().next().find("select");
		loading_juggle_empty();
		$.ajax({
			type : 'POST',
			url : "${pageContext.request.contextPath}/gamemanagement/getgameinfo.html",
			data : {
				game_id : recordingGameId
			},
			dataType : "json",
			success : function(data) {
				$.each(data.guest_athletes, function (i, item) {
					if(item.athleteNumber==selectedNum){
						//why?
						$(nextSelect).find("option:contains('" + item.athleteName + "')").each(function(){
							 if($(this).attr("id") == "guest_name_" + item.athleteId){
								 $(this).attr("selected",true);
							 }
						 })
					}
	    		});
				cancel_loading();
			},
			error : function() {
				cancel_loading();
			}
		});
	}
	function refresh_select_team_list(obj){
		var group_index = $("#game_group_select").find('option:selected').val();
		if($("#game_zone_select").find('option:selected').attr("id")==null){
			ds.dialog({
				title : '消息提示',
				content : "请选择联赛和联赛等级！",
				onyes : true,
				icon : "../images/info.png"
			});
			return;
		}
		var zone_id = $("#game_zone_select").find('option:selected').attr("id").substr(17);
		var group_name = null;
		//A,B,C,D组
		if(group_index==1){
			group_name = "A"; 
		}
		else if(group_index==2){
			group_name = "B"; 
		}
		else if(group_index==3){
			group_name = "C"; 
		}
		else if(group_index==4){
			group_name = "D"; 
		}
		var selected_teamid = "";
		
		$(".refresh_select").each(function(){
			if($(this).get(0).selectedIndex!=0){
				selected_teamid = selected_teamid + $(this).find("option:selected").attr("id").substr(19) + ",";
			}
		})
		if(selected_teamid.length>0){
			selected_teamid = selected_teamid.substr(0,selected_teamid.length-1);
		}
		$.ajax({
			type : 'POST',
			url : "${pageContext.request.contextPath}/gamemanagement/getteamsbyzone.html",
			data : {
				group_name : group_name,
				zone_id : zone_id,
				selected_teamid : selected_teamid
			},
			dataType : "json",
			success : function(data) {
	        	$(".refresh_select").each(function(){
	        		if($(this).get(0).selectedIndex!=0){
	    				selectedId = $(this).find("option:selected").attr("id");
	    				selectedSchoolName = $(this).find("option:selected").text();
	    				$(this).empty();
						$(this).append("<option id='team_select_option_0' value='0'>--请选择--</option>");
						if(obj!=null)
							$(this).append("<option id='" + selectedId + "' selected='selected'>" + selectedSchoolName + "</option>");
	    			}
	        		else{
						$(this).empty();
						$(this).append("<option id='team_select_option_0' value='0'>--请选择--</option>");
	        		}
	        		var theObj = $(this);
		        	$.each(data.teams, function (i, item) {  
		    			$(theObj).append("<option id='team_select_option_" + item.teamId + "'>" + item.schoolName + "</option>");
		    		});
		        });  
			},
			error : function() {
			}
		});
	}
	function shanchu(obj){
		$(obj).parent().remove();
	}
</script>
</body>
</html>