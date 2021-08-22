package systems.memetic.memetiq.service.object;

import org.springframework.stereotype.Service;
import systems.memetic.memetiq.domain.Media;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ObjectStorageProvider {
    List<Media> getAll();

    Media getByName(String name);

    void add(String name, BufferedImage media);
}
