package com.coderise.saas.huawei.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ProduceExpire entity.
 */
public class ProduceExpireDTO implements Serializable {

    private Long id;

    private Boolean testFlag;

    private Instant timeStamp;

    private Long instanceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isTestFlag() {
        return testFlag;
    }

    public void setTestFlag(Boolean testFlag) {
        this.testFlag = testFlag;
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

        ProduceExpireDTO produceExpireDTO = (ProduceExpireDTO) o;
        if(produceExpireDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produceExpireDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProduceExpireDTO{" +
            "id=" + getId() +
            ", testFlag='" + isTestFlag() + "'" +
            ", timeStamp='" + getTimeStamp() + "'" +
            "}";
    }
}
