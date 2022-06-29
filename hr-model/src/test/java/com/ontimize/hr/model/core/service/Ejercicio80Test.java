package com.ontimize.hr.model.core.service;

import com.ontimize.hr.model.core.dao.EducationDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.sql.Types;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;

@ExtendWith(MockitoExtension.class)
class Ejercicio80Test {
    @Mock
    DefaultOntimizeDaoHelper daoHelper;

    @InjectMocks
    MasterService service;


    @Nested
    @DisplayName("Test for Education queries")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class EducationQuery {

        @Test
        @DisplayName("Obtain all data from EDUCATION table")
        void when_queryOnlyWithAllColumns_return_allEducationData() {
            doReturn(getAllEducationData()).when(daoHelper).query(any(), anyMap(), anyList());
            EntityResult entityResult = service.educationQuery(new HashMap<>(), new ArrayList<>());
            assertEquals(EntityResult.OPERATION_SUCCESSFUL, entityResult.getCode());
            assertEquals(3, entityResult.calculateRecordNumber());
        }

        @Test
        @DisplayName("Obtain all data columns from EDUCATION table when ID is -> 2")
        void when_queryAllColumns_return_specificData() {
            HashMap<String, Object> keyMap = new HashMap<>() {{
                put("ID", 2);
            }};
            List<String> attrList = Arrays.asList("ID", "DESCRIPTION");
            doReturn(getSpecificEducationData(keyMap, attrList)).when(daoHelper).query(any(), anyMap(), anyList());
            EntityResult entityResult = service.educationQuery(new HashMap<>(), new ArrayList<>());
            assertEquals(EntityResult.OPERATION_SUCCESSFUL, entityResult.getCode());
            assertEquals(1, entityResult.calculateRecordNumber());
            assertEquals(2, entityResult.getRecordValues(0).get(EducationDao.ATTR_ID));
        }

        @Test
        @DisplayName("Obtain all data columns from EDUCATION table when ID not exist")
        void when_queryAllColumnsNotExisting_return_empty() {
            HashMap<String, Object> keyMap = new HashMap<>() {{
                put("ID", 5);
            }};
            List<String> attrList = Arrays.asList("ID", "DESCRIPTION");
            when(daoHelper.query(any(), anyMap(), anyList())).thenReturn(getSpecificEducationData(keyMap, attrList));
            EntityResult entityResult = service.educationQuery(new HashMap<>(), new ArrayList<>());
            assertEquals(EntityResult.OPERATION_SUCCESSFUL, entityResult.getCode());
            assertEquals(0, entityResult.calculateRecordNumber());
        }

        @ParameterizedTest(name = "Obtain data with ID -> {0}")
        @MethodSource("randomIDGenerator")
        @DisplayName("Obtain all data columns from EDUCATION table when ID is random")
        void when_queryAllColumnsWithRandomValue_return_specificData(int random) {
            HashMap<String, Object> keyMap = new HashMap<>() {{
                put("ID", random);
            }};
            List<String> attrList = Arrays.asList("ID", "DESCRIPTION");
            when(daoHelper.query(any(), anyMap(), anyList())).thenReturn(getSpecificEducationData(keyMap, attrList));
            EntityResult entityResult = service.educationQuery(new HashMap<>(), new ArrayList<>());
            assertEquals(EntityResult.OPERATION_SUCCESSFUL, entityResult.getCode());
            assertEquals(1, entityResult.calculateRecordNumber());
            assertEquals(random, entityResult.getRecordValues(0).get(EducationDao.ATTR_ID));
        }

        public EntityResult getAllEducationData() {
            List<String> columnList = Arrays.asList("ID", "DESCRIPTION");
            EntityResult er = new EntityResultMapImpl(columnList);
            er.addRecord(new HashMap<String, Object>() {{
                put("ID", 0);
                put("DESCRIPTION", "Degree in Computer Science");
            }});
            er.addRecord(new HashMap<String, Object>() {{
                put("ID", 1);
                put("DESCRIPTION", "Degree in Information Science");
            }});
            er.addRecord(new HashMap<String, Object>() {{
                put("ID", 2);
                put("DESCRIPTION", "Degree in Cybersecurity");
            }});
            er.setCode(EntityResult.OPERATION_SUCCESSFUL);
            er.setColumnSQLTypes(new HashMap<String, Number>() {{
                put("ID", Types.INTEGER);
                put("DESCRIPTION", Types.VARCHAR);
            }});
            return er;
        }

        public EntityResult getSpecificEducationData(Map<String, Object> keyValues, List<String> attributes) {
            EntityResult allData = this.getAllEducationData();
            int recordIndex = allData.getRecordIndex(keyValues);
            HashMap<String, Object> recordValues = (HashMap) allData.getRecordValues(recordIndex);
            List<String> columnList = Arrays.asList("ID", "DESCRIPTION");
            EntityResult er = new EntityResultMapImpl(columnList);
            if (recordValues != null) {
                er.addRecord(recordValues);
            }
            er.setCode(EntityResult.OPERATION_SUCCESSFUL);
            er.setColumnSQLTypes(new HashMap<String, Number>() {{
                put("ID", Types.INTEGER);
                put("DESCRIPTION", Types.VARCHAR);
            }});
            return er;
        }

        List<Integer> randomIDGenerator() {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                list.add(ThreadLocalRandom.current().nextInt(0, 3));
            }
            return list;
        }
    }
}