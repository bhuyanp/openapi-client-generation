plugins {
	java
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "io.github.bhuyanp.restapp"
version = "0.0.1"

java.toolchain.languageVersion = JavaLanguageVersion.of(25)

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
	testCompileOnly {
		extendsFrom(configurations.testAnnotationProcessor.get())
	}
}
ext["junit-jupiter.version"] = "6.0.0"
repositories {
	mavenCentral()
	mavenLocal()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("com.github.ben-manes.caffeine:caffeine")
	implementation("io.github.bhuyanp:rest-service-appclient:0.0.2")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testCompileOnly("org.projectlombok:lombok")
	testAnnotationProcessor("org.projectlombok:lombok")
	implementation(platform("io.cucumber:cucumber-bom:7.31.0"))
	implementation(platform("org.junit:junit-bom:6.0.0"))
	testImplementation("io.cucumber:cucumber-java")
	testImplementation("io.cucumber:cucumber-spring")
	testImplementation("io.cucumber:cucumber-junit-platform-engine")
	testImplementation("org.junit.platform:junit-platform-suite")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testImplementation("org.junit.jupiter:junit-jupiter-engine")

}


val cucumberFilterTags = project.properties["cucumber.filter.tags"] ?: ""
println("cucumberFilterTags: $cucumberFilterTags")
tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		events("passed", "failed", "skipped")
	}
	// Work around. Gradle does not include enough information to disambiguate
	// between different examples and scenarios.
	systemProperty("cucumber.junit-platform.naming-strategy", "long")
	systemProperty("cucumber.filter.tags", cucumberFilterTags)
}