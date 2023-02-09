package com.techvg.smtrthr.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Approver.
 */
@Entity
@Table(name = "approver")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Approver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "approver_name")
    private String approverName;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "status")
    private String status;

    @Column(name = "approval_setting_id")
    private Long approvalSettingId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "company_id")
    private Long companyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Approver id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApproverName() {
        return this.approverName;
    }

    public Approver approverName(String approverName) {
        this.setApproverName(approverName);
        return this;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Approver lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Approver lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getStatus() {
        return this.status;
    }

    public Approver status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getApprovalSettingId() {
        return this.approvalSettingId;
    }

    public Approver approvalSettingId(Long approvalSettingId) {
        this.setApprovalSettingId(approvalSettingId);
        return this;
    }

    public void setApprovalSettingId(Long approvalSettingId) {
        this.approvalSettingId = approvalSettingId;
    }

    public Long getDepartmentId() {
        return this.departmentId;
    }

    public Approver departmentId(Long departmentId) {
        this.setDepartmentId(departmentId);
        return this;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public Approver companyId(Long companyId) {
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
        if (!(o instanceof Approver)) {
            return false;
        }
        return id != null && id.equals(((Approver) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Approver{" +
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
