package OracleTest.testService;

import OracleTest.service.FreshProduceService;
import com.epam.reportportal.junit5.ReportPortalExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import OracleTest.service.DatabaseTestApplication;

@ExtendWith(ReportPortalExtension.class)
@SpringBootTest(classes = DatabaseTestApplication.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class FreshProduceServiceTests {

    @Autowired
    private FreshProduceService freshProduceService;

    @Test
    @DisplayName("✅ Get Fresh Produce By ARTNR")
    void testGetByArtnr() {
        int artnr = 1164276;
        String result = freshProduceService.getByArtnr(artnr);
        log.info("\n\n🧪 Result: getByArtnr({})\n{}", artnr, result);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("✅ Get Fresh Produce By WAGR")
    void testGetByWagr() {
        String wagr = "11";
        String result = freshProduceService.getByWagr(wagr);
        log.info("\n\n🧪 Result: getByWagr('{}')\n{}", wagr, result);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("✅ Get Fresh Produce By Quality Class")
    void testGetByQualityClass() {
        String qualityClass = "1";
        String result = freshProduceService.getByQualityClass(qualityClass);
        log.info("\n\n🧪 Result: getByQualityClass('{}')\n{}", qualityClass, result);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("✅ Get All Fresh Produce Data")
    void testGetAll() {
        String result = freshProduceService.getAllFreshProduceData();
        log.info("\n\n🧪 Result: getAllFreshProduceData()\n{}", result);
        Assertions.assertNotNull(result);
    }
}