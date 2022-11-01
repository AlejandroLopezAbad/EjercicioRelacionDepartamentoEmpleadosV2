package model

import java.time.LocalDate
import java.util.Date
import java.util.UUID

open class Persona(
     open val uuid: UUID= UUID.randomUUID(),
     open val nombre: String,
     open val fechaAlta: LocalDate,
     open val departamento: Departamento?,
    )
