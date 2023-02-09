package com.techvg.smtrthr.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Contacts.
 */
@Entity
@Table(name = "contacts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contacts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_pref")
    private String contactPref;

    @Column(name = "contact_type")
    private String contactType;

    @Column(name = "contact")
    private String contact;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "status")
    private String status;

    @Column(name = "ref_table_type")
    private String refTableType;

    @Column(name = "ref_table_id")
    private Long refTableId;

    @Column(name = "company_id")
    private Long companyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contacts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Contacts name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPref() {
        return this.contactPref;
    }

    public Contacts contactPref(String contactPref) {
        this.setContactPref(contactPref);
        return this;
    }

    public void setContactPref(String contactPref) {
        this.contactPref = contactPref;
    }

    public String getContactType() {
        return this.contactType;
    }

    public Contacts contactType(String contactType) {
        this.setContactType(contactType);
        return this;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContact() {
        return this.contact;
    }

    public Contacts contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Contacts lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Contacts lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getStatus() {
        return this.status;
    }

    public Contacts status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefTableType() {
        return this.refTableType;
    }

    public Contacts refTableType(String refTableType) {
        this.setRefTableType(refTableType);
        return this;
    }

    public void setRefTableType(String refTableType) {
        this.refTableType = refTableType;
    }

    public Long getRefTableId() {
        return this.refTableId;
    }

    public Contacts refTableId(Long refTableId) {
        this.setRefTableId(refTableId);
        return this;
    }

    public void setRefTableId(Long refTableId) {
        this.refTableId = refTableId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public Contacts companyId(Long companyId) {
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
        if (!(o instanceof Contacts)) {
            return false;
        }
        return id != null && id.equals(((Contacts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contacts{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contactPref='" + getContactPref() + "'" +
            ", contactType='" + getContactType() + "'" +
            ", contact='" + getContact() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", refTableType='" + getRefTableType() + "'" +
            ", refTableId=" + getRefTableId() +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
