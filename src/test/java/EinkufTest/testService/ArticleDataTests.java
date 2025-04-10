package EinkufTest.testService;

import EinkufTest.service.ArticleDataService;
import EinkufTest.service.DatabaseTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DatabaseTestApplication.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ArticleDataTests {

    @Autowired
    private ArticleDataService service;

    @Test
    @DisplayName("🔍 article@masterdata_prod by ID")
    void testArticleById() {
        String result = service.getArticleById(1164276);
        log.info("\n\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("🔍 article_supplier@masterdata_prod by UUID")
    void testArticleSupplierByUUID() {
        String result = service.getArticleSupplierByUUID("DF423212686943349D275C231718ABB7");
        log.info("\n\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("📦 All suppliers")
    void testAllSuppliers() {
        String result = service.getAllSuppliers();
        log.info("\n\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("💰 All article price references")
    void testAllPriceRefs() {
        String result = service.getAllArticlePriceReferences();
        log.info("\n\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("📦 All from artli_f")
    void testArtliF() {
        String result = service.getAllArtliF();
        log.info("\n\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("📦 artst_A by artnr_A")
    void testArtstA() {
        String result = service.getArtstAByArtnr(1164276);
        log.info("\n\n{}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("📦 artli_a by artnr_A sorted")
    void testArtliASorted() {
        String result = service.getArtliAByArtnrSorted(1164276);
        log.info("\n\n{}", result);
        Assertions.assertNotNull(result);
    }
}