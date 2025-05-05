package OracleTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AretoArticleCharacteristicsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ========================== COLUMN CONSTANTS ==========================
    // These constants help keep the SQL column names in one place
    // So if the DB schema changes, update here only!
    private static final String COL_HEAD_UUID = "head_uuid";
    private static final String COL_TYPE = "type";
    private static final String COL_GROUP_DESCRIPTION = "group_description";
    private static final String COL_SORT_INDEX = "sort_index";
    private static final String COL_ID = "id";
    private static final String COL_MESSAGE_VALUE = "MESSAGE_VALUE";
    private static final String COL_ARTICLE_UUID = "ARTICLE_UUID";
    // ======================================================================

    // ========================== SERVICE METHODS ==========================

    /**
     * Get all records filtered by Head UUID
     */
    public String getArticleCharacteristicsByHeadUuid(String uuid) {
        return executeQuery("SELECT * FROM areto_article_characteristics WHERE "
                + COL_HEAD_UUID + " = ?", uuid);
    }

    /**
     * Get all records filtered by Type
     */
    public String getArticleCharacteristicsByTypeText(String type) {
        return executeQuery("SELECT * FROM areto_article_characteristics WHERE " + COL_TYPE + " = ?", type);
    }

    /**
     * Get records based on Group Description + Sort Index
     */
    public String getCharacteristicsByGroupAndSortIndex(String groupDescription, int sortIndex) {
        return executeQuery("SELECT * FROM areto_article_characteristics WHERE " + COL_GROUP_DESCRIPTION + " = ? AND " + COL_SORT_INDEX + " = ?", groupDescription, sortIndex);
    }

    /**
     * Get record by its ID
     */
    public String getArticleCharacteristicsById(String id) {
        return executeQuery("SELECT * FROM areto_article_characteristics WHERE " + COL_ID + " = ?", id);
    }

    /**
     * Get all records filtered by Sort Index
     */
    public String getArticleCharacteristicsBySortIndex(int sortIndex) {
        return executeQuery("SELECT * FROM areto_article_characteristics WHERE " + COL_SORT_INDEX + " = ?", sortIndex);
    }

    // =====================================================================

    // =============== QUERY EXECUTION + FORMATTING METHODS ===============

    private String executeQuery(String sql, Object... params) {
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, params);
            return formatResultsAsTable(results);
        } catch (Exception e) {
            log.error("❌ Error while executing query: {}", e.getMessage());
            return "❌ Error while executing query.";
        }
    }

    /**
     * Converts List of Map results into a table-style formatted string
     */
    private String formatResultsAsTable(List<Map<String, Object>> results) {
        if (results.isEmpty()) return "⚠️ No data found.";

        StringBuilder table = new StringBuilder();
        List<String> headers = new ArrayList<>(results.get(0).keySet());
        int[] columnWidths = new int[headers.size()];

        // Calculate column width for formatting
        for (int i = 0; i < headers.size(); i++) {
            columnWidths[i] = headers.get(i).length();
        }

        // Adjust column widths based on actual content
        for (Map<String, Object> row : results) {
            for (int i = 0; i < headers.size(); i++) {
                String columnName = headers.get(i);
                String cell = sanitize(toStringSafe(row.get(columnName), columnName));
                columnWidths[i] = Math.max(columnWidths[i], cell.length());
            }
        }

        // Build table header
        String format = Arrays.stream(columnWidths)
                .mapToObj(w -> "%-" + (w + 2) + "s")
                .collect(Collectors.joining("| ", "| ", " |"));
        table.append(String.format(format, headers.toArray())).append("\n");

        // Header separator
        for (int width : columnWidths) table.append("-".repeat(width + 3));
        table.append("\n");

        // Data rows
        for (Map<String, Object> row : results) {
            Object[] rowData = headers.stream()
                    .map(h -> sanitize(toStringSafe(row.get(h), h)))
                    .toArray();
            table.append(String.format(format, rowData)).append("\n");
        }

        return table.toString();
    }

    /**
     * Sanitize text values to avoid line breaks in output
     */
    private String sanitize(String value) {
        return value == null ? "" : value.replaceAll("[\\r\\n]+", " ").trim();
    }

    /**
     * Safely convert any type of object to a readable string
     */
    private String toStringSafe(Object obj, String columnName) {
        if (COL_MESSAGE_VALUE.equalsIgnoreCase(columnName)) {
            if (obj == null) return "NULL";
            if (obj instanceof byte[] || obj instanceof Blob) return "BLOB";
            return obj.toString();
        }

        if (obj == null) return "NULL";

        try {
            if (obj instanceof byte[]) {
                byte[] bytes = (byte[]) obj;
                if (COL_ARTICLE_UUID.equalsIgnoreCase(columnName) || COL_HEAD_UUID.equalsIgnoreCase(columnName)) {
                    return toHexString(bytes);
                }
                String utf8 = new String(bytes, StandardCharsets.UTF_8);
                return utf8.length() > 1000 ? utf8.substring(0, 1000) + "..." : utf8;
            }

            if (obj instanceof Blob) {
                Blob blob = (Blob) obj;
                long length = blob.length();

                if (length == 0) return "[BLOB empty]";
                if (length > 1_000_000) return "[BLOB too large to display: " + length + " bytes]";

                byte[] bytes = blob.getBytes(1, (int) length);
                String utf8 = new String(bytes, StandardCharsets.UTF_8);
                return utf8.length() > 1000 ? utf8.substring(0, 1000) + "..." : utf8;
            }

        } catch (Exception e) {
            log.error("❌ Error while reading column [{}]: {}", columnName, e.getMessage());
            return "[Error reading column: " + columnName + "]";
        }

        return obj.toString();
    }

    /**
     * Convert byte array to HEX string
     */
    private String toHexString(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02X", b));
        }
        return hex.toString();
    }
}