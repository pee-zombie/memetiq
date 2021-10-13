package systems.memetic.memetiq.service.media;

import io.vavr.CheckedFunction1;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.stereotype.Service;
import systems.memetic.memetiq.domain.Media;
import systems.memetic.memetiq.service.object.ObjectService;
import systems.memetic.memetiq.tables.records.MediaRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static systems.memetic.memetiq.Tables.MEDIA;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaService {
    private final DSLContext create;
    private final ObjectService objectService;

    public List<Media> getAll() {
        return getMedia(create.select().from(MEDIA)
            .fetchInto(MEDIA)).toList();
    }

    private Stream<Media> getMedia(Result<MediaRecord> record) {
        return record.stream()
            .map(CheckedFunction1.liftTry(Media::new))
            .flatMap(Try::toJavaStream);
    }

    public Optional<Media> getByName(String name) {
        Result<MediaRecord> record = create.select().from(MEDIA)
            .where(MEDIA.NAME.eq(name)).fetchInto(MEDIA);

        return getMedia(record).findAny();
    }

    @SneakyThrows
    public String addMedia(String name, byte[] file) {
        getByName(name).ifPresent(x -> {
            throw new RuntimeException(String.format(
                "Media called %s already exists!", name));
        });

        String url = objectService.store(name, file).toString();

        int affected = create.insertInto(MEDIA)
            .columns(
                MEDIA.NAME,
                MEDIA.URL,
                MEDIA.CREATED
            ).values(name, url, LocalDateTime.now()).execute();

        log.info("affected: {}", affected);

        return url;
    }
}
