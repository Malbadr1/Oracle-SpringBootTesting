package OracleTest.testService;

import com.epam.reportportal.junit5.ReportPortalExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import OracleTest.service.AretoStaticCriteriaService;
import OracleTest.service.DatabaseTestApplication;

/**
 * ✅ Test class for AretoStaticCriteriaService.
 * This class contains unit tests for queries related to areto_static_criteria and dia_areto_static_criteria.
 */
@ExtendWith(ReportPortalExtension.class)
@SpringBootTest(classes = DatabaseTestApplication.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AretoStaticCriteriaTests {

    @Autowired
    private AretoStaticCriteriaService service;

    // ======================================================
    // 🔧 Centralized test values (easy to edit in one place)
    // ======================================================
    private final String testClassName = "Bana";
    private final String testClassCode = "1";
    private final String testNameId = "gesund";
    private final int testProductGroupId = 1312;
    private final int testInternalId = 15;

    /**
     * 🏷️ Test: Static criteria by class name
     */
    @Test
    @Description("🏷️ Static criteria by class name")
    @DisplayName("Test: getStaticCriteriaByClassName")
    void testByClassName() {
        String result = service.getStaticCriteriaByClassName(testClassName);
        log.info("\n\n📊 Result: Static Criteria by Class Name\n{}", result);
        Assertions.assertNotNull(result);
    }

    /**
     * 🏷️ Test: Static criteria by class code
     */
    @Test
    @Description("🏷️ Static criteria by class code")
    @DisplayName("Test: getStaticCriteriaByClassCode")
    void testByClassCode() {
        String result = service.getStaticCriteriaByClassCode(testClassCode);
        log.info("\n\n📊 Result: Static Criteria by Class Code\n{}", result);
        Assertions.assertNotNull(result);
    }

    /**
     * 🏷️ Test: Static criteria by name ID
     */
    @Test
    @Description("🕵️ Static criteria by name ID")
    @DisplayName("Test: getStaticCriteriaByNameId")
    void testByNameId() {
        String result = service.getStaticCriteriaByNameId(testNameId);
        log.info("\n\n📊 Result: Static Criteria by Name ID\n{}", result);
        Assertions.assertNotNull(result);
    }

    /**
     * 🏷️ Test: DIA criteria by product group ID
     */
    @Test
    @Description("🕵️ DIA Static criteria by product group ID")
    @DisplayName("Test: getDiaStaticCriteriaByProductGroupId")
    void testDiaByProductGroupId() {
        String result = service.getDiaStaticCriteriaByProductGroupId(testProductGroupId);
        log.info("\n\n📊 Result: DIA by Product Group ID\n{}", result);
        Assertions.assertNotNull(result);
    }

    /**
     * 🏷️ Test: DIA criteria by internal ID
     */
    @Test
    @Description("🕵️ DIA Static criteria by internal ID")
    @DisplayName("Test: getDiaStaticCriteriaByInternalId")
    void testDiaByInternalId() {
        String result = service.getDiaStaticCriteriaByInternalId(testInternalId);
        log.info("\n\n📊 Result: DIA by Internal ID\n{}", result);
        Assertions.assertNotNull(result);
    }
}