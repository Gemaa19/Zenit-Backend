package com.gema.zenit.routes

import com.gema.zenit.data.*
import com.gema.zenit.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.metaRouting() {
    authenticate("auth-jwt") {
        route("/metas") {
            get {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()
                val id = ServicioUsuarios.obtenerIdPorEmail(email!!)
                call.respond(ServicioMetas.obtenerMetas(id!!))
            }
            post {
                val datos = call.receive<SolicitudMeta>()
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()
                val id = ServicioUsuarios.obtenerIdPorEmail(email!!)

                val metaId = ServicioMetas.crearMeta(datos, id!!)
                call.respond(HttpStatusCode.Created, "Meta creada con éxito")
            }
        }
    }
}