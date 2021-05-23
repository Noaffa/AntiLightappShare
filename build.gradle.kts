plugins {
    kotlin("jvm") version "1.4.32"

    id("net.mamoe.mirai-console") version "2.6.4"
}

group = "org.yorin"
version = "0.13.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.code.gson:gson:2.8.2")
}
