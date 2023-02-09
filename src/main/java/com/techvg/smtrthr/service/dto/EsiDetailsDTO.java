package com.techvg.smtrthr.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.smtrthr.domain.EsiDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EsiDetailsDTO implements Serializable {

    private Long id;

    private Boolean isEsiContribution;

    private String esiNumber;

    private Double esiRate;

    private String additionalEsiRate;

    private Double totalEsiRate;

    private Instant lastModified;

    private String lastModifiedBy;

    private String status;

    private Long employeId;

    private Long reEnumerationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsEsiContribution() {
        return isEsiContribution;
    }

    public void setIsEsiContribution(Boolean isEsiContribution) {
        this.isEsiContribution = isEsiContribution;
    }

    public String getEsiNumber() {
        return esiNumber;
    }

    public void setEsiNumber(String esiNumber) {
        this.esiNumber = esiNumber;
    }

    public Double getEsiRate() {
        return esiRate;
    }

    public void setEsiRate(Double esiRate) {
        this.esiRate = esiRate;
    }

    public String getAdditionalEsiRate() {
        return additionalEsiRate;
    }

    public void setAdditionalEsiRate(String additionalEsiRate) {
        this.additionalEsiRate = additionalEsiRate;
    }

    public Double getTotalEsiRate() {
        return totalEsiRate;
    }

    public void setTotalEsiRate(Double totalEsiRate) {
        this.totalEsiRate = totalEsiRate;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    public Long getReEnumerationId() {
        return reEnumerationId;
    }

    public void setReEnumerationId(Long reEnumerationId) {
        this.reEnumerationId = reEnumerationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EsiDetailsDTO)) {
            return false;
        }

        EsiDetailsDTO esiDetailsDTO = (EsiDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, esiDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EsiDetailsDTO{" +
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
