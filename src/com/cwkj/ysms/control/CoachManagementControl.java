package com.cwkj.ysms.control;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cwkj.ysms.model.YsmsCoach;
import com.cwkj.ysms.model.YsmsSchool;
import com.cwkj.ysms.model.view.CoachManagerListView;
import com.cwkj.ysms.model.view.CoachView;
import com.cwkj.ysms.service.CoachManagementService;
import com.cwkj.ysms.service.SchoolManagementService;
import com.cwkj.ysms.service.UserManagementService;
import com.cwkj.ysms.util.Page;
import com.cwkj.ysms.util.ToolsUtil;

@Controller
@RequestMapping(value = "/coachmanagement")
public class CoachManagementControl {
	@Resource
	private UserManagementService userManagementService;	
	public UserManagementService getUserManagementService() {
		return userManagementService;
	}

	public void setUserManagementService(UserManagementService userManagementService) {
		this.userManagementService = userManagementService;
	}

	@Resource
	private CoachManagementService coachManagementService;

	public CoachManagementService getCoachManagementService() {
		return coachManagementService;
	}

	public void setCoachManagementService(
			CoachManagementService coachManagementService) {
		this.coachManagementService = coachManagementService;
	}

	@Resource
	private SchoolManagementService schoolManagementService;

	public SchoolManagementService getSchoolManagementService() {
		return schoolManagementService;
	}

	public void setSchoolManagementService(
			SchoolManagementService schoolManagementService) {
		this.schoolManagementService = schoolManagementService;
	}

