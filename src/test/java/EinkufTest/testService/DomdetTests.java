package EinkufTest.testService;

import EinkufTest.service.DomdetService;
import EinkufTest.service.DatabaseTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ✅ Test class for DomdetService.
 * This test verifies the query retrieving records from DOMDET table where dom_id = 'PART_SYS_TYP'.
 */

@SpringBootTest(classes = DatabaseTestApplication.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DomdetTests {

    @Autowired
    private DomdetService service;

    // ======================================
    // 🕵️ Centralized test values (editable)
    // ======================================
    private final String expectedDomId = "PART_SYS_TYP";

    /**
     * 🔍 Test: Fetch DOMDET rows by dom_id = 'PART_SYS_TYP'
     */
    @Test
    @DisplayName("🕵️ Get DOMDET rows where dom_id = 'PART_SYS_TYP'")
    void testDomdetByPartSysTyp() {
        String result = service.getDomdetByPartSysTyp();
        log.info("\n\n📊 DOMDET Result for dom_id = '{}':\n{}", expectedDomId, result);
        Assertions.assertNotNull(result, "❌ Result should not be null");
    }
}