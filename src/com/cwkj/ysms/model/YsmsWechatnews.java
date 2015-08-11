package com.cwkj.ysms.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "ysms_wechatnews", catalog = "ysms")
public class YsmsWechatnews implements java.io.Serializable {

	// Fields

	private Integer nid;
	private YsmsUser ysmsUser;
	private String author;
	private String title;
	private Date date;
	private String digest;
	private String picurl;
	private String url;
	private Integer nindex;
	private Integer verified;
	private Integer forServiceFlag;

	private Set<YsmsWechatnewsAttr> ysmsWechatnewsAttrs = new HashSet<YsmsWechatnewsAttr>(
			0);

	// Constructors

	/** default constructor */
	public YsmsWechatnews() {
	}

	/** minimal constructor */
	public YsmsWechatnews(YsmsUser ysmsUser, String author, String title,
			Date date, String picurl, String url, Integer nindex, Integer verified) {
		this.ysmsUser = ysmsUser;
		this.author = author;
		this.title = title;
		this.date = date;
		this.picurl = picurl;
		this.url = url;
		this.nindex = nindex;
		this.verified = verified;
	}

	/** full constructor */
	public YsmsWechatnews(YsmsUser ysmsUser, String author, String title,
			Date date, String digest, String picurl, String url,
			Integer nindex, Integer verified, Set<YsmsWechatnewsAttr> ysmsWechatnewsAttrs) {
		this.ysmsUser = ysmsUser;
		this.author = author;
		this.title = title;
		this.date = date;
		this.digest = digest;
		this.picurl = picurl;
		this.url = url;
		this.nindex = nindex;
		this.verified = verified;
		this.ysmsWechatnewsAttrs = ysmsWechatnewsAttrs;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "nid", unique = true, nullable = false)
	public Integer getNid() {
		return this.nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	public YsmsUser getYsmsUser() {
		return this.ysmsUser;
	}

	public void setYsmsUser(YsmsUser ysmsUser) {
		this.ysmsUser = ysmsUser;
	}

	@Column(name = "author", nullable = false, length = 128)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "title", nullable = false, length = 512)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "date", nullable = false, length = 0)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "digest", length = 512)
	public String getDigest() {
		return this.digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	@Column(name = "picurl", length = 512)
	public String getPicurl() {
		return this.picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	@Column(name = "url", length = 512)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "nindex")
	public Integer getNindex() {
		return this.nindex;
	}

	public void setNindex(Integer nindex) {
		this.nindex = nindex;
	}

	@Column(name = "verified")
	public Integer getVerified() {
		return verified;
	}

	public void setVerified(Integer verified) {
		this.verified = verified;
	}
	
	@Column(name = "forserviceflag", nullable = false)
	public Integer getForServiceFlag() {
		return forServiceFlag;
	}

	public void setForServiceFlag(Integer forServiceFlag) {
		this.forServiceFlag = forServiceFlag;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ysmsWechatnews")
	@JsonIgnore
	public Set<YsmsWechatnewsAttr> getYsmsWechatnewsAttrs() {
		return this.ysmsWechatnewsAttrs;
	}

	public void setYsmsWechatnewsAttrs(
			Set<YsmsWechatnewsAttr> ysmsWechatnewsAttrs) {
		this.ysmsWechatnewsAttrs = ysmsWechatnewsAttrs;
	}

}