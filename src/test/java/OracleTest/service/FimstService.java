package OracleTest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ✅ FimstService: Executes queries related to the FIMST table.
 */
@Service
@Slf4j
public class FimstService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // =================== Constants ===================
    private static final String TABLE_NAME = "fimst";
    private static final String COLUMN_FIRMA = "firma";
    private static final String DEFAULT_FIRMA = "WT";
    // ================================================

    /**
     * ✅ Get all rows from FIMST where firma = 'WT'.
     *
     * @return Formatted table result of query.
     */
    public String getFimstByFirmaWT() {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_FIRMA + " = ?";
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, DEFAULT_FIRMA);
            return formatResultsAsTable(results);
        } catch (Exception e) {
            log.error("❌ Error while querying FIMST table: {}", e.getMessage());
            return "❌ Error while querying FIMST table.";
        }
    }

    // =================== Formatting Helper ===================

    /**
     * Formats the query results into a table-like String.
     *
     * @param results List of result rows from the query.
     * @return Formatted result as String.
     */
    private String formatResultsAsTable(List<Map<String, Object>> results) {
        if (results.isEmpty()) return "⚠️ No data found.";

        List<String> headers = new ArrayList<>(results.get(0).keySet());
        int[] columnWidths = new int[headers.size()];

        for (int i = 0; i < headers.size(); i++) {
            columnWidths[i] = headers.get(i).length();
        }

        for (Map<String, Object> row : results) {
            for (int i = 0; i < headers.size(); i++) {
                String cell = sanitize(row.get(headers.get(i)));
                columnWidths[i] = Math.max(columnWidths[i], cell.length());
            }
        }

        String format = Arrays.stream(columnWidths)
                .mapToObj(w -> "%-" + (w + 2) + "s")
                .collect(Collectors.joining("| ", "| ", " |"));

        StringBuilder table = new StringBuilder();
        table.append(String.format(format, headers.toArray())).append("\n");

        for (int width : columnWidths) {
            table.append("-".repeat(width + 3));
        }
        table.append("\n");

        for (Map<String, Object> row : results) {
            Object[] rowData = headers.stream()
                    .map(h -> sanitize(row.get(h)))
                    .toArray();
            table.append(String.format(format, rowData)).append("\n");
        }

        return table.toString();
    }

    /**
     * Cleans cell data by removing line breaks and trimming.
     *
     * @param value The cell value object.
     * @return Cleaned string representation of the cell.
     */
    private String sanitize(Object value) {
        if (value == null) return "NULL";
        return value.toString().replaceAll("[\\r\\n]+", " ").trim();
    }
}