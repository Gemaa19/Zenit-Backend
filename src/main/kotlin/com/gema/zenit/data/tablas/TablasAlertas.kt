package com.gema.zenit.data.tablas

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object TablasAlertas : Table("alertas") {
    val id = long("id").autoIncrement()
    val mensaje = text("mensaje")
    val leida = bool("leida").default(false)
    val fechaCreacion = datetime("fecha_creacion").default(LocalDateTime.now())
    val usuarioId = long("usuario_id") references TablasUsuarios.id

    override val primaryKey = PrimaryKey(id)
}