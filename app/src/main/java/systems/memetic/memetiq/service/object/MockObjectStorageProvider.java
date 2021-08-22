package systems.memetic.memetiq.service.object;

import io.findify.s3mock.S3Mock;
import org.springframework.beans.factory.annotation.Value;

public class MockObjectStorageProvider {
    @Value("${mockBackendPath}")
    private String mockBackendPath;

    public MockObjectStorageProvider() {
        S3Mock api = new S3Mock.Builder().withPort(8001).withFileBackend(this.mockBackendPath).build();
        api.start();

    }
}
