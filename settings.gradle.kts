rootProject.name = "collectionManager"

include("common")
include("server")
include("client")
include("server:main.java.com.ssnagin.collectionmanager.server")
findProject(":server:main.java.com.ssnagin.collectionmanager.server")?.name = "main.java.com.ssnagin.collectionmanager.server"
