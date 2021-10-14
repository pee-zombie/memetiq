package systems.memetic.memetiq.service.object;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import systems.memetic.memetiq.domain.Media;

import java.net.URI;
import java.net.URL;
import java.util.List;

public class S3StorageProvider implements ObjectStorageProvider {
    private final S3Client client;

    private S3ClientBuilder buildClient() {
        return S3Client.builder().region(Region.US_EAST_1)
            .credentialsProvider(() ->
                AwsBasicCredentials.create("test", "test"))
            .serviceConfiguration(
                S3Configuration.builder()
                    .pathStyleAccessEnabled(true).build());
    }

    public S3StorageProvider() {
        this.client = buildClient().build();
    }

    public S3StorageProvider(String endpoint) {
        this.client = buildClient().endpointOverride(
            URI.create(endpoint)
        ).build();
    }

    @Override
    public List<Media> listAll() {
        return this.client.listObjects(x -> x.bucket(BUCKET_NAME)).contents().stream()
            .map(x -> new Media(x.key(), getUrlForKey(x.key()))).toList();
    }

    @Override
    public Boolean checkKeyExists(String name) {
        try {
            this.client.headObject(x -> x.bucket(BUCKET_NAME));
            return true;
        } catch(NoSuchKeyException e) {
            return false;
        }
    }

    private static final String BUCKET_NAME = "media";

    @Override
    public URL add(String name, byte[] bytes) {
        this.client.createBucket(x -> x.bucket(BUCKET_NAME));

        var response = this.client.putObject(
            builder -> builder.bucket(BUCKET_NAME).key(name),
            RequestBody.fromBytes(bytes));

        return getUrlForKey(name);
    }

    @Override
    public void remove(String name) {
        var response = this.client.deleteObject(
            builder -> builder.bucket(BUCKET_NAME).key(name));
    }

    private URL getUrlForKey(String name) {
        return this.client.utilities().getUrl(x -> x.bucket(BUCKET_NAME).key(name));
    }
}
