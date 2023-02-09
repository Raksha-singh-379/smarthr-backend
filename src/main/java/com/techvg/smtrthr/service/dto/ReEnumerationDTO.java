package com.techvg.smtrthr.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.smtrthr.domain.ReEnumeration} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReEnumerationDTO implements Serializable {

    private Long id;

    private String salaryBasis;

    private Double amount;

    private String paymentType;

    private Instant lastModified;

    private String lastModifiedBy;

    private String status;

    private Long employeId;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalaryBasis() {
        return salaryBasis;
    }

    public void setSalaryBasis(String salaryBasis) {
        this.salaryBasis = salaryBasis;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReEnumerationDTO)) {
            return false;
        }

        ReEnumerationDTO reEnumerationDTO = (ReEnumerationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reEnumerationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReEnumerationDTO{" +
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
