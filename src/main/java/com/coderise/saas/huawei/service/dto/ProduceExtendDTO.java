package com.coderise.saas.huawei.service.dto;


import java.time.Instant;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ProduceExtend entity.
 */
public class ProduceExtendDTO implements Serializable {

    private Long id;

    @Size(max = 64)
    private String orderId;

    @Size(max = 64)
    private String productId;

    private ZonedDateTime expireTime;

    private Boolean testFlag;

    private Boolean trialToFormal;

    private Instant timeStamp;

    private Long instanceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ZonedDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(ZonedDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public Boolean isTestFlag() {
        return testFlag;
    }

    public void setTestFlag(Boolean testFlag) {
        this.testFlag = testFlag;
    }

    public Boolean isTrialToFormal() {
        return trialToFormal;
    }

    public void setTrialToFormal(Boolean trialToFormal) {
        this.trialToFormal = trialToFormal;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProduceExtendDTO produceExtendDTO = (ProduceExtendDTO) o;
        if(produceExtendDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produceExtendDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProduceExtendDTO{" +
            "id=" + getId() +
            ", orderId='" + getOrderId() + "'" +
            ", productId='" + getProductId() + "'" +
            ", expireTime='" + getExpireTime() + "'" +
            ", testFlag='" + isTestFlag() + "'" +
            ", trialToFormal='" + isTrialToFormal() + "'" +
            ", timeStamp='" + getTimeStamp() + "'" +
            "}";
    }
}
