package com.gema.zenit.routes

import com.gema.zenit.data.ServicioCategorias
import com.gema.zenit.data.ServicioUsuarios
import com.gema.zenit.models.SolicitudCategoria
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
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
                val principal = call.principal<JWTPrincipal>()

                // 1. Sacamos el email del Token
                val emailUsuario = principal?.payload?.getClaim("email")?.asString()

                if (emailUsuario == null) {
                    call.respond(HttpStatusCode.Unauthorized, "Token inválido")
                    return@post
                }

                // 2. Usamos tu función para buscar el ID real en la base de datos
                val usuarioId = ServicioUsuarios.obtenerIdPorEmail(emailUsuario)

                if (usuarioId == null) {
                    call.respond(HttpStatusCode.Unauthorized, "Usuario no encontrado en BD")
                    return@post
                }

                try {
                    // 3. ¡Todo listo! Enviamos los datos y el ID (que es de tipo Long)
                    val id = ServicioCategorias.crearCategoria(datos, usuarioId)
                    call.respond(HttpStatusCode.Created, "Categoría creada con ID: $id")

                } catch (e: Exception) {
                    e.printStackTrace()
                    call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
                }
            }
        }
    }
}