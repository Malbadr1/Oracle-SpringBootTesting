package EinkufTest.testService;

import com.epam.reportportal.junit5.ReportPortalExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import EinkufTest.service.AretoArticleCharacteristicsService;
import EinkufTest.service.DatabaseTestApplication;

@ExtendWith(ReportPortalExtension.class)
@SpringBootTest(classes = DatabaseTestApplication.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AretoArticleCharacteristicsTests {

    @Autowired
    private AretoArticleCharacteristicsService service;

    // ================== Input Variables (Easy to Modify) ==================

    private final String HEAD_UUID = "3F5B0A9427B8C52F8B0652E56C39EAAC";
    private final String TYPE_TEXT = "TEXT";
    private final String GROUP_DESCRIPTION = "static";
    private final int SORT_INDEX = 1276;
    private final String CHARACTERISTIC_ID = "Anlieferung zu spät";

    // ======================================================================

    @Test
    @Description("🕵️ Get characteristics by UUID")
    @DisplayName("getArticleCharacteristicsByHeadUuid")
    void testByUuid() {
        String result = service.getArticleCharacteristicsByHeadUuid(HEAD_UUID);
        log.info("\n\n📊 Result: By HeadUuid\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Get characteristics by type")
    @DisplayName("getArticleCharacteristicsByTypeText")
    void testByType() {
        String result = service.getArticleCharacteristicsByTypeText(TYPE_TEXT);
        log.info("\n\n📊 Result: By Type\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Get by group and sort index")
    @DisplayName("getCharacteristicsByGroupAndSortIndex")
    void testByGroupAndSort() {
        String result = service.getCharacteristicsByGroupAndSortIndex(GROUP_DESCRIPTION, SORT_INDEX);
        log.info("\n\n📊 Result: By Group + SortIndex\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Get by ID")
    @DisplayName("getArticleCharacteristicsById")
    void testById() {
        String result = service.getArticleCharacteristicsById(CHARACTERISTIC_ID);
        log.info("\n\n📊 Result: By ID\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Get by Sort Index")
    @DisplayName("getArticleCharacteristicsBySortIndex")
    void testBySortIndex() {
        String result = service.getArticleCharacteristicsBySortIndex(SORT_INDEX);
        log.info("\n\n📊 Result: By SortIndex\n{}", result);
        Assertions.assertNotNull(result);
    }
}