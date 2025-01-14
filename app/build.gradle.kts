
plugins {
    kotlin("jvm") version "2.0.21"
    application
}

val lwjglVersion = "3.3.6"

// OSに応じてネイティブライブラリを選択
val lwjglNatives = Pair(
    System.getProperty("os.name")!!,
    System.getProperty("os.arch")!!
).let { (name, arch) ->
    when {
        arrayOf("Mac OS X", "Darwin").any { name.startsWith(it) } ->
            if (arch.startsWith("aarch64")) "natives-macos-arm64"
            else "natives-macos"
        arrayOf("Windows").any { name.startsWith(it) } ->
            if (arch.endsWith("64")) "natives-windows"
            else "natives-windows"
        arrayOf("Linux").any { name.startsWith(it) } ->
            if (arch.endsWith("64")) "natives-linux"
            else "natives-linux"
        else -> throw Error("Unsupported OS: $name")
    }
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    mavenLocal() // ローカルリポジトリを追加
}

dependencies {
    // JUnit Jupiterをテスト用に使用
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    // JUnitプラットフォームランチャーをテストランタイム用に使用
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.1-jre")

    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    implementation("org.lwjgl:lwjgl")
    implementation("org.lwjgl:lwjgl-assimp")
    implementation("org.lwjgl:lwjgl-glfw")
    implementation("org.lwjgl:lwjgl-openal")
    implementation("org.lwjgl:lwjgl-opengl")
    implementation("org.lwjgl:lwjgl-stb")

    // Add JOML dependency
    implementation("org.joml:joml:1.10.5")

    // ネイティブライブラリの依存関係
    listOf(
        "lwjgl",
        "lwjgl-assimp",
        "lwjgl-glfw",
        "lwjgl-openal",
        "lwjgl-opengl",
        "lwjgl-stb"
    ).forEach { lib ->
        runtimeOnly("org.lwjgl:$lib:$lwjglVersion:${lwjglNatives}")
    }

    testImplementation("org.assertj:assertj-core:3.24.2")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("org.lwjgl:lwjgl:$lwjglVersion")
    implementation("org.lwjgl:lwjgl-glfw:$lwjglVersion")
    // 他の LWJGL モジュールも必要に応じて追加

    // ローカルリポジトリから依存関係を追加したら色々解決するのでは
    // mplementation(files("libs/lwjgl.jar"))
    // implementation(files("libs/lwjgl-glfw.jar"))
    // implementation(files("libs/lwjgl-opengl.jar"))
    // implementation(files("libs/lwjgl-stb.jar"))
    // runtimeOnly(files("libs/lwjgl-natives-macos.jar"))
    // runtimeOnly(files("libs/lwjgl-glfw-natives-macos.jar"))
    // runtimeOnly(files("libs/lwjgl-opengl-natives-macos.jar"))
    // runtimeOnly(files("libs/lwjgl-stb-natives-macos.jar"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

application {
    // Define the main class for the application.
    mainClass.set("jp.ac.uryukyu.ie.e245719.Main")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8" // エンコーディングをUTF-8に設定
}

tasks.test {
    // Macの場合に特定のJVM引数を設定
    if (System.getProperty("os.name").lowercase().contains("mac")) {
        jvmArgs = listOf("-XstartOnFirstThread")
    }
    
    // JUnitプラットフォームを使用
    useJUnitPlatform()
}


tasks.jar {
    archiveBaseName.set("minicraft") // JARファイルの基本名を設定
    archiveVersion.set("1.0") // バージョンを設定
    manifest {
        attributes["Main-Class"] = "jp.ac.uryukyu.ie.e245719.Main" // メインクラスを指定
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "jp.ac.uryukyu.ie.e245719.Main"
    }
}


tasks.register<Jar>("fatJar") {
    archiveBaseName.set("minicraft")
    archiveVersion.set("1.0")
    manifest {
        attributes["Main-Class"] = "jp.ac.uryukyu.ie.e245719.Main"
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    // 重複処理戦略を設定
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// 他のタスクに依存関係を追加
tasks.named("distZip") {
    dependsOn("fatJar")
}

tasks.named("distTar") {
    dependsOn("fatJar")
}

tasks.named("startScripts") {
    dependsOn("fatJar")
}
