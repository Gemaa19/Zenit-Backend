package com.gema.zenit.data

import com.gema.zenit.data.tablas.TablasTransacciones
import com.gema.zenit.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

object ServicioTransacciones {
    fun crearTransaccion(datos: SolicitudTransaccion, usuarioId: Long) {
        transaction {
            TablasTransacciones.insert {
                it[monto] = datos.monto.toBigDecimal()
                it[descripcion] = datos.descripcion
                it[tipo] = datos.tipo
                it[fecha] = LocalDate.now()
                it[this.usuarioId] = usuarioId
                it[categoriaId] = datos.categoriaId
            }
        }
    }

    fun obtenerTransaccionesUsuario(idUsuario: Long): List<TransaccionResponse> {
        return transaction {
            TablasTransacciones.select { TablasTransacciones.usuarioId eq idUsuario }
                .map {
                    TransaccionResponse(
                        id = it[TablasTransacciones.id],
                        monto = it[TablasTransacciones.monto].toDouble(),
                        descripcion = it[TablasTransacciones.descripcion],
                        tipo = it[TablasTransacciones.tipo],
                        fecha = it[TablasTransacciones.fecha].toString(),
                        categoriaId = it[TablasTransacciones.categoriaId]
                    )
                }
        }
    }

    fun borrarTransaccion(idTransaccion: Long, idUsuario: Long): Boolean {
        return transaction {
            // Devuelve el número de filas borradas (debería ser 1)
            val filasBorradas = TablasTransacciones.deleteWhere {
                (id eq idTransaccion) and (usuarioId eq idUsuario)
            }
            filasBorradas > 0
        }
    }

    fun editarTransaccion(idTransaccion: Long, idUsuario: Long, datos: SolicitudTransaccion): Boolean {
        return transaction {
            val filasActualizadas = TablasTransacciones.update({
                (TablasTransacciones.id eq idTransaccion) and (TablasTransacciones.usuarioId eq idUsuario)
            }) {
                it[monto] = datos.monto.toBigDecimal()
                it[descripcion] = datos.descripcion
                it[tipo] = datos.tipo
                it[categoriaId] = datos.categoriaId
            }
            filasActualizadas > 0
        }
    }
}