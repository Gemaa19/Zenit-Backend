package com.gema.zenit

import com.gema.zenit.data.tablas.TablasAlertas
import com.gema.zenit.data.tablas.TablasCategorias
import com.gema.zenit.data.tablas.TablasMetas
import com.gema.zenit.data.tablas.TablasObjetivosAhorro
import com.gema.zenit.data.tablas.TablasPresupuestos
import com.gema.zenit.data.tablas.TablasTransacciones
import com.gema.zenit.data.tablas.TablasUsuarios
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    // Ktor lee esto desde tu application.yaml
    val driverClass = environment.config.property("storage.driverClassName").getString()
    val dbUrl = environment.config.property("storage.jdbcURL").getString()
    val dbUser = environment.config.property("storage.user").getString()
    val dbPassword = environment.config.property("storage.password").getString()

    Database.connect(
        url = dbUrl,
        driver = driverClass,
        user = dbUser,
        password = dbPassword
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