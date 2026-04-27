package com.gema.zenit.data

import org.jetbrains.exposed.sql.Table

object TablasUsuarios: Table("usuarios") {
    val id = long("id").autoIncrement()
    val username = varchar("username", 50).uniqueIndex()
    val email = varchar("email", 100).uniqueIndex()
    val password = varchar("password", 255)
    val rol = varchar("rol", 10).default("USER") // Para simplificar hoy usamos varchar

    override val primaryKey = PrimaryKey(id)
}