package com.techvg.smtrthr.service;

import com.techvg.smtrthr.domain.*; // for static metamodels
import com.techvg.smtrthr.domain.ReportingStructure;
import com.techvg.smtrthr.repository.ReportingStructureRepository;
import com.techvg.smtrthr.service.criteria.ReportingStructureCriteria;
import com.techvg.smtrthr.service.dto.ReportingStructureDTO;
import com.techvg.smtrthr.service.mapper.ReportingStructureMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ReportingStructure} entities in the database.
 * The main input is a {@link ReportingStructureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReportingStructureDTO} or a {@link Page} of {@link ReportingStructureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReportingStructureQueryService extends QueryService<ReportingStructure> {

    private final Logger log = LoggerFactory.getLogger(ReportingStructureQueryService.class);

    private final ReportingStructureRepository reportingStructureRepository;

    private final ReportingStructureMapper reportingStructureMapper;

    public ReportingStructureQueryService(
        ReportingStructureRepository reportingStructureRepository,
        ReportingStructureMapper reportingStructureMapper
    ) {
        this.reportingStructureRepository = reportingStructureRepository;
        this.reportingStructureMapper = reportingStructureMapper;
    }

    /**
     * Return a {@link List} of {@link ReportingStructureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReportingStructureDTO> findByCriteria(ReportingStructureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReportingStructure> specification = createSpecification(criteria);
        return reportingStructureMapper.toDto(reportingStructureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReportingStructureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportingStructureDTO> findByCriteria(ReportingStructureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReportingStructure> specification = createSpecification(criteria);
        return reportingStructureRepository.findAll(specification, page).map(reportingStructureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReportingStructureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReportingStructure> specification = createSpecification(criteria);
        return reportingStructureRepository.count(specification);
    }

    /**
     * Function to convert {@link ReportingStructureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ReportingStructure> createSpecification(ReportingStructureCriteria criteria) {
        Specification<ReportingStructure> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ReportingStructure_.id));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), ReportingStructure_.employeeId));
            }
            if (criteria.getReportingEmpId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getReportingEmpId(), ReportingStructure_.reportingEmpId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), ReportingStructure_.status));
            }
            if (criteria.getReportingStrId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getReportingStrId(), ReportingStructure_.reportingStrId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), ReportingStructure_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ReportingStructure_.lastModifiedBy));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), ReportingStructure_.companyId));
            }
        }
        return specification;
    }
}
