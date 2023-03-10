package com.techvg.smtrthr.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.smtrthr.domain.Tds} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TdsDTO implements Serializable {

    private Long id;

    private Instant salaryFrom;

    private Instant salaryTo;

    private Double percentage;

    private Instant lastModified;

    private String lastModifiedBy;

    private String status;

    private Long salarySettingId;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
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

    public Long getSalarySettingId() {
        return salarySettingId;
    }

    public void setSalarySettingId(Long salarySettingId) {
        this.salarySettingId = salarySettingId;
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
        if (!(o instanceof TdsDTO)) {
            return false;
        }

        TdsDTO tdsDTO = (TdsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tdsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TdsDTO{" +
            "id=" + getId() +
            ", salaryFrom='" + getSalaryFrom() + "'" +
            ", salaryTo='" + getSalaryTo() + "'" +
            ", percentage=" + getPercentage() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", salarySettingId=" + getSalarySettingId() +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
