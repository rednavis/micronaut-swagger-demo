import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val micronautVersion: String by project

plugins {
    kotlin("jvm") version embeddedKotlinVersion
    kotlin("kapt") version embeddedKotlinVersion
    kotlin("plugin.allopen") version embeddedKotlinVersion
    id("net.ltgt.apt-eclipse") version "0.21"
    id("com.github.johnrengelman.shadow") version "5.0.0"
    application
}

version = "0.1"
group = "com.rednavis.micronaut.petstore"

repositories {
    mavenCentral()
    maven(url = "https://jcenter.bintray.com")
}

// for dependencies that are needed for development only
val developmentOnly: Configuration by configurations.creating

dependencies {
    kapt(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")
    kapt("io.micronaut.configuration:micronaut-openapi")

    implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    implementation("io.micronaut:micronaut-inject")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-http-client")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.swagger.core.v3:swagger-annotations")

    runtimeOnly("ch.qos.logback:logback-classic:1.2.3")

    kaptTest(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kaptTest("io.micronaut:micronaut-inject-java")

    testImplementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("io.kotlintest:kotlintest-assertions:3.2.1")
    testImplementation("io.ktor:ktor-client-apache:1.3.0")
    testImplementation("io.ktor:ktor-client-jackson:1.3.0")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.3.3")
}

application {
    mainClassName = "com.rednavis.micronaut.petstore.Application"
}

// use JUnit 5 platform
tasks.withType<Test> {
    classpath += developmentOnly
    useJUnitPlatform()
}

allOpen {
    annotation("io.micronaut.aop.Around")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
        javaParameters = true
    }
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}

tasks.withType<JavaExec> {
    classpath += developmentOnly
    jvmArgs("-noverify", "-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
}
