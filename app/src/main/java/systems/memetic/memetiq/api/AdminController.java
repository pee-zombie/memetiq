package systems.memetic.memetiq.api;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import systems.memetic.memetiq.domain.Media;
import systems.memetic.memetiq.service.media.MediaService;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final DSLContext create;
    private final MediaService mediaService;

    @Value("classpath:data.sql")
    Resource dataSql;

    @DeleteMapping("/db")
    public void deleteDb() {
        int affected = create.execute("DROP ALL OBJECTS DELETE FILES");
        log.info("Deleted {} objects!", affected);
    }

    @PostMapping("/db")
    public void initDb() throws IOException {
        log.info("Recreating db schema...");
        int affected = create.execute(new String(dataSql.getInputStream().readAllBytes()));
        log.info("Created {} objects!", affected);
    }

    @PutMapping("/db")
    public void recreateDb() throws IOException {
        deleteDb();
        initDb();
    }

    @DeleteMapping("/objects")
    public void deleteObjects() {
        int affected = create.execute("DROP ALL OBJECTS DELETE FILES");
        log.info("Deleted {} objects!", affected);
    }
}
