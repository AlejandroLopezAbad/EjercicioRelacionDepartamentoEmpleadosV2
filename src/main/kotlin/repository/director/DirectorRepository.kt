package repository.director

import model.Director
import repository.CrudRepository
import java.util.*

interface DirectorRepository : CrudRepository<Director, UUID>
