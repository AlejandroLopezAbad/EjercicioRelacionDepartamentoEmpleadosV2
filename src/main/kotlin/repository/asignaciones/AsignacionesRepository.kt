package repository.asignaciones

import model.Asignaciones
import repository.CrudRepository
import java.util.UUID

interface AsignacionesRepository :CrudRepository<Asignaciones,UUID>

