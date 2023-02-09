package com.techvg.smtrthr.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApproverMapperTest {

    private ApproverMapper approverMapper;

    @BeforeEach
    public void setUp() {
        approverMapper = new ApproverMapperImpl();
    }
}
