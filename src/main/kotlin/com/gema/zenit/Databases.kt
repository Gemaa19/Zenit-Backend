package com.gema.zenit

import com.gema.zenit.data.tablas.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val jdbcUrl = environment.config.property("storage.jdbcURL").getString()
    val driverClass = environment.config.property("storage.driverClassName").getString()
    val user = environment.config.property("storage.user").getString()
    val password = environment.config.property("storage.password").getString()

    // 1. Conexión
    Database.connect(
        url = jdbcUrl,
        driver = driverClass,
        user = user,
        password = password
    )

    // 2. Creación automática de tablas
    transaction {
        SchemaUtils.create(
            TablasUsuarios,
            TablasCategorias,      // Categorías depende de Usuarios
            TablasTransacciones,   // Transacciones depende de Usuarios y Categorías
            TablasObjetivosAhorro,
            TablasAlertas,
            TablasPresupuestos,
            TablasMetas
        )
    }
}