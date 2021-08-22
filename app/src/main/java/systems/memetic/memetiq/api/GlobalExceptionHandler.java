package systems.memetic.memetiq.api;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public String handleFileSizeLimitException(MaxUploadSizeExceededException e) {
        return String.format("Your uploaded file was above the limit of %d", e.getMaxUploadSize());
    }

    @ExceptionHandler({Exception.class})
    public String handleGenericException(Exception e) {
        return String.format("Unexpected error: %s", e.getMessage());
    }
}
