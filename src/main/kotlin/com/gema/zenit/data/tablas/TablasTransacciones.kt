package com.gema.zenit.data.tablas

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object TablasTransacciones : Table("transacciones") {
    val id = long("id").autoIncrement()
    val monto = decimal("monto", 10, 2)
    val descripcion = varchar("descripcion", 255).nullable()
    val fecha = date("fecha")
    val tipo = varchar("tipo", 20)
    val usuarioId = long("usuario_id") references TablasUsuarios.id
    val categoriaId = (long("categoria_id") references TablasCategorias.id).nullable()

    override val primaryKey = PrimaryKey(id)
}