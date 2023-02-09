package com.techvg.smtrthr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.smtrthr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkinDaysSettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkinDaysSetting.class);
        WorkinDaysSetting workinDaysSetting1 = new WorkinDaysSetting();
        workinDaysSetting1.setId(1L);
        WorkinDaysSetting workinDaysSetting2 = new WorkinDaysSetting();
        workinDaysSetting2.setId(workinDaysSetting1.getId());
        assertThat(workinDaysSetting1).isEqualTo(workinDaysSetting2);
        workinDaysSetting2.setId(2L);
        assertThat(workinDaysSetting1).isNotEqualTo(workinDaysSetting2);
        workinDaysSetting1.setId(null);
        assertThat(workinDaysSetting1).isNotEqualTo(workinDaysSetting2);
    }
}
