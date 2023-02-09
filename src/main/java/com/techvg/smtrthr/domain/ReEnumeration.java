package com.techvg.smtrthr.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ReEnumeration.
 */
@Entity
@Table(name = "re_enumeration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReEnumeration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "salary_basis")
    private String salaryBasis;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "status")
    private String status;

    @Column(name = "employe_id")
    private Long employeId;

    @Column(name = "company_id")
    private Long companyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReEnumeration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalaryBasis() {
        return this.salaryBasis;
    }

    public ReEnumeration salaryBasis(String salaryBasis) {
        this.setSalaryBasis(salaryBasis);
        return this;
    }

    public void setSalaryBasis(String salaryBasis) {
        this.salaryBasis = salaryBasis;
    }

    public Double getAmount() {
        return this.amount;
    }

    public ReEnumeration amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public ReEnumeration paymentType(String paymentType) {
        this.setPaymentType(paymentType);
        return this;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public ReEnumeration lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ReEnumeration lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getStatus() {
        return this.status;
    }

    public ReEnumeration status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeId() {
        return this.employeId;
    }

    public ReEnumeration employeId(Long employeId) {
        this.setEmployeId(employeId);
        return this;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public ReEnumeration companyId(Long companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReEnumeration)) {
            return false;
        }
        return id != null && id.equals(((ReEnumeration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReEnumeration{" +
            "id=" + getId() +
            ", salaryBasis='" + getSalaryBasis() + "'" +
            ", amount=" + getAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", employeId=" + getEmployeId() +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
