package com.techvg.smtrthr.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.smtrthr.domain.WorkDaysSetting} entity. This class is used
 * in {@link com.techvg.smtrthr.web.rest.WorkDaysSettingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /work-days-settings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkDaysSettingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter day;

    private StringFilter hours;

    private BooleanFilter dayOff;

    private LongFilter companyId;

    private StringFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public WorkDaysSettingCriteria() {}

    public WorkDaysSettingCriteria(WorkDaysSettingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.day = other.day == null ? null : other.day.copy();
        this.hours = other.hours == null ? null : other.hours.copy();
        this.dayOff = other.dayOff == null ? null : other.dayOff.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public WorkDaysSettingCriteria copy() {
        return new WorkDaysSettingCriteria(this);
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

    public StringFilter getDay() {
        return day;
    }

    public StringFilter day() {
        if (day == null) {
            day = new StringFilter();
        }
        return day;
    }

    public void setDay(StringFilter day) {
        this.day = day;
    }

    public StringFilter getHours() {
        return hours;
    }

    public StringFilter hours() {
        if (hours == null) {
            hours = new StringFilter();
        }
        return hours;
    }

    public void setHours(StringFilter hours) {
        this.hours = hours;
    }

    public BooleanFilter getDayOff() {
        return dayOff;
    }

    public BooleanFilter dayOff() {
        if (dayOff == null) {
            dayOff = new BooleanFilter();
        }
        return dayOff;
    }

    public void setDayOff(BooleanFilter dayOff) {
        this.dayOff = dayOff;
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
        final WorkDaysSettingCriteria that = (WorkDaysSettingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(day, that.day) &&
            Objects.equals(hours, that.hours) &&
            Objects.equals(dayOff, that.dayOff) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, day, hours, dayOff, companyId, status, lastModified, lastModifiedBy, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkDaysSettingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (day != null ? "day=" + day + ", " : "") +
            (hours != null ? "hours=" + hours + ", " : "") +
            (dayOff != null ? "dayOff=" + dayOff + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
