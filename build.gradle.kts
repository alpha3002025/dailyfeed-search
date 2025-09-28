plugins {
	java
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
}

allprojects {
    group = "click.dailyfeed"
    version = "0.0.1-SNAPSHOT"
    description = "Demo project for Spring Boot"

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    val mapstructVersion = "1.5.4.Final"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(project(":dailyfeed-code"))
        implementation(project(":dailyfeed-feign"))
        implementation(project(":dailyfeed-pagination-support"))

        implementation("org.mapstruct:mapstruct:${mapstructVersion}")
        annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")

        implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
        implementation("org.springframework.boot:spring-boot-starter-web")
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}