package com.gema.zenit.routes

import com.gema.zenit.data.ServicioCategorias
import com.gema.zenit.models.SolicitudCategoria
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoriaRouting() {
    route("/categorias") {
        get {
            val lista = ServicioCategorias.obtenerTodas()
            call.respond(lista)
        }
        //crear categorias con token
        authenticate("auth-jwt") {
            post {
                val datos = call.receive<SolicitudCategoria>()
                try {
                    val id = ServicioCategorias.crearCategoria(datos)
                    call.respond(HttpStatusCode.Created, "Categoría creada con ID: $id")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error al crear la categoría (quizás ya existe)")
                }
            }
        }
    }
}