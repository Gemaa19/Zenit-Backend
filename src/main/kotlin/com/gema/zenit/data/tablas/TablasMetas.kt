package com.gema.zenit.data.tablas

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.date

object TablasMetas : LongIdTable("metas") {
    val nombre = varchar("nombre", 100)
    val objetivo = decimal("objetivo", 15, 2)
    val fechaLimite = date("fecha_limite").nullable()
    val usuarioId = reference("usuario_id", TablasUsuarios.id) // Clave foránea
}