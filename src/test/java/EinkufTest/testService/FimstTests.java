package EinkufTest.testService;

import EinkufTest.service.FimstService;
import EinkufTest.service.DatabaseTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ✅ Test class for FimstService.
 * This test retrieves all rows from FIMST where firma = 'WT'.
 */

@SpringBootTest(classes = DatabaseTestApplication.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class FimstTests {

    @Autowired
    private FimstService service;

    // ======================================
    // 🔧 Centralized test value
    // ======================================
    private final String firmaCode = "WT";

    /**
     *  Test: Fetch rows from FIMST table where firma = 'WT'
     */
    @Test
    @DisplayName("🕵️ Get FIMST rows where firma = 'WT'")
    void testFimstByFirmaWT() {
        String result = service.getFimstByFirmaWT();
        log.info("\n\n🏢 FIMST Result for firma = '{}':\n{}", firmaCode, result);
        Assertions.assertNotNull(result, "❌ Result should not be null");
    }
}