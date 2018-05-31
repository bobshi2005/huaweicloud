package com.coderise.saas.huawei.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Instance.
 */
@Entity
@Table(name = "instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Instance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 512)
    @Column(name = "app_front_end_url", length = 512)
    private String appFrontEndUrl;

    @Size(max = 512)
    @Column(name = "app_admin_url", length = 512)
    private String appAdminUrl;

    @Size(max = 128)
    @Column(name = "user_name", length = 128)
    private String userName;

    @Size(max = 128)
    @Column(name = "_password", length = 128)
    private String password;

    @Column(name = "expire_time")
    private ZonedDateTime expireTime;

    @Column(name = "is_released")
    private Boolean isReleased;

    @Column(name = "is_trial")
    private Boolean isTrial;

    @Column(name = "update_time")
    private Instant updateTime;

    @Size(max = 512)
    @Column(name = "result_code", length = 512)
    private String resultCode;

    @Size(max = 2000)
    @Column(name = "result_msg", length = 2000)
    private String resultMsg;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppFrontEndUrl() {
        return appFrontEndUrl;
    }

    public Instance appFrontEndUrl(String appFrontEndUrl) {
        this.appFrontEndUrl = appFrontEndUrl;
        return this;
    }

    public void setAppFrontEndUrl(String appFrontEndUrl) {
        this.appFrontEndUrl = appFrontEndUrl;
    }

    public String getAppAdminUrl() {
        return appAdminUrl;
    }

    public Instance appAdminUrl(String appAdminUrl) {
        this.appAdminUrl = appAdminUrl;
        return this;
    }

    public void setAppAdminUrl(String appAdminUrl) {
        this.appAdminUrl = appAdminUrl;
    }

    public String getUserName() {
        return userName;
    }

    public Instance userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public Instance password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ZonedDateTime getExpireTime() {
        return expireTime;
    }

    public Instance expireTime(ZonedDateTime expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public void setExpireTime(ZonedDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public Boolean isIsReleased() {
        return isReleased;
    }

    public Instance isReleased(Boolean isReleased) {
        this.isReleased = isReleased;
        return this;
    }

    public void setIsReleased(Boolean isReleased) {
        this.isReleased = isReleased;
    }

    public Boolean isIsTrial() {
        return isTrial;
    }

    public Instance isTrial(Boolean isTrial) {
        this.isTrial = isTrial;
        return this;
    }

    public void setIsTrial(Boolean isTrial) {
        this.isTrial = isTrial;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public Instance updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getResultCode() {
        return resultCode;
    }

    public Instance resultCode(String resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public Instance resultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
        return this;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Instance instance = (Instance) o;
        if (instance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), instance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Instance{" +
            "id=" + getId() +
            ", appFrontEndUrl='" + getAppFrontEndUrl() + "'" +
            ", appAdminUrl='" + getAppAdminUrl() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", expireTime='" + getExpireTime() + "'" +
            ", isReleased='" + isIsReleased() + "'" +
            ", isTrial='" + isIsTrial() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", resultCode='" + getResultCode() + "'" +
            ", resultMsg='" + getResultMsg() + "'" +
            "}";
    }
}
