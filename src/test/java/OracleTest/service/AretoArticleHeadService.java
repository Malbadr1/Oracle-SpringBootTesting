package OracleTest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AretoArticleHeadService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ========================== Column Constants ==========================
    private static final String COL_STATUS = "status";
    private static final String COL_WAGR = "wagr";
    private static final String COL_SUPPLIER_ID = "supplier_id";
    private static final String COL_QUALITY_CLASS = "quality_class";
    private static final String COL_ITEM_NUMBER = "item_number";
    private static final String COL_HEAD_UUID = "HEAD_UUID";
    private static final String COL_ARTICLE_UUID = "ARTICLE_UUID";
    private static final String COL_MESSAGE_VALUE = "MESSAGE_VALUE";
    // ======================================================================

    // ========================== Public Methods ============================

    /**
     * ✅ Get articles by WAGR
     */
    public String getAretoArticleByWagr(String wagr) {
        return executeQuery(
                "SELECT * FROM areto_article_head WHERE " + COL_STATUS + " NOT IN ('5','9') AND " + COL_WAGR + " LIKE ?",
                "%" + wagr + "%"
        );
    }

    /**
     * ✅ Get articles by Supplier ID
     */
    public String getAretoArticleBySupplierId(String supplierId) {
        return executeQuery(
                "SELECT * FROM areto_article_head WHERE " + COL_STATUS + " NOT IN ('5','9') AND " + COL_SUPPLIER_ID + " LIKE ?",
                "%" + supplierId + "%"
        );
    }

    /**
     * ✅ Get articles by Quality Class
     */
    public String getAretoArticleWithQualityClass(String qualityClass) {
        return executeQuery(
                "SELECT * FROM areto_article_head WHERE " + COL_STATUS + " NOT IN ('5','9') AND " + COL_QUALITY_CLASS + " LIKE ?",
                "%" + qualityClass + "%"
        );
    }

    /**
     * ✅ Get articles by Item Number
     */
    public String getAretoArticleHeadResults(String itemNumber) {
        return executeQuery(
                "SELECT * FROM areto_article_head WHERE " + COL_STATUS + " NOT IN ('5','9') AND " + COL_ITEM_NUMBER + " LIKE ?",
                "%" + itemNumber + "%"
        );
    }

    // ======================================================================

    // ========================== Query Formatter ===========================

    private String executeQuery(String sql, Object... params) {
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, params);
            return formatResultsAsTable(results);
        } catch (Exception e) {
            log.error("❌ Error while executing query: {}", e.getMessage());
            return "❌ Error while executing query.";
        }
    }

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
                String columnName = headers.get(i);
                String cell = sanitize(toStringSafe(row.get(columnName), columnName));
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

    // ========================== Safe toString Handler ======================

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

    private String toHexString(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02X", b));
        }
        return hex.toString();
    }
}