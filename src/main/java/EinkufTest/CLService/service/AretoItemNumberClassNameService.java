package EinkufTest.CLService.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AretoItemNumberClassNameService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // =================== Constants for Table Columns ===================
    private static final String TABLE_NAME = "areto_item_number_class_name";
    private static final String COL_ITEM_NUMBER = "item_number";
    private static final String COL_CLASS_NAME = "class_name";
    private static final String COL_MESSAGE_VALUE = "MESSAGE_VALUE";
    // ===================================================================

    /**
     * ✅ Get data by item number (LIKE match)
     */
    public String getItemNumberClassNameByItemNumber(String itemNumber) {
        return executeQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ITEM_NUMBER + " LIKE ?",
                "%" + itemNumber + "%"
        );
    }

    /**
     * ✅ Get data by class name (LIKE match)
     */
    public String getItemNumberClassNameByClassName(String className) {
        return executeQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_CLASS_NAME + " LIKE ?",
                "%" + className + "%"
        );
    }

    // ======================= Query Executor =======================

    private String executeQuery(String sql, Object... params) {
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, params);
            return formatResultsAsTable(results);
        } catch (Exception e) {
            log.error("❌ Error while executing query: {}", e.getMessage());
            return "❌ Error while executing query.";
        }
    }

    // ======================= Formatter =======================

    private String formatResultsAsTable(List<Map<String, Object>> results) {
        if (results.isEmpty()) return "⚠️ No data found.";

        StringBuilder table = new StringBuilder();
        List<String> headers = new ArrayList<>(results.get(0).keySet());

        int[] columnWidths = new int[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            columnWidths[i] = headers.get(i).length();
        }

        for (Map<String, Object> row : results) {
            for (int i = 0; i < headers.size(); i++) {
                String col = headers.get(i);
                String cell = sanitize(toStringSafe(row.get(col), col));
                columnWidths[i] = Math.max(columnWidths[i], cell.length());
            }
        }

        String format = Arrays.stream(columnWidths)
                .mapToObj(w -> "%-" + (w + 2) + "s")
                .collect(Collectors.joining("| ", "| ", " |"));

        table.append(String.format(format, headers.toArray())).append("\n");

        for (int width : columnWidths) {
            table.append("-".repeat(width + 3));
        }
        table.append("\n");

        for (Map<String, Object> row : results) {
            Object[] rowData = headers.stream()
                    .map(h -> sanitize(toStringSafe(row.get(h), h)))
                    .toArray();
            table.append(String.format(format, rowData)).append("\n");
        }

        return table.toString();
    }

    private String sanitize(String value) {
        return value == null ? "" : value.replaceAll("[\\r\\n]+", " ").trim();
    }

    // ======================= Safe toString =======================

    private String toStringSafe(Object obj, String columnName) {
        if (COL_MESSAGE_VALUE.equalsIgnoreCase(columnName)) {
            if (obj == null) return "NULL";
            if (obj instanceof byte[] || obj instanceof Blob) return "BLOB";
            return obj.toString();
        }

        if (obj == null) return "NULL";

        try {
            if (obj instanceof byte[]) {
                return new String((byte[]) obj, StandardCharsets.UTF_8);
            }

            if (obj instanceof Blob) {
                Blob blob = (Blob) obj;
                byte[] bytes = blob.getBytes(1, (int) blob.length());
                return new String(bytes, StandardCharsets.UTF_8);
            }

        } catch (Exception e) {
            log.error("❌ Error reading column [{}]: {}", columnName, e.getMessage());
            return "[Error reading column: " + columnName + "]";
        }

        return obj.toString();
    }
}