package com.techvg.smtrthr.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.smtrthr.domain.PersonalDetails} entity. This class is used
 * in {@link com.techvg.smtrthr.web.rest.PersonalDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /personal-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonalDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter passportNo;

    private InstantFilter passportExpDate;

    private StringFilter telephoneNo;

    private StringFilter nationality;

    private StringFilter maritalStatus;

    private StringFilter religion;

    private BooleanFilter isSpousEmployed;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter status;

    private LongFilter employeeId;

    private LongFilter companyId;

    private StringFilter personalIdNo;

    private StringFilter bloodGroup;

    private LocalDateFilter dateOfBirth;

    private Boolean distinct;

    public PersonalDetailsCriteria() {}

    public PersonalDetailsCriteria(PersonalDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.passportNo = other.passportNo == null ? null : other.passportNo.copy();
        this.passportExpDate = other.passportExpDate == null ? null : other.passportExpDate.copy();
        this.telephoneNo = other.telephoneNo == null ? null : other.telephoneNo.copy();
        this.nationality = other.nationality == null ? null : other.nationality.copy();
        this.maritalStatus = other.maritalStatus == null ? null : other.maritalStatus.copy();
        this.religion = other.religion == null ? null : other.religion.copy();
        this.isSpousEmployed = other.isSpousEmployed == null ? null : other.isSpousEmployed.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.personalIdNo = other.personalIdNo == null ? null : other.personalIdNo.copy();
        this.bloodGroup = other.bloodGroup == null ? null : other.bloodGroup.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PersonalDetailsCriteria copy() {
        return new PersonalDetailsCriteria(this);
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

    public StringFilter getPassportNo() {
        return passportNo;
    }

    public StringFilter passportNo() {
        if (passportNo == null) {
            passportNo = new StringFilter();
        }
        return passportNo;
    }

    public void setPassportNo(StringFilter passportNo) {
        this.passportNo = passportNo;
    }

    public InstantFilter getPassportExpDate() {
        return passportExpDate;
    }

    public InstantFilter passportExpDate() {
        if (passportExpDate == null) {
            passportExpDate = new InstantFilter();
        }
        return passportExpDate;
    }

    public void setPassportExpDate(InstantFilter passportExpDate) {
        this.passportExpDate = passportExpDate;
    }

    public StringFilter getTelephoneNo() {
        return telephoneNo;
    }

    public StringFilter telephoneNo() {
        if (telephoneNo == null) {
            telephoneNo = new StringFilter();
        }
        return telephoneNo;
    }

    public void setTelephoneNo(StringFilter telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public StringFilter getNationality() {
        return nationality;
    }

    public StringFilter nationality() {
        if (nationality == null) {
            nationality = new StringFilter();
        }
        return nationality;
    }

    public void setNationality(StringFilter nationality) {
        this.nationality = nationality;
    }

    public StringFilter getMaritalStatus() {
        return maritalStatus;
    }

    public StringFilter maritalStatus() {
        if (maritalStatus == null) {
            maritalStatus = new StringFilter();
        }
        return maritalStatus;
    }

    public void setMaritalStatus(StringFilter maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public StringFilter getReligion() {
        return religion;
    }

    public StringFilter religion() {
        if (religion == null) {
            religion = new StringFilter();
        }
        return religion;
    }

    public void setReligion(StringFilter religion) {
        this.religion = religion;
    }

    public BooleanFilter getIsSpousEmployed() {
        return isSpousEmployed;
    }

    public BooleanFilter isSpousEmployed() {
        if (isSpousEmployed == null) {
            isSpousEmployed = new BooleanFilter();
        }
        return isSpousEmployed;
    }

    public void setIsSpousEmployed(BooleanFilter isSpousEmployed) {
        this.isSpousEmployed = isSpousEmployed;
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

    public StringFilter getPersonalIdNo() {
        return personalIdNo;
    }

    public StringFilter personalIdNo() {
        if (personalIdNo == null) {
            personalIdNo = new StringFilter();
        }
        return personalIdNo;
    }

    public void setPersonalIdNo(StringFilter personalIdNo) {
        this.personalIdNo = personalIdNo;
    }

    public StringFilter getBloodGroup() {
        return bloodGroup;
    }

    public StringFilter bloodGroup() {
        if (bloodGroup == null) {
            bloodGroup = new StringFilter();
        }
        return bloodGroup;
    }

    public void setBloodGroup(StringFilter bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDateFilter dateOfBirth() {
        if (dateOfBirth == null) {
            dateOfBirth = new LocalDateFilter();
        }
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
        final PersonalDetailsCriteria that = (PersonalDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(passportNo, that.passportNo) &&
            Objects.equals(passportExpDate, that.passportExpDate) &&
            Objects.equals(telephoneNo, that.telephoneNo) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(religion, that.religion) &&
            Objects.equals(isSpousEmployed, that.isSpousEmployed) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(status, that.status) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(personalIdNo, that.personalIdNo) &&
            Objects.equals(bloodGroup, that.bloodGroup) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            passportNo,
            passportExpDate,
            telephoneNo,
            nationality,
            maritalStatus,
            religion,
            isSpousEmployed,
            lastModified,
            lastModifiedBy,
            status,
            employeeId,
            companyId,
            personalIdNo,
            bloodGroup,
            dateOfBirth,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (passportNo != null ? "passportNo=" + passportNo + ", " : "") +
            (passportExpDate != null ? "passportExpDate=" + passportExpDate + ", " : "") +
            (telephoneNo != null ? "telephoneNo=" + telephoneNo + ", " : "") +
            (nationality != null ? "nationality=" + nationality + ", " : "") +
            (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
            (religion != null ? "religion=" + religion + ", " : "") +
            (isSpousEmployed != null ? "isSpousEmployed=" + isSpousEmployed + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (personalIdNo != null ? "personalIdNo=" + personalIdNo + ", " : "") +
            (bloodGroup != null ? "bloodGroup=" + bloodGroup + ", " : "") +
            (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
