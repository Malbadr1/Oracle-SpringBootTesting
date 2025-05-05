package OracleTest.CLService.service;

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
 * ✅ Service to handle queries related to the PARTFIL table.
 */
@Service
@Slf4j
public class PartfilService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ======================= Queries =======================

    /**
     * ✅ Get all records from PARTFIL where von_firma = 'GX'
     *
     * @return Formatted table of results
     */
    public String getByFirmaGX() {
        String sql = "SELECT * FROM partfil WHERE von_firma = 'GX'";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
        return formatResultsAsTable(results);
    }

    // ======================= Shared Formatting =======================

    /**
     * 🔍 Formats a list of result maps into a clean ASCII table.
     *
     * @param results query result rows
     * @return formatted string
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
     *  Clean cell data: remove newlines and trim whitespace.
     *
     * @param value cell value
     * @return cleaned string
     */
    private String sanitize(Object value) {
        if (value == null) return "NULL";
        return value.toString().replaceAll("[\\r\\n]+", " ").trim();
    }
}