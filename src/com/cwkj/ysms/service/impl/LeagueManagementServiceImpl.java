package com.cwkj.ysms.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.cwkj.ysms.dao.GamesDao;
import com.cwkj.ysms.dao.LeagueDao;
import com.cwkj.ysms.dao.LeagueLevelDao;
import com.cwkj.ysms.dao.LeagueZoneDao;
import com.cwkj.ysms.dao.SchoolDao;
import com.cwkj.ysms.dao.TeamDao;
import com.cwkj.ysms.dao.ZoneLevelDao;
import com.cwkj.ysms.dao.ZoneTeamDao;
import com.cwkj.ysms.model.YsmsGames;
import com.cwkj.ysms.model.YsmsLeague;
import com.cwkj.ysms.model.YsmsLeagueLevel;
import com.cwkj.ysms.model.YsmsLeagueZone;
import com.cwkj.ysms.model.YsmsSchool;
import com.cwkj.ysms.model.YsmsTeam;
import com.cwkj.ysms.model.YsmsZoneLevel;
import com.cwkj.ysms.model.YsmsZoneTeam;
import com.cwkj.ysms.model.view.LeagueView;
import com.cwkj.ysms.model.view.ZoneView;
import com.cwkj.ysms.service.LeagueManagementService;
import com.cwkj.ysms.util.Page;
import com.cwkj.ysms.util.ToolsUtil;

@Service
public class LeagueManagementServiceImpl implements LeagueManagementService{
	@Resource
	private LeagueDao leagueDao;
	public LeagueDao getLeagueDao() {
		return leagueDao;
	}

	public void setLeagueDao(LeagueDao leagueDao) {
		this.leagueDao = leagueDao;
	}

	@Resource
	private GamesDao gamesDao;
	public GamesDao getGamesDao() {
		return gamesDao;
	}

	public void setGamesDao(GamesDao gamesDao) {
		this.gamesDao = gamesDao;
	}

	@Resource
	private ZoneTeamDao zoneTeamDao;

	public ZoneTeamDao getZoneTeamDao() {
		return zoneTeamDao;
	}

	public void setZoneTeamDao(ZoneTeamDao zoneTeamDao) {
		this.zoneTeamDao = zoneTeamDao;
	}

	@Resource
	private TeamDao teamDao;

	public void setTeamDaol(TeamDao teamDaol) {
		this.teamDao = teamDaol;
	}
	@Resource
	private SchoolDao schoolDao;

	public TeamDao getTeamDao() {
		return teamDao;
	}

	public void setTeamDao(TeamDao teamDao) {
		this.teamDao = teamDao;
	}

	public SchoolDao getSchoolDao() {
		return schoolDao;
	}

	public void setSchoolDao(SchoolDao schoolDao) {
		this.schoolDao = schoolDao;
	}

	@Resource
	private LeagueZoneDao leagueZoneDao;

	public LeagueZoneDao getLeagueZoneDao() {
		return leagueZoneDao;
	}

	public void setLeagueZoneDao(LeagueZoneDao leagueZoneDao) {
		this.leagueZoneDao = leagueZoneDao;
	}

	@Resource
	private ZoneLevelDao zoneLevelDao;
	public ZoneLevelDao getZoneLevelDao() {
		return zoneLevelDao;
	}

	public void setZoneLevelDao(ZoneLevelDao zoneLevelDao) {
		this.zoneLevelDao = zoneLevelDao;
	}

	@Resource
	private LeagueLevelDao leagueLevelDao;

	public LeagueLevelDao getLeagueLevelDao() {
		return leagueLevelDao;
	}

	public void setLeagueLevelDao(LeagueLevelDao leagueLevelDao) {
		this.leagueLevelDao = leagueLevelDao;
	}

