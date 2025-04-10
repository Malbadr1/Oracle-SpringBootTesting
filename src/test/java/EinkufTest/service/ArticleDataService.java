package EinkufTest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getArticleById(int id) {
        String sql = "SELECT * FROM article@masterdata_prod WHERE id = ?";
        return runQuery(sql, id);
    }

    public String getArticleSupplierByUUID(String uuid) {
        String sql = "SELECT * FROM article_supplier@masterdata_prod WHERE article_uuid = ?";
        return runQuery(sql, uuid);
    }

    public String getAllSuppliers() {
        String sql = "SELECT * FROM supplier@masterdata_prod";
        return runQuery(sql);
    }

    public String getAllArticlePriceReferences() {
        String sql = "SELECT * FROM article_price_references@masterdata_prod";
        return runQuery(sql);
    }

    public String getAllArtliF() {
        String sql = "SELECT * FROM artli_f";
        return runQuery(sql);
    }

    public String getArtstAByArtnr(int artnr) {
        String sql = "SELECT * FROM artst_A WHERE artnr_A = ?";
        return runQuery(sql, artnr);
    }

    public String getArtliAByArtnrSorted(int artnr) {
        String sql = "SELECT * FROM artli_a WHERE artnr_A = ? ORDER BY von_datum DESC";
        return runQuery(sql, artnr);
    }

    private String runQuery(String sql, Object... params) {
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, params);
            return formatResults(results);
        } catch (Exception e) {
            log.error("❌ Query failed: {}", e.getMessage());
            return "❌ Query failed.";
        }
    }

    private String formatResults(List<Map<String, Object>> results) {
        if (results.isEmpty()) return "⚠️ No data found.";

        List<String> headers = new ArrayList<>(results.get(0).keySet());
        int[] widths = new int[headers.size()];

        for (int i = 0; i < headers.size(); i++) {
            widths[i] = headers.get(i).length();
        }

        for (Map<String, Object> row : results) {
            for (int i = 0; i < headers.size(); i++) {
                String value = Objects.toString(row.get(headers.get(i)), "");
                widths[i] = Math.max(widths[i], value.length());
            }
        }

        String format = Arrays.stream(widths)
                .mapToObj(w -> "%-" + (w + 2) + "s")
                .collect(Collectors.joining("| ", "| ", " |"));

        StringBuilder table = new StringBuilder();
        table.append(String.format(format, headers.toArray())).append("\n");
        for (int w : widths) table.append("-".repeat(w + 3));
        table.append("\n");

        for (Map<String, Object> row : results) {
            Object[] values = headers.stream().map(h -> Objects.toString(row.get(h), "")).toArray();
            table.append(String.format(format, values)).append("\n");
        }

        return table.toString();
    }
}