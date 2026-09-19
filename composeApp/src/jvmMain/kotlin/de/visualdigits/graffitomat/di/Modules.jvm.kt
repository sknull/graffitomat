package de.visualdigits.graffitomat.di


actual val isDevMode: Boolean
    get() = (System.getProperty("graffitomat.dev") ?: "false").toBoolean()
