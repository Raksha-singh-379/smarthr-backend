package com.techvg.smtrthr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.smtrthr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReportingStructureDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportingStructureDTO.class);
        ReportingStructureDTO reportingStructureDTO1 = new ReportingStructureDTO();
        reportingStructureDTO1.setId(1L);
        ReportingStructureDTO reportingStructureDTO2 = new ReportingStructureDTO();
        assertThat(reportingStructureDTO1).isNotEqualTo(reportingStructureDTO2);
        reportingStructureDTO2.setId(reportingStructureDTO1.getId());
        assertThat(reportingStructureDTO1).isEqualTo(reportingStructureDTO2);
        reportingStructureDTO2.setId(2L);
        assertThat(reportingStructureDTO1).isNotEqualTo(reportingStructureDTO2);
        reportingStructureDTO1.setId(null);
        assertThat(reportingStructureDTO1).isNotEqualTo(reportingStructureDTO2);
    }
}
