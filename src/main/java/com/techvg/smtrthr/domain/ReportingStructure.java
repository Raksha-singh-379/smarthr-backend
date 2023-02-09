package com.techvg.smtrthr.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ReportingStructure.
 */
@Entity
@Table(name = "reporting_structure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportingStructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "reporting_emp_id")
    private Long reportingEmpId;

    @Column(name = "status")
    private String status;

    @Column(name = "reporting_str_id")
    private Long reportingStrId;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "company_id")
    private Long companyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReportingStructure id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public ReportingStructure employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getReportingEmpId() {
        return this.reportingEmpId;
    }

    public ReportingStructure reportingEmpId(Long reportingEmpId) {
        this.setReportingEmpId(reportingEmpId);
        return this;
    }

    public void setReportingEmpId(Long reportingEmpId) {
        this.reportingEmpId = reportingEmpId;
    }

    public String getStatus() {
        return this.status;
    }

    public ReportingStructure status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getReportingStrId() {
        return this.reportingStrId;
    }

    public ReportingStructure reportingStrId(Long reportingStrId) {
        this.setReportingStrId(reportingStrId);
        return this;
    }

    public void setReportingStrId(Long reportingStrId) {
        this.reportingStrId = reportingStrId;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public ReportingStructure lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ReportingStructure lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public ReportingStructure companyId(Long companyId) {
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
        if (!(o instanceof ReportingStructure)) {
            return false;
        }
        return id != null && id.equals(((ReportingStructure) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportingStructure{" +
            "id=" + getId() +
            ", employeeId=" + getEmployeeId() +
            ", reportingEmpId=" + getReportingEmpId() +
            ", status='" + getStatus() + "'" +
            ", reportingStrId=" + getReportingStrId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
