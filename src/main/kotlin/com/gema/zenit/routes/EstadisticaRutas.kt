package com.gema.zenit.routes

import com.gema.zenit.data.ServicioEstadisticas
import com.gema.zenit.data.ServicioUsuarios
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.estadisticaRouting() {
    authenticate("auth-jwt") {
        get("/stats/resumen") {
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()

            if (email != null) {
                val id = ServicioUsuarios.obtenerIdPorEmail(email)
                if (id != null) {
                    val resumen = ServicioEstadisticas.obtenerResumenMesActual(id)
                    call.respond(resumen)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}