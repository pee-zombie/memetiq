package systems.memetic.memetiq.service.media;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Service;
import systems.memetic.memetiq.tables.Media;

import systems.memetic.memetiq.tables.*;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaService {
    private final DSLContext create;

    public List<String> getAll() {
        log.info("test log from getAll");
        Result<Record> record = create.select().from(Media.MEDIA).fetch();
        return record.stream().map(x -> x.get(Media.MEDIA.NAME)).toList();
    }
}
