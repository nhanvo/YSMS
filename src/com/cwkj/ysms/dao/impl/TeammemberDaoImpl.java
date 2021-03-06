package com.cwkj.ysms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.cwkj.ysms.basedao.impl.GenericDaoImpl;
import com.cwkj.ysms.dao.TeammemberDao;
import com.cwkj.ysms.model.YsmsTeam;
import com.cwkj.ysms.model.YsmsTeammember;

@Repository
public class TeammemberDaoImpl extends GenericDaoImpl implements TeammemberDao{
	private static final Log log = LogFactory.getLog(TeammemberDaoImpl.class);
	@Override
	public void save(YsmsTeammember ysmsTeammember) {
		// TODO Auto-generated method stub
		log.debug("Saving YsmsTeammember instance...");
		try {
			getSession().saveOrUpdate(ysmsTeammember);
			log.debug("Save Successful!");
		} catch (Exception exception) {
			exception.printStackTrace();
			log.debug("Save Failed!");
		}
	}

	@Override
	public void delete(YsmsTeammember ysmsTeammember) {
		// TODO Auto-generated method stub
		log.debug("deleting YsmsTeammember instance");
		try {
			getSession().delete(ysmsTeammember);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public List<YsmsTeammember> findAthletesByTeamId(int teamId) {
		// TODO Auto-generated method stub
		log.debug("finding YsmsTeammember instance by teamId");
		try {
			String sql = " from YsmsTeammember where team_id = "+ teamId + 
					" and athlete_id is not null";
			List<Object> objects= findByHQL(sql);
			List<YsmsTeammember> results = new ArrayList<YsmsTeammember>();
			for(int i=0;i<objects.size();i++){
				YsmsTeammember teammember = (YsmsTeammember)objects.get(i);
				results.add(teammember);
			}
			log.debug("find by teamId successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by teamId failed", re);
			throw re;
		}
	}

	@Override
	public List<YsmsTeammember> findCoachesByTeamId(int teamId) {
		// TODO Auto-generated method stub
		log.debug("finding YsmsTeammember instance by teamId");
		try {
			String sql = " from YsmsTeammember where team_id = "+ teamId + 
					" and coach_id is not null";
			List<Object> objects= findByHQL(sql);
			List<YsmsTeammember> results = new ArrayList<YsmsTeammember>();
			for(int i=0;i<objects.size();i++){
				YsmsTeammember teammember = (YsmsTeammember)objects.get(i);
				results.add(teammember);
			}
			log.debug("find by teamId successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by teamId failed", re);
			throw re;
		}
	}

	@Override
	public List<YsmsTeammember> findTeamsByAthleteId(int athleteId) {
		// TODO Auto-generated method stub
		log.debug("finding YsmsTeammember instance by athleteId");
		try {
			String sql = " from YsmsTeammember where athlete_id = "+ athleteId;
			List<Object> objects= findByHQL(sql);
			List<YsmsTeammember> results = new ArrayList<YsmsTeammember>();
			for(int i=0;i<objects.size();i++){
				YsmsTeammember teammember = (YsmsTeammember)objects.get(i);
				results.add(teammember);
			}
			log.debug("find by athleteId successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by athleteId failed", re);
			throw re;
		}
	}

	@Override
	public List<YsmsTeammember> findTeamsByCoachesId(int coachesId) {
		// TODO Auto-generated method stub
		log.debug("finding YsmsTeammember instance by coachesId");
		try {
			String sql = " from YsmsTeammember where coach_id = "+ coachesId;
			List<Object> objects= findByHQL(sql);
			List<YsmsTeammember> results = new ArrayList<YsmsTeammember>();
			for(int i=0;i<objects.size();i++){
				YsmsTeammember teammember = (YsmsTeammember)objects.get(i);
				results.add(teammember);
			}
			log.debug("find by coachesId successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by coachesId failed", re);
			throw re;
		}
	}

	@Override
	public YsmsTeammember findById(int id) {
		// TODO Auto-generated method stub
		log.debug("getting YsmsTeammember instance with id: " + id);
		try {
			YsmsTeammember result = (YsmsTeammember) getSession()
					.get("com.cwkj.ysms.model.YsmsTeammember", id);
			return result;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<YsmsTeammember> findByAthleteId(int athleteId) {
		// TODO Auto-generated method stub
		log.debug("finding YsmsTeammember instance by athleteId");
		try {
			String sql = " from YsmsTeammember as yt where yt.ysmsAthlete.athleteId = "+ athleteId;
			List<Object> objects= findByHQL(sql);
			List<YsmsTeammember> results = new ArrayList<YsmsTeammember>();
			for(int i=0;i<objects.size();i++){
				YsmsTeammember teammember = (YsmsTeammember)objects.get(i);
				results.add(teammember);
			}
			log.debug("find by athleteId successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by athleteId failed", re);
			throw re;
		}
	}

	@Override
	public List<YsmsTeammember> findByCoachId(int coachId) {
		// TODO Auto-generated method stub
		log.debug("finding YsmsTeammember instance by coachId");
		try {
			String sql = " from YsmsTeammember as yt where yt.ysmsCoach.coachId = "+ coachId;
			List<Object> objects= findByHQL(sql);
			List<YsmsTeammember> results = new ArrayList<YsmsTeammember>();
			for(int i=0;i<objects.size();i++){
				YsmsTeammember teammember = (YsmsTeammember)objects.get(i);
				results.add(teammember);
			}
			log.debug("find by coachesId successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by coachesId failed", re);
			throw re;
		}
	}

	@Override
	public List<YsmsTeammember> findByAthleteIdAndTeamId(Integer athleteId,
			int teamId) {
		log.debug("finding YsmsTeammember instance by athleteId");
		try {
			String sql = " from YsmsTeammember as yt where yt.ysmsAthlete.athleteId = "+ athleteId + 
					" and yt.ysmsTeam.teamId = " + teamId;
			List<Object> objects= findByHQL(sql);
			List<YsmsTeammember> results = new ArrayList<YsmsTeammember>();
			for(int i=0;i<objects.size();i++){
				YsmsTeammember teammember = (YsmsTeammember)objects.get(i);
				results.add(teammember);
			}
			log.debug("find by athleteId successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by athleteId failed", re);
			throw re;
		}
	}

	@Override
	public List<YsmsTeammember> findbyTeamIdAndAthleteNum(int teamId, int athleteNum) {
		log.debug("finding YsmsTeammember instance by teamId and athleteNum");
		try {
			String sql = " from YsmsTeammember as yt where yt.athleteNum = "+ athleteNum + 
					" and yt.ysmsTeam.teamId = " + teamId;
			List<Object> objects= findByHQL(sql);
			List<YsmsTeammember> results = new ArrayList<YsmsTeammember>();
			for(int i=0;i<objects.size();i++){
				YsmsTeammember teammember = (YsmsTeammember)objects.get(i);
				results.add(teammember);
			}
			log.debug("find by athleteId successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by athleteId failed", re);
			throw re;
		}
	}

}
