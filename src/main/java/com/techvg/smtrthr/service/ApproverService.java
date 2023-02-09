package com.techvg.smtrthr.service;

import com.techvg.smtrthr.domain.Approver;
import com.techvg.smtrthr.repository.ApproverRepository;
import com.techvg.smtrthr.service.dto.ApproverDTO;
import com.techvg.smtrthr.service.mapper.ApproverMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Approver}.
 */
@Service
@Transactional
public class ApproverService {

    private final Logger log = LoggerFactory.getLogger(ApproverService.class);

    private final ApproverRepository approverRepository;

    private final ApproverMapper approverMapper;

    public ApproverService(ApproverRepository approverRepository, ApproverMapper approverMapper) {
        this.approverRepository = approverRepository;
        this.approverMapper = approverMapper;
    }

    /**
     * Save a approver.
     *
     * @param approverDTO the entity to save.
     * @return the persisted entity.
     */
    public ApproverDTO save(ApproverDTO approverDTO) {
        log.debug("Request to save Approver : {}", approverDTO);
        Approver approver = approverMapper.toEntity(approverDTO);
        approver = approverRepository.save(approver);
        return approverMapper.toDto(approver);
    }

    /**
     * Update a approver.
     *
     * @param approverDTO the entity to save.
     * @return the persisted entity.
     */
    public ApproverDTO update(ApproverDTO approverDTO) {
        log.debug("Request to update Approver : {}", approverDTO);
        Approver approver = approverMapper.toEntity(approverDTO);
        approver = approverRepository.save(approver);
        return approverMapper.toDto(approver);
    }

    /**
     * Partially update a approver.
     *
     * @param approverDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApproverDTO> partialUpdate(ApproverDTO approverDTO) {
        log.debug("Request to partially update Approver : {}", approverDTO);

        return approverRepository
            .findById(approverDTO.getId())
            .map(existingApprover -> {
                approverMapper.partialUpdate(existingApprover, approverDTO);

                return existingApprover;
            })
            .map(approverRepository::save)
            .map(approverMapper::toDto);
    }

    /**
     * Get all the approvers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApproverDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Approvers");
        return approverRepository.findAll(pageable).map(approverMapper::toDto);
    }

    /**
     * Get one approver by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApproverDTO> findOne(Long id) {
        log.debug("Request to get Approver : {}", id);
        return approverRepository.findById(id).map(approverMapper::toDto);
    }

    /**
     * Delete the approver by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Approver : {}", id);
        approverRepository.deleteById(id);
    }
}
