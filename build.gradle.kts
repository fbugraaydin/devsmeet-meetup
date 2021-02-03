import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
	id("org.springframework.boot") version "2.4.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
	kotlin("plugin.jpa") version "1.4.21"
	kotlin("plugin.allopen") version "1.4.21"
	kotlin("kapt") version "1.4.21"
}

group = "com.devsmeet"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	jcenter()
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.mapstruct:mapstruct:1.4.1.Final")
	kapt("org.mapstruct:mapstruct-processor:1.4.1.Final")
	implementation("io.springfox:springfox-swagger2:2.9.2")
	implementation("io.springfox:springfox-swagger-ui:2.9.2")
	testImplementation("org.mockito:mockito-core:2.+")
	testImplementation("org.mockito:mockito-inline:2.+")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		// set options for log level LIFECYCLE
		events = setOf(FAILED, PASSED, SKIPPED, STANDARD_OUT)
		exceptionFormat = TestExceptionFormat.FULL
		showExceptions = true
		showCauses = true
		showStackTraces = true

		// set options for log level DEBUG and INFO
		debug {
			events = setOf(STARTED, FAILED, PASSED, SKIPPED, STANDARD_ERROR, STANDARD_OUT)
			exceptionFormat = TestExceptionFormat.FULL
		}
		info{
			events = debug.events
			exceptionFormat = debug.exceptionFormat
		}
	}
	addTestListener(object : TestListener {
		override fun beforeTest(p0: TestDescriptor?) = Unit
		override fun beforeSuite(p0: TestDescriptor?) = Unit
		override fun afterTest(desc: TestDescriptor, result: TestResult) = Unit
		override fun afterSuite(desc: TestDescriptor, result: TestResult) {
			printResults(desc, result)
		}
	})

}

fun printResults(desc: TestDescriptor, result: TestResult) {
	if (desc.parent != null) {
		val output = result.run {
			"Results: $resultType (" +
					"$testCount tests, " +
					"$successfulTestCount successes, " +
					"$failedTestCount failures, " +
					"$skippedTestCount skipped" +
					")"
		}
		val testResultLine = "|  $output  |"
		val repeatLength = testResultLine.length
		val separationLine = "-".repeat(repeatLength)
		println(separationLine)
		println(testResultLine)
		println(separationLine)
	}
}
