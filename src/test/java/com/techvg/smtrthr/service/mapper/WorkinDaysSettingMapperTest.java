package com.techvg.smtrthr.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkinDaysSettingMapperTest {

    private WorkinDaysSettingMapper workinDaysSettingMapper;

    @BeforeEach
    public void setUp() {
        workinDaysSettingMapper = new WorkinDaysSettingMapperImpl();
    }
}
