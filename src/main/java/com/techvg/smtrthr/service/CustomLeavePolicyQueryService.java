package com.techvg.smtrthr.service;

import com.techvg.smtrthr.domain.*; // for static metamodels
import com.techvg.smtrthr.domain.CustomLeavePolicy;
import com.techvg.smtrthr.repository.CustomLeavePolicyRepository;
import com.techvg.smtrthr.service.criteria.CustomLeavePolicyCriteria;
import com.techvg.smtrthr.service.dto.CustomLeavePolicyDTO;
import com.techvg.smtrthr.service.mapper.CustomLeavePolicyMapper;
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
 * Service for executing complex queries for {@link CustomLeavePolicy} entities in the database.
 * The main input is a {@link CustomLeavePolicyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomLeavePolicyDTO} or a {@link Page} of {@link CustomLeavePolicyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomLeavePolicyQueryService extends QueryService<CustomLeavePolicy> {

    private final Logger log = LoggerFactory.getLogger(CustomLeavePolicyQueryService.class);

    private final CustomLeavePolicyRepository customLeavePolicyRepository;

    private final CustomLeavePolicyMapper customLeavePolicyMapper;

    public CustomLeavePolicyQueryService(
        CustomLeavePolicyRepository customLeavePolicyRepository,
        CustomLeavePolicyMapper customLeavePolicyMapper
    ) {
        this.customLeavePolicyRepository = customLeavePolicyRepository;
        this.customLeavePolicyMapper = customLeavePolicyMapper;
    }

    /**
     * Return a {@link List} of {@link CustomLeavePolicyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomLeavePolicyDTO> findByCriteria(CustomLeavePolicyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomLeavePolicy> specification = createSpecification(criteria);
        return customLeavePolicyMapper.toDto(customLeavePolicyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomLeavePolicyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomLeavePolicyDTO> findByCriteria(CustomLeavePolicyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomLeavePolicy> specification = createSpecification(criteria);
        return customLeavePolicyRepository.findAll(specification, page).map(customLeavePolicyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomLeavePolicyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomLeavePolicy> specification = createSpecification(criteria);
        return customLeavePolicyRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomLeavePolicyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomLeavePolicy> createSpecification(CustomLeavePolicyCriteria criteria) {
        Specification<CustomLeavePolicy> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomLeavePolicy_.id));
            }
            if (criteria.getLeavePolicyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLeavePolicyId(), CustomLeavePolicy_.leavePolicyId));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), CustomLeavePolicy_.employeeId));
            }
            if (criteria.getDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDays(), CustomLeavePolicy_.days));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), CustomLeavePolicy_.companyId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), CustomLeavePolicy_.status));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), CustomLeavePolicy_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CustomLeavePolicy_.lastModifiedBy));
            }
        }
        return specification;
    }
}
