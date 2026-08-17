pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "JobTrackAI"

// ---------------------------------------------------------------------------------------------
// App module
// ---------------------------------------------------------------------------------------------
include(":app")

// ---------------------------------------------------------------------------------------------
// Core modules — shared infrastructure, no feature-specific UI
// ---------------------------------------------------------------------------------------------
include(":core:common")
include(":core:designsystem")
include(":core:database")
include(":core:datastore")
include(":core:network")
include(":core:sync")
include(":core:notifications")
include(":core:di")

// ---------------------------------------------------------------------------------------------
// Feature modules — each owns data/domain/presentation for its slice of the app
// ---------------------------------------------------------------------------------------------
include(":feature:onboarding")
include(":feature:auth")
include(":feature:profile")
include(":feature:resume")
include(":feature:jobs")
include(":feature:applications")
include(":feature:interviews")
include(":feature:ai")
include(":feature:speech")
include(":feature:analytics")
include(":feature:settings")
