package EinkufTest.testService;

import com.epam.reportportal.junit5.ReportPortalExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Description;
import EinkufTest.service.DatabaseTestApplication;
import EinkufTest.service.DatabaseTestService;

@ExtendWith(ReportPortalExtension.class)
@SpringBootTest(classes = DatabaseTestApplication.class) // ✅ ربط الكلاس مع تطبيق Spring Boot
@ComponentScan(basePackages = "EinkufTest")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j

public class DatabaseTestServiceTest {

    @Autowired
    private DatabaseTestService service;

    private int productGroupId;
    private int internalId;
    private String itemNumber;
    private int sortIndex;

    private String wagr;
    private String supplierId;
    private String qualityClass;

    private String headUuid;
    private String type;
    private String groupDescription;
    private String characteristicId;

    private String staticClassName;
    private String classCode;
    private String nameId;

    private int txnId;
    private int schemaId;
    private int recordId;

    private String itemNumberClassName;
    private String itemClassNamePattern;

    @BeforeAll

    void setUp() {
        productGroupId = 1312;
        internalId = 15;
        itemNumber = "EK-89117";
        sortIndex = 1276;

        wagr = "1111";
        supplierId = "300520";
        qualityClass = "1";

        headUuid = "3F5B0A9427B8C52F8B0652E56C39EAAC";
        type = "TEXT";
        groupDescription = "static";
        characteristicId = "Anlieferung zu spät";

        staticClassName = "Bana";
        classCode = "1";
        nameId = "gesund";

        txnId = 185610;
        schemaId = -1;
        recordId = 99170;

        itemNumberClassName = "EK-405960";
        itemClassNamePattern = "Minimum";
    }

    @BeforeEach

    void beforeEachTest(TestInfo testInfo) {
        log.info("\n\n🔹 Running test: {}\n", testInfo.getDisplayName());
    }

    // areto_article_head

