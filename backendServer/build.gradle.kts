group = "de.visualdigits"
version = "1.0.0-SNAPSHOT"
description = "graffitomat-server"
java.sourceCompatibility = JavaVersion.VERSION_21

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.kotlin.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.spring.boot.plugin)
    alias(libs.plugins.spring.boot.dependency.management)
    alias(libs.plugins.kotlin.spring)
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation(libs.bundles.spring.boot)
    implementation(libs.bundles.kotlin)
    implementation(libs.net.coobird.thumbnailator)
    implementation(libs.org.apache.commons.commons.text)
    implementation(libs.org.webjars.bootstrap)
    implementation(libs.framebuffer)
    implementation(libs.bannermatic)
    implementation(libs.svg.loader)

    testImplementation(libs.bundles.spring.boot.test)
    testRuntimeOnly(libs.platform.junit.platform.launcher)
}

tasks.withType<Test> {
    useJUnitPlatform()

    maxHeapSize = "2g" // Erhöht den Speicher für den Konsolen-Build
    jvmArgs("-XX:+EnableDynamicAgentLoading") // (Falls du die Mockito-Warnung unterdrücken willst)
}

val testsJar by tasks.registering(Jar::class) {
    archiveClassifier = "tests"
    from(sourceSets["test"].output)
}

java {
    withSourcesJar()
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        artifact(testsJar)
        artifactId = "graffitomat-server"
    }

    repositories {
        mavenLocal()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/sknull/graffitomat")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
