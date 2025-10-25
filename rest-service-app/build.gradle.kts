import io.github.bhuyanp.gradle.theme.ThemePreset
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.springframework.cloud.contract.verifier.config.TestFramework
import org.springframework.cloud.contract.verifier.config.TestMode

plugins {
    java
    `java-library`
    `maven-publish`
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.github.bhuyanp.spring-banner-gradle-plugin").version("1.1")
    id("org.springframework.cloud.contract") version "4.3.0"
    id("org.openapi.generator") version "7.16.0"
}


extra["springCloudVersion"] = "2025.0.0"

group = "io.github.bhuyanp"

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}

java {
    withSourcesJar()
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}



// Client generation
val clientSourceFolder = "$projectDir/build/clientSdk"
val clientTargetFolder = "$projectDir/src/clientSdk"
sourceSets {
    create("clientSdk") {
        java{
            srcDir("$clientTargetFolder/java")
            compileClasspath += sourceSets["main"].compileClasspath
            runtimeClasspath += sourceSets["main"].runtimeClasspath
        }
    }
}

openApiGenerate {
    //https://openapi-generator.tech/docs/generators/spring
    generatorName.set("spring")
    library.set("spring-http-interface")
    inputSpec.set("$projectDir/rest-service-spec.yaml")
    outputDir.set(clientSourceFolder)
    invokerPackage.set("io.github.bhuyanp.restapp.client")
    apiPackage.set("io.github.bhuyanp.restapp.client.api")
    modelPackage.set("io.github.bhuyanp.restapp.client.model")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8-localdatetime",
            "enumPropertyNaming" to "MACRO_CASE",
            "useSpringBoot3" to "true",
            "useSpringBuiltInValidation" to "true",
            "documentationProvider" to "none",
            "annotationLibrary" to "none",
            "interfaceOnly" to "true",
            "openApiNullable" to "false",
        )
    )
}

tasks.openApiGenerate {
    doFirst {
        delete(clientSourceFolder)
        //delete(clientTargetFolder)
    }
}
val copyClientSdk = tasks.register<Copy>("copyClientSdk") {
    from("$clientSourceFolder/src/main")
    into(clientTargetFolder)
}
val compileClientSdkJavaTask = tasks.named<JavaCompile>("compileClientSdkJava") {
    dependsOn(copyClientSdk)
}
val clientSdkJarTask = tasks.register<Jar>("clientSdkJar") {
    from(compileClientSdkJavaTask)
    archiveBaseName = project.name
    archiveClassifier = "clientsdk"
}

tasks.assemble {
    dependsOn(clientSdkJarTask)
}
// Client generation

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.0")
    runtimeOnly("com.h2database:h2")
    implementation("io.jsonwebtoken:jjwt-api:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.13.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

// Contract Stub Generation and Testing
contracts {
    testFramework = TestFramework.JUNIT5
    testMode = TestMode.MOCKMVC
    contractsDslDir = project.file("$projectDir/src/contractTest/resources/contracts")
    baseClassForTests = "io.github.bhuyanp.restapp.ProductContractBaseTest"
    sourceSet = "contractTest"
}

tasks.processResources {
    filesMatching("application.yaml") {
        expand(properties)
    }
}

tasks.contractTest {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events("passed", "failed", "skipped")
    }
}
// Contract Stub Generation and Testing


tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {

    publications {

        create<MavenPublication>("stubsPublication") {
            artifact(tasks.bootJar)
            artifact(tasks.verifierStubsJar)
            artifact(clientSdkJarTask)
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
    repositories {
        mavenLocal()
    }
}

springBanner{
    themePreset = ThemePreset.SURPRISE_ME
}
