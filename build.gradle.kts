plugins {
	java
	id("org.springframework.boot") version "4.0.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.mmdev.meowmayo"
version = "0.0.1-SNAPSHOT"
description = "MeowMayo Web Server for Hypixel API"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
    maven("https://jitpack.io/")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("com.github.Querz:NBT:6.1")
    testImplementation("org.springframework.boot:spring-boot-starter-cache-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
	testImplementation("org.springframework.boot:spring-boot-starter-data-redis-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("com.github.Querz:NBT:6.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
