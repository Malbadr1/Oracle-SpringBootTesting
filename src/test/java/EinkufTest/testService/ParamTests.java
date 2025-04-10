package EinkufTest.testService;

import EinkufTest.service.ParamService;
import EinkufTest.service.DatabaseTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DatabaseTestApplication.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ParamTests {

    @Autowired
    private ParamService service;

    @Test
    @DisplayName("🕵️ Get all records from PARAM table")
    void testAllParams() {
        log.info("\n\n📊 PARAM - All Rows:\n{}", service.getAllParams());
    }

    @Test
    @DisplayName("🕵️ Get PARAM rows where firma = 'EZ'")
    void testParamsByFirmaEZ() {
        log.info("\n\n📊 PARAM - Where firma = 'EZ':\n{}", service.getParamsByFirmaEZ());
    }

    @Test
    @DisplayName("🕵️ Get PARAM rows with param_art LIKE '%MIN_DATUM%'")
    void testParamsByMinDatum() {
        log.info("\n\n📊 PARAM - param_art contains MIN_DATUM:\n{}", service.getParamsByMinDatum());
    }

    @Test
    @DisplayName("🕵️ Describe PARAM table structure")
    void testDescribeParamTable() {
        log.info("\n\n📊 PARAM - Table Description:\n{}", service.describeParamTable());
    }
}