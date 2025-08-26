plugins {
    id("com.quadra.spring-library-conventions")
}

version = "1.0.0-SNAPSHOT"

dependencies {
    implementation("org.springframework:spring-web")
    implementation("org.springframework:spring-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
}