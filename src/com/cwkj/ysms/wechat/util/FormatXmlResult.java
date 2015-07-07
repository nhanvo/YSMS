package com.cwkj.ysms.wechat.util;

import java.util.Calendar;
import java.util.List;

import com.cwkj.ysms.model.YsmsWechatnews;
import com.cwkj.ysms.wechat.entity.ReceiveXmlEntity;

/**
 * 封装返回给微信的xml数据
 * @author Administrator
 *
 */
public class FormatXmlResult {
	/**
	 * 封装xml数据
	 * @param rxe ReceiveXmlEntity对象
	 * @param tlResult 图灵机器人返回的结果
	 * @return
	 */
	public static String getXmlResult(ReceiveXmlEntity rxe, String tlResult){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(rxe.getFromUserName());
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(rxe.getToUserName());
		sb.append("]]></FromUserName><CreateTime>");
		Calendar calendar = Calendar.getInstance();
		sb.append(calendar.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
		sb.append(tlResult);
		sb.append("]]></Content></xml>");
		return sb.toString();
	}
	
}
