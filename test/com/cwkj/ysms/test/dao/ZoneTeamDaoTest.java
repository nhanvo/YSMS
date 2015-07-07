package com.cwkj.ysms.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cwkj.ysms.dao.LeagueDao;
import com.cwkj.ysms.dao.LeagueZoneDao;
import com.cwkj.ysms.dao.TeamDao;
import com.cwkj.ysms.dao.ZoneTeamDao;
import com.cwkj.ysms.model.YsmsZoneTeam;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="file:WebContent/WEB-INF/springMVC-servlet.xml") 
public class ZoneTeamDaoTest {

	@Autowired
	private ZoneTeamDao zoneTeamDao;
	
	@Autowired
	private LeagueDao leagueDao;
	
	@Autowired
	private TeamDao teamDao;
	
	@Autowired
	private LeagueZoneDao leagueZoneDao;
	
	@Test
	public void testSave(){
		YsmsZoneTeam ysmsZoneTeam = new YsmsZoneTeam();
		ysmsZoneTeam.setYsmsLeagueZone(leagueZoneDao.findById(1));
		ysmsZoneTeam.setYsmsTeam(teamDao.findById(2));
		ysmsZoneTeam.setZoneGroup("0");
		zoneTeamDao.save(ysmsZoneTeam);
	}
	
	@Test
	public void testFindSignedTeam(){
		List<YsmsZoneTeam> list = zoneTeamDao.getSignedTeam(1,0);
		System.out.println(list.size());
	}
	
	@Test
	public void testFindParticipateTeam(){
		List<YsmsZoneTeam> list = zoneTeamDao.getParticipatedTeam(1);
		System.out.println(list.size());
	}
	

	@Test
	public void testFindByExample(){
		YsmsZoneTeam ysmsZoneTeam = new YsmsZoneTeam();
		ysmsZoneTeam.setYsmsLeagueZone(leagueZoneDao.findById(1));
		ysmsZoneTeam.setYsmsTeam(teamDao.findById(1));
		List<YsmsZoneTeam> list = zoneTeamDao.findByExample(ysmsZoneTeam);
		System.out.println(list.size());
	}
	
	@Test
	public void testFindById(){
		YsmsZoneTeam ysmsLeagueTeam = zoneTeamDao.findById(1);
		System.out.println(ysmsLeagueTeam.getYsmsTeam().getTeamName());
	}
	
	@Test
	public void testSelectedTeam(){
		 List<Integer> list=new ArrayList<Integer>();
		 list.add(16);
		System.out.println(zoneTeamDao.seletedTeam(229, list));
	}
	
	
	@Test
	public void testGetSignedTeamCount(){
		 
		 zoneTeamDao.getSignedTeamCount(229) ;
	}
}
