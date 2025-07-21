plugins {
    java
    `maven-publish`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-web:6.2.3")
    implementation("org.springframework:spring-webmvc:6.2.3")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.5.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.2")
    implementation("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
}


publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url  = uri("https://maven.pkg.github.com/quadra-dumbass/common-module")
            credentials {
                username = findProperty("GITHUB_USER").toString()
                password = findProperty("GITHUB_TOKEN").toString()
            }
        }
    }
    publications{
        create<MavenPublication>("gpr") {
            from(components["java"])
            groupId = project.group as String
            artifactId = "common-controller-advice"
            version = "1.0.1"
        }
    }
}