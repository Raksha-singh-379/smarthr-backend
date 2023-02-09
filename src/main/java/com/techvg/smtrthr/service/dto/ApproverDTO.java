package com.techvg.smtrthr.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.smtrthr.domain.Approver} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApproverDTO implements Serializable {

    private Long id;

    private String approverName;

    private Instant lastModified;

    private String lastModifiedBy;

    private String status;

    private Long approvalSettingId;

    private Long departmentId;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
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

    public Long getApprovalSettingId() {
        return approvalSettingId;
    }

    public void setApprovalSettingId(Long approvalSettingId) {
        this.approvalSettingId = approvalSettingId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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
        if (!(o instanceof ApproverDTO)) {
            return false;
        }

        ApproverDTO approverDTO = (ApproverDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, approverDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApproverDTO{" +
            "id=" + getId() +
            ", approverName='" + getApproverName() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", approvalSettingId=" + getApprovalSettingId() +
            ", departmentId=" + getDepartmentId() +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
