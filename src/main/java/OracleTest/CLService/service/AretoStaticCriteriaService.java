package OracleTest.CLService.service;

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
public class AretoStaticCriteriaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // =================== Constants for Column Names ===================
    private static final String TABLE_STATIC = "areto_static_criteria";
    private static final String TABLE_DIA = "dia_areto_static_criteria";
    private static final String COL_CLASS_NAME = "class_name";
    private static final String COL_CLASS_CODE = "class_code";
    private static final String COL_NAME_ID = "name_id";
    private static final String COL_PRODUCT_GROUP_ID = "product_group_id";
    private static final String COL_INTERNAL_ID = "internal_id";
    private static final String COL_MESSAGE_VALUE = "MESSAGE_VALUE";
    // ==================================================================

    // ======================= Query Methods =======================

    /**
     * ✅ Get records from areto_static_criteria by class name
     */
    public String getStaticCriteriaByClassName(String className) {
        return executeQuery("SELECT * FROM " + TABLE_STATIC + " WHERE " + COL_CLASS_NAME + " LIKE ?", "%" + className + "%");
    }

    /**
     * ✅ Get records by class code
     */
    public String getStaticCriteriaByClassCode(String classCode) {
        return executeQuery("SELECT * FROM " + TABLE_STATIC + " WHERE " + COL_CLASS_CODE + " = ?", classCode);
    }

    /**
     * ✅ Get records by name ID
     */
    public String getStaticCriteriaByNameId(String nameId) {
        return executeQuery("SELECT * FROM " + TABLE_STATIC + " WHERE " + COL_NAME_ID + " LIKE ?", "%" + nameId + "%");
    }

    /**
     * ✅ Get records from dia_areto_static_criteria by product group ID
     */
    public String getDiaStaticCriteriaByProductGroupId(int productGroupId) {
        return executeQuery("SELECT * FROM " + TABLE_DIA + " WHERE " + COL_PRODUCT_GROUP_ID + " = ?", productGroupId);
    }

    /**
     * ✅ Get records by internal ID from dia table
     */
    public String getDiaStaticCriteriaByInternalId(int internalId) {
        return executeQuery("SELECT * FROM " + TABLE_DIA + " WHERE " + COL_INTERNAL_ID + " = ?", internalId);
    }

    // ======================= Execution Helper =======================

    private String executeQuery(String sql, Object... params) {
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, params);
            return formatResultsAsTable(results);
        } catch (Exception e) {
            log.error("❌ Error while executing query: {}", e.getMessage());
            return "❌ Error while executing query.";
        }
    }

    // ======================= Table Formatter =======================

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
                String cell = sanitize(toStringSafe(row.get(headers.get(i)), headers.get(i)));
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

    // ======================= Helpers =======================

    private String sanitize(String value) {
        return value == null ? "" : value.replaceAll("[\\r\\n]+", " ").trim();
    }

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