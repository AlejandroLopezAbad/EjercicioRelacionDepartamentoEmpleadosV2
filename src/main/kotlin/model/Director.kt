package model

import java.time.LocalDate
import java.util.UUID

data class Director (
     override val uuid: UUID= UUID.randomUUID(),
     override val nombre: String,
     override val fechaAlta: LocalDate,
     override val departamento: Departamento?,
     val yearExperience: Int,
        ):Persona(uuid, nombre, fechaAlta,departamento)

