package com.cwkj.ysms.control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cwkj.ysms.model.YsmsLeague;
import com.cwkj.ysms.model.view.FoulView;
import com.cwkj.ysms.model.view.GameView;
import com.cwkj.ysms.model.view.GoalView;
import com.cwkj.ysms.model.view.MemberAthleteView;
import com.cwkj.ysms.model.view.SuspensionView;
import com.cwkj.ysms.model.view.TeamView;
import com.cwkj.ysms.model.view.ZoneView;
import com.cwkj.ysms.service.GamesManagementService;
import com.cwkj.ysms.service.JudgeManagementService;
import com.cwkj.ysms.service.LeagueManagementService;
import com.cwkj.ysms.service.TeamManagementService;
import com.cwkj.ysms.util.Page;
import com.cwkj.ysms.util.PageUtil;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/gamemanagement")
public class GameManagementControl {
	@Resource
	private GamesManagementService gameManagementService;

	public GamesManagementService getGameManagementService() {
		return gameManagementService;
	}

	public void setGameManagementService(
			GamesManagementService gameManagementService) {
		this.gameManagementService = gameManagementService;
	}
	@Resource
	private LeagueManagementService leagueManagementService;

	public LeagueManagementService getLeagueManagementService() {
		return leagueManagementService;
	}

	public void setLeagueManagementService(
			LeagueManagementService leagueManagementService) {
		this.leagueManagementService = leagueManagementService;
	}
	@Resource
	private TeamManagementService teamManagementService;

	public TeamManagementService getTeamManagementService() {
		return teamManagementService;
	}

	public void setTeamManagementService(TeamManagementService teamManagementService) {
		this.teamManagementService = teamManagementService;
	}
	
	@Resource
	private JudgeManagementService judgeManagementService;

	public JudgeManagementService getJudgeManagementService() {
		return judgeManagementService;
	}

