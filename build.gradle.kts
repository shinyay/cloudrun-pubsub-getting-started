import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.1.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("com.gorylenko.gradle-git-properties") version "2.0.0"
	id("com.palantir.git-version") version "0.12.3"
	id("com.google.cloud.tools.jib") version "2.1.0"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
}

group = "com.google.shinyay.cloudrun"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.apache.commons:commons-lang3")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

val project_id = if (hasProperty("docker_project_id")) findProperty("docker_project_id") as String else "library"

// Packaging OCI Images
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
//	builder = "cloudfoundry/cnb:bionic"
	builder = "gcr.io/buildpacks/builder"
	imageName = "docker.io/${project_id}/${project.name}:${project.version}"
}

val username= if (hasProperty("docker_username")) findProperty("docker_username") as String else ""
val password = if (hasProperty("docker_password")) findProperty("docker_password") as String else ""

jib {
	from {
		image = "openjdk:11-slim"
	}
	to {
		image = "registry.hub.docker.com/${project_id}/${project.name}:${project.version}"
		auth.username = username
		auth.password = password
	}
	container {
		jvmFlags = mutableListOf("-Xms512m", "-Xdebug")
		creationTime = "USE_CURRENT_TIMESTAMP"
	}
}

// Build Information For BuildProperties
springBoot {
	buildInfo()
}