	/**
	 * 打开管理界面 无参数
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listResult(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		return new ModelAndView("CoachManagementPage");
	}
	
	/**
	 * 管理员打开管理界面 无参数
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "admincoachmanagement", method = RequestMethod.GET)
	public ModelAndView adminListResult(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		return new ModelAndView("AdminCoachManagementPage");
	}

	/**
	 * 进入教练员注册页
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "registercoach", method = RequestMethod.GET)
	public ModelAndView registerCoach(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("jobs_list", coachManagementService.getAllJobs());
		return new ModelAndView("CoachRegisterPage", model);
	}
	
	/**
	 * 管理员进入教练员注册页
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "adminregistercoach", method = RequestMethod.GET)
	public ModelAndView adminRegisterCoach(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("jobs_list", coachManagementService.getAllJobs());
		return new ModelAndView("AdminCoachRegisterPage", model);
	}

	/**
	 * 获取全部运教练员信息列表
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "listcoach", method = RequestMethod.GET)
	public ModelAndView listCoach(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (session.getAttribute("schoolId") == null) {
			List<YsmsSchool> ysmsSchools = schoolManagementService.getSchools(
					null, null);
			modelMap.put("school_list", ysmsSchools);
		}
		modelMap.put("jobs_list", coachManagementService.getAllJobs());
		return new ModelAndView("CoachListPage", modelMap);
	}

	@ResponseBody
	@RequestMapping(value = "listpagecoach", method = RequestMethod.GET)
	public Map<String, Object> listPageCoach(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception {
		String requestJson = request.getParameter("requestJson");
		JSONArray jsonArray = new JSONArray(requestJson);
		String sEcho = null;
		String startIndex = null;// = request.getParameter("startIndex");
		String pageSize = null;// request.getParameter("pageSize");
		String name_filterString = null;
		String sex_filterString = null;
		String school_filterString = null;
		Integer iSortCol_0 = null;
		String sSortDir_0 = null;
		String orderString = null;
		String identifiedId = null;
		Integer schoolId = null;
		String coachContact = null;
		String identifiedName = null;
		Integer identifiedGender = null;
		Page page = null;
		for (int i = 0; i < jsonArray.length(); i++) // 从传递参数里面选出待用的参数
		{
			JSONObject obj = (JSONObject) jsonArray.get(i);
			if (obj.get("name").equals("sEcho")) {
				sEcho = obj.get("value").toString();
			}
			if (obj.get("name").equals("iDisplayStart")) {
				startIndex = obj.get("value").toString();
			}
			if (obj.get("name").equals("iDisplayLength")) {
				pageSize = obj.get("value").toString();
			}
			if (obj.get("name").equals("name_filter")) {
				name_filterString = obj.get("value").toString();
				name_filterString = new String(
						name_filterString.getBytes("ISO8859-1"), "UTF-8");
			}
			if (obj.get("name").equals("sex_filter")) {
				sex_filterString = obj.get("value").toString();
			}
			if (obj.get("name").equals("school_filter")) {
				school_filterString = obj.get("value").toString();
			}
			if (obj.get("name").equals("iSortCol_0")) {
				iSortCol_0 = Integer.parseInt(obj.get("value").toString());
			}
			if (obj.get("name").equals("sSortDir_0")) {
				sSortDir_0 = obj.get("value").toString();
			}
		}

		if (!ToolsUtil.isEmpty(startIndex) && !ToolsUtil.isEmpty(pageSize)) {
			page = new Page();
			page.setEveryPage(Integer.parseInt(pageSize));
			page.setBeginIndex(Integer.parseInt(startIndex));
		}

		if (!ToolsUtil.isEmpty(name_filterString)) {
			identifiedName = name_filterString;
		}
		if (!ToolsUtil.isEmpty(sex_filterString)) {
			identifiedGender = Integer.parseInt(sex_filterString);
		}
		if (!ToolsUtil.isEmpty(school_filterString)) {
			schoolId = Integer.parseInt(school_filterString);
		}
		if (iSortCol_0 != null) {
			orderString = " order by ";
			switch (iSortCol_0) {
			case 0:
				orderString += "model.identifiedId ";
				break;
			case 1:
				orderString += "model.identifiedName ";
				break;
			case 2:
				orderString += "model.identifiedGender ";
				break;
			case 3:
				orderString += "model.ysmsSchool.schoolName ";
				break;
			}

			orderString += sSortDir_0;
		}
		if (session.getAttribute("schoolId") != null) {
			schoolId = Integer.parseInt(session.getAttribute("schoolId")
					.toString());
		}
		List<YsmsCoach> ysmsCoachs = coachManagementService
				.getCoachesByPageAndOrder(identifiedId, schoolId, coachContact,
						identifiedName, identifiedGender, page, orderString);
		List<CoachView> coachViews = new ArrayList<CoachView>();
		for (YsmsCoach coach : ysmsCoachs) {
			coachViews.add(new CoachView(coach));
		}

		// List<CoachManagerListView>
		// coachViews=coachManagementService.getCoachesManagerListViewByPageAndOrder(identifiedId,
		// schoolId, coachContact,
		// identifiedName, identifiedGender, page, orderString);
		int count = coachManagementService.getCoachesByPageAndOrderCount(
				identifiedId, schoolId, coachContact, identifiedName,
				identifiedGender);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sEcho", sEcho);
		resultMap.put("iTotalRecords", count);
		resultMap.put("iTotalDisplayRecords", count);
		resultMap.put("coaches", coachViews);
		return resultMap;
	}

	/**
	 * 根据模糊查询获取教练员信息列表
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "coachbyfq", method = RequestMethod.GET)
	public Map<String, Object> listCoachByFQ(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		return null;

	}

	/**
	 * 根据教练员id，获取单个教练员信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "coachbyid", method = RequestMethod.GET)
	public Map<String, Object> listResultById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {

		String requestID = request.getParameter("id");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (requestID != null && !requestID.equals("")) {
			CoachManagerListView coachManagerListView = coachManagementService
					.getCoachListViewByID(Integer.parseInt(requestID));
			resultMap.put("returnCode", 200);
			resultMap.put("coach", coachManagerListView);

		} else {
			resultMap.put("returnCode", 404);
			resultMap.put("returnMessage", "找不到该教练员");
		}
		return resultMap;

	}

	/**
	 * 教练员注册
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addcoach", method = RequestMethod.POST)
	public Map<String, Object> addCoach(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Object schoolIdInSession = session.getAttribute("schoolId");
			if (schoolIdInSession == null) {
				map.put("success", false);
				map.put("mesg", "网络会话失效，请重新登陆！");
				return map;
			}

			if (request instanceof DefaultMultipartHttpServletRequest) {
				DefaultMultipartHttpServletRequest rq = (DefaultMultipartHttpServletRequest) request;
				if (rq != null) {
					int schoolId = Integer.parseInt(schoolIdInSession
							.toString());
					String identifiedName = "";
					int identifiedGender = 0;
					String identifiedNationality = "";
					String birthday = "";
					String identifiedId = "";
					String identifiedAddress = "";
					String coachPhone = "";
					String coachLandPhone = "";
					boolean schoolcoachFlag = false;
					int coachlevel = 0;
					String photoBase64 = "";
					String attachment1 = "";
					String attachment2 = "";
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyyMMddHHmmss");
					String dateString = sdf.format(new Date());
					String tcffilename = "";
					String cfafilename = "";
					int jobIndex = 0;

					String tempDir = request.getSession().getServletContext()
							.getRealPath("../") + File.separator +"YSMSRepo" + File.separator + "upload" + File.separator + "attachment";
					File directory = new File(tempDir);
					if (!directory.exists()) {
						directory.mkdirs();
					}

					Map<String, String[]> params_list = rq.getParameterMap();
					if (params_list != null && params_list.size() > 0) {
						if (params_list.containsKey("name")) { // 姓名
							String[] names = params_list.get("name");
							if (names != null && names.length == 1) {
								identifiedName = names[0];
							}
						}
						if (params_list.containsKey("gender")) { // 性别
							String[] genders = params_list.get("gender");
							if (genders != null && genders.length == 1) {
								String gender = genders[0];
								if (gender.equals("男")) {
									identifiedGender = 1;
								} else if (gender.equals("女")) {
									identifiedGender = 0;
								}
							}
						}
						if (params_list.containsKey("nation")) { // 民族
							String[] nations = params_list.get("nation");
							if (nations != null && nations.length == 1) {
								identifiedNationality = nations[0];
							}
						}
						if (params_list.containsKey("birthday")) { // 生日
							String[] birthdays = params_list.get("birthday");
							if (birthdays != null && birthdays.length == 1) {
								birthday = birthdays[0];
							}
						}
						if (params_list.containsKey("identity")) { // 身份证号
							String[] identitys = params_list.get("identity");
							if (identitys != null && identitys.length == 1) {
								identifiedId = identitys[0];
							}
						}
						if (params_list.containsKey("address")) { // 户籍地址
							String[] addresses = params_list.get("address");
							if (addresses != null && addresses.length == 1) {
								identifiedAddress = addresses[0];
							}
						}
						if (params_list.containsKey("phonenum")) { // 电话号码
							String[] phonenums = params_list.get("phonenum");
							if (phonenums != null && phonenums.length == 1) {
								coachPhone = phonenums[0];
							}
						}
						if(params_list.containsKey("landphonenum")){ //固定电话
							String[] landPhoneNums = params_list.get("landphonenum");
							if (landPhoneNums != null && landPhoneNums.length == 1) {
								coachLandPhone = landPhoneNums[0];
							}
						}
						if(params_list.containsKey("job_select")){ //职业
							String[] jobs = params_list.get("job_select");
							if(jobs != null && jobs.length == 1){
								String job = jobs[0];
								jobIndex = Integer.parseInt(job);
								
							}
						}
						if (params_list.containsKey("tcfcertificate")) { // 校园足球证书
																			// 1-已获取
																			// 2-未获取
							String[] tcfcertificates = params_list
									.get("tcfcertificate");
							if (tcfcertificates != null
									&& tcfcertificates.length == 1) {
								String type = tcfcertificates[0];
								int flag = Integer.parseInt(type);
								if (flag == 1) {
									schoolcoachFlag = true;
								}
							}
						}
						if (params_list.containsKey("cfacertificate")) { // 中国足协证书
																			// 1-D级证书
																			// 2-C级证书
																			// 3-B级证书
																			// 4-A级证书
																			// 5-无证书
							String[] cfacertificates = params_list
									.get("cfacertificate");
							if (cfacertificates != null
									&& cfacertificates.length == 1) {
								String type = cfacertificates[0];
								coachlevel = Integer.parseInt(type);
							}
						}
						if (params_list.containsKey("base64Image")) { // 身份证头像
							String[] base64Images = params_list
									.get("base64Image");
							if (base64Images != null
									&& base64Images.length == 1) {
								photoBase64 = base64Images[0];
							}
						}
						if (params_list.containsKey("tcffilename")) { // 身份证头像
							String[] tcffilenames = params_list
									.get("tcffilename");
							if (tcffilenames != null
									&& tcffilenames.length == 1) {
								tcffilename = tcffilenames[0];
							}
						}
						if (params_list.containsKey("cfafilename")) { // 身份证头像
							String[] cfafilenames = params_list
									.get("cfafilename");
							if (cfafilenames != null
									&& cfafilenames.length == 1) {
								cfafilename = cfafilenames[0];
							}
						}
					}

					Map<String, MultipartFile> file_list = rq.getFileMap();
					if (file_list != null && file_list.size() > 0) {
						if (file_list.containsKey("filetcfcertificate")) { // 校园足球证书附件
							if (schoolcoachFlag) {
								MultipartFile file = file_list
										.get("filetcfcertificate");
								if (tcffilename != "" && identifiedId != "") {
									String[] desp = tcffilename.split("\\.");
									if (desp != null && desp.length > 0) {
										String extendName = desp[desp.length - 1];
										attachment1 = "C1" + identifiedId
												+ dateString + "." + extendName;
										File saveFile = new File(tempDir + File.separator
												+ attachment1);
										if (file != null) {
											file.transferTo(saveFile);
										}
									}
								}
							}

						}
						if (file_list.containsKey("filecfacertificate")) { // 中国足协证书附件
							if (coachlevel > 0 && coachlevel < 5) {
								MultipartFile file = file_list
										.get("filecfacertificate");
								if (cfafilename != "" && identifiedId != "") {
									String[] desp = cfafilename.split("\\.");
									if (desp != null && desp.length > 0) {
										String extendName = desp[desp.length - 1];
										attachment2 = "C2" + identifiedId
												+ dateString + "." + extendName;
										File saveFile = new File(tempDir + File.separator
												+ attachment2);
										if (file != null) {
											file.transferTo(saveFile);
										}
									}
								}
							}
						}
					}

					if (identifiedName == "" || identifiedGender == 0
							|| identifiedNationality == "" || birthday == ""
							|| identifiedId == "" || identifiedAddress == "") {
						map.put("success", false);
						map.put("mesg", "注册失败,缺少必要的教练员信息！");
					}

					sdf = new SimpleDateFormat("yyyy年MM月dd日");
					Date identifiedBirthday = sdf.parse(birthday);
					List<YsmsCoach> coach_list = coachManagementService
							.getCoaches(identifiedId, null, null, null, null);
					if (coach_list != null && coach_list.size() > 0) {
						map.put("success", false);
						map.put("mesg", "注册失败，该教练员已经注册！");
					} else {
						boolean result = coachManagementService.addCoach(
								identifiedId, schoolId, coachPhone,
								identifiedName, identifiedGender,
								identifiedBirthday, identifiedAddress,
								identifiedNationality, photoBase64, coachLandPhone, 
								jobIndex, coachlevel,
								attachment1, schoolcoachFlag, attachment2);

						if (result) {
							map.put("success", true);
							map.put("mesg", "教练员注册成功！");
						} else {
							map.put("success", false);
							map.put("mesg", "教练员注册失败！");
						}
					}
				}
			} else {
				map.put("success", false);
				map.put("mesg", "教练员注册失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 管理员为教练员注册
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "adminaddcoach", method = RequestMethod.POST)
	public Map<String, Object> adminAddCoach(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (request instanceof DefaultMultipartHttpServletRequest) {
				DefaultMultipartHttpServletRequest rq = (DefaultMultipartHttpServletRequest) request;
				if (rq != null) {
					String identifiedName = "";
					int identifiedGender = 0;
					String identifiedNationality = "";
					String birthday = "";
					String identifiedId = "";
					String identifiedAddress = "";
					String coachPhone = "";
					String coachLandPhone = "";
					boolean schoolcoachFlag = false;
					int coachlevel = 0;
					String photoBase64 = "";
					String attachment1 = "";
					String attachment2 = "";
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyyMMddHHmmss");
					String dateString = sdf.format(new Date());
					String tcffilename = "";
					String cfafilename = "";
					int jobIndex = 0;
					int schoolId = 0;
					
					String tempDir = request.getSession().getServletContext()
							.getRealPath("../") + File.separator +"YSMSRepo" + File.separator + "upload" + File.separator + "attachment";
					File directory = new File(tempDir);
					if (!directory.exists()) {
						directory.mkdirs();
					}
					
					Map<String, String[]> params_list = rq.getParameterMap();
					if (params_list != null && params_list.size() > 0) {
						if (params_list.containsKey("name")) { // 姓名
							String[] names = params_list.get("name");
							if (names != null && names.length == 1) {
								identifiedName = names[0];
							}
						}
						if (params_list.containsKey("gender")) { // 性别
							String[] genders = params_list.get("gender");
							if (genders != null && genders.length == 1) {
								String gender = genders[0];
								if (gender.equals("男")) {
									identifiedGender = 1;
								} else if (gender.equals("女")) {
									identifiedGender = 0;
								}
							}
						}
						if (params_list.containsKey("nation")) { // 民族
							String[] nations = params_list.get("nation");
							if (nations != null && nations.length == 1) {
								identifiedNationality = nations[0];
							}
						}
						if (params_list.containsKey("birthday")) { // 生日
							String[] birthdays = params_list.get("birthday");
							if (birthdays != null && birthdays.length == 1) {
								birthday = birthdays[0];
							}
						}
						if (params_list.containsKey("identity")) { // 身份证号
							String[] identitys = params_list.get("identity");
							if (identitys != null && identitys.length == 1) {
								identifiedId = identitys[0];
							}
						}
						if (params_list.containsKey("address")) { // 户籍地址
							String[] addresses = params_list.get("address");
							if (addresses != null && addresses.length == 1) {
								identifiedAddress = addresses[0];
							}
						}
						if (params_list.containsKey("phonenum")) { // 电话号码
							String[] phonenums = params_list.get("phonenum");
							if (phonenums != null && phonenums.length == 1) {
								coachPhone = phonenums[0];
							}
						}
						if(params_list.containsKey("landphonenum")){ //固定电话
							String[] landPhoneNums = params_list.get("landphonenum");
							if (landPhoneNums != null && landPhoneNums.length == 1) {
								coachLandPhone = landPhoneNums[0];
							}
						}
						if(params_list.containsKey("job_select")){ //职业
							String[] jobs = params_list.get("job_select");
							if(jobs != null && jobs.length == 1){
								String job = jobs[0];
								jobIndex = Integer.parseInt(job);
								
							}
						}
						if (params_list.containsKey("tcfcertificate")) { // 校园足球证书
							// 1-已获取
							// 2-未获取
							String[] tcfcertificates = params_list
									.get("tcfcertificate");
							if (tcfcertificates != null
									&& tcfcertificates.length == 1) {
								String type = tcfcertificates[0];
								int flag = Integer.parseInt(type);
								if (flag == 1) {
									schoolcoachFlag = true;
								}
							}
						}
						if (params_list.containsKey("cfacertificate")) { // 中国足协证书
							// 1-D级证书
							// 2-C级证书
							// 3-B级证书
							// 4-A级证书
							// 5-无证书
							String[] cfacertificates = params_list
									.get("cfacertificate");
							if (cfacertificates != null
									&& cfacertificates.length == 1) {
								String type = cfacertificates[0];
								coachlevel = Integer.parseInt(type);
							}
						}
						if (params_list.containsKey("base64Image")) { // 身份证头像
							String[] base64Images = params_list
									.get("base64Image");
							if (base64Images != null
									&& base64Images.length == 1) {
								photoBase64 = base64Images[0];
							}
						}
						if (params_list.containsKey("schoolid")) { // 身份证头像
							String[] ids = params_list
									.get("schoolid");
							if (ids != null
									&& ids.length == 1) {
								schoolId = Integer.parseInt(ids[0]);
							}
						}
						if (params_list.containsKey("tcffilename")) { // 身份证头像
							String[] tcffilenames = params_list
									.get("tcffilename");
							if (tcffilenames != null
									&& tcffilenames.length == 1) {
								tcffilename = tcffilenames[0];
							}
						}
						if (params_list.containsKey("cfafilename")) { // 身份证头像
							String[] cfafilenames = params_list
									.get("cfafilename");
							if (cfafilenames != null
									&& cfafilenames.length == 1) {
								cfafilename = cfafilenames[0];
							}
						}
					}
					
					Map<String, MultipartFile> file_list = rq.getFileMap();
					if (file_list != null && file_list.size() > 0) {
						if (file_list.containsKey("filetcfcertificate")) { // 校园足球证书附件
							if (schoolcoachFlag) {
								MultipartFile file = file_list
										.get("filetcfcertificate");
								if (tcffilename != "" && identifiedId != "") {
									String[] desp = tcffilename.split("\\.");
									if (desp != null && desp.length > 0) {
										String extendName = desp[desp.length - 1];
										attachment1 = "C1" + identifiedId
												+ dateString + "." + extendName;
										File saveFile = new File(tempDir + File.separator
												+ attachment1);
										if (file != null) {
											file.transferTo(saveFile);
										}
									}
								}
							}
							
						}
						if (file_list.containsKey("filecfacertificate")) { // 中国足协证书附件
							if (coachlevel > 0 && coachlevel < 5) {
								MultipartFile file = file_list
										.get("filecfacertificate");
								if (cfafilename != "" && identifiedId != "") {
									String[] desp = cfafilename.split("\\.");
									if (desp != null && desp.length > 0) {
										String extendName = desp[desp.length - 1];
										attachment2 = "C2" + identifiedId
												+ dateString + "." + extendName;
										File saveFile = new File(tempDir + File.separator
												+ attachment2);
										if (file != null) {
											file.transferTo(saveFile);
										}
									}
								}
							}
						}
					}
					
					if (identifiedName == "" || identifiedGender == 0
							|| identifiedNationality == "" || birthday == ""
							|| identifiedId == "" || identifiedAddress == "") {
						map.put("success", false);
						map.put("mesg", "注册失败,缺少必要的教练员信息！");
					}
					
					sdf = new SimpleDateFormat("yyyy年MM月dd日");
					Date identifiedBirthday = sdf.parse(birthday);
					List<YsmsCoach> coach_list = coachManagementService
							.getCoaches(identifiedId, null, null, null, null);
					if (coach_list != null && coach_list.size() > 0) {
						map.put("success", false);
						map.put("mesg", "注册失败，该教练员已经注册！");
					} else {
						boolean result = coachManagementService.addCoach(
								identifiedId, schoolId, coachPhone,
								identifiedName, identifiedGender,
								identifiedBirthday, identifiedAddress,
								identifiedNationality, photoBase64, coachLandPhone, 
								jobIndex, coachlevel,
								attachment1, schoolcoachFlag, attachment2);
						
						if (result) {
							map.put("success", true);
							map.put("mesg", "教练员注册成功！");
						} else {
							map.put("success", false);
							map.put("mesg", "教练员注册失败！");
						}
					}
				}
			} else {
				map.put("success", false);
				map.put("mesg", "教练员注册失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		return map;
	}

	
	@ResponseBody
	@RequestMapping(value = "getschoolid", method = RequestMethod.POST)
	public Map<String, Object> getSchoolId(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String schoolUsername = request.getParameter("schoolusername");
		int schoolId = userManagementService.checkSchoolUsername(schoolUsername);
		map.put("schoolid", schoolId);
		return map;
	}
	
	/**
	 * 教练员删除
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@ResponseBody
	@Transactional
	@RequestMapping(value = "deletecoach", method = RequestMethod.POST)
	public Map<String, Object> deleteCoach(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		String idsString = request.getParameter("id");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (idsString != null) {
			String[] ids = idsString.split(",");
			boolean result = true;
			for (int i = 0; i < ids.length; i++) {
				result = result
						& coachManagementService.deleteCoach(Integer
								.parseInt(ids[i]));
			}
			if (result) {
				resultMap.put("returnCode", 200);
				resultMap.put("returnMessage", "删除成功");
			} else {
				resultMap.put("returnCode", 500);
				resultMap.put("returnMessage", "删除失败");
			}
		} else {
			resultMap.put("returnCode", 300);
			resultMap.put("returnMessage", "未选定删除行");
		}
		return resultMap;
	}

	/**
	 * 教练员信息修改
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@ResponseBody
	@Transactional
	@RequestMapping(value = "modifycoach", method = RequestMethod.POST)
	public Map<String, Object> modifyCoach(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Object schoolIdInSession = session.getAttribute("schoolId");
			Integer schoolId = null;
			if (schoolIdInSession != null) {
				schoolId = Integer.parseInt(schoolIdInSession.toString());
			}
			String coachIdString = request.getParameter("coachid");
			if (coachIdString == null || coachIdString.equals("")) {
				map.put("returnCode", 500);
				map.put("returnMessage", "无效的教练员编号");
				return map;
			}
			int coachId = Integer.parseInt(coachIdString);
			String coachPhone = request.getParameter("phonenum");
			String coachLandPhone = request.getParameter("landphonenum");
			int jobIndex = Integer.parseInt(request.getParameter("job_input"));
			// String photoBase64 = request.getParameter("image");
			int coachLevel = 0;
			String level_str = request.getParameter("coach_level");
			if (level_str != null) {
				coachLevel = Integer.parseInt(level_str);
			}
			boolean schoolcoachFlag = false;
			String schoolcoach_str = request.getParameter("school_coach");
			if (schoolcoach_str != null) {
				if (schoolcoach_str.equals("1")) {
					schoolcoachFlag = true;
				}
			}
			String photoBase64 = request.getParameter("base64Image");
			boolean result = coachManagementService.updateCoach(coachId,
					schoolId, coachPhone, coachLandPhone, jobIndex, coachLevel, schoolcoachFlag, photoBase64);
			if (result) {
				map.put("returnCode", 200);
				map.put("returnMessage", "修改成功");
			} else {
				map.put("returnCode", 500);
				map.put("returnMessage", "修改失败");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			map.put("returnCode", 400);
			map.put("returnMessage", "参数错误");
		}
		return map;

	}

	/**
	 * 获取excel文件
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "exportexcel", method = RequestMethod.POST)
	public void exportExcel(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		try {
			
			String name_filterString = request.getParameter("name_filter");
			String sex_filterString = request.getParameter("sex_filter");
			String school_filterString = request.getParameter("school_filter");

			Integer schoolId = null;

			String identifiedName = null;
			if (!ToolsUtil.isEmpty(name_filterString)) {
				identifiedName = name_filterString;
			}
			Integer identifiedGender = null;
			if (!ToolsUtil.isEmpty(sex_filterString)
					&& !sex_filterString.equals("-1")) {
				identifiedGender = Integer.parseInt(sex_filterString);
			}
			if (!ToolsUtil.isEmpty(school_filterString)) {
				schoolId = Integer.parseInt(school_filterString);
			}
			if (session.getAttribute("schoolId") != null) {
				schoolId = Integer.parseInt(session.getAttribute("schoolId")
						.toString());
			}
			List<YsmsCoach> ysmsCoachs = coachManagementService
					.getCoachesByPageAndOrder(null, schoolId, null,
							identifiedName, identifiedGender, null, null);
			List<CoachView> coachViews = new ArrayList<CoachView>();
			for (YsmsCoach coach : ysmsCoachs) {
				coachViews.add(new CoachView(coach));
			}

			response.reset();
			String fileName = "教练员.xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			String userAgent = request.getHeader("User-Agent");
			if (userAgent.contains("Firefox")) {// 专修火狐编码
				response.setHeader("Content-Disposition",
						"attachment;filename="
								+ new String(fileName.getBytes(), "ISO-8859-1"));// 指定下载的文件名
				response.setCharacterEncoding("UTF-8");
			} else {// IE ,google
				response.setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ java.net.URLEncoder.encode(fileName, "UTF-8"));
			}
			response.setDateHeader("Expires", 0);

			WritableWorkbook wwb = Workbook.createWorkbook(response
					.getOutputStream());
			// 创建工作表
			WritableSheet sheet = wwb.createSheet("教练员列表", 0);
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 10);
			sheet.setColumnView(2, 10);
			sheet.setColumnView(3, 10);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 15);
			sheet.setColumnView(6, 15);
			sheet.setColumnView(7, 30);
			sheet.setColumnView(8, 10);
			sheet.setColumnView(9, 25);
			// 定义格式 字体 下划线 斜体 粗体 颜色
			WritableFont fontStyle1 = new WritableFont(WritableFont.TIMES, 15,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			WritableFont fontStyle2 = new WritableFont(WritableFont.TIMES, 13,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			WritableFont fontStyle3 = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.BOLD);
			WritableCellFormat formatCell = new WritableCellFormat(fontStyle1);
			WritableCellFormat formatCell2 = new WritableCellFormat(fontStyle2);
			WritableCellFormat formatCell3 = new WritableCellFormat(fontStyle3);
			WritableCellFormat formatCell4 = new WritableCellFormat(fontStyle3);
			WritableCellFormat formatCell5 = new WritableCellFormat(fontStyle3);
			formatCell.setAlignment(jxl.format.Alignment.CENTRE);
			formatCell2.setAlignment(jxl.format.Alignment.CENTRE);
			formatCell3.setAlignment(jxl.format.Alignment.CENTRE);
			
			sheet.addCell(new Label(0, 0, "编号", formatCell5));
			sheet.addCell(new Label(1, 0, "学校", formatCell5));
			sheet.addCell(new Label(2, 0, "姓名", formatCell5));
			sheet.addCell(new Label(3, 0, "性别", formatCell5));
			sheet.addCell(new Label(4, 0, "身份证", formatCell5));
			sheet.addCell(new Label(5, 0, "生日", formatCell5));
			sheet.addCell(new Label(6, 0, "联系电话", formatCell5));
			sheet.addCell(new Label(7, 0, "地址", formatCell5));
			sheet.addCell(new Label(8, 0, "学校证书", formatCell5));
			sheet.addCell(new Label(9, 0, "教练证书", formatCell5));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			for (int i = 1; i < coachViews.size() + 1; i++) {
				CoachView coachView = coachViews.get(i - 1);
				sheet.addCell(new Label(0, i, coachView.getCoachId() + ""));
				sheet.addCell(new Label(1, i, coachView.getSchoolName()));
				sheet.addCell(new Label(2, i, coachView.getIdentifiedName()));
				sheet.addCell(new Label(3, i,
						coachView.getIdentifiedGender() == 0 ? "女" : "男"));
				sheet.addCell(new Label(4, i, coachView.getIdentifiedId()));
				sheet.addCell(new Label(5, i, simpleDateFormat.format(coachView
						.getIdentifiedBirthday())));
				sheet.addCell(new Label(6, i, coachView.getCoachMobile()));
				sheet.addCell(new Label(7, i, coachView.getIdentifiedAddress()));
				sheet.addCell(new Label(8, i,
						coachView.getSchoolcoachFlag() == true ? "已获得" : "未获得"));
				sheet.addCell(new Label(9, i, getCoachLevel(coachView.getCoachLevel())));
			}
			// 写进文档
			wwb.write();
			// // 关闭Excel工作簿对象
			wwb.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getCoachLevel(int coachLevel) {
		switch (coachLevel) {
		case 5:
			return "无证书";
		case 4:
			return "中国足协A级教练员证书";
		case 3:
			return "中国足协B级教练员证书";
		case 2:
			return "中国足协C级教练员证书";
		case 1:
			return "中国足协D级教练员证书";
		default:
			return "";
		}
	}
}
