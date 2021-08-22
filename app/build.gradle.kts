plugins {
    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    application
}

group = "systems.memetic"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_16

application {
    mainClass.set("systems.memetic.memetiq.MemetiqApplication")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("commons-fileupload:commons-fileupload:1.4")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

//    implementation("org.springframework.boot:spring-boot-starter-jooq")
//    implementation("org.flywaydb:flyway-core")

    implementation(platform("software.amazon.awssdk:bom:2.17.24"))
    implementation("software.amazon.awssdk:s3")
    implementation("io.minio:minio:8.3.0")
    implementation("io.findify:s3mock_2.13:0.2.6")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
