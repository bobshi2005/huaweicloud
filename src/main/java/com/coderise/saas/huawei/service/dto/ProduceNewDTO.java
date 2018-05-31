package com.coderise.saas.huawei.service.dto;


import java.time.Instant;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ProduceNew entity.
 */
public class ProduceNewDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String authToken;

    private Instant timeStamp;

    @Size(max = 100)
    private String customerId;

    @Size(max = 64)
    private String customerName;

    @Size(max = 256)
    private String mobilePhone;

    @Size(max = 256)
    private String email;

    @Size(max = 64)
    private String businessId;

    @Size(max = 64)
    private String orderId;

    @Size(max = 64)
    private String skuCode;

    @Size(max = 64)
    private String productId;

    private Boolean testFlag;

    private Boolean trialFlag;

    private ZonedDateTime expireTime;

    private Long instanceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Boolean isTestFlag() {
        return testFlag;
    }

    public void setTestFlag(Boolean testFlag) {
        this.testFlag = testFlag;
    }

    public Boolean isTrialFlag() {
        return trialFlag;
    }

    public void setTrialFlag(Boolean trialFlag) {
        this.trialFlag = trialFlag;
    }

    public ZonedDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(ZonedDateTime expireTime) {
        this.expireTime = expireTime;
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

        ProduceNewDTO produceNewDTO = (ProduceNewDTO) o;
        if(produceNewDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produceNewDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProduceNewDTO{" +
            "id=" + getId() +
            ", authToken='" + getAuthToken() + "'" +
            ", timeStamp='" + getTimeStamp() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", businessId='" + getBusinessId() + "'" +
            ", orderId='" + getOrderId() + "'" +
            ", skuCode='" + getSkuCode() + "'" +
            ", productId='" + getProductId() + "'" +
            ", testFlag='" + isTestFlag() + "'" +
            ", trialFlag='" + isTrialFlag() + "'" +
            ", expireTime='" + getExpireTime() + "'" +
            "}";
    }
}
