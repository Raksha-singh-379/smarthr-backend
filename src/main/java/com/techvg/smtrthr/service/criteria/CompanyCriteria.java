package com.techvg.smtrthr.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.smtrthr.domain.Company} entity. This class is used
 * in {@link com.techvg.smtrthr.web.rest.CompanyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter companyName;

    private StringFilter contactPerson;

    private StringFilter postalCode;

    private StringFilter email;

    private StringFilter phoneNumber;

    private StringFilter mobileNumber;

    private StringFilter websiteUrl;

    private StringFilter fax;

    private StringFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter regNumber;

    private StringFilter gstin;

    private StringFilter pan;

    private StringFilter tan;

    private Boolean distinct;

    public CompanyCriteria() {}

    public CompanyCriteria(CompanyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.companyName = other.companyName == null ? null : other.companyName.copy();
        this.contactPerson = other.contactPerson == null ? null : other.contactPerson.copy();
        this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.mobileNumber = other.mobileNumber == null ? null : other.mobileNumber.copy();
        this.websiteUrl = other.websiteUrl == null ? null : other.websiteUrl.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.regNumber = other.regNumber == null ? null : other.regNumber.copy();
        this.gstin = other.gstin == null ? null : other.gstin.copy();
        this.pan = other.pan == null ? null : other.pan.copy();
        this.tan = other.tan == null ? null : other.tan.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CompanyCriteria copy() {
        return new CompanyCriteria(this);
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

    public StringFilter getCompanyName() {
        return companyName;
    }

    public StringFilter companyName() {
        if (companyName == null) {
            companyName = new StringFilter();
        }
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public StringFilter getContactPerson() {
        return contactPerson;
    }

    public StringFilter contactPerson() {
        if (contactPerson == null) {
            contactPerson = new StringFilter();
        }
        return contactPerson;
    }

    public void setContactPerson(StringFilter contactPerson) {
        this.contactPerson = contactPerson;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public StringFilter postalCode() {
        if (postalCode == null) {
            postalCode = new StringFilter();
        }
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public StringFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new StringFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getMobileNumber() {
        return mobileNumber;
    }

    public StringFilter mobileNumber() {
        if (mobileNumber == null) {
            mobileNumber = new StringFilter();
        }
        return mobileNumber;
    }

    public void setMobileNumber(StringFilter mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public StringFilter getWebsiteUrl() {
        return websiteUrl;
    }

    public StringFilter websiteUrl() {
        if (websiteUrl == null) {
            websiteUrl = new StringFilter();
        }
        return websiteUrl;
    }

    public void setWebsiteUrl(StringFilter websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public StringFilter getFax() {
        return fax;
    }

    public StringFilter fax() {
        if (fax == null) {
            fax = new StringFilter();
        }
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
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

    public StringFilter getRegNumber() {
        return regNumber;
    }

    public StringFilter regNumber() {
        if (regNumber == null) {
            regNumber = new StringFilter();
        }
        return regNumber;
    }

    public void setRegNumber(StringFilter regNumber) {
        this.regNumber = regNumber;
    }

    public StringFilter getGstin() {
        return gstin;
    }

    public StringFilter gstin() {
        if (gstin == null) {
            gstin = new StringFilter();
        }
        return gstin;
    }

    public void setGstin(StringFilter gstin) {
        this.gstin = gstin;
    }

    public StringFilter getPan() {
        return pan;
    }

    public StringFilter pan() {
        if (pan == null) {
            pan = new StringFilter();
        }
        return pan;
    }

    public void setPan(StringFilter pan) {
        this.pan = pan;
    }

    public StringFilter getTan() {
        return tan;
    }

    public StringFilter tan() {
        if (tan == null) {
            tan = new StringFilter();
        }
        return tan;
    }

    public void setTan(StringFilter tan) {
        this.tan = tan;
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
        final CompanyCriteria that = (CompanyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(contactPerson, that.contactPerson) &&
            Objects.equals(postalCode, that.postalCode) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(mobileNumber, that.mobileNumber) &&
            Objects.equals(websiteUrl, that.websiteUrl) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(regNumber, that.regNumber) &&
            Objects.equals(gstin, that.gstin) &&
            Objects.equals(pan, that.pan) &&
            Objects.equals(tan, that.tan) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            companyName,
            contactPerson,
            postalCode,
            email,
            phoneNumber,
            mobileNumber,
            websiteUrl,
            fax,
            status,
            lastModified,
            lastModifiedBy,
            regNumber,
            gstin,
            pan,
            tan,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (companyName != null ? "companyName=" + companyName + ", " : "") +
            (contactPerson != null ? "contactPerson=" + contactPerson + ", " : "") +
            (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (mobileNumber != null ? "mobileNumber=" + mobileNumber + ", " : "") +
            (websiteUrl != null ? "websiteUrl=" + websiteUrl + ", " : "") +
            (fax != null ? "fax=" + fax + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (regNumber != null ? "regNumber=" + regNumber + ", " : "") +
            (gstin != null ? "gstin=" + gstin + ", " : "") +
            (pan != null ? "pan=" + pan + ", " : "") +
            (tan != null ? "tan=" + tan + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
