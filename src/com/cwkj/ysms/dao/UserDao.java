package com.cwkj.ysms.dao;

import java.util.List;
import java.util.Map;

import com.cwkj.ysms.basedao.GenericDao;
import com.cwkj.ysms.model.YsmsUser;

/**
 * 
 * 用户表(YSMS_USER)数据处理接口
 * 
 * @author panhailin henry_pan@126.com
 * @date 2015年3月6日 下午2:58:47
 *
 */
public interface UserDao extends GenericDao {
	/**
	 * 
	 * 保存用户信息
	 * 
	 * @param ysmsUser
	 *            用户信息
	 *
	 */
	public void save(YsmsUser ysmsUser);

	/**
	 * 
	 * 删除用户信息
	 * 
	 * @param userId
	 *            void
	 *
	 */
	public void delete(int userId);

	/**
	 * 
	 * 修改用户信息
	 * 
	 * @param ysmsUser
	 *            void
	 *
	 */
	public void update(YsmsUser ysmsUser);

	/**
	 * 
	 * 模糊查找用户
	 * 
	 * @param ysmsUser
	 * @return List<Map<String,Object>>
	 *
	 */
	public List<Map<String, Object>> findUser(String groupId, String userEmail,
			String userName, String userPassword, String deleteFlag,int startIndex);
	
	public List<Map<String, Object>> findUserCount(String groupId, String userEmail,
			String userName, String userPassword, String deleteFlag);

	/**
	 * 
	 * 根据用户ID精确查找
	 * 
	 * @param userId
	 * @returnList<Map<String,Object>>
	 *
	 */
	public List<Map<String, Object>> findUserByID(int userId);

	/**
	 * 
	 * 根据用户名和密码查找
	 * 
	 * @param userName
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return List<Map<String,Object>>
	 *
	 */
	public List<Map<String, Object>> findUserByNameAndPwd(String userName,
			String passWord);
	
	
	public List<Map<String,Object>> getGroups(String args);
	
	public YsmsUser getUserByUsername(String username);
	
	public YsmsUser findById(int userId);
	
  
	 
}
