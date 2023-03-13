package com.techvg.smtrthr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.smtrthr.IntegrationTest;
import com.techvg.smtrthr.domain.Attendance;
import com.techvg.smtrthr.repository.AttendanceRepository;
import com.techvg.smtrthr.service.criteria.AttendanceCriteria;
import com.techvg.smtrthr.service.dto.AttendanceDTO;
import com.techvg.smtrthr.service.mapper.AttendanceMapper;
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
 * Integration tests for the {@link AttendanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttendanceResourceIT {

    private static final String DEFAULT_DEVICE_INFO = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_INFO = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DAY = "AAAAAAAAAA";
    private static final String UPDATED_DAY = "BBBBBBBBBB";

    private static final String DEFAULT_HOURS = "AAAAAAAAAA";
    private static final String UPDATED_HOURS = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/attendances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendanceMockMvc;

    private Attendance attendance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendance createEntity(EntityManager em) {
        Attendance attendance = new Attendance()
            .deviceInfo(DEFAULT_DEVICE_INFO)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .date(DEFAULT_DATE)
            .day(DEFAULT_DAY)
            .hours(DEFAULT_HOURS)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return attendance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendance createUpdatedEntity(EntityManager em) {
        Attendance attendance = new Attendance()
            .deviceInfo(UPDATED_DEVICE_INFO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .date(UPDATED_DATE)
            .day(UPDATED_DAY)
            .hours(UPDATED_HOURS)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return attendance;
    }

    @BeforeEach
    public void initTest() {
        attendance = createEntity(em);
    }

    @Test
    @Transactional
    void createAttendance() throws Exception {
        int databaseSizeBeforeCreate = attendanceRepository.findAll().size();
        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);
        restAttendanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeCreate + 1);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getDeviceInfo()).isEqualTo(DEFAULT_DEVICE_INFO);
        assertThat(testAttendance.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testAttendance.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testAttendance.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAttendance.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testAttendance.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testAttendance.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testAttendance.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testAttendance.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAttendance.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAttendance.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createAttendanceWithExistingId() throws Exception {
        // Create the Attendance with an existing ID
        attendance.setId(1L);
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        int databaseSizeBeforeCreate = attendanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttendances() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList
        restAttendanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceInfo").value(hasItem(DEFAULT_DEVICE_INFO)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get the attendance
        restAttendanceMockMvc
            .perform(get(ENTITY_API_URL_ID, attendance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendance.getId().intValue()))
            .andExpect(jsonPath("$.deviceInfo").value(DEFAULT_DEVICE_INFO))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getAttendancesByIdFiltering() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        Long id = attendance.getId();

        defaultAttendanceShouldBeFound("id.equals=" + id);
        defaultAttendanceShouldNotBeFound("id.notEquals=" + id);

        defaultAttendanceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAttendanceShouldNotBeFound("id.greaterThan=" + id);

        defaultAttendanceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAttendanceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAttendancesByDeviceInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where deviceInfo equals to DEFAULT_DEVICE_INFO
        defaultAttendanceShouldBeFound("deviceInfo.equals=" + DEFAULT_DEVICE_INFO);

        // Get all the attendanceList where deviceInfo equals to UPDATED_DEVICE_INFO
        defaultAttendanceShouldNotBeFound("deviceInfo.equals=" + UPDATED_DEVICE_INFO);
    }

    @Test
    @Transactional
    void getAllAttendancesByDeviceInfoIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where deviceInfo in DEFAULT_DEVICE_INFO or UPDATED_DEVICE_INFO
        defaultAttendanceShouldBeFound("deviceInfo.in=" + DEFAULT_DEVICE_INFO + "," + UPDATED_DEVICE_INFO);

        // Get all the attendanceList where deviceInfo equals to UPDATED_DEVICE_INFO
        defaultAttendanceShouldNotBeFound("deviceInfo.in=" + UPDATED_DEVICE_INFO);
    }

    @Test
    @Transactional
    void getAllAttendancesByDeviceInfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where deviceInfo is not null
        defaultAttendanceShouldBeFound("deviceInfo.specified=true");

        // Get all the attendanceList where deviceInfo is null
        defaultAttendanceShouldNotBeFound("deviceInfo.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendancesByDeviceInfoContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where deviceInfo contains DEFAULT_DEVICE_INFO
        defaultAttendanceShouldBeFound("deviceInfo.contains=" + DEFAULT_DEVICE_INFO);

        // Get all the attendanceList where deviceInfo contains UPDATED_DEVICE_INFO
        defaultAttendanceShouldNotBeFound("deviceInfo.contains=" + UPDATED_DEVICE_INFO);
    }

    @Test
    @Transactional
    void getAllAttendancesByDeviceInfoNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where deviceInfo does not contain DEFAULT_DEVICE_INFO
        defaultAttendanceShouldNotBeFound("deviceInfo.doesNotContain=" + DEFAULT_DEVICE_INFO);

        // Get all the attendanceList where deviceInfo does not contain UPDATED_DEVICE_INFO
        defaultAttendanceShouldBeFound("deviceInfo.doesNotContain=" + UPDATED_DEVICE_INFO);
    }

    @Test
    @Transactional
    void getAllAttendancesByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where latitude equals to DEFAULT_LATITUDE
        defaultAttendanceShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the attendanceList where latitude equals to UPDATED_LATITUDE
        defaultAttendanceShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllAttendancesByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultAttendanceShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the attendanceList where latitude equals to UPDATED_LATITUDE
        defaultAttendanceShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllAttendancesByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where latitude is not null
        defaultAttendanceShouldBeFound("latitude.specified=true");

        // Get all the attendanceList where latitude is null
        defaultAttendanceShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendancesByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultAttendanceShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the attendanceList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultAttendanceShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllAttendancesByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultAttendanceShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the attendanceList where latitude is less than or equal to SMALLER_LATITUDE
        defaultAttendanceShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllAttendancesByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where latitude is less than DEFAULT_LATITUDE
        defaultAttendanceShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the attendanceList where latitude is less than UPDATED_LATITUDE
        defaultAttendanceShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllAttendancesByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where latitude is greater than DEFAULT_LATITUDE
        defaultAttendanceShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the attendanceList where latitude is greater than SMALLER_LATITUDE
        defaultAttendanceShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllAttendancesByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where longitude equals to DEFAULT_LONGITUDE
        defaultAttendanceShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the attendanceList where longitude equals to UPDATED_LONGITUDE
        defaultAttendanceShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAttendancesByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultAttendanceShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the attendanceList where longitude equals to UPDATED_LONGITUDE
        defaultAttendanceShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAttendancesByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where longitude is not null
        defaultAttendanceShouldBeFound("longitude.specified=true");

        // Get all the attendanceList where longitude is null
        defaultAttendanceShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendancesByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultAttendanceShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the attendanceList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultAttendanceShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAttendancesByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultAttendanceShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the attendanceList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultAttendanceShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAttendancesByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where longitude is less than DEFAULT_LONGITUDE
        defaultAttendanceShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the attendanceList where longitude is less than UPDATED_LONGITUDE
        defaultAttendanceShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAttendancesByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where longitude is greater than DEFAULT_LONGITUDE
        defaultAttendanceShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the attendanceList where longitude is greater than SMALLER_LONGITUDE
        defaultAttendanceShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAttendancesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where date equals to DEFAULT_DATE
        defaultAttendanceShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the attendanceList where date equals to UPDATED_DATE
        defaultAttendanceShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAttendancesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where date in DEFAULT_DATE or UPDATED_DATE
        defaultAttendanceShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the attendanceList where date equals to UPDATED_DATE
        defaultAttendanceShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAttendancesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where date is not null
        defaultAttendanceShouldBeFound("date.specified=true");

        // Get all the attendanceList where date is null
        defaultAttendanceShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendancesByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where day equals to DEFAULT_DAY
        defaultAttendanceShouldBeFound("day.equals=" + DEFAULT_DAY);

        // Get all the attendanceList where day equals to UPDATED_DAY
        defaultAttendanceShouldNotBeFound("day.equals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllAttendancesByDayIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where day in DEFAULT_DAY or UPDATED_DAY
        defaultAttendanceShouldBeFound("day.in=" + DEFAULT_DAY + "," + UPDATED_DAY);

        // Get all the attendanceList where day equals to UPDATED_DAY
        defaultAttendanceShouldNotBeFound("day.in=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllAttendancesByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where day is not null
        defaultAttendanceShouldBeFound("day.specified=true");

        // Get all the attendanceList where day is null
        defaultAttendanceShouldNotBeFound("day.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendancesByDayContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where day contains DEFAULT_DAY
        defaultAttendanceShouldBeFound("day.contains=" + DEFAULT_DAY);

        // Get all the attendanceList where day contains UPDATED_DAY
        defaultAttendanceShouldNotBeFound("day.contains=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllAttendancesByDayNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where day does not contain DEFAULT_DAY
        defaultAttendanceShouldNotBeFound("day.doesNotContain=" + DEFAULT_DAY);

        // Get all the attendanceList where day does not contain UPDATED_DAY
        defaultAttendanceShouldBeFound("day.doesNotContain=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllAttendancesByHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where hours equals to DEFAULT_HOURS
        defaultAttendanceShouldBeFound("hours.equals=" + DEFAULT_HOURS);

        // Get all the attendanceList where hours equals to UPDATED_HOURS
        defaultAttendanceShouldNotBeFound("hours.equals=" + UPDATED_HOURS);
    }

    @Test
    @Transactional
    void getAllAttendancesByHoursIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where hours in DEFAULT_HOURS or UPDATED_HOURS
        defaultAttendanceShouldBeFound("hours.in=" + DEFAULT_HOURS + "," + UPDATED_HOURS);

        // Get all the attendanceList where hours equals to UPDATED_HOURS
        defaultAttendanceShouldNotBeFound("hours.in=" + UPDATED_HOURS);
    }

    @Test
    @Transactional
    void getAllAttendancesByHoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where hours is not null
        defaultAttendanceShouldBeFound("hours.specified=true");

        // Get all the attendanceList where hours is null
        defaultAttendanceShouldNotBeFound("hours.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendancesByHoursContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where hours contains DEFAULT_HOURS
        defaultAttendanceShouldBeFound("hours.contains=" + DEFAULT_HOURS);

        // Get all the attendanceList where hours contains UPDATED_HOURS
        defaultAttendanceShouldNotBeFound("hours.contains=" + UPDATED_HOURS);
    }

    @Test
    @Transactional
    void getAllAttendancesByHoursNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where hours does not contain DEFAULT_HOURS
        defaultAttendanceShouldNotBeFound("hours.doesNotContain=" + DEFAULT_HOURS);

        // Get all the attendanceList where hours does not contain UPDATED_HOURS
        defaultAttendanceShouldBeFound("hours.doesNotContain=" + UPDATED_HOURS);
    }

    @Test
    @Transactional
    void getAllAttendancesByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultAttendanceShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the attendanceList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultAttendanceShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAttendancesByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultAttendanceShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the attendanceList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultAttendanceShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAttendancesByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeId is not null
        defaultAttendanceShouldBeFound("employeeId.specified=true");

        // Get all the attendanceList where employeeId is null
        defaultAttendanceShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendancesByEmployeeIdContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeId contains DEFAULT_EMPLOYEE_ID
        defaultAttendanceShouldBeFound("employeeId.contains=" + DEFAULT_EMPLOYEE_ID);

        // Get all the attendanceList where employeeId contains UPDATED_EMPLOYEE_ID
        defaultAttendanceShouldNotBeFound("employeeId.contains=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAttendancesByEmployeeIdNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeId does not contain DEFAULT_EMPLOYEE_ID
        defaultAttendanceShouldNotBeFound("employeeId.doesNotContain=" + DEFAULT_EMPLOYEE_ID);

        // Get all the attendanceList where employeeId does not contain UPDATED_EMPLOYEE_ID
        defaultAttendanceShouldBeFound("employeeId.doesNotContain=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAttendancesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where companyId equals to DEFAULT_COMPANY_ID
        defaultAttendanceShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the attendanceList where companyId equals to UPDATED_COMPANY_ID
        defaultAttendanceShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAttendancesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultAttendanceShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the attendanceList where companyId equals to UPDATED_COMPANY_ID
        defaultAttendanceShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAttendancesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where companyId is not null
        defaultAttendanceShouldBeFound("companyId.specified=true");

        // Get all the attendanceList where companyId is null
        defaultAttendanceShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendancesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultAttendanceShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the attendanceList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultAttendanceShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAttendancesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultAttendanceShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the attendanceList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultAttendanceShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAttendancesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where companyId is less than DEFAULT_COMPANY_ID
        defaultAttendanceShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the attendanceList where companyId is less than UPDATED_COMPANY_ID
        defaultAttendanceShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAttendancesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where companyId is greater than DEFAULT_COMPANY_ID
        defaultAttendanceShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the attendanceList where companyId is greater than SMALLER_COMPANY_ID
        defaultAttendanceShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAttendancesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where status equals to DEFAULT_STATUS
        defaultAttendanceShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the attendanceList where status equals to UPDATED_STATUS
        defaultAttendanceShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAttendancesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAttendanceShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the attendanceList where status equals to UPDATED_STATUS
        defaultAttendanceShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAttendancesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where status is not null
        defaultAttendanceShouldBeFound("status.specified=true");

        // Get all the attendanceList where status is null
        defaultAttendanceShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendancesByStatusContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where status contains DEFAULT_STATUS
        defaultAttendanceShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the attendanceList where status contains UPDATED_STATUS
        defaultAttendanceShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAttendancesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where status does not contain DEFAULT_STATUS
        defaultAttendanceShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the attendanceList where status does not contain UPDATED_STATUS
        defaultAttendanceShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAttendancesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAttendanceShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the attendanceList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAttendanceShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAttendancesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAttendanceShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the attendanceList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAttendanceShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAttendancesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where lastModified is not null
        defaultAttendanceShouldBeFound("lastModified.specified=true");

        // Get all the attendanceList where lastModified is null
        defaultAttendanceShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendancesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAttendanceShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the attendanceList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAttendanceShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAttendancesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAttendanceShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the attendanceList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAttendanceShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAttendancesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where lastModifiedBy is not null
        defaultAttendanceShouldBeFound("lastModifiedBy.specified=true");

        // Get all the attendanceList where lastModifiedBy is null
        defaultAttendanceShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendancesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAttendanceShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the attendanceList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAttendanceShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAttendancesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAttendanceShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the attendanceList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAttendanceShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttendanceShouldBeFound(String filter) throws Exception {
        restAttendanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceInfo").value(hasItem(DEFAULT_DEVICE_INFO)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restAttendanceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttendanceShouldNotBeFound(String filter) throws Exception {
        restAttendanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttendanceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAttendance() throws Exception {
        // Get the attendance
        restAttendanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // Update the attendance
        Attendance updatedAttendance = attendanceRepository.findById(attendance.getId()).get();
        // Disconnect from session so that the updates on updatedAttendance are not directly saved in db
        em.detach(updatedAttendance);
        updatedAttendance
            .deviceInfo(UPDATED_DEVICE_INFO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .date(UPDATED_DATE)
            .day(UPDATED_DAY)
            .hours(UPDATED_HOURS)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(updatedAttendance);

        restAttendanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attendanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendanceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getDeviceInfo()).isEqualTo(UPDATED_DEVICE_INFO);
        assertThat(testAttendance.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAttendance.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAttendance.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAttendance.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testAttendance.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testAttendance.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testAttendance.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testAttendance.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAttendance.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAttendance.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();
        attendance.setId(count.incrementAndGet());

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attendanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();
        attendance.setId(count.incrementAndGet());

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();
        attendance.setId(count.incrementAndGet());

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttendanceWithPatch() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // Update the attendance using partial update
        Attendance partialUpdatedAttendance = new Attendance();
        partialUpdatedAttendance.setId(attendance.getId());

        partialUpdatedAttendance
            .longitude(UPDATED_LONGITUDE)
            .date(UPDATED_DATE)
            .hours(UPDATED_HOURS)
            .companyId(UPDATED_COMPANY_ID)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttendance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttendance))
            )
            .andExpect(status().isOk());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getDeviceInfo()).isEqualTo(DEFAULT_DEVICE_INFO);
        assertThat(testAttendance.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testAttendance.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAttendance.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAttendance.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testAttendance.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testAttendance.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testAttendance.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testAttendance.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAttendance.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAttendance.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateAttendanceWithPatch() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // Update the attendance using partial update
        Attendance partialUpdatedAttendance = new Attendance();
        partialUpdatedAttendance.setId(attendance.getId());

        partialUpdatedAttendance
            .deviceInfo(UPDATED_DEVICE_INFO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .date(UPDATED_DATE)
            .day(UPDATED_DAY)
            .hours(UPDATED_HOURS)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttendance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttendance))
            )
            .andExpect(status().isOk());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getDeviceInfo()).isEqualTo(UPDATED_DEVICE_INFO);
        assertThat(testAttendance.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAttendance.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAttendance.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAttendance.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testAttendance.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testAttendance.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testAttendance.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testAttendance.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAttendance.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAttendance.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();
        attendance.setId(count.incrementAndGet());

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attendanceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attendanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();
        attendance.setId(count.incrementAndGet());

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attendanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();
        attendance.setId(count.incrementAndGet());

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(attendanceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        int databaseSizeBeforeDelete = attendanceRepository.findAll().size();

        // Delete the attendance
        restAttendanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, attendance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
