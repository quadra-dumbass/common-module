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
}

rootProject.name = "common-module"
include("jwt", "controller-advice")