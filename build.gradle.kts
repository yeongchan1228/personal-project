import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
}

group = "personal-project"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

val frontendDir = "$projectDir/frontend"

sourceSets {
    main {
        resources {
            srcDirs("$projectDir/src/main/resources")
        }
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    val installReact by register<Exec>("installReact") {
        workingDir(frontendDir)
        inputs.dir(frontendDir)
        group = BasePlugin.BUILD_GROUP
        val yarnCommand = if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("windows")) {
            "yarn.cmd"
        } else {
            "yarn"
        }
        commandLine(yarnCommand, "install")
    }

    val buildReact by register<Exec>("buildReact") {
        dependsOn(installReact)
        workingDir(frontendDir)
        inputs.dir(frontendDir)
        group = BasePlugin.BUILD_GROUP
        val yarnCommand = if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("windows")) {
            "yarn.cmd"
        } else {
            "yarn"
        }
        commandLine(yarnCommand, "build")
    }

    register<Copy>("copyReactBuildFiles") {
        dependsOn(buildReact)
        from("$frontendDir/build")
        into("$projectDir/src/main/resources/static")
    }

    processResources {
        dependsOn("copyReactBuildFiles")
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}


