package com.techvg.smtrthr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.smtrthr.IntegrationTest;
import com.techvg.smtrthr.domain.WorkinDaysSetting;
import com.techvg.smtrthr.repository.WorkinDaysSettingRepository;
import com.techvg.smtrthr.service.criteria.WorkinDaysSettingCriteria;
import com.techvg.smtrthr.service.dto.WorkinDaysSettingDTO;
import com.techvg.smtrthr.service.mapper.WorkinDaysSettingMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WorkinDaysSettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkinDaysSettingResourceIT {

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/workin-days-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkinDaysSettingRepository workinDaysSettingRepository;

    @Autowired
    private WorkinDaysSettingMapper workinDaysSettingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkinDaysSettingMockMvc;

    private WorkinDaysSetting workinDaysSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkinDaysSetting createEntity(EntityManager em) {
        WorkinDaysSetting workinDaysSetting = new WorkinDaysSetting()
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .status(DEFAULT_STATUS)
            .companyId(DEFAULT_COMPANY_ID);
        return workinDaysSetting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkinDaysSetting createUpdatedEntity(EntityManager em) {
        WorkinDaysSetting workinDaysSetting = new WorkinDaysSetting()
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .companyId(UPDATED_COMPANY_ID);
        return workinDaysSetting;
    }

    @BeforeEach
    public void initTest() {
        workinDaysSetting = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkinDaysSetting() throws Exception {
        int databaseSizeBeforeCreate = workinDaysSettingRepository.findAll().size();
        // Create the WorkinDaysSetting
        WorkinDaysSettingDTO workinDaysSettingDTO = workinDaysSettingMapper.toDto(workinDaysSetting);
        restWorkinDaysSettingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workinDaysSettingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkinDaysSetting in the database
        List<WorkinDaysSetting> workinDaysSettingList = workinDaysSettingRepository.findAll();
        assertThat(workinDaysSettingList).hasSize(databaseSizeBeforeCreate + 1);
        WorkinDaysSetting testWorkinDaysSetting = workinDaysSettingList.get(workinDaysSettingList.size() - 1);
        assertThat(testWorkinDaysSetting.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testWorkinDaysSetting.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testWorkinDaysSetting.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkinDaysSetting.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    void createWorkinDaysSettingWithExistingId() throws Exception {
        // Create the WorkinDaysSetting with an existing ID
        workinDaysSetting.setId(1L);
        WorkinDaysSettingDTO workinDaysSettingDTO = workinDaysSettingMapper.toDto(workinDaysSetting);

        int databaseSizeBeforeCreate = workinDaysSettingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkinDaysSettingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workinDaysSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkinDaysSetting in the database
        List<WorkinDaysSetting> workinDaysSettingList = workinDaysSettingRepository.findAll();
        assertThat(workinDaysSettingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettings() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList
        restWorkinDaysSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workinDaysSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));
    }

    @Test
    @Transactional
    void getWorkinDaysSetting() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get the workinDaysSetting
        restWorkinDaysSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, workinDaysSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workinDaysSetting.getId().intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()));
    }

    @Test
    @Transactional
    void getWorkinDaysSettingsByIdFiltering() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        Long id = workinDaysSetting.getId();

        defaultWorkinDaysSettingShouldBeFound("id.equals=" + id);
        defaultWorkinDaysSettingShouldNotBeFound("id.notEquals=" + id);

        defaultWorkinDaysSettingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkinDaysSettingShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkinDaysSettingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkinDaysSettingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultWorkinDaysSettingShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the workinDaysSettingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWorkinDaysSettingShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultWorkinDaysSettingShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the workinDaysSettingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWorkinDaysSettingShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where lastModified is not null
        defaultWorkinDaysSettingShouldBeFound("lastModified.specified=true");

        // Get all the workinDaysSettingList where lastModified is null
        defaultWorkinDaysSettingShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultWorkinDaysSettingShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workinDaysSettingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkinDaysSettingShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultWorkinDaysSettingShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the workinDaysSettingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkinDaysSettingShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where lastModifiedBy is not null
        defaultWorkinDaysSettingShouldBeFound("lastModifiedBy.specified=true");

        // Get all the workinDaysSettingList where lastModifiedBy is null
        defaultWorkinDaysSettingShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultWorkinDaysSettingShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workinDaysSettingList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultWorkinDaysSettingShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultWorkinDaysSettingShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workinDaysSettingList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultWorkinDaysSettingShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where status equals to DEFAULT_STATUS
        defaultWorkinDaysSettingShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the workinDaysSettingList where status equals to UPDATED_STATUS
        defaultWorkinDaysSettingShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultWorkinDaysSettingShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the workinDaysSettingList where status equals to UPDATED_STATUS
        defaultWorkinDaysSettingShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where status is not null
        defaultWorkinDaysSettingShouldBeFound("status.specified=true");

        // Get all the workinDaysSettingList where status is null
        defaultWorkinDaysSettingShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByStatusContainsSomething() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where status contains DEFAULT_STATUS
        defaultWorkinDaysSettingShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the workinDaysSettingList where status contains UPDATED_STATUS
        defaultWorkinDaysSettingShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where status does not contain DEFAULT_STATUS
        defaultWorkinDaysSettingShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the workinDaysSettingList where status does not contain UPDATED_STATUS
        defaultWorkinDaysSettingShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where companyId equals to DEFAULT_COMPANY_ID
        defaultWorkinDaysSettingShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the workinDaysSettingList where companyId equals to UPDATED_COMPANY_ID
        defaultWorkinDaysSettingShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultWorkinDaysSettingShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the workinDaysSettingList where companyId equals to UPDATED_COMPANY_ID
        defaultWorkinDaysSettingShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where companyId is not null
        defaultWorkinDaysSettingShouldBeFound("companyId.specified=true");

        // Get all the workinDaysSettingList where companyId is null
        defaultWorkinDaysSettingShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultWorkinDaysSettingShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the workinDaysSettingList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultWorkinDaysSettingShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultWorkinDaysSettingShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the workinDaysSettingList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultWorkinDaysSettingShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where companyId is less than DEFAULT_COMPANY_ID
        defaultWorkinDaysSettingShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the workinDaysSettingList where companyId is less than UPDATED_COMPANY_ID
        defaultWorkinDaysSettingShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkinDaysSettingsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        // Get all the workinDaysSettingList where companyId is greater than DEFAULT_COMPANY_ID
        defaultWorkinDaysSettingShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the workinDaysSettingList where companyId is greater than SMALLER_COMPANY_ID
        defaultWorkinDaysSettingShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkinDaysSettingShouldBeFound(String filter) throws Exception {
        restWorkinDaysSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workinDaysSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));

        // Check, that the count call also returns 1
        restWorkinDaysSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkinDaysSettingShouldNotBeFound(String filter) throws Exception {
        restWorkinDaysSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkinDaysSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWorkinDaysSetting() throws Exception {
        // Get the workinDaysSetting
        restWorkinDaysSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkinDaysSetting() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        int databaseSizeBeforeUpdate = workinDaysSettingRepository.findAll().size();

        // Update the workinDaysSetting
        WorkinDaysSetting updatedWorkinDaysSetting = workinDaysSettingRepository.findById(workinDaysSetting.getId()).get();
        // Disconnect from session so that the updates on updatedWorkinDaysSetting are not directly saved in db
        em.detach(updatedWorkinDaysSetting);
        updatedWorkinDaysSetting
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .companyId(UPDATED_COMPANY_ID);
        WorkinDaysSettingDTO workinDaysSettingDTO = workinDaysSettingMapper.toDto(updatedWorkinDaysSetting);

        restWorkinDaysSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workinDaysSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workinDaysSettingDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkinDaysSetting in the database
        List<WorkinDaysSetting> workinDaysSettingList = workinDaysSettingRepository.findAll();
        assertThat(workinDaysSettingList).hasSize(databaseSizeBeforeUpdate);
        WorkinDaysSetting testWorkinDaysSetting = workinDaysSettingList.get(workinDaysSettingList.size() - 1);
        assertThat(testWorkinDaysSetting.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkinDaysSetting.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWorkinDaysSetting.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkinDaysSetting.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void putNonExistingWorkinDaysSetting() throws Exception {
        int databaseSizeBeforeUpdate = workinDaysSettingRepository.findAll().size();
        workinDaysSetting.setId(count.incrementAndGet());

        // Create the WorkinDaysSetting
        WorkinDaysSettingDTO workinDaysSettingDTO = workinDaysSettingMapper.toDto(workinDaysSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkinDaysSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workinDaysSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workinDaysSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkinDaysSetting in the database
        List<WorkinDaysSetting> workinDaysSettingList = workinDaysSettingRepository.findAll();
        assertThat(workinDaysSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkinDaysSetting() throws Exception {
        int databaseSizeBeforeUpdate = workinDaysSettingRepository.findAll().size();
        workinDaysSetting.setId(count.incrementAndGet());

        // Create the WorkinDaysSetting
        WorkinDaysSettingDTO workinDaysSettingDTO = workinDaysSettingMapper.toDto(workinDaysSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkinDaysSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workinDaysSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkinDaysSetting in the database
        List<WorkinDaysSetting> workinDaysSettingList = workinDaysSettingRepository.findAll();
        assertThat(workinDaysSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkinDaysSetting() throws Exception {
        int databaseSizeBeforeUpdate = workinDaysSettingRepository.findAll().size();
        workinDaysSetting.setId(count.incrementAndGet());

        // Create the WorkinDaysSetting
        WorkinDaysSettingDTO workinDaysSettingDTO = workinDaysSettingMapper.toDto(workinDaysSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkinDaysSettingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workinDaysSettingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkinDaysSetting in the database
        List<WorkinDaysSetting> workinDaysSettingList = workinDaysSettingRepository.findAll();
        assertThat(workinDaysSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkinDaysSettingWithPatch() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        int databaseSizeBeforeUpdate = workinDaysSettingRepository.findAll().size();

        // Update the workinDaysSetting using partial update
        WorkinDaysSetting partialUpdatedWorkinDaysSetting = new WorkinDaysSetting();
        partialUpdatedWorkinDaysSetting.setId(workinDaysSetting.getId());

        partialUpdatedWorkinDaysSetting.lastModified(UPDATED_LAST_MODIFIED).companyId(UPDATED_COMPANY_ID);

        restWorkinDaysSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkinDaysSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkinDaysSetting))
            )
            .andExpect(status().isOk());

        // Validate the WorkinDaysSetting in the database
        List<WorkinDaysSetting> workinDaysSettingList = workinDaysSettingRepository.findAll();
        assertThat(workinDaysSettingList).hasSize(databaseSizeBeforeUpdate);
        WorkinDaysSetting testWorkinDaysSetting = workinDaysSettingList.get(workinDaysSettingList.size() - 1);
        assertThat(testWorkinDaysSetting.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkinDaysSetting.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testWorkinDaysSetting.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkinDaysSetting.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void fullUpdateWorkinDaysSettingWithPatch() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        int databaseSizeBeforeUpdate = workinDaysSettingRepository.findAll().size();

        // Update the workinDaysSetting using partial update
        WorkinDaysSetting partialUpdatedWorkinDaysSetting = new WorkinDaysSetting();
        partialUpdatedWorkinDaysSetting.setId(workinDaysSetting.getId());

        partialUpdatedWorkinDaysSetting
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .companyId(UPDATED_COMPANY_ID);

        restWorkinDaysSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkinDaysSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkinDaysSetting))
            )
            .andExpect(status().isOk());

        // Validate the WorkinDaysSetting in the database
        List<WorkinDaysSetting> workinDaysSettingList = workinDaysSettingRepository.findAll();
        assertThat(workinDaysSettingList).hasSize(databaseSizeBeforeUpdate);
        WorkinDaysSetting testWorkinDaysSetting = workinDaysSettingList.get(workinDaysSettingList.size() - 1);
        assertThat(testWorkinDaysSetting.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkinDaysSetting.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWorkinDaysSetting.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkinDaysSetting.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingWorkinDaysSetting() throws Exception {
        int databaseSizeBeforeUpdate = workinDaysSettingRepository.findAll().size();
        workinDaysSetting.setId(count.incrementAndGet());

        // Create the WorkinDaysSetting
        WorkinDaysSettingDTO workinDaysSettingDTO = workinDaysSettingMapper.toDto(workinDaysSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkinDaysSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workinDaysSettingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workinDaysSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkinDaysSetting in the database
        List<WorkinDaysSetting> workinDaysSettingList = workinDaysSettingRepository.findAll();
        assertThat(workinDaysSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkinDaysSetting() throws Exception {
        int databaseSizeBeforeUpdate = workinDaysSettingRepository.findAll().size();
        workinDaysSetting.setId(count.incrementAndGet());

        // Create the WorkinDaysSetting
        WorkinDaysSettingDTO workinDaysSettingDTO = workinDaysSettingMapper.toDto(workinDaysSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkinDaysSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workinDaysSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkinDaysSetting in the database
        List<WorkinDaysSetting> workinDaysSettingList = workinDaysSettingRepository.findAll();
        assertThat(workinDaysSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkinDaysSetting() throws Exception {
        int databaseSizeBeforeUpdate = workinDaysSettingRepository.findAll().size();
        workinDaysSetting.setId(count.incrementAndGet());

        // Create the WorkinDaysSetting
        WorkinDaysSettingDTO workinDaysSettingDTO = workinDaysSettingMapper.toDto(workinDaysSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkinDaysSettingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workinDaysSettingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkinDaysSetting in the database
        List<WorkinDaysSetting> workinDaysSettingList = workinDaysSettingRepository.findAll();
        assertThat(workinDaysSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkinDaysSetting() throws Exception {
        // Initialize the database
        workinDaysSettingRepository.saveAndFlush(workinDaysSetting);

        int databaseSizeBeforeDelete = workinDaysSettingRepository.findAll().size();

        // Delete the workinDaysSetting
        restWorkinDaysSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, workinDaysSetting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkinDaysSetting> workinDaysSettingList = workinDaysSettingRepository.findAll();
        assertThat(workinDaysSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
