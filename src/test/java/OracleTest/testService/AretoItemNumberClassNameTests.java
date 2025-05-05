package OracleTest.testService;

import com.epam.reportportal.junit5.ReportPortalExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import OracleTest.service.AretoItemNumberClassNameService;
import OracleTest.service.DatabaseTestApplication;

/**
 * ✅ Test class for AretoItemNumberClassNameService.
 * Contains unit tests for querying the areto_item_number_class_name table.
 */
@ExtendWith(ReportPortalExtension.class)
@SpringBootTest(classes = DatabaseTestApplication.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AretoItemNumberClassNameTests {

    @Autowired
    private AretoItemNumberClassNameService service;

    // ============================================================
    // ✅ Centralized test inputs (easy to update all from here)
    // ============================================================
    private final String testItemNumber = "EK-405960";    // Item number to search for
    private final String testClassName = "Minimum";       // Class name pattern to search for

    /**
     * ✅ Test query by item_number using pattern match
     */
    @Test
    @Description("🕵️ Test item number class by item number pattern")
    @DisplayName("Test: getItemNumberClassNameByItemNumber")
    void testGetItemNumberClassNameByItemNumber() {
        String result = service.getItemNumberClassNameByItemNumber(testItemNumber);
        log.info("\n\n📊 Result from areto_item_number_class_name by item_number:\n{}", result);
        Assertions.assertNotNull(result);
    }

    /**
     * ✅ Test query by class_name using pattern match
     */
    @Test
    @Description("🕵️ Test item number class by class name pattern")
    @DisplayName("Test: getItemNumberClassNameByClassName")
    void testGetItemNumberClassNameByClassName() {
        String result = service.getItemNumberClassNameByClassName(testClassName);
        log.info("\n\n📊 Result from areto_item_number_class_name by class_name:\n{}", result);
        Assertions.assertNotNull(result);
    }
}