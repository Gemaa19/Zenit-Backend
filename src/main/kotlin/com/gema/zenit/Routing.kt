package com.gema.zenit

import com.gema.zenit.routes.AuthRutas
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        AuthRutas()

        get("/") {
            call.respondText("¡Servidor Zenit funcionando!")
        }
    }
}