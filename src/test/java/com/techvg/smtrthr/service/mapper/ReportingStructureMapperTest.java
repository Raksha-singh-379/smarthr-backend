package com.techvg.smtrthr.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportingStructureMapperTest {

    private ReportingStructureMapper reportingStructureMapper;

    @BeforeEach
    public void setUp() {
        reportingStructureMapper = new ReportingStructureMapperImpl();
    }
}
