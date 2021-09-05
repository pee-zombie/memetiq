package systems.memetic.memetiq.service.media;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import systems.memetic.memetiq.service.object.ObjectService;
import systems.memetic.memetiq.tables.Media;

import static systems.memetic.memetiq.Tables.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaService {
    private final DSLContext create;
    private final ObjectService objectService;

    public List<String> getAll() {
        log.info("test log from getAll");
        
        Result<Record> record = create.select().from(MEDIA).fetch();
        return record.stream().map(x -> x.get(MEDIA.NAME)).toList();
    }

    @SneakyThrows
    public String addMedia(String name, byte[] file) {
        return objectService.store(name, file).toString();
    }
}
