package repository.departamento

import model.Departamento
import repository.CrudRepository
import java.util.UUID

interface DepartamentoRepository :CrudRepository<Departamento,UUID>
