import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.springframework.cloud.contract.verifier.config.TestFramework
import org.springframework.cloud.contract.verifier.config.TestMode

plugins {
    java
    `maven-publish`
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.github.bhuyanp.spring-banner-gradle-plugin").version("1.1")
    id("org.springframework.cloud.contract") version "4.3.0"
}

extra["springCloudVersion"] = "2025.0.0"

allprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    group = "io.github.bhuyanp"

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(25)
        }
    }
    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }
}
tasks.processResources {
    filesMatching("application.yaml") {
        expand(properties)
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    implementation("io.jsonwebtoken:jjwt-api:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.13.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testImplementation("io.rest-assured:spring-web-test-client")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

contracts {
    testFramework = TestFramework.JUNIT5
    testMode = TestMode.MOCKMVC
    contractsDslDir = project.file("$projectDir/src/contractTest/resources/contracts")
    baseClassForTests = "io.github.bhuyanp.restapp.ProductContractBaseTest"
    sourceSet = "contractTest"
}

tasks.withType<Test> {
    //dependsOn(tasks.generateOpenApiDocs)
    useJUnitPlatform()
}

tasks.contractTest{
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events("passed","failed", "skipped")
    }
}

publishing {
    publications {
        create<MavenPublication>("stubsPublication") {
            artifact(tasks.verifierStubsJar)
            artifact(tasks.bootJar)
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }
    }
    repositories{
        mavenLocal()
    }
}
