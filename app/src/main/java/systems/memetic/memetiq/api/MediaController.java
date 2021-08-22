package systems.memetic.memetiq.api;

import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import systems.memetic.memetiq.domain.Media;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MediaController {
    private Map<String, Media> media;

    @GetMapping("/media")
    public List<Media> listMedia() {
        return media.values().stream().toList();
    }

    @GetMapping("/media")
    public Media getMedia(String name) throws Exception {
        return Optional.ofNullable(media.get(name)).orElseThrow(() -> new Exception("Media not found"));
    }

//    @SneakyThrows
//    @PostMapping("/media")
//    public Media addMedia(String name, @RequestParam("image") MultipartFile multipartFile) {
//        String fileName = StringUtils.cleanPath(
//                Optional.ofNullable(
//                        multipartFile.getOriginalFilename()
//                ).orElseThrow(Exception::new));
//    }
}
