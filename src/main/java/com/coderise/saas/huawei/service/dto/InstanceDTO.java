package com.coderise.saas.huawei.service.dto;


import java.time.Instant;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Instance entity.
 */
public class InstanceDTO implements Serializable {

    private Long id;

    @Size(max = 512)
    private String appFrontEndUrl;

    @Size(max = 512)
    private String appAdminUrl;

    @Size(max = 128)
    private String userName;

    @Size(max = 128)
    private String password;

    private ZonedDateTime expireTime;

    private Boolean isReleased;

    private Boolean isTrial;

    private Instant updateTime;

    @Size(max = 512)
    private String resultCode;

    @Size(max = 2000)
    private String resultMsg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppFrontEndUrl() {
        return appFrontEndUrl;
    }

    public void setAppFrontEndUrl(String appFrontEndUrl) {
        this.appFrontEndUrl = appFrontEndUrl;
    }

    public String getAppAdminUrl() {
        return appAdminUrl;
    }

    public void setAppAdminUrl(String appAdminUrl) {
        this.appAdminUrl = appAdminUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ZonedDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(ZonedDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public Boolean isIsReleased() {
        return isReleased;
    }

    public void setIsReleased(Boolean isReleased) {
        this.isReleased = isReleased;
    }

    public Boolean isIsTrial() {
        return isTrial;
    }

    public void setIsTrial(Boolean isTrial) {
        this.isTrial = isTrial;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstanceDTO instanceDTO = (InstanceDTO) o;
        if(instanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), instanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InstanceDTO{" +
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
