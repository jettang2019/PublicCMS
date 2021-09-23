package com.publiccms.entities.cms;
// Generated 2021-09-23 16:55:08 by Hibernate Tools 6.0.0-SNAPSHOT

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.publiccms.common.generator.annotation.GeneratorColumn;

/**
 * CmsUserSurvey generated by hbm2java
 */
@Entity
@Table(name = "cms_user_survey")
public class CmsUserSurvey implements java.io.Serializable {

    /**
    *
    */
    private static final long serialVersionUID = 1L;

    @GeneratorColumn(title = "ID")
    private CmsUserSurveyId id;
    @GeneratorColumn(title = "站点", condition = true)
    private short siteId;
    @GeneratorColumn(title = "分数", order = true)
    private Integer score;
    @GeneratorColumn(title = "创建日期", order = true)
    private Date createDate;

    public CmsUserSurvey() {
    }

    public CmsUserSurvey(short siteId, Date createDate) {
        this.siteId = siteId;
        this.createDate = createDate;
    }

    public CmsUserSurvey(short siteId, Integer score, Date createDate) {
        this.siteId = siteId;
        this.score = score;
        this.createDate = createDate;
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
            @AttributeOverride(name = "questionId", column = @Column(name = "question_id", nullable = false)) })
    public CmsUserSurveyId getId() {
        return this.id;
    }

    public void setId(CmsUserSurveyId id) {
        this.id = id;
    }

    @Column(name = "site_id", nullable = false)
    public short getSiteId() {
        return this.siteId;
    }

    public void setSiteId(short siteId) {
        this.siteId = siteId;
    }

    @Column(name = "score", nullable = false)
    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, length = 19)
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
