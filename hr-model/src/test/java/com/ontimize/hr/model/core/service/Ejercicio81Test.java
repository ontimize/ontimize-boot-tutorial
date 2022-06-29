package com.ontimize.hr.model.core.service;

import com.ontimize.hr.model.core.dao.CandidateDao;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Ejercicio81Test {

    CandidateService candidateService;

    @BeforeEach
    void setUp() {
        this.candidateService = new CandidateService();
    }

    @Test
    @DisplayName("Delete data not directly related when inserting a candidate")
    void when_candidateHasNonRelatedData_return_onlyRelatedData() {
        Map<String, Object> attrMap = new HashMap<>() {{
            put("PHONE", "555-444-8888");
            put("BIRTHDAY", 788224700000L);
            put("SURNAME", "Wilson");
            put("EMAIL", "wwiilsoon@example.org");
            put("SPECIALTIES", "C#");
            put("NAME", "William");
            put("DNI", "88643946Z");
            put("EDUCATION", 1);
            put("EXPERIENCE_LEVEL", 1);
            put("ORIGIN", "Event");
            put("PROFILE", "Administrator");
            put("STATUS", "1");
        }};

        String[] attrToExclude = new String[]{CandidateDao.ATTR_EDUCATION, CandidateDao.ATTR_EXPERIENCE_LEVEL,
                CandidateDao.ATTR_ORIGIN, CandidateDao.ATTR_PROFILE, CandidateDao.ATTR_STATUS};

        Map<String, Object> nonRelatedData = this.candidateService.removeNonRelatedData(attrMap, attrToExclude);

        assertTrue(!(attrMap.get(CandidateDao.ATTR_EDUCATION) instanceof String));
        assertTrue(!(attrMap.get(CandidateDao.ATTR_EXPERIENCE_LEVEL) instanceof String));
        assertTrue(!(attrMap.get(CandidateDao.ATTR_ORIGIN) instanceof String));
        assertTrue(!(attrMap.get(CandidateDao.ATTR_PROFILE) instanceof String));
        assertTrue(!(attrMap.get(CandidateDao.ATTR_STATUS) instanceof String));
    }

}