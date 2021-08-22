package systems.memetic.memetiq.service.object;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import systems.memetic.memetiq.domain.Media;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

public class S3StorageProvider implements ObjectStorageProvider {
    private final S3Client client;

    private S3ClientBuilder buildClient() {
        return S3Client.builder().region(Region.US_EAST_1);
    }

    public S3StorageProvider() {
        client = buildClient().build();
    }

    public S3StorageProvider(String endpoint) {
        client = buildClient().endpointOverride(
                URI.create(endpoint)
        ).build();
    }

    @Override
    public List<Media> getAll() {
        return null;
    }

    @Override
    public Media getByName(String name) {
        return null;
    }

    @Override
    public void add(String name, BufferedImage media) {

    }
}
