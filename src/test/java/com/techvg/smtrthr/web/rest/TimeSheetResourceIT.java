package com.techvg.smtrthr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.smtrthr.IntegrationTest;
import com.techvg.smtrthr.domain.Attendance;
import com.techvg.smtrthr.domain.TimeSheet;
import com.techvg.smtrthr.repository.TimeSheetRepository;
import com.techvg.smtrthr.service.criteria.TimeSheetCriteria;
import com.techvg.smtrthr.service.dto.TimeSheetDTO;
import com.techvg.smtrthr.service.mapper.TimeSheetMapper;
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
 * Integration tests for the {@link TimeSheetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TimeSheetResourceIT {

    private static final Instant DEFAULT_CHECK_IN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECK_IN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CHECK_OUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECK_OUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_HAS_CHECKED_IN = false;
    private static final Boolean UPDATED_HAS_CHECKED_IN = true;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/time-sheets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TimeSheetRepository timeSheetRepository;

    @Autowired
    private TimeSheetMapper timeSheetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimeSheetMockMvc;

    private TimeSheet timeSheet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeSheet createEntity(EntityManager em) {
        TimeSheet timeSheet = new TimeSheet()
            .checkIn(DEFAULT_CHECK_IN)
            .checkOut(DEFAULT_CHECK_OUT)
            .date(DEFAULT_DATE)
            .hasCheckedIn(DEFAULT_HAS_CHECKED_IN)
            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return timeSheet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeSheet createUpdatedEntity(EntityManager em) {
        TimeSheet timeSheet = new TimeSheet()
            .checkIn(UPDATED_CHECK_IN)
            .checkOut(UPDATED_CHECK_OUT)
            .date(UPDATED_DATE)
            .hasCheckedIn(UPDATED_HAS_CHECKED_IN)
            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return timeSheet;
    }

    @BeforeEach
    public void initTest() {
        timeSheet = createEntity(em);
    }

    @Test
    @Transactional
    void createTimeSheet() throws Exception {
        int databaseSizeBeforeCreate = timeSheetRepository.findAll().size();
        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);
        restTimeSheetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSheetDTO)))
            .andExpect(status().isCreated());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeCreate + 1);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getCheckIn()).isEqualTo(DEFAULT_CHECK_IN);
        assertThat(testTimeSheet.getCheckOut()).isEqualTo(DEFAULT_CHECK_OUT);
        assertThat(testTimeSheet.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTimeSheet.getHasCheckedIn()).isEqualTo(DEFAULT_HAS_CHECKED_IN);
        assertThat(testTimeSheet.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testTimeSheet.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTimeSheet.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTimeSheet.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createTimeSheetWithExistingId() throws Exception {
        // Create the TimeSheet with an existing ID
        timeSheet.setId(1L);
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        int databaseSizeBeforeCreate = timeSheetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeSheetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSheetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTimeSheets() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList
        restTimeSheetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].checkIn").value(hasItem(DEFAULT_CHECK_IN.toString())))
            .andExpect(jsonPath("$.[*].checkOut").value(hasItem(DEFAULT_CHECK_OUT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].hasCheckedIn").value(hasItem(DEFAULT_HAS_CHECKED_IN.booleanValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get the timeSheet
        restTimeSheetMockMvc
            .perform(get(ENTITY_API_URL_ID, timeSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timeSheet.getId().intValue()))
            .andExpect(jsonPath("$.checkIn").value(DEFAULT_CHECK_IN.toString()))
            .andExpect(jsonPath("$.checkOut").value(DEFAULT_CHECK_OUT.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.hasCheckedIn").value(DEFAULT_HAS_CHECKED_IN.booleanValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getTimeSheetsByIdFiltering() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        Long id = timeSheet.getId();

        defaultTimeSheetShouldBeFound("id.equals=" + id);
        defaultTimeSheetShouldNotBeFound("id.notEquals=" + id);

        defaultTimeSheetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTimeSheetShouldNotBeFound("id.greaterThan=" + id);

        defaultTimeSheetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTimeSheetShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByCheckInIsEqualToSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where checkIn equals to DEFAULT_CHECK_IN
        defaultTimeSheetShouldBeFound("checkIn.equals=" + DEFAULT_CHECK_IN);

        // Get all the timeSheetList where checkIn equals to UPDATED_CHECK_IN
        defaultTimeSheetShouldNotBeFound("checkIn.equals=" + UPDATED_CHECK_IN);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByCheckInIsInShouldWork() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where checkIn in DEFAULT_CHECK_IN or UPDATED_CHECK_IN
        defaultTimeSheetShouldBeFound("checkIn.in=" + DEFAULT_CHECK_IN + "," + UPDATED_CHECK_IN);

        // Get all the timeSheetList where checkIn equals to UPDATED_CHECK_IN
        defaultTimeSheetShouldNotBeFound("checkIn.in=" + UPDATED_CHECK_IN);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByCheckInIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where checkIn is not null
        defaultTimeSheetShouldBeFound("checkIn.specified=true");

        // Get all the timeSheetList where checkIn is null
        defaultTimeSheetShouldNotBeFound("checkIn.specified=false");
    }

    @Test
    @Transactional
    void getAllTimeSheetsByCheckOutIsEqualToSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where checkOut equals to DEFAULT_CHECK_OUT
        defaultTimeSheetShouldBeFound("checkOut.equals=" + DEFAULT_CHECK_OUT);

        // Get all the timeSheetList where checkOut equals to UPDATED_CHECK_OUT
        defaultTimeSheetShouldNotBeFound("checkOut.equals=" + UPDATED_CHECK_OUT);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByCheckOutIsInShouldWork() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where checkOut in DEFAULT_CHECK_OUT or UPDATED_CHECK_OUT
        defaultTimeSheetShouldBeFound("checkOut.in=" + DEFAULT_CHECK_OUT + "," + UPDATED_CHECK_OUT);

        // Get all the timeSheetList where checkOut equals to UPDATED_CHECK_OUT
        defaultTimeSheetShouldNotBeFound("checkOut.in=" + UPDATED_CHECK_OUT);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByCheckOutIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where checkOut is not null
        defaultTimeSheetShouldBeFound("checkOut.specified=true");

        // Get all the timeSheetList where checkOut is null
        defaultTimeSheetShouldNotBeFound("checkOut.specified=false");
    }

    @Test
    @Transactional
    void getAllTimeSheetsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where date equals to DEFAULT_DATE
        defaultTimeSheetShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the timeSheetList where date equals to UPDATED_DATE
        defaultTimeSheetShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where date in DEFAULT_DATE or UPDATED_DATE
        defaultTimeSheetShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the timeSheetList where date equals to UPDATED_DATE
        defaultTimeSheetShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where date is not null
        defaultTimeSheetShouldBeFound("date.specified=true");

        // Get all the timeSheetList where date is null
        defaultTimeSheetShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllTimeSheetsByHasCheckedInIsEqualToSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where hasCheckedIn equals to DEFAULT_HAS_CHECKED_IN
        defaultTimeSheetShouldBeFound("hasCheckedIn.equals=" + DEFAULT_HAS_CHECKED_IN);

        // Get all the timeSheetList where hasCheckedIn equals to UPDATED_HAS_CHECKED_IN
        defaultTimeSheetShouldNotBeFound("hasCheckedIn.equals=" + UPDATED_HAS_CHECKED_IN);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByHasCheckedInIsInShouldWork() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where hasCheckedIn in DEFAULT_HAS_CHECKED_IN or UPDATED_HAS_CHECKED_IN
        defaultTimeSheetShouldBeFound("hasCheckedIn.in=" + DEFAULT_HAS_CHECKED_IN + "," + UPDATED_HAS_CHECKED_IN);

        // Get all the timeSheetList where hasCheckedIn equals to UPDATED_HAS_CHECKED_IN
        defaultTimeSheetShouldNotBeFound("hasCheckedIn.in=" + UPDATED_HAS_CHECKED_IN);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByHasCheckedInIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where hasCheckedIn is not null
        defaultTimeSheetShouldBeFound("hasCheckedIn.specified=true");

        // Get all the timeSheetList where hasCheckedIn is null
        defaultTimeSheetShouldNotBeFound("hasCheckedIn.specified=false");
    }

    @Test
    @Transactional
    void getAllTimeSheetsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where companyId equals to DEFAULT_COMPANY_ID
        defaultTimeSheetShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the timeSheetList where companyId equals to UPDATED_COMPANY_ID
        defaultTimeSheetShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultTimeSheetShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the timeSheetList where companyId equals to UPDATED_COMPANY_ID
        defaultTimeSheetShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where companyId is not null
        defaultTimeSheetShouldBeFound("companyId.specified=true");

        // Get all the timeSheetList where companyId is null
        defaultTimeSheetShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllTimeSheetsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultTimeSheetShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the timeSheetList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultTimeSheetShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultTimeSheetShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the timeSheetList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultTimeSheetShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where companyId is less than DEFAULT_COMPANY_ID
        defaultTimeSheetShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the timeSheetList where companyId is less than UPDATED_COMPANY_ID
        defaultTimeSheetShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where companyId is greater than DEFAULT_COMPANY_ID
        defaultTimeSheetShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the timeSheetList where companyId is greater than SMALLER_COMPANY_ID
        defaultTimeSheetShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where status equals to DEFAULT_STATUS
        defaultTimeSheetShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the timeSheetList where status equals to UPDATED_STATUS
        defaultTimeSheetShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTimeSheetShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the timeSheetList where status equals to UPDATED_STATUS
        defaultTimeSheetShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where status is not null
        defaultTimeSheetShouldBeFound("status.specified=true");

        // Get all the timeSheetList where status is null
        defaultTimeSheetShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTimeSheetsByStatusContainsSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where status contains DEFAULT_STATUS
        defaultTimeSheetShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the timeSheetList where status contains UPDATED_STATUS
        defaultTimeSheetShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where status does not contain DEFAULT_STATUS
        defaultTimeSheetShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the timeSheetList where status does not contain UPDATED_STATUS
        defaultTimeSheetShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTimeSheetShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the timeSheetList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTimeSheetShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTimeSheetShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the timeSheetList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTimeSheetShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where lastModified is not null
        defaultTimeSheetShouldBeFound("lastModified.specified=true");

        // Get all the timeSheetList where lastModified is null
        defaultTimeSheetShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTimeSheetsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTimeSheetShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the timeSheetList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTimeSheetShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTimeSheetShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the timeSheetList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTimeSheetShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where lastModifiedBy is not null
        defaultTimeSheetShouldBeFound("lastModifiedBy.specified=true");

        // Get all the timeSheetList where lastModifiedBy is null
        defaultTimeSheetShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTimeSheetsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTimeSheetShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the timeSheetList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTimeSheetShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTimeSheetShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the timeSheetList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTimeSheetShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTimeSheetsByAttendanceIsEqualToSomething() throws Exception {
        Attendance attendance;
        if (TestUtil.findAll(em, Attendance.class).isEmpty()) {
            timeSheetRepository.saveAndFlush(timeSheet);
            attendance = AttendanceResourceIT.createEntity(em);
        } else {
            attendance = TestUtil.findAll(em, Attendance.class).get(0);
        }
        em.persist(attendance);
        em.flush();
        timeSheet.setAttendance(attendance);
        timeSheetRepository.saveAndFlush(timeSheet);
        Long attendanceId = attendance.getId();

        // Get all the timeSheetList where attendance equals to attendanceId
        defaultTimeSheetShouldBeFound("attendanceId.equals=" + attendanceId);

        // Get all the timeSheetList where attendance equals to (attendanceId + 1)
        defaultTimeSheetShouldNotBeFound("attendanceId.equals=" + (attendanceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTimeSheetShouldBeFound(String filter) throws Exception {
        restTimeSheetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].checkIn").value(hasItem(DEFAULT_CHECK_IN.toString())))
            .andExpect(jsonPath("$.[*].checkOut").value(hasItem(DEFAULT_CHECK_OUT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].hasCheckedIn").value(hasItem(DEFAULT_HAS_CHECKED_IN.booleanValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restTimeSheetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTimeSheetShouldNotBeFound(String filter) throws Exception {
        restTimeSheetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTimeSheetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTimeSheet() throws Exception {
        // Get the timeSheet
        restTimeSheetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();

        // Update the timeSheet
        TimeSheet updatedTimeSheet = timeSheetRepository.findById(timeSheet.getId()).get();
        // Disconnect from session so that the updates on updatedTimeSheet are not directly saved in db
        em.detach(updatedTimeSheet);
        updatedTimeSheet
            .checkIn(UPDATED_CHECK_IN)
            .checkOut(UPDATED_CHECK_OUT)
            .date(UPDATED_DATE)
            .hasCheckedIn(UPDATED_HAS_CHECKED_IN)
            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(updatedTimeSheet);

        restTimeSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timeSheetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isOk());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getCheckIn()).isEqualTo(UPDATED_CHECK_IN);
        assertThat(testTimeSheet.getCheckOut()).isEqualTo(UPDATED_CHECK_OUT);
        assertThat(testTimeSheet.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTimeSheet.getHasCheckedIn()).isEqualTo(UPDATED_HAS_CHECKED_IN);
        assertThat(testTimeSheet.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTimeSheet.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTimeSheet.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTimeSheet.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timeSheetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSheetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTimeSheetWithPatch() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();

        // Update the timeSheet using partial update
        TimeSheet partialUpdatedTimeSheet = new TimeSheet();
        partialUpdatedTimeSheet.setId(timeSheet.getId());

        partialUpdatedTimeSheet
            .checkIn(UPDATED_CHECK_IN)
            .checkOut(UPDATED_CHECK_OUT)
            .hasCheckedIn(UPDATED_HAS_CHECKED_IN)
            .lastModified(UPDATED_LAST_MODIFIED);

        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimeSheet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimeSheet))
            )
            .andExpect(status().isOk());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getCheckIn()).isEqualTo(UPDATED_CHECK_IN);
        assertThat(testTimeSheet.getCheckOut()).isEqualTo(UPDATED_CHECK_OUT);
        assertThat(testTimeSheet.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTimeSheet.getHasCheckedIn()).isEqualTo(UPDATED_HAS_CHECKED_IN);
        assertThat(testTimeSheet.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testTimeSheet.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTimeSheet.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTimeSheet.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateTimeSheetWithPatch() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();

        // Update the timeSheet using partial update
        TimeSheet partialUpdatedTimeSheet = new TimeSheet();
        partialUpdatedTimeSheet.setId(timeSheet.getId());

        partialUpdatedTimeSheet
            .checkIn(UPDATED_CHECK_IN)
            .checkOut(UPDATED_CHECK_OUT)
            .date(UPDATED_DATE)
            .hasCheckedIn(UPDATED_HAS_CHECKED_IN)
            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimeSheet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimeSheet))
            )
            .andExpect(status().isOk());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getCheckIn()).isEqualTo(UPDATED_CHECK_IN);
        assertThat(testTimeSheet.getCheckOut()).isEqualTo(UPDATED_CHECK_OUT);
        assertThat(testTimeSheet.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTimeSheet.getHasCheckedIn()).isEqualTo(UPDATED_HAS_CHECKED_IN);
        assertThat(testTimeSheet.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTimeSheet.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTimeSheet.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTimeSheet.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, timeSheetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeDelete = timeSheetRepository.findAll().size();

        // Delete the timeSheet
        restTimeSheetMockMvc
            .perform(delete(ENTITY_API_URL_ID, timeSheet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
