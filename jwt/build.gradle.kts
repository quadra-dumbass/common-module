plugins {
    id("com.quadra.spring-library-conventions") version "1.0.1"
}

version = "0.0.1-SNAPSHOT"

dependencies {

    implementation("org.springframework.boot:spring-boot-starter")

    // Used to implement jwt-related functionality
    implementation("com.auth0:java-jwt:4.4.0")
}