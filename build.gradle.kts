plugins {
    kotlin("jvm") version "1.4.32"

    id("net.mamoe.mirai-console") version "2.5.2"
}

group = "org.yorin"
version = "0.10.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.code.gson:gson:2.8.2")
}
