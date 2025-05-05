package OracleTest.testService;

import OracleTest.service.PartfilService;
import OracleTest.service.DatabaseTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ✅ Test class for PartfilService.
 * This test fetches all rows from PARTFIL where von_firma = 'GX'.
 */

@SpringBootTest(classes = DatabaseTestApplication.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PartfilTests {

    @Autowired
    private PartfilService service;

    // ======================================
    // 🔧 Centralized test value
    // ======================================
    private final String firmaCode = "GX";

    /**
     * 🧪 Test: Fetch PARTFIL rows where von_firma = 'GX'
     */
    @Test
    @DisplayName("🕵️ Get PARTFIL rows where von_firma = 'GX'")
    void testPartfilByFirmaGX() {
        String result = service.getByFirmaGX();
        log.info("\n\n📦 PARTFIL Result for von_firma = '{}':\n{}", firmaCode, result);
        Assertions.assertNotNull(result, "❌ Result should not be null");
    }
}