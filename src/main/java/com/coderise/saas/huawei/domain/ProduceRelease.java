package com.coderise.saas.huawei.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ProduceRelease.
 */
@Entity
@Table(name = "produce_release")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProduceRelease implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test_flag")
    private Boolean testFlag;

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

    public Boolean isTestFlag() {
        return testFlag;
    }

    public ProduceRelease testFlag(Boolean testFlag) {
        this.testFlag = testFlag;
        return this;
    }

    public void setTestFlag(Boolean testFlag) {
        this.testFlag = testFlag;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public ProduceRelease timeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Instance getInstance() {
        return instance;
    }

    public ProduceRelease instance(Instance instance) {
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
        ProduceRelease produceRelease = (ProduceRelease) o;
        if (produceRelease.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produceRelease.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProduceRelease{" +
            "id=" + getId() +
            ", testFlag='" + isTestFlag() + "'" +
            ", timeStamp='" + getTimeStamp() + "'" +
            "}";
    }
}
