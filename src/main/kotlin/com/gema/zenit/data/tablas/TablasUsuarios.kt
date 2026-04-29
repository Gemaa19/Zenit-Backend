package com.gema.zenit.data.tablas

import org.jetbrains.exposed.sql.Table

object TablasUsuarios: Table("usuarios") {
    val id = long("id").autoIncrement()
    val username = varchar("username", 50).uniqueIndex()
    val email = varchar("email", 100).uniqueIndex()
    val password = varchar("password", 255)
    val rol = enumerationByName("rol", 10, Roles::class).default(Roles.USER)

    enum class Roles { USER, ADMIN }
    override val primaryKey = PrimaryKey(id)
}