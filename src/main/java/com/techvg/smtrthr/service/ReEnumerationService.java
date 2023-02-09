package com.techvg.smtrthr.service;

import com.techvg.smtrthr.domain.ReEnumeration;
import com.techvg.smtrthr.repository.ReEnumerationRepository;
import com.techvg.smtrthr.service.dto.ReEnumerationDTO;
import com.techvg.smtrthr.service.mapper.ReEnumerationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReEnumeration}.
 */
@Service
@Transactional
public class ReEnumerationService {

    private final Logger log = LoggerFactory.getLogger(ReEnumerationService.class);

    private final ReEnumerationRepository reEnumerationRepository;

    private final ReEnumerationMapper reEnumerationMapper;

    public ReEnumerationService(ReEnumerationRepository reEnumerationRepository, ReEnumerationMapper reEnumerationMapper) {
        this.reEnumerationRepository = reEnumerationRepository;
        this.reEnumerationMapper = reEnumerationMapper;
    }

    /**
     * Save a reEnumeration.
     *
     * @param reEnumerationDTO the entity to save.
     * @return the persisted entity.
     */
    public ReEnumerationDTO save(ReEnumerationDTO reEnumerationDTO) {
        log.debug("Request to save ReEnumeration : {}", reEnumerationDTO);
        ReEnumeration reEnumeration = reEnumerationMapper.toEntity(reEnumerationDTO);
        reEnumeration = reEnumerationRepository.save(reEnumeration);
        return reEnumerationMapper.toDto(reEnumeration);
    }

    /**
     * Update a reEnumeration.
     *
     * @param reEnumerationDTO the entity to save.
     * @return the persisted entity.
     */
    public ReEnumerationDTO update(ReEnumerationDTO reEnumerationDTO) {
        log.debug("Request to update ReEnumeration : {}", reEnumerationDTO);
        ReEnumeration reEnumeration = reEnumerationMapper.toEntity(reEnumerationDTO);
        reEnumeration = reEnumerationRepository.save(reEnumeration);
        return reEnumerationMapper.toDto(reEnumeration);
    }

    /**
     * Partially update a reEnumeration.
     *
     * @param reEnumerationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ReEnumerationDTO> partialUpdate(ReEnumerationDTO reEnumerationDTO) {
        log.debug("Request to partially update ReEnumeration : {}", reEnumerationDTO);

        return reEnumerationRepository
            .findById(reEnumerationDTO.getId())
            .map(existingReEnumeration -> {
                reEnumerationMapper.partialUpdate(existingReEnumeration, reEnumerationDTO);

                return existingReEnumeration;
            })
            .map(reEnumerationRepository::save)
            .map(reEnumerationMapper::toDto);
    }

    /**
     * Get all the reEnumerations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReEnumerationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReEnumerations");
        return reEnumerationRepository.findAll(pageable).map(reEnumerationMapper::toDto);
    }

    /**
     * Get one reEnumeration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReEnumerationDTO> findOne(Long id) {
        log.debug("Request to get ReEnumeration : {}", id);
        return reEnumerationRepository.findById(id).map(reEnumerationMapper::toDto);
    }

    /**
     * Delete the reEnumeration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReEnumeration : {}", id);
        reEnumerationRepository.deleteById(id);
    }
}
