plugins {
    id("com.quadra.spring-library-conventions") version "1.1.0"
}

version = "0.0.1-SNAPSHOT"

dependencies {

    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-context")
    // Used to implement jwt-related functionality
    implementation("com.auth0:java-jwt:4.4.0")
}