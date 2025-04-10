package EinkufTest.testService;

import com.epam.reportportal.junit5.ReportPortalExtension;
import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import EinkufTest.service.AretoArticleHeadService;
import EinkufTest.service.DatabaseTestApplication;

/**
 * ✅ Test class for AretoArticleHeadService.
 * This class covers various query methods on the `areto_article_head` table.
 * The test inputs are isolated as variables for easier maintainability and quick updates.
 */
@ExtendWith(ReportPortalExtension.class)
@SpringBootTest(classes = DatabaseTestApplication.class) // ✅ Link this test class to Spring Boot context
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AretoArticleHeadTests {

    @Autowired
    private AretoArticleHeadService service;

    // ============================================================
    // ✅ Centralized test inputs (easy to update all from here)
    // ============================================================
    private final String testWagr = "1111";                  // Article group number to search
    private final String testSupplierId = "300520";          // Supplier ID to test
    private final String testQualityClass = "1";             // Quality class value to check
    private final String testItemNumber = "EK-89117";        // Item number to retrieve

    /**
     * ✅ Test getting articles by WAGR (article group) value
     */
    @Test
    @DisplayName("Test: getAretoArticleByWagr")
    @Description(" Test articles by WAGR number")
    void testGetAretoArticleByWagr() {
        String result = service.getAretoArticleByWagr(testWagr);
        log.info("\n\n📊 Result: By WAGR number from areto_article_head\n{}", result);
        Assertions.assertNotNull(result);
    }

    /**
     * ✅ Test getting articles by supplier ID
     */
    @Test
    @DisplayName("Test: getAretoArticleBySupplierId")
    @Description("🕵️ Test articles by Supplier ID")
    void testGetAretoArticleBySupplierId() {
        String result = service.getAretoArticleBySupplierId(testSupplierId);
        log.info("\n\n📊 Result: By SupplierId from areto_article_head\n{}", result);
        Assertions.assertNotNull(result);
    }

    /**
     * ✅ Test filtering articles using a quality class
     */
    @Test
    @DisplayName("Test: getAretoArticleWithQualityClass")
    @Description("🕵️ Test articles with quality class")
    void testGetAretoArticleWithQualityClass() {
        String result = service.getAretoArticleWithQualityClass(testQualityClass);
        log.info("\n\n📊 Result: By QualityClass from areto_article_head\n{}", result);
        Assertions.assertNotNull(result);
    }

    /**
     * ✅ Test retrieving article head data by item number
     */
    @Test
    @DisplayName("Test: getAretoArticleHeadResults")
    @Description("🕵️ Test article head search by item number")
    void testGetAretoArticleHeadResults() {
        String result = service.getAretoArticleHeadResults(testItemNumber);
        log.info("\n\n📊 Result: By item number from areto_article_head\n{}", result);
        Assertions.assertNotNull(result);
    }
}