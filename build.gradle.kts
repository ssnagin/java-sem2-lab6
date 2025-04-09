import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    java
    id("com.github.johnrengelman.shadow") version "7.1.2";
}

dependencies {
    implementation(project(":commons"))
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    implementation("com.google.code.gson:gson:2.12.1")
    implementation("org.apache.commons:commons-lang3:3.17.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testCompileOnly("com.github.peichhorn:lombok-pg:0.11.3")
    testCompileOnly("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.36")

    testImplementation("org.mockito:mockito-core:5.3.1")
} 

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "com.ssnagin.collectionmanager.App"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("collectionmanager")
    archiveVersion.set("1.0.0")
    archiveClassifier.set("")
    manifest {
        attributes["Main-Class"] = "com.ssnagin.collectionmanager.App"
    }
    mergeServiceFiles()
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}

tasks.jar {
    enabled = false
    manifest {
        attributes["Main-Class"] = "com.ssnagin.collectionmanager.App"
    }
}
