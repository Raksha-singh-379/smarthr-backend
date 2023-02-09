package com.techvg.smtrthr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.smtrthr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkinDaysSettingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkinDaysSettingDTO.class);
        WorkinDaysSettingDTO workinDaysSettingDTO1 = new WorkinDaysSettingDTO();
        workinDaysSettingDTO1.setId(1L);
        WorkinDaysSettingDTO workinDaysSettingDTO2 = new WorkinDaysSettingDTO();
        assertThat(workinDaysSettingDTO1).isNotEqualTo(workinDaysSettingDTO2);
        workinDaysSettingDTO2.setId(workinDaysSettingDTO1.getId());
        assertThat(workinDaysSettingDTO1).isEqualTo(workinDaysSettingDTO2);
        workinDaysSettingDTO2.setId(2L);
        assertThat(workinDaysSettingDTO1).isNotEqualTo(workinDaysSettingDTO2);
        workinDaysSettingDTO1.setId(null);
        assertThat(workinDaysSettingDTO1).isNotEqualTo(workinDaysSettingDTO2);
    }
}
