package com.techvg.smtrthr.web.rest;

import com.techvg.smtrthr.repository.WorkinDaysSettingRepository;
import com.techvg.smtrthr.service.WorkinDaysSettingQueryService;
import com.techvg.smtrthr.service.WorkinDaysSettingService;
import com.techvg.smtrthr.service.criteria.WorkinDaysSettingCriteria;
import com.techvg.smtrthr.service.dto.WorkinDaysSettingDTO;
import com.techvg.smtrthr.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.smtrthr.domain.WorkinDaysSetting}.
 */
@RestController
@RequestMapping("/api")
public class WorkinDaysSettingResource {

    private final Logger log = LoggerFactory.getLogger(WorkinDaysSettingResource.class);

    private static final String ENTITY_NAME = "workinDaysSetting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkinDaysSettingService workinDaysSettingService;

    private final WorkinDaysSettingRepository workinDaysSettingRepository;

    private final WorkinDaysSettingQueryService workinDaysSettingQueryService;

    public WorkinDaysSettingResource(
        WorkinDaysSettingService workinDaysSettingService,
        WorkinDaysSettingRepository workinDaysSettingRepository,
        WorkinDaysSettingQueryService workinDaysSettingQueryService
    ) {
        this.workinDaysSettingService = workinDaysSettingService;
        this.workinDaysSettingRepository = workinDaysSettingRepository;
        this.workinDaysSettingQueryService = workinDaysSettingQueryService;
    }

    /**
     * {@code POST  /workin-days-settings} : Create a new workinDaysSetting.
     *
     * @param workinDaysSettingDTO the workinDaysSettingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workinDaysSettingDTO, or with status {@code 400 (Bad Request)} if the workinDaysSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workin-days-settings")
    public ResponseEntity<WorkinDaysSettingDTO> createWorkinDaysSetting(@RequestBody WorkinDaysSettingDTO workinDaysSettingDTO)
        throws URISyntaxException {
        log.debug("REST request to save WorkinDaysSetting : {}", workinDaysSettingDTO);
        if (workinDaysSettingDTO.getId() != null) {
            throw new BadRequestAlertException("A new workinDaysSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkinDaysSettingDTO result = workinDaysSettingService.save(workinDaysSettingDTO);
        return ResponseEntity
            .created(new URI("/api/workin-days-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workin-days-settings/:id} : Updates an existing workinDaysSetting.
     *
     * @param id the id of the workinDaysSettingDTO to save.
     * @param workinDaysSettingDTO the workinDaysSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workinDaysSettingDTO,
     * or with status {@code 400 (Bad Request)} if the workinDaysSettingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workinDaysSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workin-days-settings/{id}")
    public ResponseEntity<WorkinDaysSettingDTO> updateWorkinDaysSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkinDaysSettingDTO workinDaysSettingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WorkinDaysSetting : {}, {}", id, workinDaysSettingDTO);
        if (workinDaysSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workinDaysSettingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workinDaysSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkinDaysSettingDTO result = workinDaysSettingService.update(workinDaysSettingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workinDaysSettingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /workin-days-settings/:id} : Partial updates given fields of an existing workinDaysSetting, field will ignore if it is null
     *
     * @param id the id of the workinDaysSettingDTO to save.
     * @param workinDaysSettingDTO the workinDaysSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workinDaysSettingDTO,
     * or with status {@code 400 (Bad Request)} if the workinDaysSettingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the workinDaysSettingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the workinDaysSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/workin-days-settings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkinDaysSettingDTO> partialUpdateWorkinDaysSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkinDaysSettingDTO workinDaysSettingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkinDaysSetting partially : {}, {}", id, workinDaysSettingDTO);
        if (workinDaysSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workinDaysSettingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workinDaysSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkinDaysSettingDTO> result = workinDaysSettingService.partialUpdate(workinDaysSettingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workinDaysSettingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /workin-days-settings} : get all the workinDaysSettings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workinDaysSettings in body.
     */
    @GetMapping("/workin-days-settings")
    public ResponseEntity<List<WorkinDaysSettingDTO>> getAllWorkinDaysSettings(
        WorkinDaysSettingCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get WorkinDaysSettings by criteria: {}", criteria);
        Page<WorkinDaysSettingDTO> page = workinDaysSettingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /workin-days-settings/count} : count all the workinDaysSettings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/workin-days-settings/count")
    public ResponseEntity<Long> countWorkinDaysSettings(WorkinDaysSettingCriteria criteria) {
        log.debug("REST request to count WorkinDaysSettings by criteria: {}", criteria);
        return ResponseEntity.ok().body(workinDaysSettingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /workin-days-settings/:id} : get the "id" workinDaysSetting.
     *
     * @param id the id of the workinDaysSettingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workinDaysSettingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workin-days-settings/{id}")
    public ResponseEntity<WorkinDaysSettingDTO> getWorkinDaysSetting(@PathVariable Long id) {
        log.debug("REST request to get WorkinDaysSetting : {}", id);
        Optional<WorkinDaysSettingDTO> workinDaysSettingDTO = workinDaysSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workinDaysSettingDTO);
    }

    /**
     * {@code DELETE  /workin-days-settings/:id} : delete the "id" workinDaysSetting.
     *
     * @param id the id of the workinDaysSettingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workin-days-settings/{id}")
    public ResponseEntity<Void> deleteWorkinDaysSetting(@PathVariable Long id) {
        log.debug("REST request to delete WorkinDaysSetting : {}", id);
        workinDaysSettingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
