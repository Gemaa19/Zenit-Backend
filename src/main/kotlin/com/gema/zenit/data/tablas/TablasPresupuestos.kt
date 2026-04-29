package com.gema.zenit.data.tablas
import org.jetbrains.exposed.dao.id.LongIdTable

object TablasPresupuestos : LongIdTable("presupuestos") {
    val montoLimite = decimal("monto_limite", 10, 2)
    val mes = byte("mes")
    val anio = integer("anio")
    val usuarioId = reference("usuario_id", TablasUsuarios.id)
    val categoriaId = reference("categoria_id", TablasCategorias.id)
}