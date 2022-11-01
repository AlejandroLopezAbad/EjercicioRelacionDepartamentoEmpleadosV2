package repository.persona

import model.Persona
import repository.CrudRepository
import java.util.UUID

interface PersonaRepository :CrudRepository<Persona,UUID>

