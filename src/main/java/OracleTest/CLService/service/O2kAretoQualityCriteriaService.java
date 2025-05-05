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

/**
 * ✅ Service to handle queries related to o2k_areto_quality_criteria table.
 */
@Service
@Slf4j
public class O2kAretoQualityCriteriaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // =================== Constants ===================
    private static final String TABLE_NAME = "o2k_areto_quality_criteria";
    private static final String COL_TXN_ID = "txn_id";
    private static final String COL_SCHEMA_ID = "schema_id";
    private static final String COL_RECORD_ID = "record_id";
    // ================================================

    /**
     * ✅ Get results from o2k_areto_quality_criteria by txn_id.
     */
    public String getQualityCriteriaByTxnId(int txnId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_TXN_ID + " = ?";
        return executeQuery(sql, txnId);
    }

    /**
     * ✅ Get results from o2k_areto_quality_criteria by schema_id.
     */
    public String getQualityCriteriaBySchemaId(int schemaId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_SCHEMA_ID + " = ?";
        return executeQuery(sql, schemaId);
    }

    /**
     * ✅ Get results from o2k_areto_quality_criteria by record_id.
     */
    public String getQualityCriteriaByRecordId(int recordId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_RECORD_ID + " = ?";
        return executeQuery(sql, recordId);
    }

    // =================== Query Helper ===================

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

        StringBuilder table = new StringBuilder();
        table.append(String.format(format, headers.toArray())).append("\n");
        for (int width : columnWidths) table.append("-".repeat(width + 3));
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

    // =================== toString Helper ===================

    private String toStringSafe(Object obj, String columnName) {
        if ("MESSAGE_VALUE".equalsIgnoreCase(columnName)) {
            if (obj == null) return "NULL";
            if (obj instanceof byte[] || obj instanceof Blob) return "BLOB";
            return obj.toString();
        }

        if (obj == null) return "NULL";

        try {
            if (obj instanceof byte[]) {
                byte[] bytes = (byte[]) obj;
                if ("ARTICLE_UUID".equalsIgnoreCase(columnName) || "HEAD_UUID".equalsIgnoreCase(columnName)) {
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