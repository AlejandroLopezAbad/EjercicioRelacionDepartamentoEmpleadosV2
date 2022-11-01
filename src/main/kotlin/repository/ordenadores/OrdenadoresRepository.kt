package repository.ordenadores

import model.Ordenador
import repository.CrudRepository
import java.util.UUID

interface OrdenadoresRepository :CrudRepository<Ordenador,UUID>
