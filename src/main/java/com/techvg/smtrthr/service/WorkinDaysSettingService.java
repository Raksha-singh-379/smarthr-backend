package com.techvg.smtrthr.service;

import com.techvg.smtrthr.domain.WorkinDaysSetting;
import com.techvg.smtrthr.repository.WorkinDaysSettingRepository;
import com.techvg.smtrthr.service.dto.WorkinDaysSettingDTO;
import com.techvg.smtrthr.service.mapper.WorkinDaysSettingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkinDaysSetting}.
 */
@Service
@Transactional
public class WorkinDaysSettingService {

    private final Logger log = LoggerFactory.getLogger(WorkinDaysSettingService.class);

    private final WorkinDaysSettingRepository workinDaysSettingRepository;

    private final WorkinDaysSettingMapper workinDaysSettingMapper;

    public WorkinDaysSettingService(
        WorkinDaysSettingRepository workinDaysSettingRepository,
        WorkinDaysSettingMapper workinDaysSettingMapper
    ) {
        this.workinDaysSettingRepository = workinDaysSettingRepository;
        this.workinDaysSettingMapper = workinDaysSettingMapper;
    }

    /**
     * Save a workinDaysSetting.
     *
     * @param workinDaysSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkinDaysSettingDTO save(WorkinDaysSettingDTO workinDaysSettingDTO) {
        log.debug("Request to save WorkinDaysSetting : {}", workinDaysSettingDTO);
        WorkinDaysSetting workinDaysSetting = workinDaysSettingMapper.toEntity(workinDaysSettingDTO);
        workinDaysSetting = workinDaysSettingRepository.save(workinDaysSetting);
        return workinDaysSettingMapper.toDto(workinDaysSetting);
    }

    /**
     * Update a workinDaysSetting.
     *
     * @param workinDaysSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkinDaysSettingDTO update(WorkinDaysSettingDTO workinDaysSettingDTO) {
        log.debug("Request to update WorkinDaysSetting : {}", workinDaysSettingDTO);
        WorkinDaysSetting workinDaysSetting = workinDaysSettingMapper.toEntity(workinDaysSettingDTO);
        workinDaysSetting = workinDaysSettingRepository.save(workinDaysSetting);
        return workinDaysSettingMapper.toDto(workinDaysSetting);
    }

    /**
     * Partially update a workinDaysSetting.
     *
     * @param workinDaysSettingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkinDaysSettingDTO> partialUpdate(WorkinDaysSettingDTO workinDaysSettingDTO) {
        log.debug("Request to partially update WorkinDaysSetting : {}", workinDaysSettingDTO);

        return workinDaysSettingRepository
            .findById(workinDaysSettingDTO.getId())
            .map(existingWorkinDaysSetting -> {
                workinDaysSettingMapper.partialUpdate(existingWorkinDaysSetting, workinDaysSettingDTO);

                return existingWorkinDaysSetting;
            })
            .map(workinDaysSettingRepository::save)
            .map(workinDaysSettingMapper::toDto);
    }

    /**
     * Get all the workinDaysSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkinDaysSettingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkinDaysSettings");
        return workinDaysSettingRepository.findAll(pageable).map(workinDaysSettingMapper::toDto);
    }

    /**
     * Get one workinDaysSetting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkinDaysSettingDTO> findOne(Long id) {
        log.debug("Request to get WorkinDaysSetting : {}", id);
        return workinDaysSettingRepository.findById(id).map(workinDaysSettingMapper::toDto);
    }

    /**
     * Delete the workinDaysSetting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkinDaysSetting : {}", id);
        workinDaysSettingRepository.deleteById(id);
    }
}
