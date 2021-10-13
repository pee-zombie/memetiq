package systems.memetic.memetiq.api;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import systems.memetic.memetiq.domain.Media;
import systems.memetic.memetiq.service.media.MediaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @GetMapping("/media")
    public List<Media> listMedia() {
        return mediaService.getAll();
    }


    @GetMapping("/media/{name}")
    public Media getMedia(String name) throws Exception {
        return mediaService.getByName(name).orElseThrow(() ->
            new Exception("Media not found"));
    }

    @SneakyThrows
    @PostMapping("/media/{name}")
    public String addMedia(@PathVariable("name") String name, @RequestParam("image") MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(
            Optional.ofNullable(
                multipartFile.getOriginalFilename()
            ).orElseThrow(Exception::new));

        return mediaService.addMedia(name, multipartFile.getBytes());
    }
}
