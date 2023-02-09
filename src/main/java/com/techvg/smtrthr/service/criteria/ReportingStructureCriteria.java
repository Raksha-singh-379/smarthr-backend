package com.techvg.smtrthr.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.smtrthr.domain.ReportingStructure} entity. This class is used
 * in {@link com.techvg.smtrthr.web.rest.ReportingStructureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /reporting-structures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportingStructureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter employeeId;

    private LongFilter reportingEmpId;

    private StringFilter status;

    private LongFilter reportingStrId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter companyId;

    private Boolean distinct;

    public ReportingStructureCriteria() {}

    public ReportingStructureCriteria(ReportingStructureCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.reportingEmpId = other.reportingEmpId == null ? null : other.reportingEmpId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.reportingStrId = other.reportingStrId == null ? null : other.reportingStrId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ReportingStructureCriteria copy() {
        return new ReportingStructureCriteria(this);
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

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getReportingEmpId() {
        return reportingEmpId;
    }

    public LongFilter reportingEmpId() {
        if (reportingEmpId == null) {
            reportingEmpId = new LongFilter();
        }
        return reportingEmpId;
    }

    public void setReportingEmpId(LongFilter reportingEmpId) {
        this.reportingEmpId = reportingEmpId;
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

    public LongFilter getReportingStrId() {
        return reportingStrId;
    }

    public LongFilter reportingStrId() {
        if (reportingStrId == null) {
            reportingStrId = new LongFilter();
        }
        return reportingStrId;
    }

    public void setReportingStrId(LongFilter reportingStrId) {
        this.reportingStrId = reportingStrId;
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
        final ReportingStructureCriteria that = (ReportingStructureCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(reportingEmpId, that.reportingEmpId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(reportingStrId, that.reportingStrId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, reportingEmpId, status, reportingStrId, lastModified, lastModifiedBy, companyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportingStructureCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (reportingEmpId != null ? "reportingEmpId=" + reportingEmpId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (reportingStrId != null ? "reportingStrId=" + reportingStrId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
