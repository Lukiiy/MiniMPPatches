plugins {
    java
    id("fabric-loom") version "1.19.3"
    id("ploceus") version "1.19.1"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
}

base.archivesName.set(project.property("name").toString())

loom {}

ploceus.setIntermediaryGeneration(2)

version = project.property("version").toString()
group = project.property("group").toString()

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraftVer")}")
    modImplementation("net.fabricmc:fabric-loader:${property("loaderVer")}")
    modCompileOnly(files("lib/f3mod-1.2.jar"))

    clientExceptions(ploceus.raven(project.property("clientRaven").toString(), "client"))
    serverExceptions(ploceus.raven(project.property("serverRaven").toString(), "server"))
    clientSignatures(ploceus.sparrow(project.property("clientSparrow").toString(), "client"))
    serverSignatures(ploceus.sparrow(project.property("serverSparrow").toString(), "server"))
    clientNests(ploceus.nests(project.property("clientNests").toString(), "client"))
    serverNests(ploceus.nests(project.property("serverNests").toString(), "server"))
    mappings(ploceus.featherMappings(project.property("feather").toString()))

    ploceus.dependOsl(project.property("oslVer").toString())
}

tasks {
    jar {
        from("LICENSE") {
            rename { "${it}_${base.archivesName.get()}" }
        }
    }

    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
}