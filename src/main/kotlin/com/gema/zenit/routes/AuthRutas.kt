package com.gema.zenit.routes

import com.gema.zenit.data.ServicioUsuarios
import com.gema.zenit.models.RegistroUsuarios
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.AuthRutas() {
    post("/register") {
        try {
            val datos = call.receive<RegistroUsuarios>()
            ServicioUsuarios.crearUsuario(datos)
            call.respond(HttpStatusCode.Created, "Usuario registrado correctamente")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Error: ${e.message}")
        }
    }
}