	@Override
	public boolean addLeague(int leagueYear, String leagueName, int leagueTotal,
			Date registerBeginTime, Date registerEndTime) {
		YsmsLeague ysmsLeague = new YsmsLeague();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, leagueYear);
		Date leagueYearDate = calendar.getTime();
		ysmsLeague.setLeagueYear(leagueYearDate);
		ysmsLeague.setLeagueTotal(leagueTotal);
		ysmsLeague.setRegisterBegintime(registerBeginTime);
		ysmsLeague.setLeagueName(leagueName);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(registerEndTime);
//		endCalendar.add(Calendar.DATE, 1);
		ysmsLeague.setRegisterEndtime(endCalendar.getTime());
		ysmsLeague.setDeleteflag(0);
		leagueDao.save(ysmsLeague);
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteLeague(int leagueId) {
		// TODO Auto-generated method stub
		YsmsLeague ysmsLeague = leagueDao.findById(leagueId);
		ysmsLeague.setDeleteflag(1);
		leagueDao.save(ysmsLeague);
		//将所有该联赛包含的组别也一并删除
		List<YsmsLeagueZone> zoneList = leagueZoneDao.findByLeagueId(leagueId);
		if(zoneList!=null){
			for(int i=0;i<zoneList.size();i++){
				YsmsLeagueZone zone = zoneList.get(i);
				zone.setDeleteflag(1);
				//将所有该组别的比赛也一并删除
				List<YsmsGames> gamesList = gamesDao.getGamesByZoneId(zone.getZoneId());
				if(gamesList!=null){
					for(int m=0;m<gamesList.size();m++){
						YsmsGames game = gamesList.get(m);
						gamesDao.delete(game);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean isModifyPermitted(int leagueId) {
		YsmsLeague ysmsLeague = leagueDao.findById(leagueId);
		Date registerDate = ysmsLeague.getRegisterBegintime();
		Date nowDate = new Date();
		if(nowDate.before(registerDate)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean modifyLeague(int leagueId, int leagueYear, String leagueName,
			Date registerBeginTime, Date registerEndTime) {
		YsmsLeague ysmsLeague = new YsmsLeague();
		ysmsLeague.setLeagueId(leagueId);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, leagueYear);
		Date date = cal.getTime();
		ysmsLeague.setLeagueYear(date);
		ysmsLeague.setRegisterBegintime(registerBeginTime);
		ysmsLeague.setLeagueName(leagueName);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(registerEndTime);
//		endCalendar.add(Calendar.DATE, 1);
		ysmsLeague.setRegisterEndtime(endCalendar.getTime());
		//修改
		//		List<YsmsLeagueTeam> teams = leagueTeamDao.getParticipatedTeam(leagueId);
		//		int leagueTotal = calulatorLeagueTotal(teams.size());
		ysmsLeague.setLeagueTotal(0); 
		ysmsLeague.setDeleteflag(0);
		leagueDao.save(ysmsLeague);
		return true;
	}

	@Override
	public boolean modifyLeagueRegisterDateAndName(int leagueId, String leagueName, Date registerEndTime){
		YsmsLeague ysmsLeague = leagueDao.findById(leagueId);
		if(ysmsLeague.getRegisterBegintime().before(registerEndTime)){
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(registerEndTime);
//			endCalendar.add(Calendar.DATE, 1);
			ysmsLeague.setRegisterEndtime(endCalendar.getTime());
			ysmsLeague.setLeagueName(leagueName);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<YsmsLeague> getAllLeagues() {
		return leagueDao.findAll();
	}

	@Override
	public List<YsmsLeague> getLeaguesByPage(Page page) {
		return leagueDao.findAllByPage(page);
	}

	@Override
	public List<YsmsLeague> getYearlyLeagues(int year) {
		return leagueDao.findByLeagueYear(year);
	}

	@Override
	public List<YsmsLeague> getYearlyLeaguesByPage(int year, Page page) {
		return leagueDao.findByLeagueYearAndPage(year, page);
	}

	@Override
	public List<YsmsGames> groupTeamsForZone(int zoneId, Map groupedTeams, 
			int promotionTeamNum, boolean isDoubleRound) {
		List<YsmsGames> games = new ArrayList<YsmsGames>();
		YsmsLeagueZone ysmsLeagueZone = leagueZoneDao.findById(zoneId);
		for(Iterator iter = groupedTeams.entrySet().iterator();iter.hasNext();){ 
			Map.Entry element = (Map.Entry)iter.next(); 
			String groupName = element.getKey().toString();
			String groupTeams = element.getValue().toString();
			String[] teamIds = groupTeams.split(",");
			for(int i=0;i<teamIds.length;i++){
				List<Object> list = zoneTeamDao
						.findByHQL("from YsmsZoneTeam zt where zt.ysmsLeagueZone.zoneId = " + zoneId 
								+ " and zt.ysmsTeam.teamId = " + Integer.parseInt(teamIds[i]));
				for(int j=0;j<list.size();j++){
					YsmsZoneTeam ysmsZoneTeam = (YsmsZoneTeam) list.get(j);
					ysmsZoneTeam.setZoneGroup(groupName);
					zoneTeamDao.save(ysmsZoneTeam);
				}
			}
			if (isDoubleRound){
				for(int a = 0;a<teamIds.length;a++){
					YsmsTeam hostTeam = teamDao.findById(Integer.parseInt(teamIds[a]));
					for(int b = 0;b<teamIds.length;b++){
						if(a!=b){
							YsmsGames match = new YsmsGames();
							YsmsTeam guestTeam = teamDao.findById(Integer.parseInt(teamIds[b]));
							match.setYsmsTeamByGuestTeamid(guestTeam);
							match.setYsmsTeamByHostTeamid(hostTeam);
							match.setGamesOrder(0);
							match.setYsmsLeagueZone(ysmsLeagueZone);
							gamesDao.save(match);
							games.add(match);
						}
					}
				}
			}else{
				for(int a = 0;a<teamIds.length;a++){
					YsmsTeam hostTeam = teamDao.findById(Integer.parseInt(teamIds[a]));
					for(int b = a;b<teamIds.length;b++){
						if(a!=b){
							YsmsGames match = new YsmsGames();
							YsmsTeam guestTeam = teamDao.findById(Integer.parseInt(teamIds[b]));
							match.setYsmsTeamByGuestTeamid(guestTeam);
							match.setYsmsTeamByHostTeamid(hostTeam);
							match.setGamesOrder(0);
							match.setYsmsLeagueZone(ysmsLeagueZone);
							gamesDao.save(match);
							games.add(match);
						}
					}
				}
			}
		}
		return games;		
	}

	@Override
	public int calulatorLeagueTotal(int totalTeams) {
		// TODO 根据参加球队数计算联赛总场次
		return 0;
	}

	@Override
	public YsmsLeague getLeagueById(int leagueId) {
		// TODO Auto-generated method stub
		return leagueDao.findById(leagueId);
	}

	@Override
	public boolean isRegisterEnd(int leagueId) {
		YsmsLeague ysmsLeague = leagueDao.findById(leagueId);
		Date registerDate = ysmsLeague.getRegisterEndtime();
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyyMMdd");
		String theDateStr = sdf.format(registerDate);
		Date nowDate = new Date();
		String nowDateStr = sdf.format(nowDate);
		if(nowDateStr.compareTo(theDateStr)>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<LeagueView> getYearlyLeagueViews(int year, int schoolId) {
		List<YsmsLeague> list = this.getYearlyLeagues(year);
		List<LeagueView> league_list = new ArrayList<LeagueView>();
		YsmsSchool school = schoolDao.findById(schoolId);
		if(list!=null){
			for(int i=0;i<list.size();i++){
				YsmsLeague ysmsLeague = list.get(i);
				if(ysmsLeague.getDeleteflag()==0){
					LeagueView leagueView = new LeagueView();
					leagueView.setLeagueId(ysmsLeague.getLeagueId());
					Date yearDate = ysmsLeague.getLeagueYear();
					Calendar yearCalendar = Calendar.getInstance();
					yearCalendar.setTime(yearDate);
					leagueView.setLeagueYear(yearCalendar.get(Calendar.YEAR));
					leagueView.setTotalNumber(ysmsLeague.getLeagueTotal());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					leagueView.setRegisterStartDate(sdf.format(ysmsLeague.getRegisterBegintime()));
					leagueView.setRegisterEndDate(sdf.format(ysmsLeague.getRegisterEndtime()));
					leagueView.setModifyAllowed(this.isModifyPermitted(ysmsLeague
							.getLeagueId())==true?1:0);
					leagueView.setRegisterEnd(this.isRegisterEnd(ysmsLeague
							.getLeagueId())?1:0);					
					String leagueStatus = "";
					if(leagueView.isModifyAllowed()==1)
						leagueStatus = "未开始";
					else if(leagueView.isRegisterEnd()==1)
						leagueStatus = "已截止";
					else
						leagueStatus = "进行中";
					leagueView.setLeagueStatus(leagueStatus);
					leagueView.setLeagueName(ysmsLeague.getLeagueName());
					league_list.add(leagueView);
				}
			}
			for(int i=0;i<league_list.size();i++){
				for(int j=i+1;j<league_list.size();j++){
					LeagueView view_i = league_list.get(i);
					LeagueView view_j = league_list.get(j);
					if(view_i.getLeagueStatus().compareTo(view_j.getLeagueStatus())<0){
						league_list.set(i, view_j);
						league_list.set(j, view_i);
					}
				}
			}
		}
		return league_list;
	}

	@Override
	public List<ZoneView> getZonesByLeague(int leagueId) {
		List<YsmsLeagueZone> zoneList = leagueZoneDao.findByLeagueId(leagueId);
		List<ZoneView> result = new ArrayList<ZoneView>();
		for(int i=0;i<zoneList.size();i++){
			YsmsLeagueZone leagueZone = zoneList.get(i);
			ZoneView zv = new ZoneView();
			zv.setZoneId(leagueZone.getZoneId());
			zv.setZoneName(leagueZone.getZoneName());
//			zv.setRegisteredZoneTeamCount(zoneTeamDao.getSignedTeam(leagueZone.getZoneId(), 0).size());
			List<YsmsZoneLevel> zoneLevelList = zoneLevelDao.findByZoneId(leagueZone.getZoneId());
			StringBuffer levelStr = new StringBuffer("");
			boolean[] levelArray = new boolean[22];
			if(zoneLevelList!=null){
				for(int m=0;m<zoneLevelList.size();m++){
					levelStr.append(zoneLevelList.get(m).getYsmsLeagueLevel().getLevelName() + ",");
					levelArray[zoneLevelList.get(m).getYsmsLeagueLevel().getLevelId()-1] = true;
				}
			}
			if(levelStr.lastIndexOf(",")>0)
				levelStr = new StringBuffer(levelStr.substring(0, levelStr.lastIndexOf(",")));
			zv.setLevelStr(levelStr.toString());
			zv.setLevelArray(levelArray);
			result.add(zv);
		}
		return result;
	}

	@Override
	public List<YsmsLeagueLevel> getAllLevels() {
		return leagueLevelDao.findAll();
	}

	@Override
	public boolean addZone(int leagueId, String zoneName, String[] levelIndexes) {
		YsmsLeagueZone ysmsLeagueZone = new YsmsLeagueZone();
		ysmsLeagueZone.setYsmsLeague(leagueDao.findById(leagueId));
		ysmsLeagueZone.setZoneName(zoneName);
		ysmsLeagueZone.setDeleteflag(0);
		leagueZoneDao.save(ysmsLeagueZone);

		for(int i=0;i<levelIndexes.length;i++){
			if(!"".equals(levelIndexes[i])){
				YsmsLeagueLevel ysmsLeagueLevel = leagueLevelDao.findById(Integer.parseInt(levelIndexes[i]));
				YsmsZoneLevel ysmsZoneLevel = new YsmsZoneLevel();
				ysmsZoneLevel.setYsmsLeagueLevel(ysmsLeagueLevel);
				ysmsZoneLevel.setYsmsLeagueZone(ysmsLeagueZone);
				zoneLevelDao.save(ysmsZoneLevel);
			}
		}
		return true;
	}

	@Override
	public boolean modifyZone(int zoneId, String zoneName, String[] levelIndexes) {
		YsmsLeagueZone ysmsLeagueZone = leagueZoneDao.findById(zoneId);
		ysmsLeagueZone.setZoneName(zoneName);
		leagueZoneDao.save(ysmsLeagueZone);

		List<YsmsZoneLevel> level_list = zoneLevelDao.findByZoneId(zoneId);
		for(int i=0;i<level_list.size();i++){
			zoneLevelDao.delete(level_list.get(i));
		}
		for(int i=0;i<levelIndexes.length;i++){
			YsmsLeagueLevel ysmsLeagueLevel = leagueLevelDao.findById(Integer.parseInt(levelIndexes[i]));
			YsmsZoneLevel ysmsZoneLevel = new YsmsZoneLevel();
			ysmsZoneLevel.setYsmsLeagueLevel(ysmsLeagueLevel);
			ysmsZoneLevel.setYsmsLeagueZone(ysmsLeagueZone);
			zoneLevelDao.save(ysmsZoneLevel);
		}
		return true;
	}

	@Override
	public boolean deleteZone(int zoneId) {
		YsmsLeagueZone ysmsLeagueZone = leagueZoneDao.findById(zoneId);
		ysmsLeagueZone.setDeleteflag(1);
		leagueZoneDao.save(ysmsLeagueZone);
		return true;
	}

	@Override
	public List<ZoneView> getZonesByLeagueAndSchool(int leagueId, int schoolId) {
		YsmsSchool ysmsSchool = schoolDao.findById(schoolId);
		int category = ysmsSchool.getSchoolCategory();
		String schoolLevelsStr = "";
		switch (category){
		case 1:
			schoolLevelsStr = "1,2,3,4,5,6,7,8,9,10";
			break;
		case 2:
			schoolLevelsStr = "11,12,13,14,15,16";
			break;
		case 3:
			schoolLevelsStr = "17,18,19,20,21,22";
			break;
		}
		String[] levelStrArray = schoolLevelsStr.split(",");
		int[] levelArray = new int[levelStrArray.length];
		for(int i=0;i<levelStrArray.length;i++){
			levelArray[i] = Integer.parseInt(levelStrArray[i]);
		}
		List<ZoneView> zoneList = this.getZonesByLeague(leagueId);
		if(zoneList!=null){
			for(int i=0;i<zoneList.size();i++){
				ZoneView zoneView = zoneList.get(i);
				boolean containsLevel = false;
				List<YsmsZoneLevel> zonelevels = zoneLevelDao.findByZoneId(zoneView.getZoneId());
				if(zonelevels!=null){
					for(int m=0;m<levelArray.length;m++){
						for(int n=0;n<zonelevels.size();n++){
							if(levelArray[m]==zonelevels.get(n).getYsmsLeagueLevel().getLevelId()){
								containsLevel = true;
							}
						}
					}
					if(containsLevel == false){
						zoneView.setZoneStatus(1);
					}
					else{
						YsmsZoneTeam zoneTeam = teamDao.findBySchoolIdAndZoneId(schoolId, zoneView.getZoneId());
						if(zoneTeam==null){
							zoneView.setZoneStatus(0);//未报名
						}
						else if(zoneTeam.getZoneGroup()==null||zoneTeam.getZoneGroup().equals("")){
							zoneView.setZoneStatus(0);//未报名
						}
						else{
							zoneView.setZoneStatus(2);//报名
						}
					}
					zoneList.set(i, zoneView);
				}
			}
		}
		return zoneList;
	}

	@Override
	public boolean groupTeamsForZone(int zoneId,  Map<String,Object> groupedTeams) {
			boolean  a=true;
			boolean  b=true;
			boolean  c=true;
			boolean  d=true;
			List<Integer> list=new ArrayList<Integer>();
			List<Integer> list_a=(List<Integer>) groupedTeams.get("A");
			List<Integer> list_b=(List<Integer>) groupedTeams.get("B");
			List<Integer> list_c=(List<Integer>) groupedTeams.get("C");
			List<Integer> list_d=(List<Integer>) groupedTeams.get("D");
			list.addAll(list_a);
			list.addAll(list_b);
			list.addAll(list_c);
			list.addAll(list_d);
			if(!ToolsUtil.isEmpty(list_a)){		 
				a=zoneTeamDao.update(zoneId, list_a, "A");
			}
			if(!ToolsUtil.isEmpty( list_b)){
				b=zoneTeamDao.update(zoneId, list_b, "B");
			}
			if(!ToolsUtil.isEmpty( list_c)){
				c=zoneTeamDao.update(zoneId, list_c, "C");
			}
			if(!ToolsUtil.isEmpty( list_d)){
				 d=zoneTeamDao.update(zoneId, list_d, "D");
			}
			zoneTeamDao.fixUpdate(zoneId, list);
			if(a==b==c==d==true){
				return true;
			} 
			return false;	
			 
			
	}

	@Override
	public List<Map<String,Object>> getNotSelectedTeams(int zoneId,
			List<Integer> teamIds) {
		 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			list= zoneTeamDao.seletedTeam(zoneId, teamIds);
		}catch(Exception e){
			
		}
		return list;
	}
}
