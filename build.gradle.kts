plugins {
  id("java")
  id("org.jetbrains.intellij.platform") version "2.19.0"
}

group = "org.haokee"
version = "0.1.2"

repositories {
  mavenCentral()
  intellijPlatform {
    defaultRepositories()
  }
}

dependencies {
  intellijPlatform {
    intellijIdea("2026.2.3")
    bundledPlugin("org.jetbrains.kotlin")
  }
}

intellijPlatform {
  pluginConfiguration {
    ideaVersion {
      sinceBuild = "242"
    }
  }
}
