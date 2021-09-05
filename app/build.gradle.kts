import nu.studer.gradle.jooq.JooqEdition
import nu.studer.gradle.jooq.JooqGenerate
import org.jooq.meta.jaxb.Property

plugins {
    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    id("nu.studer.jooq") version "6.0.1"

    application
}

group = "systems.memetic"
version = "0.0.1-SNAPSHOT"
java {
    sourceCompatibility = JavaVersion.VERSION_16

    sourceSets["main"].java {
        srcDir("src/generated")
    }
}

application {
    mainClass.set("systems.memetic.memetiq.MemetiqApplication")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

jooq {
//    version.set(dependencyManagement.importedProperties["jooq.version"])
    edition.set(JooqEdition.OSS)  // the default (can be omitted)

    configurations {
        create("main") {
            jooqConfiguration.apply {
                generator.apply {
                    database.apply {
                        name = "org.jooq.meta.extensions.ddl.DDLDatabase"

                        properties.addAll(
                            arrayOf(
                                // Specify the location of your SQL script.
                                // You may use ant-style file matching, e.g. /path/**/to/*.sql
                                //
                                // Where:
                                // - ** matches any directory subtree
                                // - * matches any number of characters in a directory / file name
                                // - ? matches a single character in a directory / file name
                                Property()
                                    .withKey("scripts")
                                    .withValue("src/main/resources/data.sql")

                                // The sort order of the scripts within a directory, where:
                                //
                                // - semantic: sorts versions, e.g. v-3.10.0 is after v-3.9.0 (default)
                                // - alphanumeric: sorts strings, e.g. v-3.10.0 is before v-3.9.0
                                // - flyway: sorts files the same way as flyway does
                                // - none: doesn"t sort directory contents after fetching them from the directory
//                    property {
//                        key = "sort"
//                        value = "semantic"
//                    }

                                // The default schema for unqualified objects:
                                //
                                // - public: all unqualified objects are located in the PUBLIC (upper case) schema
                                // - none: all unqualified objects are located in the default schema (default)
                                //
                                // This configuration can be overridden with the schema mapping feature
//                    property {
//                        key = "unqualifiedSchema"
//                        value = "none"
//                    }

                                // The default name case for unquoted objects:
                                //
                                // - as_is: unquoted object names are kept unquoted
                                // - upper: unquoted object names are turned into upper case (most databases)
                                // - lower: unquoted object names are turned into lower case (e.g. PostgreSQL)
//                    property {
//                        key = "defaultNameCase"
//                        value = "as_is"
//                    }
                            )
                        )
                    }

                    target.apply {
                        packageName = "systems.memetic.memetiq"
                        directory = "src/generated/jooq"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"

                }
            }
        }
    }
}

tasks.named<JooqGenerate>("generateJooq") {
    allInputsDeclared.set(true)
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

    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("com.h2database:h2")
    implementation("io.r2dbc:r2dbc-h2")
//    implementation("org.flywaydb:flyway-core")

    implementation(platform("software.amazon.awssdk:bom:2.17.24"))
    implementation("software.amazon.awssdk:s3")
    implementation("io.minio:minio:8.3.0")
    implementation("io.findify:s3mock_2.13:0.2.6")

    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    modules {
        module("org.springframework.boot:spring-boot-starter-logging") {
            replacedBy("org.springframework.boot:spring-boot-starter-log4j2", "Use Log4j2 instead of Logback")
        }
    }
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    jooqGenerator("org.jooq:jooq-meta-extensions:3.15.2")
    jooqGenerator("com.h2database:h2:1.4.200")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

