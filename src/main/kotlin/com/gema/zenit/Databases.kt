package com.gema.zenit

import io.ktor.server.application.*

fun Application.configureDatabases() {
    val jdbcUrl = environment.config.property("storage.jdbcURL").getString()
    val driverClass = environment.config.property("storage.driverClassName").getString()
    val user = environment.config.property("storage.user").getString()
    val password = environment.config.property("storage.password").getString()

    org.jetbrains.exposed.sql.Database.connect(
        url = jdbcUrl,
        driver = driverClass,
        user = user,
        password = password
    )
}