package com.techvg.smtrthr.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.smtrthr.domain.LeavePolicy} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeavePolicyDTO implements Serializable {

    private Long id;

    private String leaveType;

    private Boolean isCarryForword;

    private String employeeType;

    private String genderLeave;

    private String leaveStatus;

    private String totalLeave;

    private String maxLeave;

    private Boolean hasproRataLeave;

    private String description;

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

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public Boolean getIsCarryForword() {
        return isCarryForword;
    }

    public void setIsCarryForword(Boolean isCarryForword) {
        this.isCarryForword = isCarryForword;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getGenderLeave() {
        return genderLeave;
    }

    public void setGenderLeave(String genderLeave) {
        this.genderLeave = genderLeave;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getTotalLeave() {
        return totalLeave;
    }

    public void setTotalLeave(String totalLeave) {
        this.totalLeave = totalLeave;
    }

    public String getMaxLeave() {
        return maxLeave;
    }

    public void setMaxLeave(String maxLeave) {
        this.maxLeave = maxLeave;
    }

    public Boolean getHasproRataLeave() {
        return hasproRataLeave;
    }

    public void setHasproRataLeave(Boolean hasproRataLeave) {
        this.hasproRataLeave = hasproRataLeave;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(o instanceof LeavePolicyDTO)) {
            return false;
        }

        LeavePolicyDTO leavePolicyDTO = (LeavePolicyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, leavePolicyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeavePolicyDTO{" +
            "id=" + getId() +
            ", leaveType='" + getLeaveType() + "'" +
            ", isCarryForword='" + getIsCarryForword() + "'" +
            ", employeeType='" + getEmployeeType() + "'" +
            ", genderLeave='" + getGenderLeave() + "'" +
            ", leaveStatus='" + getLeaveStatus() + "'" +
            ", totalLeave='" + getTotalLeave() + "'" +
            ", maxLeave='" + getMaxLeave() + "'" +
            ", hasproRataLeave='" + getHasproRataLeave() + "'" +
            ", description='" + getDescription() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
