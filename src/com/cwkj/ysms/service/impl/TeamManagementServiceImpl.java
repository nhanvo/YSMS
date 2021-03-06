package com.cwkj.ysms.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.cwkj.ysms.dao.AthleteDao;
import com.cwkj.ysms.dao.CoachDao;
import com.cwkj.ysms.dao.LeagueDao;
import com.cwkj.ysms.dao.LeagueZoneDao;
import com.cwkj.ysms.dao.SchoolDao;
import com.cwkj.ysms.dao.TeamDao;
import com.cwkj.ysms.dao.TeammemberDao;
import com.cwkj.ysms.dao.ZoneLevelDao;
import com.cwkj.ysms.dao.ZoneTeamDao;
import com.cwkj.ysms.model.YsmsAthlete;
import com.cwkj.ysms.model.YsmsCoach;
import com.cwkj.ysms.model.YsmsLeagueZone;
import com.cwkj.ysms.model.YsmsSchool;
import com.cwkj.ysms.model.YsmsTeam;
import com.cwkj.ysms.model.YsmsTeammember;
import com.cwkj.ysms.model.YsmsZoneLevel;
import com.cwkj.ysms.model.YsmsZoneTeam;
import com.cwkj.ysms.model.view.ExcelAthleteView;
import com.cwkj.ysms.model.view.ExcelCoachView;
import com.cwkj.ysms.model.view.MemberAthleteView;
import com.cwkj.ysms.model.view.TeamView;
import com.cwkj.ysms.service.TeamManagementService;

@Component
public class TeamManagementServiceImpl implements TeamManagementService{
	@Resource
	private TeamDao teamDao;
	public TeamDao getTeamDao() {
		return teamDao;
	}

	public void setTeamDao(TeamDao teamDao) {
		this.teamDao = teamDao;
	}

	@Resource
	private SchoolDao schoolDao;
	public SchoolDao getSchoolDao() {
		return schoolDao;
	}

	public void setSchoolDao(SchoolDao schoolDao) {
		this.schoolDao = schoolDao;
	}

	@Resource
	private TeammemberDao teammemberDao;

