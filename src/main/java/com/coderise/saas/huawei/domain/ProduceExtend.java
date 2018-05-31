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
 * A ProduceExtend.
 */
@Entity
@Table(name = "produce_extend")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProduceExtend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 64)
    @Column(name = "order_id", length = 64)
    private String orderId;

    @Size(max = 64)
    @Column(name = "product_id", length = 64)
    private String productId;

    @Column(name = "expire_time")
    private ZonedDateTime expireTime;

    @Column(name = "test_flag")
    private Boolean testFlag;

    @Column(name = "trial_to_formal")
    private Boolean trialToFormal;

    @Column(name = "time_stamp")
    private Instant timeStamp;

    @ManyToOne
    private Instance instance;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public ProduceExtend orderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public ProduceExtend productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ZonedDateTime getExpireTime() {
        return expireTime;
    }

    public ProduceExtend expireTime(ZonedDateTime expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public void setExpireTime(ZonedDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public Boolean isTestFlag() {
        return testFlag;
    }

    public ProduceExtend testFlag(Boolean testFlag) {
        this.testFlag = testFlag;
        return this;
    }

    public void setTestFlag(Boolean testFlag) {
        this.testFlag = testFlag;
    }

    public Boolean isTrialToFormal() {
        return trialToFormal;
    }

    public ProduceExtend trialToFormal(Boolean trialToFormal) {
        this.trialToFormal = trialToFormal;
        return this;
    }

    public void setTrialToFormal(Boolean trialToFormal) {
        this.trialToFormal = trialToFormal;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public ProduceExtend timeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Instance getInstance() {
        return instance;
    }

    public ProduceExtend instance(Instance instance) {
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
        ProduceExtend produceExtend = (ProduceExtend) o;
        if (produceExtend.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produceExtend.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProduceExtend{" +
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
