
plugins {
    `maven-publish`
    `java-library`
    id("org.openapi.generator") version "7.16.0"
    id("de.undercouch.download") version "5.6.0"
}

java {
   withSourcesJar()
}


dependencies {
    api("org.springframework.boot:spring-boot-starter-webflux")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("org.openapitools:jackson-databind-nullable:0.2.7")
}


openApiGenerate {
    //https://openapi-generator.tech/docs/generators/spring
    generatorName.set("spring")
    library.set("spring-http-interface")
    inputSpec.set("./rest-service-spec.yaml")
    outputDir.set("$projectDir/generated/")

    invokerPackage.set("io.github.bhuyanp.restapp.client")
    apiPackage.set("io.github.bhuyanp.restapp.client.api")
    modelPackage.set("io.github.bhuyanp.restapp.client.model")
//    templateDir.set("src/main/resources/templates")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8-localdatetime",
            "enumPropertyNaming" to "MACRO_CASE",
            "useSpringBoot3" to "true",
            "useSpringBuiltInValidation" to "true",
            "documentationProvider" to "none",
            "annotationLibrary" to "none",
            "interfaceOnly" to "true",
        )
    )
}
tasks.openApiGenerate {
    doFirst {
        println("Deleting generated folder $projectDir/generated")
        delete("$projectDir/generated")
        download.run {
            println("Downloading the spec file to $projectDir/rest-service-spec.yaml")
            src("http://localhost:8080/v3/api-docs.yaml")
            dest("$projectDir/rest-service-spec.yaml")
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs("src")
            srcDirs("generated/src")
        }
    }
}


//tasks.compileJava {
//    dependsOn(tasks.openApiGenerate)
//}
//tasks.processResources {
//    dependsOn(tasks.openApiGenerate)
//}
//
//tasks.processTestResources {
//    dependsOn(tasks.openApiGenerate)
//}

tasks.bootJar {
    enabled = false
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}