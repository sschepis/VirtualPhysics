import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version "1.9.22"
    application
    `maven-publish`
}


group = "com.monastery"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("MainKt")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.monastery"
            artifactId = "virtual-physics"
            version = "1.0.0-SNAPSHOT"
            
            from(components["java"])
            
            pom {
                name.set("Virtual Physics")
                description.set("A revolutionary physics-inspired framework for building robust, reliable software systems")
                url.set("https://github.com/sschepis/VirtualPhysics")
                
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                
                developers {
                    developer {
                        id.set("sschepis")
                        name.set("Sebastian Schepis")
                    }
                }
                
                scm {
                    connection.set("scm:git:git://github.com/sschepis/VirtualPhysics.git")
                    developerConnection.set("scm:git:ssh://github.com/sschepis/VirtualPhysics.git")
                    url.set("https://github.com/sschepis/VirtualPhysics")
                }
            }
        }
    }
}
