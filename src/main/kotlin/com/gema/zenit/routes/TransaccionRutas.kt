package com.gema.zenit.routes // Corregido el package

import com.gema.zenit.data.ServicioUsuarios
import com.gema.zenit.models.SolicitudTransaccion
import com.gema.zenit.data.ServicioTransacciones
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.transaccionRouting() {
    authenticate("auth-jwt") {
        route("/transacciones") {
            post {
                val datos = call.receive<SolicitudTransaccion>()

                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()

                if (email != null) {
                    val usuarioId = ServicioUsuarios.obtenerIdPorEmail(email)

                    if (usuarioId != null) {
                        ServicioTransacciones.crearTransaccion(datos, usuarioId)
                        call.respond(HttpStatusCode.Created, "Transacción guardada")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
                    }
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Token inválido")
                }
            }

            get {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()

                if (email != null) {
                    val usuarioId = ServicioUsuarios.obtenerIdPorEmail(email)
                    if (usuarioId != null) {
                        val lista = ServicioTransacciones.obtenerTransaccionesUsuario(usuarioId)
                        call.respond(lista) // Ktor convierte la lista a JSON automáticamente
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
                    }
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Token inválido")
                }
            }

            route("/{id}") {
                delete {
                    val idTransaccion = call.parameters["id"]?.toLongOrNull()
                    val principal = call.principal<JWTPrincipal>()
                    val email = principal?.payload?.getClaim("email")?.asString()

                    if (idTransaccion != null && email != null) {
                        val usuarioId = ServicioUsuarios.obtenerIdPorEmail(email)
                        val borradoExitoso = ServicioTransacciones.borrarTransaccion(idTransaccion, usuarioId!!)

                        if (borradoExitoso) {
                            call.respond(HttpStatusCode.OK, "Transacción eliminada")
                        } else {
                            call.respond(HttpStatusCode.NotFound, "No se encontró la transacción o no tienes permiso")
                        }
                    }
                }

                put {
                    val idTransaccion = call.parameters["id"]?.toLongOrNull()
                    val datos = call.receive<SolicitudTransaccion>()
                    val principal = call.principal<JWTPrincipal>()
                    val email = principal?.payload?.getClaim("email")?.asString()

                    if (idTransaccion != null && email != null) {
                        val usuarioId = ServicioUsuarios.obtenerIdPorEmail(email)
                        val editadoExitoso = ServicioTransacciones.editarTransaccion(idTransaccion, usuarioId!!, datos)

                        if (editadoExitoso) {
                            call.respond(HttpStatusCode.OK, "Transacción actualizada")
                        } else {
                            call.respond(HttpStatusCode.NotFound, "Error al actualizar")
                        }
                    }
                }
            }
        }
    }
}