    @Test
    @Description("🕵️ Test articles by WAGR number")
    @DisplayName("Test: getAretoArticleByWagr")
    void testGetAretoArticleByWagr() {
        String result = service.getAretoArticleByWagr(wagr);
        log.info("\n\n📊 Result: By  WAGR number from areto_article_head\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test articles by Supplier ID")
    @DisplayName("Test: getAretoArticleBySupplierId")
    void testGetAretoArticleBySupplierId() {
        String result = service.getAretoArticleBySupplierId(supplierId);
        log.info("\n\n📊 Result: By SupplierId from areto_article_head\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test articles with quality class")
    @DisplayName("Test: getAretoArticleWithQualityClass")
    void testGetAretoArticleWithQualityClass() {
        String result = service.getAretoArticleWithQualityClass( qualityClass);
        log.info("\n\n📊 Result: By QualityClass from areto_article_head\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test article head search by item number")
    @DisplayName("Test: getAretoArticleHeadResults")
    void testGetAretoArticleHeadResults() {
        String result = service.getAretoArticleHeadResults(itemNumber);
        log.info("\n\n📊 Result: By item number from areto_article_head\n{}", result);
        Assertions.assertNotNull(result);
    }

    // areto_article_characteristics

    @Test
    @Description("🕵️ Test characteristics by head UUID")
    @DisplayName("Test: getArticleCharacteristicsByHeadUuid")
    void testGetArticleCharacteristicsByHeadUuid() {
        String result = service.getArticleCharacteristicsByHeadUuid(headUuid);
        log.info("\n\n📊 Result: By HeadUuid from areto_article_characteristics\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test characteristics of type 'TEXT'")
    @DisplayName("Test: getArticleCharacteristicsByTypeText")
    void testGetArticleCharacteristicsByType() {
        String result = service.getArticleCharacteristicsByTypeText(type);
        log.info("\n\n📊 Result: By Type from areto_article_characteristics\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test characteristics by group description And SortIndex ")
    @DisplayName("Test: getArticleCharacteristicsByGroupDescriptionStatic")
    void testGetArticleCharacteristicsByGroupDescriptionStatic() {
        String result = service.getCharacteristicsByGroupAndSortIndex(groupDescription,sortIndex);
        log.info("\n\n📊 Result: By GroupDescriptionStatic from areto_article_characteristics\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test characteristics by specific ID")
    @DisplayName("Test: getArticleCharacteristicsById")
    void testGetArticleCharacteristicsById() {
        String result = service.getArticleCharacteristicsById(characteristicId);
        log.info("\n\n📊 Result: By Id from areto_article_characteristics\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test characteristics by sort index")
    @DisplayName("Test: getArticleCharacteristicsBySortIndex")
    void testGetArticleCharacteristicsBySortIndex() {
        String result = service.getArticleCharacteristicsBySortIndex(sortIndex);
        log.info("\n\n📊 Result: By SortIndex from areto_article_characteristics\n{}", result);
        Assertions.assertNotNull(result);
    }

    // areto_static_criteria

    @Test
    @Description("🕵️ Test static criteria by class name")
    @DisplayName("Test: getStaticCriteriaByClassName")
    void testGetStaticCriteriaByClassName() {
        String result = service.getStaticCriteriaByClassName(staticClassName);
        log.info("\n\n📊 Result: By ClassName from areto_static_criteria\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test static criteria by class code")
    @DisplayName("Test: getStaticCriteriaByClassCode")
    void testGetStaticCriteriaByClassCode() {
        String result = service.getStaticCriteriaByClassCode(classCode);
        log.info("\n\n📊 Result: By ClassCode from areto_static_criteria\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test static criteria by name ID")
    @DisplayName("Test: getStaticCriteriaByNameId")
    void testGetStaticCriteriaByName1d() {
        String result = service.getStaticCriteriaByNameId(nameId);
        log.info("\n\n📊 Result: By Name1d from areto_static_criteria\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test DIA criteria by product group ID")
    @DisplayName("Test: getDiaStaticCriteriaByProductGroupId")
    void testGetDiaStaticCriteriaByProductGroupId() {
        String result = service.getDiaStaticCriteriaByProductGroupId(productGroupId);
        log.info("\n\n📊 Result: By ProductGroupId from areto_static_criteria\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test DIA criteria by internal ID")
    @DisplayName("Test: getDiaStaticCriteriaByInternalId")
    void testGetDiaStaticCriteriaByInternalId() {
        String result = service.getDiaStaticCriteriaByInternalId(internalId);
        log.info("\n\n📊 Result: By InternalId from areto_static_criteria\n{}", result);
        Assertions.assertNotNull(result);
    }

    // o2k_areto_quality_criteria

    @Test
    @Description("🕵️ Test quality criteria by TXN ID")
    @DisplayName("Test: getQualityCriteriaByTxnId")
    void testGetQualityCriteriaByTxnId() {
        String result = service.getQualityCriteriaByTxnId(txnId);
        log.info("\n\n📊 Result: By TxnId from  o2k_areto_quality_criteria\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test quality criteria by Schema ID")
    @DisplayName("Test: getQualityCriteriaBySchemaId")
    void testGetQualityCriteriaBySchemaId() {
        String result = service.getQualityCriteriaBySchemaId(schemaId);
        log.info("\n\n📊 Result: By SchemaId from  o2k_areto_quality_criteria\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test quality criteria by Record ID")
    @DisplayName("Test: getQualityCriteriaByRecordId")
    void testGetQualityCriteriaByRecordId() {
        String result = service.getQualityCriteriaByRecordId(recordId);
        log.info("\n\n📊 Result: By RecordId from  o2k_areto_quality_criteria\n{}", result);
        Assertions.assertNotNull(result);
    }

    // areto_item_number_class_name

    @Test
    @Description("🕵️ Test item number class by item number")
    @DisplayName("Test: getItemNumberClassNameByItemNumber")
    void testGetItemNumberClassNameByItemNumber() {
        String result = service.getItemNumberClassNameByItemNumber(itemNumberClassName);
        log.info("\n\n📊 Result: By ItemNumber from areto_item_number_class_name\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @Description("🕵️ Test item number class by class name")
    @DisplayName("Test: getItemNumberClassNameByClassName")
    void testGetItemNumberClassNameByClassName() {
        String result = service.getItemNumberClassNameByClassName(itemClassNamePattern);
        log.info("\n\n📊 Result: By ClassName from areto_item_number_class_name\n{}", result);
        Assertions.assertNotNull(result);
    }

    // Time comparison

    @Test
    @Description("⏱️ Compare UTC time with local time")
    @DisplayName("Test: compareUtcWithLocalTime")
    void testCompareUtcWithLocalTime() {
        String result = service.compareUtcWithLocalTime();
        log.info("\n\n🕒 UTC vs Local Time:\n{}", result);
        Assertions.assertNotNull(result);
    }
}