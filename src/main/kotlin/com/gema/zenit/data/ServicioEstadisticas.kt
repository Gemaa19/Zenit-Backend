package com.gema.zenit.data

import com.gema.zenit.data.tablas.TablasTransacciones
import com.gema.zenit.models.ResumenMensual
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.month
import org.jetbrains.exposed.sql.javatime.year
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

object ServicioEstadisticas {
    fun obtenerResumenMesActual(idUsuario: Long): ResumenMensual {
        return transaction {
            val ahora = LocalDate.now()

            // Función auxiliar para sumar según el tipo
            fun sumarPorTipo(tipoBusqueda: String): Double {
                return TablasTransacciones
                    .slice(TablasTransacciones.monto.sum())
                    .select {
                        (TablasTransacciones.usuarioId eq idUsuario) and
                                (TablasTransacciones.tipo eq tipoBusqueda) and
                                (TablasTransacciones.fecha.month() eq ahora.monthValue) and
                                (TablasTransacciones.fecha.year() eq ahora.year)
                    }
                    .map { it[TablasTransacciones.monto.sum()] ?: java.math.BigDecimal.ZERO }
                    .first().toDouble()
            }

            val ingresos = sumarPorTipo("INGRESO")
            val gastos = sumarPorTipo("GASTO")
            val nombreMes = ahora.month.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
                .replaceFirstChar { it.uppercase() }
            ResumenMensual(
                totalIngresos = ingresos,
                totalGastos = gastos,
                balance = ingresos - gastos,
                mes = nombreMes
            )
        }
    }
}