package com.techvg.smtrthr.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.smtrthr.domain.Approver} entity. This class is used
 * in {@link com.techvg.smtrthr.web.rest.ApproverResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /approvers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApproverCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter approverName;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter status;

    private LongFilter approvalSettingId;

    private LongFilter departmentId;

    private LongFilter companyId;

    private Boolean distinct;

    public ApproverCriteria() {}

    public ApproverCriteria(ApproverCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.approverName = other.approverName == null ? null : other.approverName.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.approvalSettingId = other.approvalSettingId == null ? null : other.approvalSettingId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApproverCriteria copy() {
        return new ApproverCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getApproverName() {
        return approverName;
    }

    public StringFilter approverName() {
        if (approverName == null) {
            approverName = new StringFilter();
        }
        return approverName;
    }

    public void setApproverName(StringFilter approverName) {
        this.approverName = approverName;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getApprovalSettingId() {
        return approvalSettingId;
    }

    public LongFilter approvalSettingId() {
        if (approvalSettingId == null) {
            approvalSettingId = new LongFilter();
        }
        return approvalSettingId;
    }

    public void setApprovalSettingId(LongFilter approvalSettingId) {
        this.approvalSettingId = approvalSettingId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public LongFilter departmentId() {
        if (departmentId == null) {
            departmentId = new LongFilter();
        }
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public LongFilter companyId() {
        if (companyId == null) {
            companyId = new LongFilter();
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ApproverCriteria that = (ApproverCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(approverName, that.approverName) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(status, that.status) &&
            Objects.equals(approvalSettingId, that.approvalSettingId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, approverName, lastModified, lastModifiedBy, status, approvalSettingId, departmentId, companyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApproverCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (approverName != null ? "approverName=" + approverName + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (approvalSettingId != null ? "approvalSettingId=" + approvalSettingId + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
