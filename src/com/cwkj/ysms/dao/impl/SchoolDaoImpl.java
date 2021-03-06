package com.cwkj.ysms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cwkj.ysms.basedao.impl.GenericDaoImpl;
import com.cwkj.ysms.dao.SchoolDao;
import com.cwkj.ysms.model.YsmsSchool;
import com.cwkj.ysms.util.Page;
import com.cwkj.ysms.util.ToolsUtil;

@Repository
public class SchoolDaoImpl extends GenericDaoImpl implements SchoolDao {

	private static final Log log = LogFactory.getLog(SchoolDaoImpl.class);
	
	@Override
	public void save(YsmsSchool ysmsSchool) {
		log.debug("saving YsmsSchool instance");
		try {
			getSession().save(ysmsSchool);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	@Override
	public void updata(YsmsSchool ysmsSchool) {
		log.debug("updataing YsmsSchool instance");
		try {
			getSession().saveOrUpdate(ysmsSchool);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public void delete(YsmsSchool ysmsSchool) {
		log.debug("deleting YsmsSchool instance");
		try {
			getSession().delete(ysmsSchool);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public YsmsSchool findById(Integer id) {
		log.debug("getting YsmsSchool instance with id: " + id);
		try {
			String queryString = "from YsmsSchool as model where model.deleteflag = 0";
			queryString += " and model.schoolId = "+id;
			List<YsmsSchool> results = getSession().createQuery(queryString).list();
			if(results.size()>0){
				YsmsSchool result = results.get(0);
				return result;
			}else{
				return null;
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<YsmsSchool> findAll() {
		log.debug("finding all YsmsSchool instances");
		try {
			String queryString = "from YsmsSchool as model where model.deleteflag = 0 order by school_id desc";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public List<YsmsSchool> findByNameAndCategoryAndPage(String schoolName,
			String schoolCategory, Page page) {
		List<YsmsSchool> results = new ArrayList<YsmsSchool>();
		String hql = "from YsmsSchool as model where 1=1";
		if(schoolName!=null&&!schoolName.trim().equals("")){
			hql += " and model.schoolName like '%"+schoolName+"%'";
		}
		if(schoolCategory!=null&&!schoolCategory.trim().equals("")){
			hql += " and model.schoolCategory = "+schoolCategory;
		}
		hql += " and model.deleteflag = 0 order by school_id desc";
		List<Object> objects = this.findByHQLAndPage(hql, page);
		for(int i=0;i<objects.size();i++){
			results.add((YsmsSchool) objects.get(i));
		}
		return results;
	}
	@Override
	public List<YsmsSchool> findByNameAndCategory(String schoolName,
			String schoolCategory) {
		List<YsmsSchool> results = new ArrayList<YsmsSchool>();
		String hql = "from YsmsSchool as model where 1=1";
		if(schoolName!=null&&!schoolName.trim().equals("")){
			hql += " and model.schoolName like '%"+schoolName+"%'";
		}
		if(schoolCategory!=null&&!schoolCategory.trim().equals("")){
			hql += " and model.schoolCategory = "+schoolCategory;
		}
		hql += " and model.deleteflag = 0  order by school_name desc";
		List<Object> objects = this.findByHQL(hql);
		for(int i=0;i<objects.size();i++){
			results.add((YsmsSchool) objects.get(i));
		}
		return results;
	}

	@Override
	public int getSchoolCount(String schoolName, String schoolCategory) {
		// TODO Auto-generated method stub
		String hql = "from YsmsSchool as a  where  a.deleteflag = 0";
		if(!ToolsUtil.isEmpty(schoolName)){
			hql += " and a.schoolName like '%" + schoolName + "%'";
		}
		if(!ToolsUtil.isEmpty(schoolCategory)){
			hql += "and a.schoolCategory = '" + schoolCategory + "'";
		}
		int count = this.getHqlRecordCount(hql);
		return count;
	}

}
