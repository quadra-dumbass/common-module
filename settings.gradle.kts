pluginManagement {
    val githubPluginUser: String? = providers.gradleProperty("gpr.plugin.user").orNull ?: System.getenv("GITHUB_PLUGIN_USER")
    val githubPluginToken: String? = providers.gradleProperty("gpr.plugin.token").orNull ?: System.getenv("GITHUB_PLUGIN_TOKEN")
    repositories {
        gradlePluginPortal()
        maven {
            url = uri("https://maven.pkg.github.com/quadra-dumbass/convention-plugins")
            credentials {
                username = githubPluginUser
                password = githubPluginToken
            }
        }
    }
    plugins {
        id("com.quadra.spring-library-conventions") version "1.1.0"
    }
}

rootProject.name = "common-module"
include("jwt", "controller-advice", "cookie")
