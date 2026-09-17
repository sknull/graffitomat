package de.visualdigits.graffitomat.domain.model

import java.io.File
import java.nio.file.Paths

object GraffitomatConstants {

    val rootDirectory: File = File(System.getProperty("user.home"), ".graffitomat")

    var thumbnailCacheFolder: File = Paths.get(rootDirectory.canonicalPath, "resources", "thumbnails").toFile()

}
