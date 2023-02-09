package com.techvg.smtrthr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.smtrthr.IntegrationTest;
import com.techvg.smtrthr.domain.ApprovalSetting;
import com.techvg.smtrthr.repository.ApprovalSettingRepository;
import com.techvg.smtrthr.service.criteria.ApprovalSettingCriteria;
import com.techvg.smtrthr.service.dto.ApprovalSettingDTO;
import com.techvg.smtrthr.service.mapper.ApprovalSettingMapper;
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
 * Integration tests for the {@link ApprovalSettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApprovalSettingResourceIT {

    private static final Boolean DEFAULT_IS_SEQUENCE_APPROVAL = false;
    private static final Boolean UPDATED_IS_SEQUENCE_APPROVAL = true;

    private static final Boolean DEFAULT_IS_SIMULTANEOUS_APPROVAL = false;
    private static final Boolean UPDATED_IS_SIMULTANEOUS_APPROVAL = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/approval-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApprovalSettingRepository approvalSettingRepository;

    @Autowired
    private ApprovalSettingMapper approvalSettingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApprovalSettingMockMvc;

    private ApprovalSetting approvalSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApprovalSetting createEntity(EntityManager em) {
        ApprovalSetting approvalSetting = new ApprovalSetting()
            .isSequenceApproval(DEFAULT_IS_SEQUENCE_APPROVAL)
            .isSimultaneousApproval(DEFAULT_IS_SIMULTANEOUS_APPROVAL)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .status(DEFAULT_STATUS)
            .companyId(DEFAULT_COMPANY_ID);
        return approvalSetting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApprovalSetting createUpdatedEntity(EntityManager em) {
        ApprovalSetting approvalSetting = new ApprovalSetting()
            .isSequenceApproval(UPDATED_IS_SEQUENCE_APPROVAL)
            .isSimultaneousApproval(UPDATED_IS_SIMULTANEOUS_APPROVAL)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .companyId(UPDATED_COMPANY_ID);
        return approvalSetting;
    }

    @BeforeEach
    public void initTest() {
        approvalSetting = createEntity(em);
    }

    @Test
    @Transactional
    void createApprovalSetting() throws Exception {
        int databaseSizeBeforeCreate = approvalSettingRepository.findAll().size();
        // Create the ApprovalSetting
        ApprovalSettingDTO approvalSettingDTO = approvalSettingMapper.toDto(approvalSetting);
        restApprovalSettingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalSettingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ApprovalSetting in the database
        List<ApprovalSetting> approvalSettingList = approvalSettingRepository.findAll();
        assertThat(approvalSettingList).hasSize(databaseSizeBeforeCreate + 1);
        ApprovalSetting testApprovalSetting = approvalSettingList.get(approvalSettingList.size() - 1);
        assertThat(testApprovalSetting.getIsSequenceApproval()).isEqualTo(DEFAULT_IS_SEQUENCE_APPROVAL);
        assertThat(testApprovalSetting.getIsSimultaneousApproval()).isEqualTo(DEFAULT_IS_SIMULTANEOUS_APPROVAL);
        assertThat(testApprovalSetting.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testApprovalSetting.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testApprovalSetting.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApprovalSetting.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    void createApprovalSettingWithExistingId() throws Exception {
        // Create the ApprovalSetting with an existing ID
        approvalSetting.setId(1L);
        ApprovalSettingDTO approvalSettingDTO = approvalSettingMapper.toDto(approvalSetting);

        int databaseSizeBeforeCreate = approvalSettingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApprovalSettingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalSetting in the database
        List<ApprovalSetting> approvalSettingList = approvalSettingRepository.findAll();
        assertThat(approvalSettingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApprovalSettings() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList
        restApprovalSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approvalSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].isSequenceApproval").value(hasItem(DEFAULT_IS_SEQUENCE_APPROVAL.booleanValue())))
            .andExpect(jsonPath("$.[*].isSimultaneousApproval").value(hasItem(DEFAULT_IS_SIMULTANEOUS_APPROVAL.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));
    }

    @Test
    @Transactional
    void getApprovalSetting() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get the approvalSetting
        restApprovalSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, approvalSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(approvalSetting.getId().intValue()))
            .andExpect(jsonPath("$.isSequenceApproval").value(DEFAULT_IS_SEQUENCE_APPROVAL.booleanValue()))
            .andExpect(jsonPath("$.isSimultaneousApproval").value(DEFAULT_IS_SIMULTANEOUS_APPROVAL.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()));
    }

    @Test
    @Transactional
    void getApprovalSettingsByIdFiltering() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        Long id = approvalSetting.getId();

        defaultApprovalSettingShouldBeFound("id.equals=" + id);
        defaultApprovalSettingShouldNotBeFound("id.notEquals=" + id);

        defaultApprovalSettingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApprovalSettingShouldNotBeFound("id.greaterThan=" + id);

        defaultApprovalSettingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApprovalSettingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByIsSequenceApprovalIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where isSequenceApproval equals to DEFAULT_IS_SEQUENCE_APPROVAL
        defaultApprovalSettingShouldBeFound("isSequenceApproval.equals=" + DEFAULT_IS_SEQUENCE_APPROVAL);

        // Get all the approvalSettingList where isSequenceApproval equals to UPDATED_IS_SEQUENCE_APPROVAL
        defaultApprovalSettingShouldNotBeFound("isSequenceApproval.equals=" + UPDATED_IS_SEQUENCE_APPROVAL);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByIsSequenceApprovalIsInShouldWork() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where isSequenceApproval in DEFAULT_IS_SEQUENCE_APPROVAL or UPDATED_IS_SEQUENCE_APPROVAL
        defaultApprovalSettingShouldBeFound("isSequenceApproval.in=" + DEFAULT_IS_SEQUENCE_APPROVAL + "," + UPDATED_IS_SEQUENCE_APPROVAL);

        // Get all the approvalSettingList where isSequenceApproval equals to UPDATED_IS_SEQUENCE_APPROVAL
        defaultApprovalSettingShouldNotBeFound("isSequenceApproval.in=" + UPDATED_IS_SEQUENCE_APPROVAL);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByIsSequenceApprovalIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where isSequenceApproval is not null
        defaultApprovalSettingShouldBeFound("isSequenceApproval.specified=true");

        // Get all the approvalSettingList where isSequenceApproval is null
        defaultApprovalSettingShouldNotBeFound("isSequenceApproval.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByIsSimultaneousApprovalIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where isSimultaneousApproval equals to DEFAULT_IS_SIMULTANEOUS_APPROVAL
        defaultApprovalSettingShouldBeFound("isSimultaneousApproval.equals=" + DEFAULT_IS_SIMULTANEOUS_APPROVAL);

        // Get all the approvalSettingList where isSimultaneousApproval equals to UPDATED_IS_SIMULTANEOUS_APPROVAL
        defaultApprovalSettingShouldNotBeFound("isSimultaneousApproval.equals=" + UPDATED_IS_SIMULTANEOUS_APPROVAL);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByIsSimultaneousApprovalIsInShouldWork() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where isSimultaneousApproval in DEFAULT_IS_SIMULTANEOUS_APPROVAL or UPDATED_IS_SIMULTANEOUS_APPROVAL
        defaultApprovalSettingShouldBeFound(
            "isSimultaneousApproval.in=" + DEFAULT_IS_SIMULTANEOUS_APPROVAL + "," + UPDATED_IS_SIMULTANEOUS_APPROVAL
        );

        // Get all the approvalSettingList where isSimultaneousApproval equals to UPDATED_IS_SIMULTANEOUS_APPROVAL
        defaultApprovalSettingShouldNotBeFound("isSimultaneousApproval.in=" + UPDATED_IS_SIMULTANEOUS_APPROVAL);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByIsSimultaneousApprovalIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where isSimultaneousApproval is not null
        defaultApprovalSettingShouldBeFound("isSimultaneousApproval.specified=true");

        // Get all the approvalSettingList where isSimultaneousApproval is null
        defaultApprovalSettingShouldNotBeFound("isSimultaneousApproval.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultApprovalSettingShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the approvalSettingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultApprovalSettingShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultApprovalSettingShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the approvalSettingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultApprovalSettingShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where lastModified is not null
        defaultApprovalSettingShouldBeFound("lastModified.specified=true");

        // Get all the approvalSettingList where lastModified is null
        defaultApprovalSettingShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultApprovalSettingShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the approvalSettingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApprovalSettingShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultApprovalSettingShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the approvalSettingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApprovalSettingShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where lastModifiedBy is not null
        defaultApprovalSettingShouldBeFound("lastModifiedBy.specified=true");

        // Get all the approvalSettingList where lastModifiedBy is null
        defaultApprovalSettingShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultApprovalSettingShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the approvalSettingList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultApprovalSettingShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultApprovalSettingShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the approvalSettingList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultApprovalSettingShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where status equals to DEFAULT_STATUS
        defaultApprovalSettingShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the approvalSettingList where status equals to UPDATED_STATUS
        defaultApprovalSettingShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultApprovalSettingShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the approvalSettingList where status equals to UPDATED_STATUS
        defaultApprovalSettingShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where status is not null
        defaultApprovalSettingShouldBeFound("status.specified=true");

        // Get all the approvalSettingList where status is null
        defaultApprovalSettingShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByStatusContainsSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where status contains DEFAULT_STATUS
        defaultApprovalSettingShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the approvalSettingList where status contains UPDATED_STATUS
        defaultApprovalSettingShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where status does not contain DEFAULT_STATUS
        defaultApprovalSettingShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the approvalSettingList where status does not contain UPDATED_STATUS
        defaultApprovalSettingShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where companyId equals to DEFAULT_COMPANY_ID
        defaultApprovalSettingShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the approvalSettingList where companyId equals to UPDATED_COMPANY_ID
        defaultApprovalSettingShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultApprovalSettingShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the approvalSettingList where companyId equals to UPDATED_COMPANY_ID
        defaultApprovalSettingShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where companyId is not null
        defaultApprovalSettingShouldBeFound("companyId.specified=true");

        // Get all the approvalSettingList where companyId is null
        defaultApprovalSettingShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultApprovalSettingShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the approvalSettingList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultApprovalSettingShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultApprovalSettingShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the approvalSettingList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultApprovalSettingShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where companyId is less than DEFAULT_COMPANY_ID
        defaultApprovalSettingShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the approvalSettingList where companyId is less than UPDATED_COMPANY_ID
        defaultApprovalSettingShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalSettingsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        // Get all the approvalSettingList where companyId is greater than DEFAULT_COMPANY_ID
        defaultApprovalSettingShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the approvalSettingList where companyId is greater than SMALLER_COMPANY_ID
        defaultApprovalSettingShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApprovalSettingShouldBeFound(String filter) throws Exception {
        restApprovalSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approvalSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].isSequenceApproval").value(hasItem(DEFAULT_IS_SEQUENCE_APPROVAL.booleanValue())))
            .andExpect(jsonPath("$.[*].isSimultaneousApproval").value(hasItem(DEFAULT_IS_SIMULTANEOUS_APPROVAL.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));

        // Check, that the count call also returns 1
        restApprovalSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApprovalSettingShouldNotBeFound(String filter) throws Exception {
        restApprovalSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApprovalSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApprovalSetting() throws Exception {
        // Get the approvalSetting
        restApprovalSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApprovalSetting() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        int databaseSizeBeforeUpdate = approvalSettingRepository.findAll().size();

        // Update the approvalSetting
        ApprovalSetting updatedApprovalSetting = approvalSettingRepository.findById(approvalSetting.getId()).get();
        // Disconnect from session so that the updates on updatedApprovalSetting are not directly saved in db
        em.detach(updatedApprovalSetting);
        updatedApprovalSetting
            .isSequenceApproval(UPDATED_IS_SEQUENCE_APPROVAL)
            .isSimultaneousApproval(UPDATED_IS_SIMULTANEOUS_APPROVAL)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .companyId(UPDATED_COMPANY_ID);
        ApprovalSettingDTO approvalSettingDTO = approvalSettingMapper.toDto(updatedApprovalSetting);

        restApprovalSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalSettingDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApprovalSetting in the database
        List<ApprovalSetting> approvalSettingList = approvalSettingRepository.findAll();
        assertThat(approvalSettingList).hasSize(databaseSizeBeforeUpdate);
        ApprovalSetting testApprovalSetting = approvalSettingList.get(approvalSettingList.size() - 1);
        assertThat(testApprovalSetting.getIsSequenceApproval()).isEqualTo(UPDATED_IS_SEQUENCE_APPROVAL);
        assertThat(testApprovalSetting.getIsSimultaneousApproval()).isEqualTo(UPDATED_IS_SIMULTANEOUS_APPROVAL);
        assertThat(testApprovalSetting.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testApprovalSetting.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testApprovalSetting.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApprovalSetting.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void putNonExistingApprovalSetting() throws Exception {
        int databaseSizeBeforeUpdate = approvalSettingRepository.findAll().size();
        approvalSetting.setId(count.incrementAndGet());

        // Create the ApprovalSetting
        ApprovalSettingDTO approvalSettingDTO = approvalSettingMapper.toDto(approvalSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalSetting in the database
        List<ApprovalSetting> approvalSettingList = approvalSettingRepository.findAll();
        assertThat(approvalSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApprovalSetting() throws Exception {
        int databaseSizeBeforeUpdate = approvalSettingRepository.findAll().size();
        approvalSetting.setId(count.incrementAndGet());

        // Create the ApprovalSetting
        ApprovalSettingDTO approvalSettingDTO = approvalSettingMapper.toDto(approvalSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalSetting in the database
        List<ApprovalSetting> approvalSettingList = approvalSettingRepository.findAll();
        assertThat(approvalSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApprovalSetting() throws Exception {
        int databaseSizeBeforeUpdate = approvalSettingRepository.findAll().size();
        approvalSetting.setId(count.incrementAndGet());

        // Create the ApprovalSetting
        ApprovalSettingDTO approvalSettingDTO = approvalSettingMapper.toDto(approvalSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalSettingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalSettingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApprovalSetting in the database
        List<ApprovalSetting> approvalSettingList = approvalSettingRepository.findAll();
        assertThat(approvalSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApprovalSettingWithPatch() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        int databaseSizeBeforeUpdate = approvalSettingRepository.findAll().size();

        // Update the approvalSetting using partial update
        ApprovalSetting partialUpdatedApprovalSetting = new ApprovalSetting();
        partialUpdatedApprovalSetting.setId(approvalSetting.getId());

        partialUpdatedApprovalSetting.lastModified(UPDATED_LAST_MODIFIED).status(UPDATED_STATUS);

        restApprovalSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprovalSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprovalSetting))
            )
            .andExpect(status().isOk());

        // Validate the ApprovalSetting in the database
        List<ApprovalSetting> approvalSettingList = approvalSettingRepository.findAll();
        assertThat(approvalSettingList).hasSize(databaseSizeBeforeUpdate);
        ApprovalSetting testApprovalSetting = approvalSettingList.get(approvalSettingList.size() - 1);
        assertThat(testApprovalSetting.getIsSequenceApproval()).isEqualTo(DEFAULT_IS_SEQUENCE_APPROVAL);
        assertThat(testApprovalSetting.getIsSimultaneousApproval()).isEqualTo(DEFAULT_IS_SIMULTANEOUS_APPROVAL);
        assertThat(testApprovalSetting.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testApprovalSetting.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testApprovalSetting.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApprovalSetting.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    void fullUpdateApprovalSettingWithPatch() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        int databaseSizeBeforeUpdate = approvalSettingRepository.findAll().size();

        // Update the approvalSetting using partial update
        ApprovalSetting partialUpdatedApprovalSetting = new ApprovalSetting();
        partialUpdatedApprovalSetting.setId(approvalSetting.getId());

        partialUpdatedApprovalSetting
            .isSequenceApproval(UPDATED_IS_SEQUENCE_APPROVAL)
            .isSimultaneousApproval(UPDATED_IS_SIMULTANEOUS_APPROVAL)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .companyId(UPDATED_COMPANY_ID);

        restApprovalSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprovalSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprovalSetting))
            )
            .andExpect(status().isOk());

        // Validate the ApprovalSetting in the database
        List<ApprovalSetting> approvalSettingList = approvalSettingRepository.findAll();
        assertThat(approvalSettingList).hasSize(databaseSizeBeforeUpdate);
        ApprovalSetting testApprovalSetting = approvalSettingList.get(approvalSettingList.size() - 1);
        assertThat(testApprovalSetting.getIsSequenceApproval()).isEqualTo(UPDATED_IS_SEQUENCE_APPROVAL);
        assertThat(testApprovalSetting.getIsSimultaneousApproval()).isEqualTo(UPDATED_IS_SIMULTANEOUS_APPROVAL);
        assertThat(testApprovalSetting.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testApprovalSetting.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testApprovalSetting.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApprovalSetting.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingApprovalSetting() throws Exception {
        int databaseSizeBeforeUpdate = approvalSettingRepository.findAll().size();
        approvalSetting.setId(count.incrementAndGet());

        // Create the ApprovalSetting
        ApprovalSettingDTO approvalSettingDTO = approvalSettingMapper.toDto(approvalSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, approvalSettingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalSetting in the database
        List<ApprovalSetting> approvalSettingList = approvalSettingRepository.findAll();
        assertThat(approvalSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApprovalSetting() throws Exception {
        int databaseSizeBeforeUpdate = approvalSettingRepository.findAll().size();
        approvalSetting.setId(count.incrementAndGet());

        // Create the ApprovalSetting
        ApprovalSettingDTO approvalSettingDTO = approvalSettingMapper.toDto(approvalSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalSetting in the database
        List<ApprovalSetting> approvalSettingList = approvalSettingRepository.findAll();
        assertThat(approvalSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApprovalSetting() throws Exception {
        int databaseSizeBeforeUpdate = approvalSettingRepository.findAll().size();
        approvalSetting.setId(count.incrementAndGet());

        // Create the ApprovalSetting
        ApprovalSettingDTO approvalSettingDTO = approvalSettingMapper.toDto(approvalSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalSettingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalSettingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApprovalSetting in the database
        List<ApprovalSetting> approvalSettingList = approvalSettingRepository.findAll();
        assertThat(approvalSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApprovalSetting() throws Exception {
        // Initialize the database
        approvalSettingRepository.saveAndFlush(approvalSetting);

        int databaseSizeBeforeDelete = approvalSettingRepository.findAll().size();

        // Delete the approvalSetting
        restApprovalSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, approvalSetting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApprovalSetting> approvalSettingList = approvalSettingRepository.findAll();
        assertThat(approvalSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
