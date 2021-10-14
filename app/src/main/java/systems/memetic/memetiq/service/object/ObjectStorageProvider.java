package systems.memetic.memetiq.service.object;

import systems.memetic.memetiq.domain.Media;

import java.net.URL;
import java.util.List;

public interface ObjectStorageProvider {
    List<Media> listAll();

    Boolean checkKeyExists(String name);

    URL add(String name, byte[] media);

    void remove(String name);
}
