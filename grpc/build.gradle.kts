import com.google.protobuf.gradle.*

plugins {
    java
    idea
    id("com.google.protobuf") version "0.8.18"
}

val junitVersion: String by project
val grpcVersion: String by project
val protocVersion: String by project
val protocGenGrpcJava: String by project

repositories{
    mavenCentral()
}

dependencies {
    implementation("io.grpc:grpc-netty:$grpcVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("io.grpc:grpc-stub:$grpcVersion")

    // testImplementation("io.grpc:grpc-testing:${grpcVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.test {
    useJUnitPlatform()
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protocVersion"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$protocGenGrpcJava"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}


idea {
    module {
        generatedSourceDirs.addAll(listOf(
            file("${protobuf.protobuf.generatedFilesBaseDir}/main/grpc"),
            file("${protobuf.protobuf.generatedFilesBaseDir}/main/java")
        ))
    }
}