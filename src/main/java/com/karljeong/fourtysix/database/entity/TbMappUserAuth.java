package com.karljeong.fourtysix.database.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.karljeong.fourtysix.utils.DateUtil;


/**
 * The persistent class for the TB_MAPP_USER_AUTH database table.
 *
 */
@Entity
@Table(name="TB_MAPP_USER_AUTH")
@NamedQuery(name="TbMappUserAuth.findAll", query="SELECT t FROM TbMappUserAuth t")
public class TbMappUserAuth implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TbMappUserAuthPK id;

    @Column(name = "CREATE_DATETIME", updatable = false)
    private Timestamp createDatetime = DateUtil.getTimestamp();

    @Column(name="CREATE_USER_ID", updatable = false)
    private BigInteger createUserId;

    @Column(name="UPDATE_DATETIME", insertable = false)
    private Timestamp updateDatetime = DateUtil.getTimestamp();

    @Column(name = "UPDATE_USER_ID", insertable = false)
    private BigInteger updateUserId;

    @Column(name="DELETE_YN")
    private byte deleteYn;

	public TbMappUserAuth() {
	}

	public TbMappUserAuthPK getId() {
		return this.id;
	}

	public void setId(TbMappUserAuthPK id) {
		this.id = id;
	}

    public Timestamp getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Timestamp createDatetime) {
        this.createDatetime = createDatetime;
    }

    public BigInteger getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(BigInteger createUserId) {
        this.createUserId = createUserId;
    }

    public Timestamp getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Timestamp updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public BigInteger getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(BigInteger updateUserId) {
        this.updateUserId = updateUserId;
    }

    public byte getDeleteYn() {
        return deleteYn;
    }

    public void setDeleteYn(byte deleteYn) {
        this.deleteYn = deleteYn;
    }

}