package com.gema.zenit.data

import com.gema.zenit.data.tablas.TablasCategorias
import com.gema.zenit.models.CategoriaResponse
import com.gema.zenit.models.SolicitudCategoria
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object ServicioCategorias {
    fun obtenerTodas(): List<CategoriaResponse> {
        return transaction {
            TablasCategorias.selectAll().map {
                CategoriaResponse(
                    id = it[TablasCategorias.id],
                    nombre = it[TablasCategorias.nombre],
                    icono = it[TablasCategorias.icono]
                )
            }
        }
    }

    fun crearCategoria(datos: SolicitudCategoria, idUsuario: Long): Long { // Cambiado a Long
        return transaction {
            TablasCategorias.insert {
                it[nombre] = datos.nombre
                it[icono] = datos.icono
                it[TablasCategorias.usuarioId] = idUsuario // ¡Aquí está la magia!
            }[TablasCategorias.id].toLong() // (o .toLong() según lo tengas)
        }
    }
}