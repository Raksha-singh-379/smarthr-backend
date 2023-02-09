package com.techvg.smtrthr.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EsiDetails.
 */
@Entity
@Table(name = "esi_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EsiDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_esi_contribution")
    private Boolean isEsiContribution;

    @Column(name = "esi_number")
    private String esiNumber;

    @Column(name = "esi_rate")
    private Double esiRate;

    @Column(name = "additional_esi_rate")
    private String additionalEsiRate;

    @Column(name = "total_esi_rate")
    private Double totalEsiRate;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "status")
    private String status;

    @Column(name = "employe_id")
    private Long employeId;

    @Column(name = "re_enumeration_id")
    private Long reEnumerationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EsiDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsEsiContribution() {
        return this.isEsiContribution;
    }

    public EsiDetails isEsiContribution(Boolean isEsiContribution) {
        this.setIsEsiContribution(isEsiContribution);
        return this;
    }

    public void setIsEsiContribution(Boolean isEsiContribution) {
        this.isEsiContribution = isEsiContribution;
    }

    public String getEsiNumber() {
        return this.esiNumber;
    }

    public EsiDetails esiNumber(String esiNumber) {
        this.setEsiNumber(esiNumber);
        return this;
    }

    public void setEsiNumber(String esiNumber) {
        this.esiNumber = esiNumber;
    }

    public Double getEsiRate() {
        return this.esiRate;
    }

    public EsiDetails esiRate(Double esiRate) {
        this.setEsiRate(esiRate);
        return this;
    }

    public void setEsiRate(Double esiRate) {
        this.esiRate = esiRate;
    }

    public String getAdditionalEsiRate() {
        return this.additionalEsiRate;
    }

    public EsiDetails additionalEsiRate(String additionalEsiRate) {
        this.setAdditionalEsiRate(additionalEsiRate);
        return this;
    }

    public void setAdditionalEsiRate(String additionalEsiRate) {
        this.additionalEsiRate = additionalEsiRate;
    }

    public Double getTotalEsiRate() {
        return this.totalEsiRate;
    }

    public EsiDetails totalEsiRate(Double totalEsiRate) {
        this.setTotalEsiRate(totalEsiRate);
        return this;
    }

    public void setTotalEsiRate(Double totalEsiRate) {
        this.totalEsiRate = totalEsiRate;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public EsiDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public EsiDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getStatus() {
        return this.status;
    }

    public EsiDetails status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeId() {
        return this.employeId;
    }

    public EsiDetails employeId(Long employeId) {
        this.setEmployeId(employeId);
        return this;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    public Long getReEnumerationId() {
        return this.reEnumerationId;
    }

    public EsiDetails reEnumerationId(Long reEnumerationId) {
        this.setReEnumerationId(reEnumerationId);
        return this;
    }

    public void setReEnumerationId(Long reEnumerationId) {
        this.reEnumerationId = reEnumerationId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EsiDetails)) {
            return false;
        }
        return id != null && id.equals(((EsiDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EsiDetails{" +
            "id=" + getId() +
            ", isEsiContribution='" + getIsEsiContribution() + "'" +
            ", esiNumber='" + getEsiNumber() + "'" +
            ", esiRate=" + getEsiRate() +
            ", additionalEsiRate='" + getAdditionalEsiRate() + "'" +
            ", totalEsiRate=" + getTotalEsiRate() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", employeId=" + getEmployeId() +
            ", reEnumerationId=" + getReEnumerationId() +
            "}";
    }
}
