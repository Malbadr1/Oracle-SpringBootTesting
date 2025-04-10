package EinkufTest.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getAllTables() {
        return jdbcTemplate.queryForList(
                "SELECT table_name FROM all_tables WHERE owner = USER",
                String.class);
    }
}
