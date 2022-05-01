import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    kotlin("jvm") version "1.6.0"
    kotlin("plugin.jpa") version "1.4.32"
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.allopen") version "1.4.31"
    kotlin("plugin.noarg") version "1.6.10"
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

noArg {
    annotation("com.ead.course.core.annotations.NoArg")
}

configurations {
    all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
    }
}

dependencies {
    implementations(
        "org.springframework.boot:spring-boot-starter-web",
        "org.springframework.boot:spring-boot-starter-data-jpa",
        "org.springframework.boot:spring-boot-starter-validation",
        "org.springframework.boot:spring-boot-starter-amqp",
        "org.springframework.boot:spring-boot-starter-actuator",
        "org.springframework.cloud:spring-cloud-starter-netflix-eureka-client",
        "org.springframework.cloud:spring-cloud-starter-config",
        "org.springframework.boot:spring-boot-starter-security",
        "org.springframework.boot:spring-boot-starter-log4j2",
        "co.elastic.logging:log4j2-ecs-layout:1.3.2",

        "io.jsonwebtoken:jjwt:0.9.1",
        "org.glassfish.jaxb:jaxb-runtime",

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


