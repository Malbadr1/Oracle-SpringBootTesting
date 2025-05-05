package OracleTest.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FreshProduceService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Base SQL
    private static final String BASE_QUERY = """
            SELECT
              ARTST_TABLES.ARTNR,
              ARTST_TABLES.BEZ ART_BEZ,
              ARTST_TABLES.ARTNR ||' ' || ARTST_TABLES.BEZ ART_BEZ_,
              ARTST_TABLES.EAN_CODE_TYPE,
              ARTST_TABLES.MG_FAKTOR VE_BE_FAKTOR,
              ARTST_TABLES.STATUS,  
              LIFST_TABLES.LIEFNR,
              LIFST_TABLES.LIEF_BEZ,
              LIFST_TABLES.LIEFNR ||' '|| LIFST_TABLES.LIEF_BEZ LIEF_BEZ_,
              CASE 
                   WHEN FRESH_TABLES.COUNTRY IS NULL THEN ARTST_TABLES.COUNTRY
                   ELSE FRESH_TABLES.COUNTRY
              END AS LAND, 
              ARTST_TABLES.WAGR,
              ARTST_TABLES.WGR_BEZ,
              ARTST_TABLES.WGR_BEZ_ WGR_BEZ_,
              ARTST_TABLES.EK_BER,  
              ARTST_TABLES.EINKAUF,  
              FRESH_TABLES.PRESSURE_RESISTANCE,
              FRESH_TABLES.CALIBER,
              FRESH_TABLES.BRIX,
              FRESH_TABLES.JUICE_CONTENT,
              FRESH_TABLES.COLOR,
              FRESH_TABLES.PACKAGING,
              FRESH_TABLES.PRIMARY_PACKAGE,
              FRESH_TABLES.ADDITIONAL_ITEM_INFOS,
              FRESH_TABLES.QUALITY_CLASS,
              FRESH_TABLES.TYPE
            FROM ARTST_TABLES, FRESH_TABLES, LIFST_TABLES
            WHERE (ARTST_TABLES.ARTNR_A = FRESH_TABLES.ARTNR_A (+) AND ARTST_TABLES.FIRMA = FRESH_TABLES.FIRMA (+))
              AND (ARTST_TABLES.ARTNR = LIFST_TABLES.ARTNR (+) AND ARTST_TABLES.FIRMA = LIFST_TABLES.FIRMA (+))
        """;

    public String getAllFreshProduceData() {
        return queryWithCondition("");
    }

    public String getByArtnr(int artnr) {
        return queryWithCondition(" AND ARTST_TABLES.ARTNR = " + artnr);
    }

    public String getByWagr(String wagr) {
        return queryWithCondition(" AND ARTST_TABLES.WAGR LIKE '" + wagr + "%'");
    }

    public String getByQualityClass(String qualityClass) {
        return queryWithCondition(" AND FRESH_TABLES.QUALITY_CLASS = '" + qualityClass + "'");
    }

    private String queryWithCondition(String condition) {
        String fullQuery = BASE_QUERY + condition;
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(fullQuery);
            return format(results);
        } catch (Exception e) {
            log.error("❌ Query failed: {}", e.getMessage());
            return "❌ Error during query.";
        }
    }

    private String format(List<Map<String, Object>> rows) {
        if (rows.isEmpty()) return "⚠️ No data found.";
        StringBuilder sb = new StringBuilder();
        rows.forEach(row -> {
            row.forEach((k, v) -> sb.append(k).append(": ").append(v).append(" | "));
            sb.append("\n");
        });
        return sb.toString();
    }
}