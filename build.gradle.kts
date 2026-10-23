plugins {
    id("java")
    id("me.champeau.jmh") version "0.7.3"
}

group = "me.andreasmelone"
version = "1.0-SNAPSHOT"

val jmhIncludes = findProperty("jmhIncludes") as String?

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jmh {
    if (jmhIncludes != null) {
        includes.set(listOf(jmhIncludes))
    }

    profilers.set(listOf("gc", "comp", "stack"))
    resultFormat = "JSON"
    resultsFile = project.layout.buildDirectory.file("reports/jmh/results.json")
    warmupIterations = 3
    iterations = 8
    fork = 0
    timeOnIteration = "5s"
    warmup = "5s"
    threads = 1
}