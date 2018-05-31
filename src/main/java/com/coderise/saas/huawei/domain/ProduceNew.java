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
 * A ProduceNew.
 */
@Entity
@Table(name = "produce_new")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProduceNew implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(name = "auth_token", length = 50)
    private String authToken;

    @Column(name = "time_stamp")
    private Instant timeStamp;

    @Size(max = 100)
    @Column(name = "customer_id", length = 100)
    private String customerId;

    @Size(max = 64)
    @Column(name = "customer_name", length = 64)
    private String customerName;

    @Size(max = 256)
    @Column(name = "mobile_phone", length = 256)
    private String mobilePhone;

    @Size(max = 256)
    @Column(name = "email", length = 256)
    private String email;

    @Size(max = 64)
    @Column(name = "business_id", length = 64)
    private String businessId;

    @Size(max = 64)
    @Column(name = "order_id", length = 64)
    private String orderId;

    @Size(max = 64)
    @Column(name = "sku_code", length = 64)
    private String skuCode;

    @Size(max = 64)
    @Column(name = "product_id", length = 64)
    private String productId;

    @Column(name = "test_flag")
    private Boolean testFlag;

    @Column(name = "trial_flag")
    private Boolean trialFlag;

    @Column(name = "expire_time")
    private ZonedDateTime expireTime;

    @ManyToOne
    private Instance instance;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public ProduceNew authToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public ProduceNew timeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCustomerId() {
        return customerId;
    }

    public ProduceNew customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ProduceNew customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public ProduceNew mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public ProduceNew email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessId() {
        return businessId;
    }

    public ProduceNew businessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getOrderId() {
        return orderId;
    }

    public ProduceNew orderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public ProduceNew skuCode(String skuCode) {
        this.skuCode = skuCode;
        return this;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getProductId() {
        return productId;
    }

    public ProduceNew productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Boolean isTestFlag() {
        return testFlag;
    }

    public ProduceNew testFlag(Boolean testFlag) {
        this.testFlag = testFlag;
        return this;
    }

    public void setTestFlag(Boolean testFlag) {
        this.testFlag = testFlag;
    }

    public Boolean isTrialFlag() {
        return trialFlag;
    }

    public ProduceNew trialFlag(Boolean trialFlag) {
        this.trialFlag = trialFlag;
        return this;
    }

    public void setTrialFlag(Boolean trialFlag) {
        this.trialFlag = trialFlag;
    }

    public ZonedDateTime getExpireTime() {
        return expireTime;
    }

    public ProduceNew expireTime(ZonedDateTime expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public void setExpireTime(ZonedDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public Instance getInstance() {
        return instance;
    }

    public ProduceNew instance(Instance instance) {
        this.instance = instance;
        return this;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
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
        ProduceNew produceNew = (ProduceNew) o;
        if (produceNew.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produceNew.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProduceNew{" +
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
