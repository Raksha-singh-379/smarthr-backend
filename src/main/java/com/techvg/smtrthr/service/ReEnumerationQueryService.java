package com.techvg.smtrthr.service;

import com.techvg.smtrthr.domain.*; // for static metamodels
import com.techvg.smtrthr.domain.ReEnumeration;
import com.techvg.smtrthr.repository.ReEnumerationRepository;
import com.techvg.smtrthr.service.criteria.ReEnumerationCriteria;
import com.techvg.smtrthr.service.dto.ReEnumerationDTO;
import com.techvg.smtrthr.service.mapper.ReEnumerationMapper;
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
 * Service for executing complex queries for {@link ReEnumeration} entities in the database.
 * The main input is a {@link ReEnumerationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReEnumerationDTO} or a {@link Page} of {@link ReEnumerationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReEnumerationQueryService extends QueryService<ReEnumeration> {

    private final Logger log = LoggerFactory.getLogger(ReEnumerationQueryService.class);

    private final ReEnumerationRepository reEnumerationRepository;

    private final ReEnumerationMapper reEnumerationMapper;

    public ReEnumerationQueryService(ReEnumerationRepository reEnumerationRepository, ReEnumerationMapper reEnumerationMapper) {
        this.reEnumerationRepository = reEnumerationRepository;
        this.reEnumerationMapper = reEnumerationMapper;
    }

    /**
     * Return a {@link List} of {@link ReEnumerationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReEnumerationDTO> findByCriteria(ReEnumerationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReEnumeration> specification = createSpecification(criteria);
        return reEnumerationMapper.toDto(reEnumerationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReEnumerationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReEnumerationDTO> findByCriteria(ReEnumerationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReEnumeration> specification = createSpecification(criteria);
        return reEnumerationRepository.findAll(specification, page).map(reEnumerationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReEnumerationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReEnumeration> specification = createSpecification(criteria);
        return reEnumerationRepository.count(specification);
    }

    /**
     * Function to convert {@link ReEnumerationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ReEnumeration> createSpecification(ReEnumerationCriteria criteria) {
        Specification<ReEnumeration> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ReEnumeration_.id));
            }
            if (criteria.getSalaryBasis() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSalaryBasis(), ReEnumeration_.salaryBasis));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), ReEnumeration_.amount));
            }
            if (criteria.getPaymentType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentType(), ReEnumeration_.paymentType));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), ReEnumeration_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ReEnumeration_.lastModifiedBy));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), ReEnumeration_.status));
            }
            if (criteria.getEmployeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeId(), ReEnumeration_.employeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), ReEnumeration_.companyId));
            }
        }
        return specification;
    }
}
