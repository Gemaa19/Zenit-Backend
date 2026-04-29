package com.gema.zenit.routes

import com.auth0.jwt.JWT
import com.gema.zenit.data.ServicioUsuarios
import com.gema.zenit.models.LoginUsuario
import com.gema.zenit.models.RegistroUsuarios
import com.gema.zenit.models.RespuestaAutenticacion
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRouting() {
    route("/auth") {
        // ENDPOINT DE REGISTRO
        post("/register") {
            val datos = call.receive<RegistroUsuarios>()
            val id = ServicioUsuarios.crearUsuario(datos)

            if (id != null) {
                call.respond(HttpStatusCode.Created, "Usuario registrado con éxito")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Error: El email o usuario ya existen")
            }
        }

        // ENDPOINT DE LOGIN
        post("/login") {
            val datos = call.receive<LoginUsuario>()
            val esValido = ServicioUsuarios.validarUsuario(datos)

            if (esValido) {
                val config = call.application.environment.config
                val jwtSecret = config.property("jwt.secret").getString()
                val jwtIssuer = config.property("jwt.issuer").getString()
                val jwtAudience = config.property("jwt.audience").getString()

                val token = JWT.create()
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .withClaim("email", datos.email) // Guardamos el email dentro del token
                    .withExpiresAt(java.util.Date(System.currentTimeMillis() + 86400000)) // 24h
                    .sign(com.auth0.jwt.algorithms.Algorithm.HMAC256(jwtSecret))

                call.respond(RespuestaAutenticacion(token = token, username = datos.email))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Email o contraseña incorrectos")
            }
        }


    }
}



