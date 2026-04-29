package com.gema.zenit.data.tablas

import org.jetbrains.exposed.sql.Table

object TablasCategorias : Table("categorias") {
    val id = long("id").autoIncrement()
    val nombre = varchar("nombre", 50)
    val colorHex = varchar("color_hex", 7).nullable()
    val icono = varchar("icono", 50).nullable()
    val usuarioId = long("usuario_id") references TablasUsuarios.id

    override val primaryKey = PrimaryKey(id)
}