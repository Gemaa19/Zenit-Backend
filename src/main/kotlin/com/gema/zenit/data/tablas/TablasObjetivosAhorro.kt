package com.gema.zenit.data.tablas

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object TablasObjetivosAhorro : Table("objetivos_ahorro") {
    val id = long("id").autoIncrement()
    val nombreMeta = varchar("nombre_meta", 100)
    val cantidadObjetivo = decimal("cantidad_objetivo", 10, 2)
    val cantidadActual = decimal("cantidad_actual", 10, 2).default(0.0.toBigDecimal())
    val fechaLimite = date("fecha_limite").nullable()
    val usuarioId = long("usuario_id") references TablasUsuarios.id

    override val primaryKey = PrimaryKey(id)
}