package com.cwkj.ysms.dao;

import java.util.List;

import com.cwkj.ysms.basedao.GenericDao;
import com.cwkj.ysms.model.YsmsTeam;
import com.cwkj.ysms.model.YsmsZoneTeam;

public interface TeamDao extends GenericDao {
	/**
	 * 添加新队伍
	 * @param ysmsLeague 队伍实体
	 */
	public void save(YsmsTeam ysmsTeam);
	
	/**
	 * 删除队伍
	 * @param ysmsLeague 队伍实体
	 */
	public void delete(YsmsTeam ysmsTeam);
	
	/**
	 * 根据Id查询队伍
	 * @param teamId 队伍Id
	 * @return 查询结果
	 */
	public YsmsTeam findById(int teamId);
	
	/**
	 * 查询全部队伍
	 * @return 结果列表
	 */
	public List<YsmsTeam> findAll();
	
	/**
	 * 根据学校查询队伍
	 * @param school_id 学校Id
	 * @return 结果列表
	 */
	public List<YsmsTeam> findBySchoolId(int school_id);
	
	/**
	 * 根据学校和联赛查询队伍联赛关系
	 * @param school_id
	 * @param league_id
	 * @return
	 */
	public YsmsZoneTeam findBySchoolIdAndZoneId(int school_id, int zone_id);
	
	/**
	 * 根据zoneid查询球队
	 * 排除已经选择的球队
	 * @param zoneId
	 * @param selectedTeamId
	 * @return
	 */
	public List<YsmsTeam> findByZoneIdandGroupBesidesSelected(Integer zoneId, String groupName, Integer[] selectedTeamIds);
}
