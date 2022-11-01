package repository.empleados

import model.Empleado
import repository.CrudRepository
import java.util.*

interface EmpleadosRepository :CrudRepository<Empleado, UUID>
