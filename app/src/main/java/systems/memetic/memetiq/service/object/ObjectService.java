package systems.memetic.memetiq.service.object;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import systems.memetic.memetiq.domain.Media;

import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ObjectService {
    private final ObjectStorageProvider provider;

    public List<Media> listAllMedia() {
        return provider.listAll();
    }

    public URL store(String name, byte[] bytes) {
        return provider.add(name, bytes);
    }
}
