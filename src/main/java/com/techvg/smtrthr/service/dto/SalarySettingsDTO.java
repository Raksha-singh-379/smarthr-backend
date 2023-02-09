package com.techvg.smtrthr.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.smtrthr.domain.SalarySettings} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalarySettingsDTO implements Serializable {

    private Long id;

    private Double da;

    private Double hra;

    private Double employeeShare;

    private Double companyShare;

    private Instant salaryFrom;

    private Instant salaryTo;

    private Instant lastModified;

    private String lastModifiedBy;

    private String status;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDa() {
        return da;
    }

    public void setDa(Double da) {
        this.da = da;
    }

    public Double getHra() {
        return hra;
    }

    public void setHra(Double hra) {
        this.hra = hra;
    }

    public Double getEmployeeShare() {
        return employeeShare;
    }

    public void setEmployeeShare(Double employeeShare) {
        this.employeeShare = employeeShare;
    }

    public Double getCompanyShare() {
        return companyShare;
    }

    public void setCompanyShare(Double companyShare) {
        this.companyShare = companyShare;
    }

    public Instant getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(Instant salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public Instant getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(Instant salaryTo) {
        this.salaryTo = salaryTo;
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
        if (!(o instanceof SalarySettingsDTO)) {
            return false;
        }

        SalarySettingsDTO salarySettingsDTO = (SalarySettingsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, salarySettingsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalarySettingsDTO{" +
            "id=" + getId() +
            ", da=" + getDa() +
            ", hra=" + getHra() +
            ", employeeShare=" + getEmployeeShare() +
            ", companyShare=" + getCompanyShare() +
            ", salaryFrom='" + getSalaryFrom() + "'" +
            ", salaryTo='" + getSalaryTo() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
