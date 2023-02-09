package com.techvg.smtrthr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.smtrthr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReportingStructureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportingStructure.class);
        ReportingStructure reportingStructure1 = new ReportingStructure();
        reportingStructure1.setId(1L);
        ReportingStructure reportingStructure2 = new ReportingStructure();
        reportingStructure2.setId(reportingStructure1.getId());
        assertThat(reportingStructure1).isEqualTo(reportingStructure2);
        reportingStructure2.setId(2L);
        assertThat(reportingStructure1).isNotEqualTo(reportingStructure2);
        reportingStructure1.setId(null);
        assertThat(reportingStructure1).isNotEqualTo(reportingStructure2);
    }
}
