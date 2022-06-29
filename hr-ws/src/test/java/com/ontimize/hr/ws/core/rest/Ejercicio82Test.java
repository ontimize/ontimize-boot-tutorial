package com.ontimize.hr.ws.core.rest;

import com.ontimize.jee.common.db.SQLStatementBuilder.BasicExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Calendar;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class Ejercicio82Test {

    OfferRestController controller;

    @Nested
    public class BasicExpressionTest{

        @BeforeEach
        void setUp() {
            controller = new OfferRestController();
        }

        @Test
        @DisplayName("Return a BasicExpression of the current year")
        void when_onlyUseParam_return_basicExpressionOfCurrentYear() {
            BasicExpression date = controller.searchBetween("DATE");
            HashMap<String, Object> kv = new HashMap<>();
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            kv.put("DATE", cal.getTime());
            assertTrue(date.evaluate(kv));
            cal.set(year-1, 11,31,23, 59,59);
            kv.put("DATE", cal.getTime());
            assertFalse(date.evaluate(kv));
            cal.add(Calendar.SECOND, 1);
            kv.put("DATE", cal.getTime());
            assertTrue(date.evaluate(kv));
            cal.set(year, 11,31,23, 59,59);
            kv.put("DATE", cal.getTime());
            assertTrue(date.evaluate(kv));
            cal.add(Calendar.SECOND, 1);
            kv.put("DATE", cal.getTime());
            assertFalse(date.evaluate(kv));
            //Why is the test failing?? How can we fix it??
            //Check searchBetweenWithYear method in OfferRestController and try to fix the starting date
        }

        @ParameterizedTest(name = "Test with year -> {0}")
        @ValueSource(ints = {2010, 2015, 2020, 2025})
        @DisplayName("Return a BasicExpression of the specific year")
        void when_useParamAndYear_return_basicExpressionOfSpecificYear(int year) {
            BasicExpression date = controller.searchBetweenWithYear("DATE", year);
            HashMap<String, Object> kv = new HashMap<>();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            kv.put("DATE", cal.getTime());
            assertTrue(date.evaluate(kv));
            cal.set(year-1, 11,31,23, 59,59);
            kv.put("DATE", cal.getTime());
            assertFalse(date.evaluate(kv));
            cal.add(Calendar.SECOND, 1);
            kv.put("DATE", cal.getTime());
            assertTrue(date.evaluate(kv));
            cal.set(year, 11,31,23, 59,59);
            kv.put("DATE", cal.getTime());
            assertTrue(date.evaluate(kv));
            cal.add(Calendar.SECOND, 1);
            kv.put("DATE", cal.getTime());
            assertFalse(date.evaluate(kv));
            //Why is the test failing?? How can we fix it??
            //Check searchBetweenWithYear method in OfferRestController and try to fix the starting date
        }
    }

}