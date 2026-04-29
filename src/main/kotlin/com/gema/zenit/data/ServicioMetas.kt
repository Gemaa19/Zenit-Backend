package com.gema.zenit.data

import com.gema.zenit.data.tablas.*
import com.gema.zenit.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object ServicioMetas {
    fun crearMeta(datos: SolicitudMeta, idUsuario: Long): Long {
        return transaction {
            TablasMetas.insertAndGetId {
                it[nombre] = datos.nombre
                it[objetivo] = datos.objetivo.toBigDecimal()
                it[usuarioId] = idUsuario
                it[fechaLimite] = java.time.LocalDate.parse(datos.fechaLimite)
            }.value // .value extrae el Long para devolverlo
        }
    }

    fun obtenerMetas(idUsuario: Long): List<RespuestaMeta> {
        return transaction {
            TablasMetas.select { TablasMetas.usuarioId eq idUsuario }.map {
                val objetivo = it[TablasMetas.objetivo].toDouble()
                // Aquí podrías tener una lógica de ahorro real, por ahora simulamos con el balance
                val ahorrado = 0.0 // Esto se puede conectar a una tabla de "Aportaciones" más adelante

                RespuestaMeta(
                    id = it[TablasMetas.id].value,
                    nombre = it[TablasMetas.nombre],
                    objetivo = objetivo,
                    ahorrado = ahorrado,
                    progreso = if (objetivo > 0) (ahorrado / objetivo) * 100 else 0.0,
                    completada = ahorrado >= objetivo
                )
            }
        }
    }
}