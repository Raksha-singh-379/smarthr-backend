package com.techvg.smtrthr.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.smtrthr.domain.ReEnumeration} entity. This class is used
 * in {@link com.techvg.smtrthr.web.rest.ReEnumerationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /re-enumerations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReEnumerationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter salaryBasis;

    private DoubleFilter amount;

    private StringFilter paymentType;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter status;

    private LongFilter employeId;

    private LongFilter companyId;

    private Boolean distinct;

    public ReEnumerationCriteria() {}

    public ReEnumerationCriteria(ReEnumerationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.salaryBasis = other.salaryBasis == null ? null : other.salaryBasis.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.paymentType = other.paymentType == null ? null : other.paymentType.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.employeId = other.employeId == null ? null : other.employeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ReEnumerationCriteria copy() {
        return new ReEnumerationCriteria(this);
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

    public StringFilter getSalaryBasis() {
        return salaryBasis;
    }

    public StringFilter salaryBasis() {
        if (salaryBasis == null) {
            salaryBasis = new StringFilter();
        }
        return salaryBasis;
    }

    public void setSalaryBasis(StringFilter salaryBasis) {
        this.salaryBasis = salaryBasis;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public DoubleFilter amount() {
        if (amount == null) {
            amount = new DoubleFilter();
        }
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public StringFilter getPaymentType() {
        return paymentType;
    }

    public StringFilter paymentType() {
        if (paymentType == null) {
            paymentType = new StringFilter();
        }
        return paymentType;
    }

    public void setPaymentType(StringFilter paymentType) {
        this.paymentType = paymentType;
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

    public LongFilter getEmployeId() {
        return employeId;
    }

    public LongFilter employeId() {
        if (employeId == null) {
            employeId = new LongFilter();
        }
        return employeId;
    }

    public void setEmployeId(LongFilter employeId) {
        this.employeId = employeId;
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
        final ReEnumerationCriteria that = (ReEnumerationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(salaryBasis, that.salaryBasis) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(paymentType, that.paymentType) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(status, that.status) &&
            Objects.equals(employeId, that.employeId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, salaryBasis, amount, paymentType, lastModified, lastModifiedBy, status, employeId, companyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReEnumerationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (salaryBasis != null ? "salaryBasis=" + salaryBasis + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (employeId != null ? "employeId=" + employeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
