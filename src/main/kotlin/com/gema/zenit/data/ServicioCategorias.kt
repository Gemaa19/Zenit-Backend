package com.gema.zenit.data

import com.gema.zenit.data.tablas.TablasCategorias
import com.gema.zenit.models.CategoriaResponse
import com.gema.zenit.models.SolicitudCategoria
import org.jetbrains.exposed.sql.*
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

    fun crearCategoria(datos: SolicitudCategoria): Long {
        return transaction {
            TablasCategorias.insert {
                it[nombre] = datos.nombre
                it[icono] = datos.icono
            }[TablasCategorias.id]
        }
    }
}

