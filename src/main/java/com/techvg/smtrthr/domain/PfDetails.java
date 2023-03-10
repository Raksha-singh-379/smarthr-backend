package com.techvg.smtrthr.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PfDetails.
 */
@Entity
@Table(name = "pf_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PfDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_pf_contribution")
    private Boolean isPfContribution;

    @Column(name = "pf_number")
    private String pfNumber;

    @Column(name = "pf_rate")
    private Double pfRate;

    @Column(name = "additional_pf_rate")
    private String additionalPfRate;

    @Column(name = "total_pf_rate")
    private Double totalPfRate;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "status")
    private String status;

    @Column(name = "employe_id")
    private Long employeId;

    @Column(name = "re_enumeration_id")
    private Long reEnumerationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PfDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsPfContribution() {
        return this.isPfContribution;
    }

    public PfDetails isPfContribution(Boolean isPfContribution) {
        this.setIsPfContribution(isPfContribution);
        return this;
    }

    public void setIsPfContribution(Boolean isPfContribution) {
        this.isPfContribution = isPfContribution;
    }

    public String getPfNumber() {
        return this.pfNumber;
    }

    public PfDetails pfNumber(String pfNumber) {
        this.setPfNumber(pfNumber);
        return this;
    }

    public void setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }

    public Double getPfRate() {
        return this.pfRate;
    }

    public PfDetails pfRate(Double pfRate) {
        this.setPfRate(pfRate);
        return this;
    }

    public void setPfRate(Double pfRate) {
        this.pfRate = pfRate;
    }

    public String getAdditionalPfRate() {
        return this.additionalPfRate;
    }

    public PfDetails additionalPfRate(String additionalPfRate) {
        this.setAdditionalPfRate(additionalPfRate);
        return this;
    }

    public void setAdditionalPfRate(String additionalPfRate) {
        this.additionalPfRate = additionalPfRate;
    }

    public Double getTotalPfRate() {
        return this.totalPfRate;
    }

    public PfDetails totalPfRate(Double totalPfRate) {
        this.setTotalPfRate(totalPfRate);
        return this;
    }

    public void setTotalPfRate(Double totalPfRate) {
        this.totalPfRate = totalPfRate;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public PfDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public PfDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getStatus() {
        return this.status;
    }

    public PfDetails status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeId() {
        return this.employeId;
    }

    public PfDetails employeId(Long employeId) {
        this.setEmployeId(employeId);
        return this;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    public Long getReEnumerationId() {
        return this.reEnumerationId;
    }

    public PfDetails reEnumerationId(Long reEnumerationId) {
        this.setReEnumerationId(reEnumerationId);
        return this;
    }

    public void setReEnumerationId(Long reEnumerationId) {
        this.reEnumerationId = reEnumerationId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PfDetails)) {
            return false;
        }
        return id != null && id.equals(((PfDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PfDetails{" +
            "id=" + getId() +
            ", isPfContribution='" + getIsPfContribution() + "'" +
            ", pfNumber='" + getPfNumber() + "'" +
            ", pfRate=" + getPfRate() +
            ", additionalPfRate='" + getAdditionalPfRate() + "'" +
            ", totalPfRate=" + getTotalPfRate() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", employeId=" + getEmployeId() +
            ", reEnumerationId=" + getReEnumerationId() +
            "}";
    }
}
