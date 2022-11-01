package model

import model.enums.Rol
import java.time.LocalDate
import java.util.*

data class Empleado (
    override val uuid: UUID = UUID.randomUUID(),
    override val nombre: String,
    override val fechaAlta: LocalDate,
    override val departamento: Departamento?,
    val rol: Rol? = null,
    ):Persona(uuid,nombre, fechaAlta, departamento)
