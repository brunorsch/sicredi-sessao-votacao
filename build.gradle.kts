plugins {
	java
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "io.github.brunorsch"
version = "1.0.0"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	val commonsLangVersion = "3.14.0"
	val easyRandomVersion = "5.0.0"
	val jsr305Version = "3.0.2"
	val mapStructVersion = "1.5.5.Final"
	val mapStructBindingVersion = "0.2.0"
	val springdocVersion = "2.3.0"

	annotationProcessor("org.mapstruct:mapstruct-processor:$mapStructVersion")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:$mapStructBindingVersion")
	annotationProcessor("org.projectlombok:lombok")

	compileOnly("org.projectlombok:lombok")

	implementation("com.google.code.findbugs:jsr305:$jsr305Version")
	implementation("org.apache.commons:commons-lang3:$commonsLangVersion")
	implementation("org.liquibase:liquibase-core")
	implementation("org.mapstruct:mapstruct:$mapStructVersion")
	implementation("org.springdoc:springdoc-openapi-starter-common:$springdocVersion")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.kafka:spring-kafka")

	runtimeOnly("org.postgresql:postgresql")

	// Test related
	testAnnotationProcessor("org.projectlombok:lombok")
	testAnnotationProcessor("org.mapstruct:mapstruct-processor:$mapStructVersion")
	testAnnotationProcessor("org.projectlombok:lombok-mapstruct-binding:$mapStructBindingVersion")

	testCompileOnly("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("org.jeasy:easy-random-core:$easyRandomVersion")
	testImplementation("com.h2database:h2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}