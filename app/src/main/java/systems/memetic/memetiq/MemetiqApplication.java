package systems.memetic.memetiq;

import org.h2.jdbcx.JdbcDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import systems.memetic.memetiq.service.object.MockObjectStorageProvider;
import systems.memetic.memetiq.service.object.ObjectStorageProvider;
import systems.memetic.memetiq.service.object.S3StorageProvider;

import javax.sql.DataSource;
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

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();

        dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
        dataSource.setUser(environment.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public DefaultDSLContext dsl(DataSource dataSource) {
        return new DefaultDSLContext(dataSource, SQLDialect.DEFAULT);
    }
}
