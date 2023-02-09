package com.techvg.smtrthr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.smtrthr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReEnumerationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReEnumerationDTO.class);
        ReEnumerationDTO reEnumerationDTO1 = new ReEnumerationDTO();
        reEnumerationDTO1.setId(1L);
        ReEnumerationDTO reEnumerationDTO2 = new ReEnumerationDTO();
        assertThat(reEnumerationDTO1).isNotEqualTo(reEnumerationDTO2);
        reEnumerationDTO2.setId(reEnumerationDTO1.getId());
        assertThat(reEnumerationDTO1).isEqualTo(reEnumerationDTO2);
        reEnumerationDTO2.setId(2L);
        assertThat(reEnumerationDTO1).isNotEqualTo(reEnumerationDTO2);
        reEnumerationDTO1.setId(null);
        assertThat(reEnumerationDTO1).isNotEqualTo(reEnumerationDTO2);
    }
}
