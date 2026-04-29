package com.gema.zenit.data

import com.gema.zenit.data.tablas.TablasUsuarios
import com.gema.zenit.models.LoginUsuario
import com.gema.zenit.models.RegistroUsuarios
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object ServicioUsuarios {
    //Para el Sign Up
    fun crearUsuario(datos: RegistroUsuarios): Long? {
        return try {
            transaction {
                TablasUsuarios.insert {
                    it[username] = datos.username
                    it[email] = datos.email
                    it[password] = datos.password
                } get TablasUsuarios.id
            }
        } catch (e: Exception) {
            println("Error al crear usuario: ${e.message}")
            null
        }
    }
    //Para el Login
    fun buscarPorEmail(email: String) = transaction {
        TablasUsuarios.select { TablasUsuarios.email eq email }
            .map { it[TablasUsuarios.id] to it[TablasUsuarios.password] }
            .singleOrNull()
    }

    //validacion
    fun validarUsuario(login: LoginUsuario): Boolean {
        return transaction {
            // Buscamos si existe un usuario con ese email y esa contraseña
            val usuario = TablasUsuarios.select {
                (TablasUsuarios.email eq login.email) and (TablasUsuarios.password eq login.password)
            }.singleOrNull()

            usuario != null // Devuelve true si lo encuentra, false si no
        }
    }

    fun obtenerIdPorEmail(email: String): Long? {
        return transaction {
            TablasUsuarios.select { TablasUsuarios.email eq email }
                .map { it[TablasUsuarios.id] }
                .singleOrNull()
        }
    }
}