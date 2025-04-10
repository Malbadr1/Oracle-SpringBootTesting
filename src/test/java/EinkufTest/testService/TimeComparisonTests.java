package EinkufTest.testService;

import com.epam.reportportal.junit5.ReportPortalExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import EinkufTest.service.DatabaseTestApplication;
import EinkufTest.service.TimeComparisonService;

@ExtendWith(ReportPortalExtension.class)
@SpringBootTest(classes = DatabaseTestApplication.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TimeComparisonTests {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TimeComparisonService service;

    @Test
    @Description("🕒 Compare UTC database time with system local time")
    @DisplayName("Test: compareUtcWithLocalTime")
    void testCompareUtcWithLocalTime() {
        String result = service.compareUtcWithLocalTime();
        log.info("\n\n🕒 UTC vs Local Time:\n{}", result);
        Assertions.assertNotNull(result);
    }
}