package OracleTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DatabaseTestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // -------------------- areto_article_head Queries --------------------

    public String getAretoArticleByWagr(String wagr) {
        return executeQuery("SELECT * FROM areto_article_head WHERE status NOT IN ('5','9') AND wagr LIKE ?", "%" + wagr + "%");
    }

    public String getAretoArticleBySupplierId(String supplierId) {
        return executeQuery("SELECT * FROM areto_article_head WHERE status NOT IN ('5','9') AND supplier_id LIKE ?", "%" + supplierId + "%");
    }

    public String getAretoArticleWithQualityClass(String qualityClass) {
        return executeQuery("SELECT * FROM areto_article_head WHERE status NOT IN ('5','9') AND quality_class LIKE ?", "%" + qualityClass + "%");
    }

    public String getAretoArticleHeadResults(String itemNumber) {
        return executeQuery("SELECT * FROM areto_article_head WHERE status NOT IN ('5','9') AND item_number LIKE ?", "%" + itemNumber + "%");
    }

    // -------------------- areto_article_characteristics Queries --------------------

    public String getArticleCharacteristicsByHeadUuid(String uuid) {
        return executeQuery("SELECT * FROM areto_article_characteristics WHERE head_uuid = ?", uuid);
    }

    public String getArticleCharacteristicsByTypeText(String type) {
        return executeQuery("SELECT * FROM areto_article_characteristics WHERE type = ?", type);
    }

    public String getCharacteristicsByGroupAndSortIndex(String groupDescription, int sortIndex) {
        return executeQuery("SELECT * FROM areto_article_characteristics WHERE group_description = ? AND sort_index = ?", groupDescription, sortIndex);
    }

    public String getArticleCharacteristicsById(String id) {
        return executeQuery("SELECT * FROM areto_article_characteristics WHERE id = ?", id);
    }

    public String getArticleCharacteristicsBySortIndex(int sortIndex) {
        return executeQuery("SELECT * FROM areto_article_characteristics WHERE sort_index = ?", sortIndex);
    }

    // -------------------- areto_static_criteria Queries --------------------

    public String getStaticCriteriaByClassName(String className) {
        return executeQuery("SELECT * FROM areto_static_criteria WHERE class_name LIKE ?", "%" + className + "%");
    }

    public String getStaticCriteriaByClassCode(String classCode) {
        return executeQuery("SELECT * FROM areto_static_criteria WHERE class_code = ?", classCode);
    }

    public String getStaticCriteriaByNameId(String nameId) {
        return executeQuery("SELECT * FROM areto_static_criteria WHERE name_id LIKE ?", "%" + nameId + "%");
    }

    public String getDiaStaticCriteriaByProductGroupId(int productGroupId) {
        return executeQuery("SELECT * FROM dia_areto_static_criteria WHERE product_group_id = ?", productGroupId);
    }

    public String getDiaStaticCriteriaByInternalId(int internalId) {
        return executeQuery("SELECT * FROM dia_areto_static_criteria WHERE internal_Id = ?", internalId);
    }

    // -------------------- o2k_areto_quality_criteria Queries --------------------

    public String getQualityCriteriaByTxnId(int txnId) {
        return executeQuery("SELECT * FROM o2k_areto_quality_criteria WHERE txn_id = ?", txnId);
    }

    public String getQualityCriteriaBySchemaId(int schemaId) {
        return executeQuery("SELECT * FROM o2k_areto_quality_criteria WHERE schema_id = ?", schemaId);
    }

    public String getQualityCriteriaByRecordId(int recordId) {
        return executeQuery("SELECT * FROM o2k_areto_quality_criteria WHERE record_id = ?", recordId);
    }

    // -------------------- areto_item_number_class_name Queries --------------------

    public String getItemNumberClassNameByItemNumber(String itemNumber) {
        return executeQuery("SELECT * FROM areto_item_number_class_name WHERE item_number LIKE ?", "%" + itemNumber + "%");
    }

    public String getItemNumberClassNameByClassName(String className) {
        return executeQuery("SELECT * FROM areto_item_number_class_name WHERE class_name LIKE ?", "%" + className + "%");
    }

    // -------------------- UTC vs Local Time --------------------

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

    // -------------------- Query Execution Helper --------------------

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
        if (results.isEmpty()) {
            return "⚠️ No data found.";
        }

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

    // -------------------- Safe toString Handling --------------------

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