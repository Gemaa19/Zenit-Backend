package com.gema.zenit.data

import com.gema.zenit.models.RegistroUsuarios
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object ServicioUsuarios {
    fun crearUsuario(datos: RegistroUsuarios) {
        transaction {
            TablasUsuarios.insert {
                it[username] = datos.username
                it[email] = datos.email
                it[password] = datos.password // Aquí es donde el lunes pondremos el cifrado
            }
        }
    }
}