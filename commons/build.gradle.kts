plugins {
    application
    java
}

dependencies {

}


tasks.jar {
    archiveBaseName.set("commons")
    archiveVersion.set("1.0.0")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
