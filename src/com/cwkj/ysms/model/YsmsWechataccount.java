package com.cwkj.ysms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "ysms_wechataccount", catalog = "ysms")
public class YsmsWechataccount implements java.io.Serializable {

	// Fields

	private Integer wid;
	private YsmsAthlete ysmsAthlete;
	private String openid;
	private Integer createtime;
	private Integer subscribe;
	private String nickname;
	private Integer sex;
	private String city;
	private String country;
	private String province;
	private String language;
	private String headimgurl;
	private String subscribeTime;
	private String remark;
	private Integer groupid;
	private Integer deleteflag;

	// Constructors

	/** default constructor */
	public YsmsWechataccount() {
	}

	/** minimal constructor */
	public YsmsWechataccount(String openid, Integer deleteflag) {
		this.openid = openid;
		this.deleteflag = deleteflag;
	}

	/** full constructor */
	public YsmsWechataccount(YsmsAthlete ysmsAthlete, String openid,
			Integer createtime, Integer subscribe, String nickname,
			Integer sex, String city, String country, String province,
			String language, String headimgurl, String subscribeTime,
			String remark, Integer groupid, Integer deleteflag) {
		this.ysmsAthlete = ysmsAthlete;
		this.openid = openid;
		this.createtime = createtime;
		this.subscribe = subscribe;
		this.nickname = nickname;
		this.sex = sex;
		this.city = city;
		this.country = country;
		this.province = province;
		this.language = language;
		this.headimgurl = headimgurl;
		this.subscribeTime = subscribeTime;
		this.remark = remark;
		this.groupid = groupid;
		this.deleteflag = deleteflag;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "wid", unique = true, nullable = false)
	public Integer getWid() {
		return this.wid;
	}

	public void setWid(Integer wid) {
		this.wid = wid;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "athlete_id")
	@JsonIgnore
	public YsmsAthlete getYsmsAthlete() {
		return this.ysmsAthlete;
	}

	public void setYsmsAthlete(YsmsAthlete ysmsAthlete) {
		this.ysmsAthlete = ysmsAthlete;
	}

	@Column(name = "openid", nullable = false, length = 512)
	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name = "createtime")
	public Integer getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Integer createtime) {
		this.createtime = createtime;
	}

	@Column(name = "subscribe")
	public Integer getSubscribe() {
		return this.subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	@Column(name = "nickname", length = 512)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "sex")
	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "city", length = 512)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "country", length = 512)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "province", length = 512)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "language", length = 512)
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "headimgurl", length = 512)
	public String getHeadimgurl() {
		return this.headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	@Column(name = "subscribe_time", length = 512)
	public String getSubscribeTime() {
		return this.subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	@Column(name = "remark", length = 512)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "groupid")
	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	@Column(name = "deleteflag", nullable = false)
	public Integer getDeleteflag() {
		return this.deleteflag;
	}

	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
	}

}