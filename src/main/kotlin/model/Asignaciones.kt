package model

import java.time.LocalDate
import java.util.UUID

data class Asignaciones(
    val uuid: UUID= UUID.randomUUID(),
    val fechaAsignacion:LocalDate,
    val empleado: Empleado?,
    val ordenador: Ordenador?
        )
