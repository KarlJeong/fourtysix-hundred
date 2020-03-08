package com.karljeong.fourtysix.database.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the TB_COM_USER database table.
 * 
 */
@Entity
@Table(name="TB_COM_USER")
@NamedQuery(name="TbComUser.findAll", query="SELECT t FROM TbComUser t")
public class TbComUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private String userId;

	@Column(name="ACTIVATE_DATETIME")
	private Timestamp activateDatetime;

	@Column(name="ACTIVATE_KEY")
	private String activateKey;

	@Column(name="ACTIVATE_YN")
	private byte activateYn;

	@Column(name="CREATE_DATETIME")
	private Timestamp createDatetime;

	@Column(name="CREATE_USER_ID")
	private BigInteger createUserId;

	@Column(name="EMAIL")
	private String email;

	@Column(name="LAST_LOGIN_DATETIME")
	private Timestamp lastLoginDatetime;

	@Column(name="LOGIN_ID")
	private String loginId;

	@Column(name="LOGIN_PASSWORD")
	private String loginPassword;

	@Column(name="PROFILE_FILE_ID")
	private BigInteger profileFileId;

	@Column(name="SNS_ID")
	private String snsId;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID")
	private BigInteger updateUserId;

	@Column(name="USER_LOCALE")
	private String userLocale;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="USER_NICKNAME")
	private String userNickname;

	//bi-directional many-to-many association to TbComAuth
	@ManyToMany
	@JoinTable(
		name="TB_MAPP_USER_AUTH"
		, joinColumns={
			@JoinColumn(name="USER_ID", insertable = false, updatable = false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="AUTH_ID", insertable = false, updatable = false)
			}
		)
	private List<TbComAuth> tbComAuths;

	//bi-directional many-to-one association to TbComUserBan
	@OneToMany(mappedBy="tbComUser")
	private List<TbComUserBan> tbComUserBans;

	public TbComUser() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getActivateDatetime() {
		return this.activateDatetime;
	}

	public void setActivateDatetime(Timestamp activateDatetime) {
		this.activateDatetime = activateDatetime;
	}

	public String getActivateKey() {
		return this.activateKey;
	}

	public void setActivateKey(String activateKey) {
		this.activateKey = activateKey;
	}

	public byte getActivateYn() {
		return this.activateYn;
	}

	public void setActivateYn(byte activateYn) {
		this.activateYn = activateYn;
	}

	public Timestamp getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Timestamp createDatetime) {
		this.createDatetime = createDatetime;
	}

	public BigInteger getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(BigInteger createUserId) {
		this.createUserId = createUserId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getLastLoginDatetime() {
		return this.lastLoginDatetime;
	}

	public void setLastLoginDatetime(Timestamp lastLoginDatetime) {
		this.lastLoginDatetime = lastLoginDatetime;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public BigInteger getProfileFileId() {
		return this.profileFileId;
	}

	public void setProfileFileId(BigInteger profileFileId) {
		this.profileFileId = profileFileId;
	}

	public String getSnsId() {
		return this.snsId;
	}

	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}

	public Timestamp getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public BigInteger getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(BigInteger updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUserLocale() {
		return this.userLocale;
	}

	public void setUserLocale(String userLocale) {
		this.userLocale = userLocale;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNickname() {
		return this.userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public List<TbComAuth> getTbComAuths() {
		return this.tbComAuths;
	}

	public void setTbComAuths(List<TbComAuth> tbComAuths) {
		this.tbComAuths = tbComAuths;
	}

	public List<TbComUserBan> getTbComUserBans() {
		return this.tbComUserBans;
	}

	public void setTbComUserBans(List<TbComUserBan> tbComUserBans) {
		this.tbComUserBans = tbComUserBans;
	}

	public TbComUserBan addTbComUserBan(TbComUserBan tbComUserBan) {
		getTbComUserBans().add(tbComUserBan);
		tbComUserBan.setTbComUser(this);

		return tbComUserBan;
	}

	public TbComUserBan removeTbComUserBan(TbComUserBan tbComUserBan) {
		getTbComUserBans().remove(tbComUserBan);
		tbComUserBan.setTbComUser(null);

		return tbComUserBan;
	}

}