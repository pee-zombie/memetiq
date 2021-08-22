package systems.memetic.memetiq.service.object;

import akka.http.scaladsl.Http;
import io.findify.s3mock.S3Mock;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Conditional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import scala.concurrent.duration.FiniteDuration;

import javax.annotation.Nonnull;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.concurrent.TimeUnit;

//@Component
//@ConditionalOnExpression("#{mock-backend.enabled}")
public class MockObjectStorageProvider extends S3StorageProvider {
    private final Http.ServerBinding serverBinding;

    @ConstructorBinding
    @ConfigurationProperties("mock-backend")
    @Validated
    @Data
    public static class MockObjectStorageProviderProperties {
        public final Boolean enabled;

        @NotBlank
        public final String path;

        @Positive
        public final Integer port;
    }

    public MockObjectStorageProvider(MockObjectStorageProviderProperties props) {
        super(String.format("http://localhost:%d", props.port));

        S3Mock api = new S3Mock.Builder().withPort(props.port).withFileBackend(props.path).build();
        this.serverBinding = api.start();
    }

    public void shutdown() {
        this.serverBinding.terminate(
            FiniteDuration.apply(10, TimeUnit.SECONDS));
    }
}