	public TeammemberDao getTeammemberDao() {
		return teammemberDao;
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
	private AthleteDao athleteDao;

	public AthleteDao getAthleteDao() {
		return athleteDao;
	}

	public void setAthleteDao(AthleteDao athleteDao) {
		this.athleteDao = athleteDao;
	}

	@Resource
	private CoachDao coachDao;

	public CoachDao getCoachDao() {
		return coachDao;
	}

	public void setCoachDao(CoachDao coachDao) {
		this.coachDao = coachDao;
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
	private LeagueDao leagueDao;

	public LeagueDao getLeagueDao() {
		return leagueDao;
	}

	public void setLeagueDao(LeagueDao leagueDao) {
		this.leagueDao = leagueDao;
	}

	@Resource
	private ZoneLevelDao zoneLevelDao;
	public ZoneLevelDao getZoneLevelDao() {
		return zoneLevelDao;
	}

	public void setZoneLevelDao(ZoneLevelDao zoneLevelDao) {
		this.zoneLevelDao = zoneLevelDao;
	}

	public void setTeammemberDao(TeammemberDao teammemberDao) {
		this.teammemberDao = teammemberDao;
	}

	@Override
	public boolean addTeam(int zoneId, int schoolId,String teamName,
			List<YsmsCoach> coach_list, List<YsmsAthlete> athlete_list) {
		YsmsTeam ysmsTeam = new YsmsTeam();
		ysmsTeam.setYsmsSchool(schoolDao.findById(schoolId));
		ysmsTeam.setTeamName(teamName);
		ysmsTeam.setDeleteflag(0);
		teamDao.save(ysmsTeam);
		//添加教练名单
		if(coach_list!=null){
			for(int i=0;i<coach_list.size();i++){
				YsmsTeammember ysmsTeammember = new YsmsTeammember();
				ysmsTeammember.setYsmsTeam(ysmsTeam);
				ysmsTeammember.setYsmsCoach(coach_list.get(i));
				teammemberDao.save(ysmsTeammember);
			}
		}
		//添加球员名单
		if(athlete_list!=null){
			for(int i=0;i<athlete_list.size();i++){
				YsmsTeammember ysmsTeammember = new YsmsTeammember();
				ysmsTeammember.setYsmsTeam(ysmsTeam);
				ysmsTeammember.setYsmsAthlete(athlete_list.get(i));
				teammemberDao.save(ysmsTeammember);
			}
		}

		YsmsZoneTeam ysmsZoneTeam = new YsmsZoneTeam();
		ysmsZoneTeam.setYsmsLeagueZone(leagueZoneDao.findById(zoneId));
		ysmsZoneTeam.setYsmsTeam(ysmsTeam);
		zoneTeamDao.save(ysmsZoneTeam);
		return true;
	}

	@Override
	public boolean modifyTeam(int teamId, String teamName, List<YsmsCoach> coach_list,
			List<YsmsAthlete> athlete_list) {
		// TODO Auto-generated method stub
		YsmsTeam ysmsTeam = teamDao.findById(teamId);
		ysmsTeam.setTeamName(teamName);
		teamDao.save(ysmsTeam);
		//清空现有的球队成员关系
		List<YsmsTeammember> latest_athletes = teammemberDao.findAthletesByTeamId(teamId);
		if(latest_athletes!=null){
			for(int i=0;i<latest_athletes.size();i++){
				teammemberDao.delete(latest_athletes.get(i));
			}
		}
		List<YsmsTeammember> latest_coaches = teammemberDao.findCoachesByTeamId(teamId);
		if(latest_coaches!=null){
			for(int i=0;i<latest_coaches.size();i++){
				teammemberDao.delete(latest_coaches.get(i));
			}
		}
		//添加教练名单
		if(coach_list!=null){
			for(int i=0;i<coach_list.size();i++){
				YsmsTeammember ysmsTeammember = new YsmsTeammember();
				ysmsTeammember.setYsmsTeam(ysmsTeam);
				ysmsTeammember.setYsmsCoach(coach_list.get(i));
				teammemberDao.save(ysmsTeammember);
			}
		}
		//添加球员名单
		if(athlete_list!=null){
			for(int i=0;i<athlete_list.size();i++){
				YsmsTeammember ysmsTeammember = new YsmsTeammember();
				ysmsTeammember.setYsmsTeam(ysmsTeam);
				ysmsTeammember.setYsmsAthlete(athlete_list.get(i));
				teammemberDao.save(ysmsTeammember);
			}
		}
		return true;
	}

	@Override
	public boolean deleteTeam(int teamId) {
		// TODO Auto-generated method stub
		YsmsTeam ysmsTeam = teamDao.findById(teamId);
		ysmsTeam.setDeleteflag(1);
		teamDao.save(ysmsTeam);
		//清除所有球队关联信息
		List<YsmsTeammember> athletesList = teammemberDao.findAthletesByTeamId(teamId);
		List<YsmsTeammember> coachesList = teammemberDao.findCoachesByTeamId(teamId);
		if(athletesList!=null){
			for(int i=0;i<athletesList.size();i++){
				teammemberDao.delete(athletesList.get(i));
			}
		}
		if(coachesList!=null){
			for(int i=0;i<coachesList.size();i++){
				teammemberDao.delete(coachesList.get(i));
			}
		}
		YsmsZoneTeam ysmsZoneTeam = zoneTeamDao.findByTeamId(teamId);
		zoneTeamDao.delete(ysmsZoneTeam);
		return true;
	}

	@Override
	public List<YsmsTeam> getTeamInfoBySchool(int schoolId) {
		// TODO Auto-generated method stub
		return teamDao.findBySchoolId(schoolId);
	}

	@Override
	public List<TeamView> getSignedTeamByZone(int zoneId,int pageIndex ) {
		List<TeamView> teamViewList = new ArrayList<TeamView>();
		int startIndex = ( pageIndex - 1) * 10;
		List<YsmsZoneTeam> ysmsZoneTeamList = zoneTeamDao.getSignedTeam(zoneId,startIndex);
		for (int i=0;i<ysmsZoneTeamList.size();i++) {
			YsmsTeam ysmsTeam = ysmsZoneTeamList.get(i).getYsmsTeam();
			TeamView teamView = new TeamView();
			teamView.setSchoolName(ysmsTeam.getYsmsSchool().getSchoolName());
			teamView.setSchoolCategory(ysmsTeam.getYsmsSchool().getSchoolCategory());
			teamView.setTeamId(ysmsTeam.getTeamId());
			teamView.setTeamName(ysmsTeam.getTeamName());
			teamViewList.add(teamView);
		}
		return teamViewList;
	}

	@Override
	public List<TeamView> getParticipatedTeamByZone(int zoneId) {
		List<TeamView> teamViewList = new ArrayList<TeamView>();
		List<YsmsZoneTeam> ysmsZoneTeamList = zoneTeamDao.getParticipatedTeam(zoneId);
		for (int i=0;i<ysmsZoneTeamList.size();i++) {
			YsmsTeam ysmsTeam = ysmsZoneTeamList.get(i).getYsmsTeam();
			TeamView teamView = new TeamView();
			teamView.setSchoolName(ysmsTeam.getYsmsSchool().getSchoolName());
			teamView.setSchoolCategory(ysmsTeam.getYsmsSchool().getSchoolCategory());
			teamView.setTeamId(ysmsTeam.getTeamId());
			teamView.setTeamName(ysmsTeam.getTeamName());
			teamViewList.add(teamView);
		}
		return teamViewList;
	}

	@Override
	public YsmsLeagueZone getZoneByTeam(int teamId) {
		// TODO 导出Excel表格格式待定
		YsmsZoneTeam zoneTeam = zoneTeamDao.findByTeamId(teamId);
		return zoneTeam.getYsmsLeagueZone();
	}

	@Override
	public boolean addAthleteToTeam(int teamId, int athleteId) {
		YsmsAthlete athlete = athleteDao.findById(athleteId);
		YsmsTeammember ysmsTeammember = new YsmsTeammember();
		ysmsTeammember.setYsmsAthlete(athlete);
		ysmsTeammember.setYsmsTeam(teamDao.findById(teamId));
		teammemberDao.save(ysmsTeammember);
		return true;
	}

	@Override
	public boolean addCoachToTeam(int teamId, int coachId) {
		YsmsCoach coach = coachDao.findById(coachId);
		YsmsTeammember ysmsTeammember = new YsmsTeammember();
		ysmsTeammember.setYsmsCoach(coach);
		ysmsTeammember.setYsmsTeam(teamDao.findById(teamId));
		teammemberDao.save(ysmsTeammember);
		return true;
	}

	@Override
	public boolean signToZone(int teamId) {
		YsmsZoneTeam ysmsZoneTeam = zoneTeamDao.findByTeamId(teamId);
		ysmsZoneTeam.setZoneGroup("0");
		zoneTeamDao.save(ysmsZoneTeam);
		return true;
	}

	@Override
	public List<YsmsAthlete> getAthletesForTeam(String athletesStr) {
		// TODO Auto-generated method stub
		String[] athletes = athletesStr.split(",");
		List<YsmsAthlete> athleteList = new ArrayList<YsmsAthlete>();
		for(int i=0;i<athletes.length;i++){
			int athleteId = Integer.parseInt(athletes[i]);
			YsmsAthlete ysmsAthlete = athleteDao.findById(athleteId);
			athleteList.add(ysmsAthlete);
		}
		return athleteList;
	}

	@Override
	public List<YsmsCoach> getCoachesForTeam(String coachStr) {
		// TODO Auto-generated method stub
		String[] coaches = coachStr.split(",");
		List<YsmsCoach> coachList = new ArrayList<YsmsCoach>();
		for(int i=0;i<coaches.length;i++){
			int coachId = Integer.parseInt(coaches[i]);
			YsmsCoach ysmsCoach = coachDao.findById(coachId);
			coachList.add(ysmsCoach);
		}
		return coachList;
	}

	@Override
	public YsmsTeam getTeamForSchoolAndZone(int schoolId, int zoneId) {
		// TODO Auto-generated method stub
		List<Object> teamList = zoneTeamDao
				.findByHQL("select t from YsmsTeam as t, YsmsZoneTeam as zt" + 
						" where t.ysmsSchool.schoolId =" + schoolId + 
						" and t.teamId = zt.ysmsTeam.teamId and zt.ysmsLeagueZone.zoneId = " + zoneId);
		if(teamList!=null&&teamList.size()>0){
			return (YsmsTeam) teamList.get(0);
		}
		else
			return null;
	}

	@Override
	public YsmsTeam getTeamById(int teamId) {
		YsmsTeam ysmsTeam = teamDao.findById(teamId);
		if(ysmsTeam != null){
			return ysmsTeam;
		}else{
			return null;
		}
	}

	@Override
	public List<ExcelAthleteView> getAthletesForTeam(int teamId) {
		List<ExcelAthleteView> athleteViews = new ArrayList<ExcelAthleteView>();
		List<YsmsTeammember> ysmsTeammembers = teammemberDao.findAthletesByTeamId(teamId);
		int teammemberSize = ysmsTeammembers.size();
		for(int i=0;i<teammemberSize;i++){
			YsmsTeammember ysmsTeammember = ysmsTeammembers.get(i);
			YsmsAthlete ysmsAthlete = ysmsTeammember.getYsmsAthlete();
			ExcelAthleteView athleteView = new ExcelAthleteView();
			athleteView.setAthleteTeammemberId(ysmsTeammember.getDetailId());
			athleteView.setAthletePosition(ysmsTeammember.getYsmsAthlete().getAthletePosition());
			athleteView.setAthleteNum(ysmsTeammember.getAthleteNum());
			athleteView.setIdentifiedName(ysmsAthlete.getIdentifiedName());
			athleteView.setIdentifiedId(ysmsAthlete.getIdentifiedId());
			athleteView.setAthleteHeight(ysmsAthlete.getAthleteHeight());
			athleteView.setAthleteWeight(ysmsAthlete.getAthleteWeight());
			athleteView.setStudyNum(ysmsAthlete.getStudentId()+"");
			athleteView.setAthleteMobile(ysmsAthlete.getAthleteMobile());
			athleteView.setRemark("");
			athleteViews.add(athleteView);
		}
		return athleteViews;
	}

	@Override
	public List<ExcelCoachView> getCoachsForTeam(int teamId) {
		List<ExcelCoachView> coachViews = new ArrayList<ExcelCoachView>();
		List<YsmsTeammember> ysmsTeammembers = teammemberDao.findCoachesByTeamId(teamId);
		int teammemberSize = ysmsTeammembers.size();
		for(int i=0;i<teammemberSize;i++){
			YsmsTeammember ysmsTeammember = ysmsTeammembers.get(i);
			YsmsCoach ysmsCoach = ysmsTeammember.getYsmsCoach();
			ExcelCoachView excelCoachView = new ExcelCoachView();
			excelCoachView.setCoachTeammemberId(ysmsTeammember.getDetailId());
			excelCoachView.setCoachName(ysmsCoach.getIdentifiedName());
			excelCoachView.setCoachMobile(ysmsCoach.getCoachMobile());
			excelCoachView.setIdentifiedId(ysmsCoach.getIdentifiedId());
			excelCoachView.setGender(ysmsCoach.getIdentifiedGender());
			excelCoachView.setRemark("");
			coachViews.add(excelCoachView);
		}
		return coachViews;
	}

	@Override
	public boolean simplyAddTeam(int zoneId, int schoolId) {
		YsmsTeam ysmsTeam = new YsmsTeam();
		ysmsTeam.setYsmsSchool(schoolDao.findById(schoolId));
		ysmsTeam.setDeleteflag(0);
		teamDao.save(ysmsTeam);

		YsmsZoneTeam ysmsZoneTeam = new YsmsZoneTeam();
		ysmsZoneTeam.setYsmsLeagueZone(leagueZoneDao.findById(zoneId));
		ysmsZoneTeam.setYsmsTeam(ysmsTeam);
		zoneTeamDao.save(ysmsZoneTeam);
		return true;
	}

	@Override
	public boolean modifyTeamName(int teamId, String teamName) {
		YsmsTeam ysmsTeam = teamDao.findById(teamId);
		ysmsTeam.setTeamName(teamName);
		teamDao.save(ysmsTeam);
		return true;
	}

	@Override
	public boolean changeAthleteNum(int teamMemberId, int athleteNum) {
		// TODO Auto-generated method stub
		YsmsTeammember ysmsTeammember = teammemberDao.findById(teamMemberId);
		int teamId = ysmsTeammember.getYsmsTeam().getTeamId();
		List<YsmsTeammember> ysmsTeammemberSameNumList = teammemberDao.findbyTeamIdAndAthleteNum(teamId, athleteNum);
		if(ysmsTeammemberSameNumList.size()>0)
			return false;
		ysmsTeammember.setAthleteNum(athleteNum);
		teammemberDao.save(ysmsTeammember);
		return true;
	}
	
	@Override
	public boolean changeAthletePosition(int teamMemberId, int athletePosition){
		YsmsTeammember ysmsTeammember = teammemberDao.findById(teamMemberId);
		YsmsAthlete teamAthlete = ysmsTeammember.getYsmsAthlete();
		YsmsAthlete athlete = athleteDao.findById(teamAthlete.getAthleteId());
		athlete.setAthletePosition(athletePosition);
		athleteDao.save(athlete);
		return true;
	}

	@Override
	public List<YsmsAthlete> getAthletesByProperty(Integer schoolId, String identifiedId,
			String identifiedName, Integer identifiedGender, Integer athletePosition,
			Integer studentId,String athleteSchoolyear) {
		List<YsmsAthlete> athlete_list = athleteDao.findByFuzzyQuery(schoolId, identifiedId, identifiedName, identifiedGender, 
															 athletePosition, studentId, athleteSchoolyear);
		return athlete_list;
	}
	
	@Override
	public List<YsmsAthlete> getAthletesByPropertyAndZoneId(Integer schoolId, String identifiedId,
			String identifiedName, Integer identifiedGender, Integer athletePosition,
			Integer studentId,String athleteSchoolyear, int zoneId){
		List<YsmsZoneLevel> zonelevels = zoneLevelDao.findByZoneId(zoneId);
		List<YsmsAthlete> athleteList = new ArrayList<YsmsAthlete>();
		for(int i=0;i<zonelevels.size();i++){
			int level = zonelevels.get(i).getYsmsLeagueLevel().getLevelId();
			int gender = -1;
			if(level >= 1 && level <= 22){
				if(level % 2 == 0){
					gender = 0;
				}
				else if(level % 2 == 1){
					gender = 1;
				}
			}
			int yearJoinSchool;
			Calendar calendar = Calendar.getInstance();
			if(calendar.get(Calendar.MONTH)>=8){ //现在是九月份之后
				//那么入学年应该是当前年份-年级数+1
				yearJoinSchool = calendar.get(Calendar.YEAR) - (int)(Math.ceil(level/2.0) + 1) + 1;
			}
			else{ //如果在9月份之前
				yearJoinSchool = calendar.get(Calendar.YEAR) - (int)(Math.ceil(level/2.0) + 1);
			}
			athleteList.addAll(this.getAthletesByProperty(schoolId, identifiedId, identifiedName, gender, athletePosition, studentId, yearJoinSchool+""));
		}
		return athleteList;
	}

	@Override
	public List<YsmsAthlete> getAthletesByTeamId(int teamId) {
		List<YsmsTeammember> teammember_list = teammemberDao.findAthletesByTeamId(teamId);
		List<YsmsAthlete> athlete_list = null;
		if(teammember_list != null && teammember_list.size() > 0){
			athlete_list = new ArrayList<YsmsAthlete>();
			for(YsmsTeammember teammerber : teammember_list){
				if(teammerber != null && teammerber.getYsmsAthlete() != null){
					athlete_list.add(teammerber.getYsmsAthlete());
				}
			}
		}
		return athlete_list;
	}

	@Override
	public List<YsmsCoach> getCoachesByProperty(String identifiedId, Integer schoolId,
			String coachContact, String identifiedName, Integer identifiedGender) {
		List<YsmsCoach> coach_list = coachDao.findByFuzzyQuery(identifiedId, schoolId, coachContact, 
															   identifiedName, identifiedGender);
		return coach_list;
	}

	@Override
	public List<YsmsCoach> getCoachesByTeamId(int teamId) {
		List<YsmsTeammember> teammember_list = teammemberDao.findCoachesByTeamId(teamId);
		List<YsmsCoach> coach_list = null;
		if(teammember_list != null && teammember_list.size() > 0){
			coach_list = new ArrayList<YsmsCoach>();
			for(YsmsTeammember teammerber : teammember_list){
				if(teammerber != null && teammerber.getYsmsCoach() != null){
					coach_list.add(teammerber.getYsmsCoach());
				}
			}
		}
		return coach_list;
	}

	@Override
	public boolean configTeamMember(int teamId, String leaderName, String leaderPhone, 
			String doctorName, String doctorPhone, String coachStr, String athleteStr) {
		YsmsTeam ysmsTeam = teamDao.findById(teamId);
		if(ysmsTeam == null){
			return false;
		}
		else{
			if(leaderName!=null){
				ysmsTeam.setLeaderName(leaderName);
				ysmsTeam.setLeaderPhone(leaderPhone);
				ysmsTeam.setDoctorName(doctorName);
				ysmsTeam.setDoctorPhone(doctorPhone);
				teamDao.save(ysmsTeam);
			}
			String[] coaches = null;
			if(coachStr != null && coachStr != ""){
				coaches = coachStr.split(",");
			}
			List<YsmsTeammember> coach_list = teammemberDao.findCoachesByTeamId(teamId);
			if(coach_list != null && coach_list.size() > 0){
				for(int i = 0; i < coach_list.size(); i++){
					YsmsTeammember teammerber = coach_list.get(i);
					if(teammerber != null){
						YsmsCoach coach = teammerber.getYsmsCoach();
						if(coach != null){
							int coachId = coach.getCoachId();
							boolean isAdd = false;
							if(coaches != null && coaches.length > 0){
								for(int j = 0; j < coaches.length; j++){
									int coachid = Integer.parseInt(coaches[j]);
									if(coachid == coachId){
										isAdd = true;
										break;
									}
								}
							}
							if(!isAdd){
								teammemberDao.delete(teammerber);
							}
						}
					}
				}
				
				if(coaches != null && coaches.length > 0){
					for(int i = 0; i < coaches.length; i++){
						int coachId = Integer.parseInt(coaches[i]);
						boolean isAdd = true;
						if(coach_list != null && coach_list.size() > 0){
							for(int j = 0; j < coach_list.size(); j++){
								YsmsTeammember teammerber = coach_list.get(j);
								if(teammerber != null){
									YsmsCoach coach = teammerber.getYsmsCoach();
									if(coach != null){
										int coachid = coach.getCoachId();
										if(coachId == coachid){
											isAdd = false;
											break;
										}
									}
								}
							}
						}
						if(isAdd){
							YsmsCoach coach = coachDao.findById(coachId);
							YsmsTeammember teammerber = new YsmsTeammember();
							teammerber.setYsmsCoach(coach);
							teammerber.setYsmsTeam(ysmsTeam);
							teammemberDao.save(teammerber);
						}
						
					}
				}
			}
			else{
				if(coaches != null && coaches.length > 0){
					for(int i = 0; i < coaches.length; i++){
						int coachId = Integer.parseInt(coaches[i]);
						YsmsCoach coach = coachDao.findById(coachId);
						YsmsTeammember teammerber = new YsmsTeammember();
						teammerber.setYsmsCoach(coach);
						teammerber.setYsmsTeam(ysmsTeam);
						teammemberDao.save(teammerber);
					}
				}
			}
			
			String[] athletes = null;
			if(athleteStr != null && athleteStr != ""){
				athletes = athleteStr.split(",");
			}
			List<YsmsTeammember> athlete_list = teammemberDao.findAthletesByTeamId(teamId);
			
			if(athlete_list != null && athlete_list.size() > 0){
				for(int i = 0; i < athlete_list.size(); i++){
					YsmsTeammember teammerber = athlete_list.get(i);
					if(teammerber != null){
						YsmsAthlete athlete = teammerber.getYsmsAthlete();
						if(athlete != null){
							int athleteId = athlete.getAthleteId();
							boolean isAdd = false;
							if(athletes != null && athletes.length > 0){
								for(int j = 0; j < athletes.length; j++){
									int athletid = Integer.parseInt(athletes[j]);
									if(athletid == athleteId){
										isAdd = true;
										break;
									}
								}
							}
							if(!isAdd){
								teammemberDao.delete(teammerber);
							}
						}
					}
				}
				
				if(athletes != null && athletes.length > 0){
					for(int i = 0; i < athletes.length; i++){
						int athleteId = Integer.parseInt(athletes[i]);
						boolean isAdd = true;
						if(athlete_list != null && athlete_list.size() > 0){
							for(int j = 0; j < athlete_list.size(); j++){
								YsmsTeammember teammerber = athlete_list.get(j);
								if(teammerber != null){
									YsmsAthlete athlete = teammerber.getYsmsAthlete();
									if(athlete != null){
										int athleteid = athlete.getAthleteId();
										if(athleteId == athleteid){
											isAdd = false;
											break;
										}
									}
								}
							}
						}
						if(isAdd){
							YsmsAthlete athlete = athleteDao.findById(athleteId);
							YsmsTeammember teammerber = new YsmsTeammember();
							teammerber.setYsmsAthlete(athlete);
							teammerber.setYsmsTeam(ysmsTeam);
							teammemberDao.save(teammerber);
						}
						
					}
				}
			}
			else{
				if(athletes != null && athletes.length > 0){
					for(int i = 0; i < athletes.length; i++){
						int athleteId = Integer.parseInt(athletes[i]);
						YsmsAthlete athlete = athleteDao.findById(athleteId);
						YsmsTeammember teammerber = new YsmsTeammember();
						teammerber.setYsmsAthlete(athlete);
						teammerber.setYsmsTeam(ysmsTeam);
						teammemberDao.save(teammerber);
					}
				}
			}
			
			return true;
		}
	}

	@Override
	public List<TeamView> getTeamByZoneAndTeamName(int zoneId,
			String teamName) {
		List<TeamView> teamViewList = new ArrayList<TeamView>();
		List<YsmsZoneTeam> ysmsZoneTeamList = zoneTeamDao.getTeamByIdAndName(zoneId, teamName);
		for (int i=0;i<ysmsZoneTeamList.size();i++) {
			YsmsTeam ysmsTeam = ysmsZoneTeamList.get(i).getYsmsTeam();
			TeamView teamView = new TeamView();
			teamView.setSchoolName(ysmsTeam.getYsmsSchool().getSchoolName());
			teamView.setSchoolCategory(ysmsTeam.getYsmsSchool().getSchoolCategory());
			teamView.setTeamId(ysmsTeam.getTeamId());
			teamView.setTeamName(ysmsTeam.getTeamName());
			teamViewList.add(teamView);
		}
		return teamViewList;
	}

	@Override
	public List<TeamView> getTeamByZoneAndSchoolName(int zoneId,
			String schoolName) {
		List<TeamView> teamViewList = new ArrayList<TeamView>();
		List<YsmsZoneTeam> ysmsZoneTeamList = zoneTeamDao.getTeamByIdAndSchoolName(zoneId, schoolName);
		for (int i=0;i<ysmsZoneTeamList.size();i++) {
			YsmsTeam ysmsTeam = ysmsZoneTeamList.get(i).getYsmsTeam();
			TeamView teamView = new TeamView();
			teamView.setSchoolName(ysmsTeam.getYsmsSchool().getSchoolName());
			teamView.setSchoolCategory(ysmsTeam.getYsmsSchool().getSchoolCategory());
			teamView.setTeamId(ysmsTeam.getTeamId());
			teamView.setTeamName(ysmsTeam.getTeamName());
			teamViewList.add(teamView);
		}
		return teamViewList;
	}

	@Override
	public List<YsmsAthlete> getSuitedAthletesByZone(int zoneId, int schoolId) {
		List<YsmsZoneLevel> zonelevels = zoneLevelDao.findByZoneId(zoneId);
		List<YsmsAthlete> athleteList = new ArrayList<YsmsAthlete>();
		if(zonelevels!=null){
			for(int i=0;i<zonelevels.size();i++){
				int level = zonelevels.get(i).getYsmsLeagueLevel().getLevelId();
				int gender = -1;
				if(level >= 1 && level <= 22){
					if(level % 2 == 0){
						gender = 0;
					}
					else if(level % 2 == 1){
						gender = 1;
					}
				}
				int yearJoinSchool;
				Calendar calendar = Calendar.getInstance();
				if(calendar.get(Calendar.MONTH)>=8){ //现在是九月份之后
					//那么入学年应该是当前年份-年级数+1
					yearJoinSchool = calendar.get(Calendar.YEAR) - (int)(Math.ceil(level/2.0) + 1) + 1;
				}
				else{ //如果在9月份之前
					yearJoinSchool = calendar.get(Calendar.YEAR) - (int)(Math.ceil(level/2.0) + 1);
				}
				athleteList.addAll(this.getAthletesByProperty(schoolId, null, null, gender, null, null, yearJoinSchool+""));
			}
		}
		return athleteList;
	}
	
	@Override
	public int getSignUpAthletelimitCountByZoneId(Integer zoneId){
		YsmsLeagueZone zone = leagueZoneDao.findById(zoneId);
		return zone.getMaxAthleteNum();
	}

	@Override
	public List<TeamView> getTeamsByZone(Integer zoneId, Integer[] selectedTeamIds, String groupName) {
		List<YsmsTeam> teamList = teamDao.findByZoneIdandGroupBesidesSelected(zoneId, groupName, selectedTeamIds);
		List<TeamView> teams = new ArrayList<TeamView>();
		for(int i=0;i<teamList.size();i++){
			YsmsTeam ysmsTeam = teamList.get(i);
			TeamView teamView = new TeamView();
			teamView.setSchoolCategory(ysmsTeam.getYsmsSchool().getSchoolCategory());
			teamView.setSchoolName(ysmsTeam.getYsmsSchool().getSchoolName());
			teamView.setTeamId(ysmsTeam.getTeamId());
			teamView.setTeamName(ysmsTeam.getTeamName());
			teams.add(teamView);
		}
		return teams;
	}

	@Override
	public int getSignedTeamByZoneCount(int zoneId) {
	 
		return (int)zoneTeamDao.getSignedTeamCount(zoneId);
	}

	@Override
	public List<MemberAthleteView> getAthleteMemberByTeamId(int teamId) {
		List<YsmsTeammember> memberList = teammemberDao.findAthletesByTeamId(teamId);
		List<MemberAthleteView> viewList = new ArrayList<MemberAthleteView>();
		for(int i=0;i<memberList.size();i++){
			YsmsTeammember ysmsTeammember = memberList.get(i);
			MemberAthleteView view = new MemberAthleteView();
			YsmsAthlete ysmsAthlete = ysmsTeammember.getYsmsAthlete();
			view.setAthleteId(ysmsAthlete.getAthleteId());
			view.setAthleteName(ysmsAthlete.getIdentifiedName());
			view.setAthleteNumber(ysmsTeammember.getAthleteNum());
			viewList.add(view);
		}
		return viewList; 
	}

}
