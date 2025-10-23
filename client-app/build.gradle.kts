import io.github.bhuyanp.gradle.theme.ThemePreset

plugins {
	java
	id("org.springframework.boot") version "3.5.6"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.github.bhuyanp.spring-banner-gradle-plugin") version "1.1"
}

group = "io.github.bhuyanp"

extra["springCloudVersion"] = "2025.0.0"

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

tasks.processResources {
	filesMatching("application.yaml") {
		expand(properties)
	}
}

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("com.github.ben-manes.caffeine:caffeine")
	implementation("io.github.bhuyanp:rest-service-app:+:clientsdk")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
	testImplementation("io.github.bhuyanp:rest-service-app:+:stubs")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
}
dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

springBanner{
	themePreset = ThemePreset.SURPRISE_ME
}