package com.techvg.smtrthr.service;

import com.techvg.smtrthr.domain.*; // for static metamodels
import com.techvg.smtrthr.domain.Approver;
import com.techvg.smtrthr.repository.ApproverRepository;
import com.techvg.smtrthr.service.criteria.ApproverCriteria;
import com.techvg.smtrthr.service.dto.ApproverDTO;
import com.techvg.smtrthr.service.mapper.ApproverMapper;
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
 * Service for executing complex queries for {@link Approver} entities in the database.
 * The main input is a {@link ApproverCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApproverDTO} or a {@link Page} of {@link ApproverDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApproverQueryService extends QueryService<Approver> {

    private final Logger log = LoggerFactory.getLogger(ApproverQueryService.class);

    private final ApproverRepository approverRepository;

    private final ApproverMapper approverMapper;

    public ApproverQueryService(ApproverRepository approverRepository, ApproverMapper approverMapper) {
        this.approverRepository = approverRepository;
        this.approverMapper = approverMapper;
    }

    /**
     * Return a {@link List} of {@link ApproverDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApproverDTO> findByCriteria(ApproverCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Approver> specification = createSpecification(criteria);
        return approverMapper.toDto(approverRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApproverDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApproverDTO> findByCriteria(ApproverCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Approver> specification = createSpecification(criteria);
        return approverRepository.findAll(specification, page).map(approverMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApproverCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Approver> specification = createSpecification(criteria);
        return approverRepository.count(specification);
    }

    /**
     * Function to convert {@link ApproverCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Approver> createSpecification(ApproverCriteria criteria) {
        Specification<Approver> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Approver_.id));
            }
            if (criteria.getApproverName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApproverName(), Approver_.approverName));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Approver_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Approver_.lastModifiedBy));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Approver_.status));
            }
            if (criteria.getApprovalSettingId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApprovalSettingId(), Approver_.approvalSettingId));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepartmentId(), Approver_.departmentId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Approver_.companyId));
            }
        }
        return specification;
    }
}
