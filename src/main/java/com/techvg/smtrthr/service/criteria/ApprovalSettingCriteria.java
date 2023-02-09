package com.techvg.smtrthr.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.smtrthr.domain.ApprovalSetting} entity. This class is used
 * in {@link com.techvg.smtrthr.web.rest.ApprovalSettingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /approval-settings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalSettingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter isSequenceApproval;

    private BooleanFilter isSimultaneousApproval;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter status;

    private LongFilter companyId;

    private Boolean distinct;

    public ApprovalSettingCriteria() {}

    public ApprovalSettingCriteria(ApprovalSettingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isSequenceApproval = other.isSequenceApproval == null ? null : other.isSequenceApproval.copy();
        this.isSimultaneousApproval = other.isSimultaneousApproval == null ? null : other.isSimultaneousApproval.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApprovalSettingCriteria copy() {
        return new ApprovalSettingCriteria(this);
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

    public BooleanFilter getIsSequenceApproval() {
        return isSequenceApproval;
    }

    public BooleanFilter isSequenceApproval() {
        if (isSequenceApproval == null) {
            isSequenceApproval = new BooleanFilter();
        }
        return isSequenceApproval;
    }

    public void setIsSequenceApproval(BooleanFilter isSequenceApproval) {
        this.isSequenceApproval = isSequenceApproval;
    }

    public BooleanFilter getIsSimultaneousApproval() {
        return isSimultaneousApproval;
    }

    public BooleanFilter isSimultaneousApproval() {
        if (isSimultaneousApproval == null) {
            isSimultaneousApproval = new BooleanFilter();
        }
        return isSimultaneousApproval;
    }

    public void setIsSimultaneousApproval(BooleanFilter isSimultaneousApproval) {
        this.isSimultaneousApproval = isSimultaneousApproval;
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
        final ApprovalSettingCriteria that = (ApprovalSettingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(isSequenceApproval, that.isSequenceApproval) &&
            Objects.equals(isSimultaneousApproval, that.isSimultaneousApproval) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isSequenceApproval, isSimultaneousApproval, lastModified, lastModifiedBy, status, companyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalSettingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (isSequenceApproval != null ? "isSequenceApproval=" + isSequenceApproval + ", " : "") +
            (isSimultaneousApproval != null ? "isSimultaneousApproval=" + isSimultaneousApproval + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
