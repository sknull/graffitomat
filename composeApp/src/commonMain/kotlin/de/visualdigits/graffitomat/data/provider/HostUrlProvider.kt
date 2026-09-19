package de.visualdigits.graffitomat.data.provider

class HostUrlProvider(
    isDevMode: Boolean
) {

    val hostUrl: String = if (isDevMode) {
        "http://localhost:8080/api/v1"
    } else {
        "http://192.168.178.61:8080/api/v1"
    }
}
