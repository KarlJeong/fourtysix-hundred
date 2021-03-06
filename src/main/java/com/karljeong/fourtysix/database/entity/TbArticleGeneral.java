package com.karljeong.fourtysix.database.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import com.karljeong.fourtysix.utils.DateUtil;
import com.karljeong.fourtysix.utils.UserUtil;

/**
 * The persistent class for the TB_ARTICLE_General database table.
 *
 */
@Entity
@Table(name = "TB_ARTICLE_GENERAL")
@NamedQuery(name = "TbArticleGeneral.findAll", query = "SELECT t FROM TbArticleGeneral t")
public class TbArticleGeneral implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ARTICLE_ID")
	private BigInteger articleId;

	@Column(name = "ARTICLE_BAN_YN")
	private byte articleBanYn;

	@Column(name = "ARTICLE_CATEGORY_CV")
	private String articleCategoryCv;

	@Lob
	@Column(name = "ARTICLE_CONTENTS")
	private String articleContents;

	@Column(name = "ARTICLE_DELETE_YN")
	private byte articleDeleteYn;

	@Column(name = "ARTICLE_LIKE_COUNT")
	private int articleLikeCount;

	@Column(name = "ARTICLE_MODIFIER_ID", insertable = false)
	private BigInteger articleModifierId;

	@Column(name = "ARTICLE_MODIFY_DATETIME", insertable = false)
	private Timestamp articleModifyDatetime = DateUtil.getTimestamp();

	@Column(name = "ARTICLE_REPLY_COUNT")
	private int articleReplyCount;

	@Column(name = "ARTICLE_REPORT_COUNT")
	private int articleReportCount;

	@Column(name = "ARTICLE_TITLE")
	private String articleTitle;

	@Column(name = "ARTICLE_VIEW_COUNT", updatable = false)
	private int articleViewCount = 0;

	@Column(name = "ARTICLE_WRITE_DATETIME", updatable = false)
	private Timestamp articleWriteDatetime = DateUtil.getTimestamp();

	@Column(name = "ARTICLE_WRITER_ID", updatable = false)
	private BigInteger articleWriterId;

    @ManyToOne(targetEntity=TbComUser.class, fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="ARTICLE_WRITER_ID", insertable = false, updatable = false)
    private TbComUser articleGeneralWriter;

	@Column(name = "CREATE_DATETIME", updatable = false)
	private Timestamp createDatetime = DateUtil.getTimestamp();

	@Column(name = "CREATE_USER_ID", updatable = false)
	private BigInteger createUserId;

	@Column(name = "UPDATE_DATETIME", insertable = false)
	private Timestamp updateDatetime;

	@Column(name = "UPDATE_USER_ID", insertable = false)
	private BigInteger updateUserId;

	@Column(name = "CONTAIN_IMAGE")
	private byte containImage = 0;

	@Transient
	private String articleCategoryName;

	public TbArticleGeneral() {
	}

	public BigInteger getArticleId() {
		return this.articleId;
	}

	public void setArticleId(BigInteger articleId) {
		this.articleId = articleId;
	}

	public byte getArticleBanYn() {
		return this.articleBanYn;
	}

	public void setArticleBanYn(byte articleBanYn) {
		this.articleBanYn = articleBanYn;
	}


	public String getArticleContents() {
		return this.articleContents;
	}

	public void setArticleContents(String articleContents) {
		this.articleContents = articleContents;
	}

	public byte getArticleDeleteYn() {
		return this.articleDeleteYn;
	}

	public void setArticleDeleteYn(byte articleDeleteYn) {
		this.articleDeleteYn = articleDeleteYn;
	}

	public int getArticleLikeCount() {
		return this.articleLikeCount;
	}

	public void setArticleLikeCount(int articleLikeCount) {
		this.articleLikeCount = articleLikeCount;
	}

	public BigInteger getArticleModifierId() {
		return this.articleModifierId;
	}

	public void setArticleModifierId(BigInteger articleModifierId) {
		this.articleModifierId = articleModifierId;
	}

	public Timestamp getArticleModifyDatetime() {
		return this.articleModifyDatetime;
	}

	public void setArticleModifyDatetime(Timestamp articleModifyDatetime) {
		this.articleModifyDatetime = articleModifyDatetime;
	}

	public int getArticleReplyCount() {
		return this.articleReplyCount;
	}

	public void setArticleReplyCount(int articleReplyCount) {
		this.articleReplyCount = articleReplyCount;
	}

	public int getArticleReportCount() {
		return this.articleReportCount;
	}

	public void setArticleReportCount(int articleReportCount) {
		this.articleReportCount = articleReportCount;
	}

	public String getArticleTitle() {
		return this.articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public int getArticleViewCount() {
		return this.articleViewCount;
	}

	public void setArticleViewCount(int articleViewCount) {
		this.articleViewCount = articleViewCount;
	}

	public Timestamp getArticleWriteDatetime() {
		return this.articleWriteDatetime;
	}

	public void setArticleWriteDatetime(Timestamp articleWriteDatetime) {
		this.articleWriteDatetime = articleWriteDatetime;
	}

	public BigInteger getArticleWriterId() {
		return articleWriterId;
	}

	public void setArticleWriterId(BigInteger articleWriterId) {
		this.articleWriterId = articleWriterId;
	}

	public TbComUser getArticleGeneralWriter() {
		return articleGeneralWriter;
	}

	public void setArticleGeneralWriter(TbComUser articleGeneralWriter) {
		this.articleGeneralWriter = articleGeneralWriter;
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

	public String getArticleCategoryCv() {
        return articleCategoryCv;
    }

    public void setArticleCategoryCv(String articleCategoryCv) {
        this.articleCategoryCv = articleCategoryCv;
    }

    public String getArticleCategoryName() {
        return articleCategoryName;
    }

    public void setArticleCategoryName(String articleCategoryName) {
        this.articleCategoryName = articleCategoryName;
    }
    
    public byte getContainImage() {
		return containImage;
	}

	public void setContainImage(byte containImage) {
		this.containImage = containImage;
	}

	public void setUserInfo(HttpServletRequest request) {
		BigInteger userId = UserUtil.getUserId();
		this.createUserId = userId;
		this.updateUserId = userId;
		this.articleWriterId = userId;
		this.articleModifierId = userId;
	}

}