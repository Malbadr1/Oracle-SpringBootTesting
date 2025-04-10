package EinkufTest.CLService.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ArticleDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ======================== SQL Constants ========================
    private static final String TABLE_ARTICLE = "article@masterdata_prod";
    private static final String TABLE_SUPPLIER = "supplier@masterdata_prod";
    private static final String TABLE_ARTICLE_SUPPLIER = "article_supplier@masterdata_prod";
    private static final String TABLE_ARTICLE_PRICE = "article_price_references@masterdata_prod";
    private static final String TABLE_ARTLI_F = "artli_f";
    private static final String TABLE_ARTST_A = "artst_A";
    private static final String TABLE_ARTLI_A = "artli_a";

    private static final String COL_ID = "id";
    private static final String COL_ARTICLE_UUID = "article_uuid";
    private static final String COL_ARTNR_A = "artnr_A";
    private static final String COL_VON_DATUM = "von_datum";
    // ================================================================

    // ===================== Service Methods ==========================
    public String getArticleById(int id) {
        String sql = "SELECT * FROM " + TABLE_ARTICLE + " WHERE " + COL_ID + " = ?";
        return format(jdbcTemplate.queryForList(sql, id));
    }

    public String getArticleSupplierByUuid(String uuid) {
        String sql = "SELECT * FROM " + TABLE_ARTICLE_SUPPLIER + " WHERE " + COL_ARTICLE_UUID + " = ?";
        return format(jdbcTemplate.queryForList(sql, uuid));
    }

    public String getAllSuppliers() {
        String sql = "SELECT * FROM " + TABLE_SUPPLIER;
        return format(jdbcTemplate.queryForList(sql));
    }

    public String getAllPriceReferences() {
        String sql = "SELECT * FROM " + TABLE_ARTICLE_PRICE;
        return format(jdbcTemplate.queryForList(sql));
    }

    public String getAllArtliF() {
        String sql = "SELECT * FROM " + TABLE_ARTLI_F;
        return format(jdbcTemplate.queryForList(sql));
    }

    public String getArtstByArtnrA(int artnrA) {
        String sql = "SELECT * FROM " + TABLE_ARTST_A + " WHERE " + COL_ARTNR_A + " = ?";
        return format(jdbcTemplate.queryForList(sql, artnrA));
    }

    public String getArtliAByArtnrA(int artnrA) {
        String sql = "SELECT * FROM " + TABLE_ARTLI_A + " WHERE " + COL_ARTNR_A + " = ? ORDER BY " + COL_VON_DATUM + " DESC";
        return format(jdbcTemplate.queryForList(sql, artnrA));
    }

    // ======================= Formatter ============================
    private String format(List<Map<String, Object>> results) {
        if (results.isEmpty()) return "⚠️ No data found.";
        StringBuilder sb = new StringBuilder();
        results.forEach(row -> {
            row.forEach((k, v) -> sb.append(k).append(": ").append(v).append(" | "));
            sb.append("\n");
        });
        return sb.toString();
    }
}