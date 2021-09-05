package systems.memetic.memetiq.service.object;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import systems.memetic.memetiq.tables.Media;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final DSLContext create;

    public Media getAll() {

    }
}
