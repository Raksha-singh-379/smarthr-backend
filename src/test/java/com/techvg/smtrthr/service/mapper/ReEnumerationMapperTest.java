package com.techvg.smtrthr.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReEnumerationMapperTest {

    private ReEnumerationMapper reEnumerationMapper;

    @BeforeEach
    public void setUp() {
        reEnumerationMapper = new ReEnumerationMapperImpl();
    }
}
