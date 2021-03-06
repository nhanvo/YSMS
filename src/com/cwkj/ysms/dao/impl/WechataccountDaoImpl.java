package com.cwkj.ysms.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cwkj.ysms.basedao.impl.GenericDaoImpl;
import com.cwkj.ysms.dao.WechataccountDao;
import com.cwkj.ysms.model.YsmsAthlete;
import com.cwkj.ysms.model.YsmsWechataccount;

@Repository
public class WechataccountDaoImpl extends GenericDaoImpl implements WechataccountDao {
	private static final Log log = LogFactory.getLog(WechataccountDaoImpl.class);
	
	@Override
	public void save(YsmsWechataccount ysmsWechataccount) {
		log.debug("saving YsmsWechataccount instance");
		try {
			getSession().saveOrUpdate(ysmsWechataccount);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void delete(YsmsWechataccount ysmsWechataccount) {
		log.debug("deleting YsmsWechataccount instance");
		try {
			Query query = getSession().createQuery(
					"update from YsmsWechataccount set deleteflag=1 where wid=?");
			query.setParameter(0, ysmsWechataccount.getWid());
			query.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public YsmsWechataccount findById(Integer wid) {
		log.debug("getting YsmsWechataccount instance with athleteId: " + wid);
		try {
			YsmsWechataccount result = (YsmsWechataccount) getSession().get(
					"com.cwkj.ysms.model.YsmsWechataccount", wid);
			return result;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<YsmsWechataccount> findAll() {
		log.debug("finding all YsmsWechataccount instances");
		try {
			String queryString = "from YsmsWechataccount where deleteflag = 0";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public YsmsWechataccount findByOpenid(String openId) {
		log.debug("finding all YsmsAthlete instances");
		if(openId == null||"".equals(openId))
			return null;
		try {
			String queryString = "from YsmsWechataccount where deleteflag = 0 and openId = '" + openId +"'";
			Query queryObject = getSession().createQuery(queryString);
			List<YsmsWechataccount> list = queryObject.list();
			if(list.size()>0)
				return list.get(0);
			else
				return null;
		} catch (RuntimeException re) {
			log.error("find by openid failed", re);
			throw re;
		}
	}

}
