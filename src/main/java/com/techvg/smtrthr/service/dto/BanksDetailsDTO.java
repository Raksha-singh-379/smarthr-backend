package com.techvg.smtrthr.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.smtrthr.domain.BanksDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BanksDetailsDTO implements Serializable {

    private Long id;

    private Long accountNumber;

    private String bankName;

    private String branchTransCode;

    private String taxNumber;

    private String branchName;

    private Instant lastModified;

    private String lastModifiedBy;

    private String status;

    private String refTableType;

    private Long refTableId;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchTransCode() {
        return branchTransCode;
    }

    public void setBranchTransCode(String branchTransCode) {
        this.branchTransCode = branchTransCode;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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

    public String getRefTableType() {
        return refTableType;
    }

    public void setRefTableType(String refTableType) {
        this.refTableType = refTableType;
    }

    public Long getRefTableId() {
        return refTableId;
    }

    public void setRefTableId(Long refTableId) {
        this.refTableId = refTableId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BanksDetailsDTO)) {
            return false;
        }

        BanksDetailsDTO banksDetailsDTO = (BanksDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, banksDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BanksDetailsDTO{" +
            "id=" + getId() +
            ", accountNumber=" + getAccountNumber() +
            ", bankName='" + getBankName() + "'" +
            ", branchTransCode='" + getBranchTransCode() + "'" +
            ", taxNumber='" + getTaxNumber() + "'" +
            ", branchName='" + getBranchName() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", refTableType='" + getRefTableType() + "'" +
            ", refTableId=" + getRefTableId() +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
