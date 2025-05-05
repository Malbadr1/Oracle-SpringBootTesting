package OracleTest.testService;

import com.epam.reportportal.junit5.ReportPortalExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import OracleTest.service.O2kAretoQualityCriteriaService;
import OracleTest.service.DatabaseTestApplication;

/**
 * ✅ Test class for O2kAretoQualityCriteriaService.
 * This test suite validates query operations on the o2k_areto_quality_criteria table.
 */

@ExtendWith(ReportPortalExtension.class)
@SpringBootTest(classes = DatabaseTestApplication.class) // ✅ Spring Boot context setup
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class O2kAretoQualityCriteriaTests {

    @Autowired
    private O2kAretoQualityCriteriaService service;

    // ======================================
    // 🔧 Centralized test input values
    // ======================================
    private final int txnId = 185610;
    private final int schemaId = -1;
    private final int recordId = 99170;

    /**
     * 🧪 Test: Retrieve quality criteria by TXN ID
     */
    @Test
    @Description("✅ Get quality criteria by TXN ID")
    @DisplayName("Test: getQualityCriteriaByTxnId")
    void testByTxnId() {
        String result = service.getQualityCriteriaByTxnId(txnId);
        log.info("\n\n📊 Result: Quality Criteria by TXN ID = {}\n{}", txnId, result);
        Assertions.assertNotNull(result, "❌ Result for TXN ID should not be null");
    }

    /**
     * 🧪 Test: Retrieve quality criteria by Schema ID
     */
    @Test
    @Description("🕵️ Get quality criteria by Schema ID")
    @DisplayName("Test: getQualityCriteriaBySchemaId")
    void testBySchemaId() {
        String result = service.getQualityCriteriaBySchemaId(schemaId);
        log.info("\n\n📊 Result: Quality Criteria by Schema ID = {}\n{}", schemaId, result);
        Assertions.assertNotNull(result, "❌ Result for Schema ID should not be null");
    }

    /**
     *  Test: Retrieve quality criteria by Record ID
     */
    @Test
    @Description("🕵️ Get quality criteria by Record ID")
    @DisplayName("Test: getQualityCriteriaByRecordId")
    void testByRecordId() {
        String result = service.getQualityCriteriaByRecordId(recordId);
        log.info("\n\n📊 Result: Quality Criteria by Record ID = {}\n{}", recordId, result);
        Assertions.assertNotNull(result, "❌ Result for Record ID should not be null");
    }
}