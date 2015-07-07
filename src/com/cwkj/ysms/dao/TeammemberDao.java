package com.cwkj.ysms.dao;

import java.util.List;

import com.cwkj.ysms.basedao.GenericDao;
import com.cwkj.ysms.model.YsmsTeammember;

public interface TeammemberDao extends GenericDao{
	/**
	 * 添加球队成员
	 * @param ysmsteammember
	 */
	public void save(YsmsTeammember ysmsTeammember);
	
	/**
	 * 删除球队成员
	 * @param ysmsTeammember
	 */
	public void delete(YsmsTeammember ysmsTeammember);
	
	/**
	 * 根据球队Id获取运动员里列表
	 * @param teamId
	 * @return 结果列表
	 */
	public List<YsmsTeammember> findAthletesByTeamId(int teamId);
	
	/**
	 * 根据球队Id获取教练员 结果里诶包
	 * @param teamId
	 * @return 结果列表
	 */
	public List<YsmsTeammember> findCoachesByTeamId(int teamId);
	
	/**
	 * 根据球员Id查询曾效力过的球队
	 * @param athleteId
	 * @return
	 */
	public List<YsmsTeammember> findTeamsByAthleteId(int athleteId);
	
	/**
	 * 根据教练Id查询曾效力过的球队
	 * @param coachesId
	 * @return
	 */
	public List<YsmsTeammember> findTeamsByCoachesId(int coachesId);
	
	/**
	 * 根据ID查找关系
	 * @param id
	 * @return
	 */
	public YsmsTeammember findById(int id);
	
	/**
	 * 根据运动员Id查询关系
	 * @param athleteId
	 * @return
	 */
	public List<YsmsTeammember> findByAthleteId(int athleteId);
	
	/**
	 * 根据教练员Id查询关系
	 * @param coachId
	 * @return
	 */
	public List<YsmsTeammember> findByCoachId(int coachId);

	/**
	 * 根据球员Id和球队查询球员关系
	 * @param athleteId
	 * @param teamId
	 * @return
	 */
	public List<YsmsTeammember> findByAthleteIdAndTeamId(Integer athleteId, int teamId);
}
