package com.techvg.smtrthr.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.smtrthr.domain.ReportingStructure} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportingStructureDTO implements Serializable {

    private Long id;

    private Long employeeId;

    private Long reportingEmpId;

    private String status;

    private Long reportingStrId;

    private Instant lastModified;

    private String lastModifiedBy;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getReportingEmpId() {
        return reportingEmpId;
    }

    public void setReportingEmpId(Long reportingEmpId) {
        this.reportingEmpId = reportingEmpId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getReportingStrId() {
        return reportingStrId;
    }

    public void setReportingStrId(Long reportingStrId) {
        this.reportingStrId = reportingStrId;
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
        if (!(o instanceof ReportingStructureDTO)) {
            return false;
        }

        ReportingStructureDTO reportingStructureDTO = (ReportingStructureDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reportingStructureDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportingStructureDTO{" +
            "id=" + getId() +
            ", employeeId=" + getEmployeeId() +
            ", reportingEmpId=" + getReportingEmpId() +
            ", status='" + getStatus() + "'" +
            ", reportingStrId=" + getReportingStrId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
