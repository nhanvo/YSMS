var path;
$(function(){
	
	
	$('.nav ul li').hover(
			function(){$(this).addClass('nav_hover_click')},
			function(){$(this).removeClass('nav_hover_click')}
			)
	$('.nav li').click(function(){
		$('.home').css('border','none');
		$(this).find('.fous').addClass('nav_click_fous');
		$(this).siblings().find('.fous').removeClass('nav_click_fous');
		$(this).addClass('nav_li_click').siblings().removeClass('nav_li_click');
	})
	$('.home').click(function(){
		$('.home').css('border-bottom','5px solid #160b2d');
		$('.nav li').css('background','none');
		$('.nav li .fous').removeClass('nav_click_fous')
		})
	
	//联赛上操作按钮切换
		$('.operation li').click(function(){
  		$(this).css('border-bottom','3px solid #127419').siblings().css('border','none')
  	})
		/*联赛查看列表*/
		$('#league_list tr').find('td:first-child').css('border-right','1px solid #a7a7a7');
		$('.list_info tr').find('td:first-child').css('border-right','1px solid #a7a7a7');
		
		//变色
		$('.green').hover(
				function(){$(this).css('background','#0e5c14!important')},
				function(){$(this).css('background','#127419!important')}
				)
		//管理
		$("img[src*='list_edit_btn']").hover(
				function(){$(this).attr('src','/YSMS/images/list_edit_btn2.png')},
				function(){$(this).attr('src','/YSMS/images/list_edit_btn.png')})
		
		//修改
		$("img[src*='list_modify_btn']").hover(
				function(){$(this).attr('src','images/list_modify_btn2.png');},
				function(){$(this).attr('src','/YSMS/images/list_modify_btn.png');}
				)
		//确定
		$("img[src*='list_ok_btn']").mouseover(function(){
			$(this).attr('src','/YSMS/images/list_ok_btn2.png');
		})
		$("img[src*='list_ok_btn']").mouseout(function(){
			$(this).attr('src','/YSMS/images/list_ok_btn.png');
		})
		//删除
		$("img[src*='list_delete_btn']").mouseover(function(){
			$(this).attr('src','images/list_delete_btn2.png');
		})
		$("img[src*='list_delete_btn']").mouseout(function(){
			$(this).attr('src','/YSMS/images/list_delete_btn.png');
		})
		//取消
		$("img[src*='list_reset_btn']").mouseover(function(){
			$(this).attr('src','images/list_reset_btn2.png');
		})
		$("img[src*='list_reset_btn']").mouseout(function(){
			$(this).attr('src','/YSMS/images/list_reset_btn.png');
		})
	
		//修改2
		$("img[src*='btn_update']").mouseover(function(){
			$(this).attr('src','images/btn_update2.png');
		})
		$("img[src*='btn_update']").mouseout(function(){
			$(this).attr('src','/YSMS/images/btn_update.png');
		})
		//返回
		$("img[src*='registerback.png']").mouseover(function(){
			$(this).attr('src','images/registerback2.png');
		})
		$("img[src*='registerback.png']").mouseout(function(){
			$(this).attr('src','/YSMS/images/registerback.png');
		})
		
		
		//注册
		$("#button_register").hover(
				function(){$(this).css('background-image','url(/YSMS/images/register2.png)')},
				function(){$(this).css('background-image','url(/YSMS/images/register.png)')})
		//重置
		$("#button_reset").hover(
				function(){$(this).css('background-image','url(/YSMS/images/registerreset2.png)')},
				function(){$(this).css('background-image','url(/YSMS/images/registerreset.png)')})
		//读取
		$('#button_read').hover(
				function(){$(this).css('background-image','url(/YSMS/images/registerread2.png)')},
				function(){$(this).css('background-image','url(/YSMS/images/registerread.png)')})
		//筛选
		$("#button_filter").hover(
				function(){$(this).css('background-image','url(/YSMS/images/btn_filter2.png)')},
				function(){$(this).css('background-image','url(/YSMS/images/btn_filter.png)')})
		//修改3
		$("#button_change").hover(
				function(){$(this).css('background-image','url(/YSMS/images/btn_update2.png)')},
				function(){$(this).css('background-image','url(/YSMS/images/btn_update.png)')})
		//导出		
		$("#button_export").hover(
				function(){$(this).css('background-image','url(/YSMS/images/btn_export2.png)')},
				function(){$(this).css('background-image','url(/YSMS/images/btn_export.png)')})
		//修改密码的修改	
		$('#modify_btn').hover(
				function(){$(this).css('background-image','url(/YSMS/images/btn_update2.png)')},
				function(){$(this).css('background-image','url(/YSMS/images/btn_update.png)')})
		//绿色button的hover
		$('.btn_m_a_green').hover(
					function(){
				
						$(this).prev().css('background-image','url(/YSMS/images/btn_l_a_green2.png)');
						$(this).css('background-image','url(/YSMS/images/btn_m_a_green2.png)');
						$(this).next().css('background-image','url(/YSMS/images/btn_r_a_green2.png)');	
					},
					function(){
						$(this).prev().css('background-image','url(/YSMS/images/btn_l_a_green.png)');
						$(this).css('background-image','url(/YSMS/images/btn_m_a_green.png)');
						$(this).next().css('background-image','url(/YSMS/images/btn_r_a_green.png)');	
		}) 
	
	    //红色button的hover
		$('.btn_m_a_red').hover(
					function(){
						$(this).prev().css('background-image','url(/YSMS/images/btn_l_a_red2.png)');
						$(this).css('background-image','url(/YSMS/images/btn_m_a_red2.png)');
						$(this).next().css('background-image','url(/YSMS/images/btn_r_a_red2.png)');	
					},
					function(){
						$(this).prev().css('background-image','url(/YSMS/images/btn_l_a_red.png)');
						$(this).css('background','url(/YSMS/images/btn_m_a_red.png)');
						$(this).next().css('background-image','url(/YSMS/images/btn_r_a_red.png)');	
		}) 	
		//大图标
		$('.operation li img').hover(
				function(){$(this).attr('src',($(this).attr('src').split(".png")[0])+'2.png')},
				function(){$(this).attr('src',($(this).attr('src').split("2.png")[0])+'.png')}
				)		
       })
       $('.operation li').click(function(){
    	   $(this).find('img').attr('src',($(this).attr('src').split(".png")[0])+'2.png');
    	   $(this).siblings().find('img').attr('src',($(this).attr('src').split("2.png")[0])+'.png')
       })
       function mouseover_obj(obj){$(obj).attr('src',($(obj).attr('src').split(".png")[0])+'2.png')}
	   function mouseout_obj(obj){$(obj).attr('src',($(obj).attr('src').split("2.png")[0])+'.png')}
	   function loading_juggle_small(){
		   $("body").append("<div class='loading_block'><div class='blank_circle_small'></div><div class='juggle_soccer_small'></div></div>");
	   }
	   function loading_juggle_empty_small(){
		   $("body").append("<div class='loading_block_empty'><div class='blank_circle_small'></div><div class='juggle_soccer_small'></div></div>");
	   }
	   function loading_juggle(){
		   $("body").append("<div class='loading_block'><div class='blank_circle'></div><div class='juggle_soccer'></div></div>");
	   }
	   function loading_juggle_empty(){
		   $("body").append("<div class='loading_block_empty'><div class='blank_circle'></div><div class='juggle_soccer'></div></div>");
	   }
	   function cancel_loading(){
		   $(".loading_block").remove();
		   $(".loading_block_empty").remove();
	   }

