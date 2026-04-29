package com.gema.zenit.data

import com.gema.zenit.data.tablas.*
import com.gema.zenit.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insertAndGetId

object ServicioPresupuestos {
    fun crearPresupuesto(datos: SolicitudPresupuesto, idUsuario: Long): Long {
        return transaction {
            TablasPresupuestos.insertAndGetId {
                it[montoLimite] = datos.montoLimite.toBigDecimal()
                it[mes] = datos.mes.toByte()
                it[anio] = datos.anio
                it[usuarioId] = idUsuario
                it[categoriaId] = datos.categoriaId
            }.value
        }
    }

    fun obtenerPresupuestosUsuario(idUsuario: Long): List<RespuestaPresupuesto> {
        return transaction {
            (TablasPresupuestos innerJoin TablasCategorias)
                .select { TablasPresupuestos.usuarioId eq idUsuario }
                .map {
                    RespuestaPresupuesto(
                        id = it[TablasPresupuestos.id].value,
                        montoLimite = it[TablasPresupuestos.montoLimite].toDouble(),
                        categoriaId = it[TablasPresupuestos.categoriaId],
                        nombreCategoria = it[TablasCategorias.nombre],
                        mes = it[TablasPresupuestos.mes].toInt(),
                        anio = it[TablasPresupuestos.anio]
                    )
                }
        }
    }
}