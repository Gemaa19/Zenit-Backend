package com.gema.zenit

import com.gema.zenit.routes.*
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        //rutas sin token
        authRouting()

        //rutas protegidas
        authenticate("auth-jwt") {
            transaccionRouting()
            categoriaRouting()
            estadisticaRouting()
            metaRouting()
            presupuestoRouting()
        }
    }
}