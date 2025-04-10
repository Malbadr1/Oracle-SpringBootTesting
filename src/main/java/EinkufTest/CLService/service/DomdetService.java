package EinkufTest.CLService.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ✅ DomdetService: Executes queries related to the DOMDET table.
 */
@Service
@Slf4j
public class DomdetService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // =================== Constants ===================
    private static final String TABLE_NAME = "DOMDET";
    private static final String COLUMN_DOM_ID = "dom_id";
    private static final String DEFAULT_DOM_ID = "PART_SYS_TYP";
    // ================================================

    /**
     * ✅ Get records from DOMDET where dom_id = 'PART_SYS_TYP'
     */
    public String getDomdetByPartSysTyp() {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DOM_ID + " = ?";
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, DEFAULT_DOM_ID);
            return formatResultsAsTable(results);
        } catch (Exception e) {
            log.error("❌ Error querying DOMDET table: {}", e.getMessage());
            return "❌ Error querying DOMDET table.";
        }
    }

    // =================== Formatting ===================

    private String formatResultsAsTable(List<Map<String, Object>> results) {
        if (results.isEmpty()) return "⚠️ No data found.";

        List<String> headers = new ArrayList<>(results.get(0).keySet());
        int[] columnWidths = new int[headers.size()];

        // Calculate column widths
        for (int i = 0; i < headers.size(); i++) {
            columnWidths[i] = headers.get(i).length();
        }

        for (Map<String, Object> row : results) {
            for (int i = 0; i < headers.size(); i++) {
                String cell = sanitize(row.get(headers.get(i)));
                columnWidths[i] = Math.max(columnWidths[i], cell.length());
            }
        }

        // Build table format
        String format = Arrays.stream(columnWidths)
                .mapToObj(w -> "%-" + (w + 2) + "s")
                .collect(Collectors.joining("| ", "| ", " |"));

        StringBuilder table = new StringBuilder();
        table.append(String.format(format, headers.toArray())).append("\n");
        for (int width : columnWidths) {
            table.append("-".repeat(width + 3));
        }
        table.append("\n");

        // Print rows
        for (Map<String, Object> row : results) {
            Object[] rowData = headers.stream()
                    .map(h -> sanitize(row.get(h)))
                    .toArray();
            table.append(String.format(format, rowData)).append("\n");
        }

        return table.toString();
    }

    private String sanitize(Object value) {
        if (value == null) return "NULL";
        return value.toString().replaceAll("[\\r\\n]+", " ").trim();
    }
}