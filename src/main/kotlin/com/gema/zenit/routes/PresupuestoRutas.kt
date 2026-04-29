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

fun Route.presupuestoRouting() {
    authenticate("auth-jwt") {
        route("/presupuestos") {
            get {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()
                val id = ServicioUsuarios.obtenerIdPorEmail(email!!)
                call.respond(ServicioPresupuestos.obtenerPresupuestosUsuario(id!!))
            }
            post {
                val datos = call.receive<SolicitudPresupuesto>()
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()
                val id = ServicioUsuarios.obtenerIdPorEmail(email!!)

                ServicioPresupuestos.crearPresupuesto(datos, id!!)
                call.respond(HttpStatusCode.Created, "Presupuesto establecido")
            }
        }
    }
}