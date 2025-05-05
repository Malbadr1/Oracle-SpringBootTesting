package OracleTest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ✅ Service to handle operations related to the PARAM table.
 */
@Service
@Slf4j
public class ParamService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // =================== Constants ===================
    private static final String TABLE_NAME = "param";
    private static final String COL_PARAM_ART = "param_art";
    private static final String COL_FIRMA = "firma";
    // ================================================

    /**
     * ✅ Get all rows from the PARAM table.
     */
    public String getAllParams() {
        return query("SELECT * FROM " + TABLE_NAME);
    }

    /**
     * ✅ Get PARAM records where firma = 'EZ'.
     */
    public String getParamsByFirmaEZ() {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_FIRMA + " = 'EZ'";
        return query(sql);
    }

    /**
     * ✅ Get PARAM records where param_art contains 'MIN_DATUM'.
     */
    public String getParamsByMinDatum() {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_PARAM_ART + " LIKE '%MIN_DATUM%'";
        return query(sql);
    }

    /**
     * ✅ Simulate DESCRIBE by querying column metadata from Oracle data dictionary.
     */
    public String describeParamTable() {
        String sql = "SELECT column_name, data_type, data_length FROM all_tab_columns WHERE table_name = 'PARAM'";
        return query(sql);
    }

    // =================== Shared Logic ===================

    private String query(String sql) {
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
            return formatResultsAsTable(results);
        } catch (Exception e) {
            log.error("❌ Failed to execute query: {}", e.getMessage());
            return "❌ Error while executing query.";
        }
    }

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
        for (int width : columnWidths) table.append("-".repeat(width + 3));
        table.append("\n");

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