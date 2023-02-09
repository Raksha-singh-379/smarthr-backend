package com.techvg.smtrthr.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.smtrthr.domain.WorkExperience} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkExperienceDTO implements Serializable {

    private Long id;

    private String jobTitle;

    private String companyName;

    private Instant startDate;

    private Instant endDate;

    private Instant lastModified;

    private String lastModifiedBy;

    private String status;

    private Long addressId;

    private Long employeeId;

    private Long companyId;

    private Double yearOfExp;

    private String jobDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
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

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Double getYearOfExp() {
        return yearOfExp;
    }

    public void setYearOfExp(Double yearOfExp) {
        this.yearOfExp = yearOfExp;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkExperienceDTO)) {
            return false;
        }

        WorkExperienceDTO workExperienceDTO = (WorkExperienceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workExperienceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkExperienceDTO{" +
            "id=" + getId() +
            ", jobTitle='" + getJobTitle() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", addressId=" + getAddressId() +
            ", employeeId=" + getEmployeeId() +
            ", companyId=" + getCompanyId() +
            ", yearOfExp=" + getYearOfExp() +
            ", jobDesc='" + getJobDesc() + "'" +
            "}";
    }
}
