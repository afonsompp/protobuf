import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
	id("org.jetbrains.kotlin.jvm") version "1.4.32"
	id("org.jetbrains.kotlin.kapt") version "1.4.32"
	id("com.github.johnrengelman.shadow") version "7.0.0"
	id("io.micronaut.application") version "1.5.0"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.4.32"
	id("com.google.protobuf") version "0.8.15"
}

version = "0.1"
group = "com.zup.grpc"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
	mavenCentral()
}

micronaut {
	runtime("netty")
	testRuntime("junit5")
	processing {
		incremental(true)
		annotations("com.zup.grpc.*")
	}
}

dependencies {
	implementation("io.micronaut:micronaut-management")
	implementation("io.micronaut:micronaut-runtime")
	implementation("io.micronaut.grpc:micronaut-grpc-client-runtime")
	implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
	implementation("javax.annotation:javax.annotation-api")
	implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
	runtimeOnly("ch.qos.logback:logback-classic")
	implementation("io.micronaut:micronaut-validation")
	runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
	testImplementation("io.micronaut:micronaut-http-client")
}


application {
	mainClass.set("com.zup.grpc.ApplicationKt")
}
java {
	sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
	compileKotlin {
		kotlinOptions {
			jvmTarget = "11"
		}
	}
	compileTestKotlin {
		kotlinOptions {
			jvmTarget = "11"
		}
	}

}
sourceSets {
	main {
		java {
			srcDirs("build/generated/source/proto/main/grpc")
			srcDirs("build/generated/source/proto/main/java")
		}
	}
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:3.14.0"
	}
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.33.1"
		}
	}
	generateProtoTasks {
		ofSourceSet("main").forEach {
			it.plugins {
				// Apply the "grpc" plugin whose spec is defined above, without options.
				id("grpc")
			}
		}
	}
}
