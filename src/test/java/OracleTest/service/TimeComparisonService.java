package OracleTest.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class TimeComparisonService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String compareUtcWithLocalTime() {
        try {
            String sql = "SELECT TO_CHAR(SYS_EXTRACT_UTC(SYSTIMESTAMP), 'YYYY-MM-DD\"T\"HH24:MI:SS.FF3\"Z\"') AS UTC_TIME FROM DUAL";
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);

            if (results.isEmpty()) {
                return "⚠️ Unable to retrieve UTC time.";
            }

            String utcTimeString = results.get(0).get("UTC_TIME").toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            LocalDateTime utcTime = LocalDateTime.parse(utcTimeString, formatter);
            LocalDateTime localTime = LocalDateTime.now(ZoneId.systemDefault());

            Duration duration = Duration.between(utcTime, localTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            long seconds = duration.getSeconds() % 60;

            String differenceFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);

            return String.format(
                    "\n📌 UTC vs Local Time Comparison:\n" +
                            "  ➤ UTC Time:   %s\n" +
                            "  ➤ Local Time: %s\n" +
                            "  ⏱️ Difference: %s (hh:mm:ss)\n",
                    utcTimeString,
                    localTime.format(formatter),
                    differenceFormatted
            );

        } catch (Exception e) {
            log.error("❌ Error while comparing UTC and local time: {}", e.getMessage());
            return "❌ Error while comparing UTC and local time.";
        }
    }
}
