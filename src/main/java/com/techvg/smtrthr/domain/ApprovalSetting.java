package com.techvg.smtrthr.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApprovalSetting.
 */
@Entity
@Table(name = "approval_setting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_sequence_approval")
    private Boolean isSequenceApproval;

    @Column(name = "is_simultaneous_approval")
    private Boolean isSimultaneousApproval;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "status")
    private String status;

    @Column(name = "company_id")
    private Long companyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApprovalSetting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsSequenceApproval() {
        return this.isSequenceApproval;
    }

    public ApprovalSetting isSequenceApproval(Boolean isSequenceApproval) {
        this.setIsSequenceApproval(isSequenceApproval);
        return this;
    }

    public void setIsSequenceApproval(Boolean isSequenceApproval) {
        this.isSequenceApproval = isSequenceApproval;
    }

    public Boolean getIsSimultaneousApproval() {
        return this.isSimultaneousApproval;
    }

    public ApprovalSetting isSimultaneousApproval(Boolean isSimultaneousApproval) {
        this.setIsSimultaneousApproval(isSimultaneousApproval);
        return this;
    }

    public void setIsSimultaneousApproval(Boolean isSimultaneousApproval) {
        this.isSimultaneousApproval = isSimultaneousApproval;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public ApprovalSetting lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ApprovalSetting lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getStatus() {
        return this.status;
    }

    public ApprovalSetting status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public ApprovalSetting companyId(Long companyId) {
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
        if (!(o instanceof ApprovalSetting)) {
            return false;
        }
        return id != null && id.equals(((ApprovalSetting) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalSetting{" +
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