	public void setJudgeManagementService(
			JudgeManagementService judgeManagementService) {
		this.judgeManagementService = judgeManagementService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listResult(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<YsmsLeague> leagues = leagueManagementService.getAllLeagues();
		model.put("userGroup", session.getAttribute("userGroup"));
		model.put("leagues", leagues);
		return new ModelAndView("GameManagementPage", model);
	}

	@ResponseBody
	@RequestMapping(value = "/getzonesbyleague", method = RequestMethod.POST)
	public Map<String, Object> getZonesByLeague(HttpServletRequest request,
			HttpSession session,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		int leagueId = Integer.parseInt(request.getParameter("league_id"));
		List<ZoneView> zoneList = leagueManagementService.getZonesByLeague(leagueId);
		model.put("zones", zoneList);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/getteamsbyzone", method = RequestMethod.POST)
	public Map<String, Object> getTeamsbyzone(HttpServletRequest request,
			HttpSession session,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		int zoneId = Integer.parseInt(request.getParameter("zone_id"));
		String[] teamsAlreadySelected = request.getParameter("selected_teamid").split(",");
		Integer[] teamIdsAlreadySelected = new Integer[teamsAlreadySelected.length];
		for(int i=0;i<teamsAlreadySelected.length;i++){
			if(!"".equals(teamsAlreadySelected[i])){
				teamIdsAlreadySelected[i] = new Integer(teamsAlreadySelected[i]);
			}
			else
				teamIdsAlreadySelected[i] = null;
		}
		String groupName = request.getParameter("group_name");
		List<TeamView> teams = teamManagementService.getTeamsByZone(zoneId==0 ? null : zoneId, 
				teamIdsAlreadySelected, groupName);
		model.put("teams", teams);
		return model;
	}


	/**
	 * 添加比赛
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addgame", method = RequestMethod.POST)
	public Map<String, Object> addGame(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		int hostTeamId = Integer.parseInt(request.getParameter("host_team_id"));
		int guestTeamId = Integer.parseInt(request.getParameter("guest_team_id"));
		int zoneId = Integer.parseInt(request.getParameter("zone_id"));
		String gameTimeStr = request.getParameter("game_time");
		String gameLocation = request.getParameter("game_location");
		int gameOrder = Integer.parseInt(request.getParameter("game_order"));
		String hostUniform = request.getParameter("host_uniform");
		String guestUniform = request.getParameter("guest_uniform");
		Date gamesTime = null;
		if(!"".equals(gameTimeStr)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			gamesTime = sdf.parse(gameTimeStr);
		}
		boolean result = gameManagementService.addGamesInfo(zoneId, gameOrder, hostTeamId, hostUniform, guestTeamId, guestUniform, gamesTime, gameLocation);
		model.put("success", result);
		return model;
	}
	/**
	 * 修改比赛
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "modifygame", method = RequestMethod.POST)
	public Map<String, Object> modifyGame(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		int gameId = Integer.parseInt(request.getParameter("game_id"));
		String gameTimeStr = request.getParameter("game_time");
		String gamesLocation = request.getParameter("game_location");
		String hostUniform = request.getParameter("host_uniform");
		String guestUniform = request.getParameter("guest_uniform");
		Date gamesTime = null;
		if(!"".equals(gameTimeStr)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			gamesTime = sdf.parse(gameTimeStr);
		}
		boolean result = gameManagementService.modifyGame(gameId, hostUniform, guestUniform, gamesTime, gamesLocation);
		model.put("success", result);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "getgames", method = RequestMethod.POST)
	public Map<String, Object> getGames(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception {
		String userGroup = session.getAttribute("userGroup").toString();
		boolean isRecorder = false;
		boolean isJudge = false;
		if("3".equals(userGroup)){
			isRecorder = true;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		Integer leagueId = null;
		String currentPage = request.getParameter("current_page");
		String leagueIdStr = request.getParameter("league_id");

		if(leagueIdStr != null&&!"".equals(leagueIdStr)){
			leagueId = Integer.parseInt(leagueIdStr);
		}
		Integer zoneId = null;
		String zoneIdStr = request.getParameter("zone_id");
		if(zoneIdStr != null&&!"".equals(zoneIdStr)){
			zoneId = Integer.parseInt(zoneIdStr);
		}
		String dateStr = request.getParameter("date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		if(dateStr != null&&!"".equals(dateStr)){
			date = sdf.parse(dateStr);
		}
		int count;
		int userId = Integer.parseInt(session.getAttribute("userId").toString());
		if("9".equals(userGroup)){
			count = gameManagementService.getMyGamesCount(leagueId,
					zoneId, date, judgeManagementService.getJudgeByUser(userId).getJudgeId());
		}
		else{
			count = gameManagementService.getGamesCount(leagueId,
					zoneId, date);
		}
		Page returnPage = new Page();
		Integer returanCurrentPage = 0;
		if (Integer.parseInt(currentPage) <= PageUtil.getTotalPage(8,
				count)) {
			returanCurrentPage = Integer.parseInt(currentPage);

		} else {
			returanCurrentPage = PageUtil.getTotalPage(8, count);
		}
		returnPage.setCurrentPage(returanCurrentPage);
		returnPage.setHasNextPage(PageUtil.getHasNextPage(
				PageUtil.getTotalPage(8, count), returanCurrentPage));
		returnPage
		.setHasPrePage(PageUtil.getHasPrePage(returanCurrentPage));
		returnPage.setTotalPage(PageUtil.getTotalPage(8, count));
		returnPage.setTotalCount(count);
		List<GameView> viewList;
		if("9".equals(userGroup)){ 
			viewList = gameManagementService.getMyGameByPage(leagueId, zoneId, date, 
					returanCurrentPage.toString(), judgeManagementService.getJudgeByUser(userId).getJudgeId());
			isJudge = true;
		}
		else{
			viewList = gameManagementService.getGamesByPage(leagueId, zoneId, date,  returanCurrentPage.toString());
		}
		model.put("page", returnPage);
		model.put("games", viewList);
		model.put("isrecorder", isRecorder);
		model.put("isJudge", isJudge);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "deletegame", method = RequestMethod.POST)
	public Map<String, Object> deleteGame(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Integer gamesId = Integer.parseInt(request.getParameter("game_id"));
		boolean result = gameManagementService.deleteGame(gamesId);
		model.put("success", result);
		return model;
	}
	@ResponseBody
	@RequestMapping(value = "getsinglegame", method = RequestMethod.POST)
	public Map<String, Object> getSingleGame(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Integer gamesId = Integer.parseInt(request.getParameter("game_id"));
		GameView gameView = gameManagementService.getGameById(gamesId);
		model.put("game", gameView);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "getgameinfo", method = RequestMethod.POST)
	public Map<String, Object> getGameInfo(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Integer gamesId = Integer.parseInt(request.getParameter("game_id"));
		GameView gameView = gameManagementService.getGamesInfo(gamesId);
		List<GoalView> hostGoalList = gameManagementService.getTeamGoalsInGame(gamesId, gameView.getHostTeamId());
		List<GoalView> guestGoalList = gameManagementService.getTeamGoalsInGame(gamesId, gameView.getGuestTeamId());
		List<FoulView> hostFoulList = gameManagementService.getTeamFoulInGame(gamesId, gameView.getHostTeamId());
		List<FoulView> guestFoulList = gameManagementService.getTeamFoulInGame(gamesId, gameView.getGuestTeamId());
		List<MemberAthleteView> hostAthleteList = teamManagementService.getAthleteMemberByTeamId(gameView.getHostTeamId());
		List<MemberAthleteView> guestAthleteList = teamManagementService.getAthleteMemberByTeamId(gameView.getGuestTeamId());
		model.put("game", gameView);
		model.put("host_goals", hostGoalList);
		model.put("guest_goals", guestGoalList);
		model.put("host_fouls",  hostFoulList);
		model.put("guest_fouls", guestFoulList);
		model.put("host_athletes", hostAthleteList);
		model.put("guest_athletes", guestAthleteList);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "record", method = RequestMethod.POST)
	public Map<String, Object> record(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		int gamesId = Integer.parseInt(request.getParameter("game_id"));
		int hostScore = Integer.parseInt(request.getParameter("host_score"));
		int guestScore = Integer.parseInt(request.getParameter("guest_score"));
		String hostGoalStr = request.getParameter("host_goal");
		String guestGoalStr = request.getParameter("guest_goal");
		String hostFoulStr = request.getParameter("host_foul");
		String guestFoulStr = request.getParameter("guest_foul");
		int isOvertimeFlag = Integer.parseInt(request.getParameter("isovertime_flag"));
		int isPenaltyFlag = Integer.parseInt(request.getParameter("ispenalty_flag"));
		String hostGoalAttempt = request.getParameter("host_goal_attempt");
		String hostTargetNumber = request.getParameter("host_target_number");
		String hostCornerKick = request.getParameter("host_corner_kick");
		String hostFreeKick = request.getParameter("host_free_kick");
		String hostFoul = request.getParameter("host_foul_number");
		String hostOffside = request.getParameter("host_offside");
		String hostOvertimeScore = request.getParameter("host_overtime_score");
		String hostPenaltyScore = request.getParameter("host_penalty_score");
		String guestGoalAttempt = request.getParameter("guest_goal_attempt");
		String guestTargetNumber = request.getParameter("guest_target_number");
		String guestCornerKick = request.getParameter("guest_corner_kick");
		String guestFreeKick = request.getParameter("guest_free_kick");
		String guestFoul = request.getParameter("guest_foul_number");
		String guestOffside = request.getParameter("guest_offside");
		String guestOvertimeScore = request.getParameter("guest_overtime_score");
		String guestPenaltyScore = request.getParameter("guest_penalty_score");
		gameManagementService.replyGamesInfo(gamesId, hostScore, guestScore, isOvertimeFlag, 
				isPenaltyFlag, hostGoalAttempt, hostTargetNumber, hostCornerKick, hostFreeKick, 
				hostFoul, hostOffside, hostOvertimeScore, hostPenaltyScore, guestGoalAttempt, 
				guestTargetNumber, guestCornerKick, guestFreeKick, guestFoul, guestOffside, 
				guestOvertimeScore, guestPenaltyScore);
		gameManagementService.deleteAllGoalsInGame(gamesId);//清空该场比赛所有进球
		gameManagementService.deleteAllFoulsInGame(gamesId);//清空该场比赛所有红黄牌
		String[] hostGoals = hostGoalStr.split(";");
		for(int i=0;i<hostGoals.length;i++){
			if(!"".equals(hostGoals[i])){
				String[] goalArray = hostGoals[i].split(",");
				int shooterId = Integer.parseInt(goalArray[0]);
				int assistantId = Integer.parseInt(goalArray[1]);
				int goalType = Integer.parseInt(goalArray[2]);
				String goalTime = goalArray[3];
				gameManagementService.addGoalInfo(gamesId, shooterId, assistantId, goalTime, goalType);
			}
		}
		String[] guestGoals = guestGoalStr.split(";");
		for(int i=0;i<guestGoals.length;i++){
			if(!"".equals(guestGoals[i])){
				String[] goalArray = guestGoals[i].split(",");
				int shooterId = Integer.parseInt(goalArray[0]);
				int assistantId = Integer.parseInt(goalArray[1]);
				int goalType = Integer.parseInt(goalArray[2]);
				String goalTime = goalArray[3];
				gameManagementService.addGoalInfo(gamesId, shooterId, assistantId, goalTime, goalType);
			}
		}
		String[] hostFouls = hostFoulStr.split(";");
		for(int i=0;i<hostFouls.length;i++){
			if(!"".equals(hostFouls[i])){
				String[] foulArray = hostFouls[i].split(",");
				int foulerId = Integer.parseInt(foulArray[0]);
				int foulType = Integer.parseInt(foulArray[1]);
				String foulTime = foulArray[2];
				gameManagementService.addFoulInfo(gamesId, foulerId, foulType, foulTime);
			}
		}
		String[] guestFouls = guestFoulStr.split(";");
		for(int i=0;i<guestFouls.length;i++){
			if(!"".equals(guestFouls[i])){
				String[] foulArray = guestFouls[i].split(",");
				int foulerId = Integer.parseInt(foulArray[0]);
				int foulType = Integer.parseInt(foulArray[1]);
				String foulTime = foulArray[2];
				gameManagementService.addFoulInfo(gamesId, foulerId, foulType, foulTime);
			}
		}
		model.put("success", true);
		return model;
	}
	

	
	@ResponseBody
	@RequestMapping(value = "/setgameover", method = RequestMethod.POST)
	public boolean setGameOver(HttpServletRequest request,
			HttpSession session,HttpServletResponse response){
		int gamesId = Integer.parseInt(request.getParameter("game_id"));
		boolean result = gameManagementService.setGameOver(gamesId, true);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cancelgameover", method = RequestMethod.POST)
	public boolean cancelGameOver(HttpServletRequest request,
			HttpSession session,HttpServletResponse response){
		int gamesId = Integer.parseInt(request.getParameter("game_id"));
		boolean result = gameManagementService.setGameOver(gamesId, false);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/suspension", method = RequestMethod.POST)
	public List<SuspensionView> suspension(HttpServletRequest request,
			HttpSession session,HttpServletResponse response){
		int teamId = Integer.parseInt(request.getParameter("team_id"));
		int gamesId = Integer.parseInt(request.getParameter("game_id"));
		List<SuspensionView> views = gameManagementService.getSuspensionList(teamId, gamesId);
		return views;
	}
}
