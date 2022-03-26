import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    kotlin("jvm") version "1.6.0"
    kotlin("plugin.jpa") version "1.4.32"
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.allopen") version "1.4.31"
}

group = "com.ead"
version = "0.0.1-SNAPSHOT"

extra["springCloudVersion"] = "2021.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

tasks {

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

allOpen {
    annotation("javax.persistence.Entity")
}

dependencies {
    implementations(
        "org.springframework.boot:spring-boot-starter-web",
        "org.springframework.boot:spring-boot-starter-data-jpa",
        "org.springframework.boot:spring-boot-starter-validation",
        "org.springframework.cloud:spring-cloud-starter-netflix-eureka-client",

        "com.fasterxml.jackson.module:jackson-module-kotlin",
        "org.jetbrains.kotlin:kotlin-reflect",
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8",

        "io.github.microutils:kotlin-logging-jvm:2.1.20",
        "net.kaczmarzyk:specification-arg-resolver:2.6.2"
    )

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

fun DependencyHandlerScope.implementations(vararg all: Any) {
    for (impl in all) implementation(impl)
}


