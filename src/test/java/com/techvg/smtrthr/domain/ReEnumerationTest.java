package com.techvg.smtrthr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.smtrthr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReEnumerationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReEnumeration.class);
        ReEnumeration reEnumeration1 = new ReEnumeration();
        reEnumeration1.setId(1L);
        ReEnumeration reEnumeration2 = new ReEnumeration();
        reEnumeration2.setId(reEnumeration1.getId());
        assertThat(reEnumeration1).isEqualTo(reEnumeration2);
        reEnumeration2.setId(2L);
        assertThat(reEnumeration1).isNotEqualTo(reEnumeration2);
        reEnumeration1.setId(null);
        assertThat(reEnumeration1).isNotEqualTo(reEnumeration2);
    }
}
