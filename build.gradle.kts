import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // Base de Datos H2 Driver JDBC
    implementation("com.h2database:h2:2.1.214")
    // HikariCP Pool de conexiones
    implementation("com.zaxxer:HikariCP:5.0.1")
    // MyBatis para scripts SQL y otras utilidades
    implementation("org.mybatis:mybatis:3.5.11")
    // Para hacer el logging
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.2")
    implementation("ch.qos.logback:logback-classic:1.4.4")
    implementation("org.jetbrains.kotlinx:dataframe:0.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}