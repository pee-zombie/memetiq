package systems.memetic.memetiq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import systems.memetic.memetiq.service.object.MockObjectStorageProvider;
import systems.memetic.memetiq.service.object.ObjectStorageProvider;
import systems.memetic.memetiq.service.object.S3StorageProvider;

@SpringBootApplication
@EnableConfigurationProperties(MockObjectStorageProvider.MockObjectStorageProviderProperties.class)
public class MemetiqApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemetiqApplication.class, args);
    }

    @Bean
    public ObjectStorageProvider storageProvider(MockObjectStorageProvider.MockObjectStorageProviderProperties props) {
        return props.enabled
            ? new MockObjectStorageProvider(props)
            : new S3StorageProvider();
    }

    @Bean
    public MultipartResolver multipartResolver(@Value("${api.media.maxUploadSize}") Integer maxSize) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(DataSize.ofMegabytes(maxSize).toBytes());
        return multipartResolver;
    }
}
