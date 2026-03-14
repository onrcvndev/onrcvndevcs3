import java.util.Properties
import org.gradle.api.GradleException

version = 1

fun requireTmdbApiKey(): String {
    val localPropertiesFile = rootProject.file("local.properties")
    if (!localPropertiesFile.exists()) {
        throw GradleException(
            "Missing local.properties. Create it from local.properties.example and set TMDB_API."
        )
    }

    val properties = Properties().apply {
        localPropertiesFile.inputStream().use(::load)
    }

    return properties.getProperty("TMDB_API")
        ?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?: throw GradleException("Missing TMDB_API in local.properties.")
}

android {
    namespace = "com.onurcvncs3"

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    defaultConfig {
        val tmdbApi = requireTmdbApiKey()
        buildConfigField("String", "TMDB_API", "\"$tmdbApi\"")
    }
}

cloudstream {
    description = "[!] Requires Setup\n- Allows you to use any Stremio addon by pasting their manifest.json url"
    authors = listOf("onrcvndev", "Hexated", "phisher98", "erynith")
    status = 1
    tvTypes = listOf("TvSeries", "Movie", "Torrent")
    requiresResources = true
    language = "en"
    iconUrl = "https://files.catbox.moe/ol63rm.png"
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.android.material:material:1.13.0")
}
