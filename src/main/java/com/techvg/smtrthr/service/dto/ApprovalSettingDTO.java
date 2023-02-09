package com.techvg.smtrthr.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.smtrthr.domain.ApprovalSetting} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalSettingDTO implements Serializable {

    private Long id;

    private Boolean isSequenceApproval;

    private Boolean isSimultaneousApproval;

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

    public Boolean getIsSequenceApproval() {
        return isSequenceApproval;
    }

    public void setIsSequenceApproval(Boolean isSequenceApproval) {
        this.isSequenceApproval = isSequenceApproval;
    }

    public Boolean getIsSimultaneousApproval() {
        return isSimultaneousApproval;
    }

    public void setIsSimultaneousApproval(Boolean isSimultaneousApproval) {
        this.isSimultaneousApproval = isSimultaneousApproval;
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
        if (!(o instanceof ApprovalSettingDTO)) {
            return false;
        }

        ApprovalSettingDTO approvalSettingDTO = (ApprovalSettingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, approvalSettingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalSettingDTO{" +
            "id=" + getId() +
            ", isSequenceApproval='" + getIsSequenceApproval() + "'" +
            ", isSimultaneousApproval='" + getIsSimultaneousApproval() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
