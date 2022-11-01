package repository.director

import db.DataBaseManager
import model.Director
import model.Empleado
import model.enums.Rol
import mu.KotlinLogging
import repository.departamento.DepartamentoRepositoryImpl
import java.time.LocalDate
import java.util.*

private val logger = KotlinLogging.logger {}

class DirectorRepositoryImpl : DirectorRepository {

    override fun findAll(): List<Director> {
        val sql = "SELECT * FROM director"
        val sql2 = "SELECT * FROM persona"
        DataBaseManager.open()
        val result = DataBaseManager.select(sql)
        var result2 = DataBaseManager.select(sql2)
        val auxresult = result2
        val directores = mutableListOf<Director>()
        result?.let {
            while (result.next()) {
                result2?.let {
                    while (it.next()) {
                        if (result.getObject("director_uuid") as UUID === it.getObject("uuid") as UUID) {
                            val director = Director(
                                uuid = it.getObject("uuid") as UUID,
                                nombre = it.getString("nombre"),
                                fechaAlta = LocalDate.parse(it.getObject("fechaAlta").toString()),
                                departamento = (it.getObject("uuid_departamento") as UUID?)?.let { it1 ->
                                    DepartamentoRepositoryImpl().findById(it1)
                                },
                                yearExperience = it.getInt("yearExperience")
                            )
                            directores.add(director)
                        }
                    }
                }
                result2 = auxresult
            }
        }
        DataBaseManager.close()
        logger.debug { "Directores encontrados: ${directores.size}" }
        return directores.toList()
    }

    override fun findById(id: UUID): Director? {
        val query = "SELECT * FROM director WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.select(query, id)
        var director: Director? = null
        result?.let {
            if (result.next()) {
                director = Director(
                    uuid = result.getObject("uuid") as UUID,
                    nombre = result.getString("nombre"),
                    fechaAlta =LocalDate.parse(result.getObject("fechaAlta").toString()),
                    departamento = DepartamentoRepositoryImpl().findById(result.getObject("uuid_departamento") as UUID),
                    yearExperience = it.getInt("yearExperience")
                )
            }
        }
        DataBaseManager.close()
       logger.debug { "Director encontrado con $id: $director" }
        return director
    }


    private fun insert(director:Director):Director{
        val query="""INSERT INTO persona
            (uuid,nombre,fechaAlta,uuid_departamento)
            VALUES(?,?,?,?)
        """.trimMargin()
        val query2="""INSERT INTO director 
            (uuid_director,yearExperience)
            VALUES(?,?)
        """.trimMargin()
        DataBaseManager.open()
        val result= director.departamento?.let {
            DataBaseManager.insert(query,director.uuid,director.nombre,director.fechaAlta,director.departamento)
            DataBaseManager.insert(query2,director.uuid,director.yearExperience)
        }
        DataBaseManager.close()
        logger.debug { "Empleado insertado: $director - Resultado: ${result == 1}" }
        return director
    }
    private fun updateYearExperience(director: Director): Director {
        val query = """UPDATE director SET yearExperience = ? WHERE uuid = ?"""
            .trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.update(query, director.yearExperience, director.uuid)
        DataBaseManager.close()
        logger.debug { "Director actualizado: $director - Resultado: ${result == 1}" }
        return director
    }
    private fun update(director:Director):Director{
        val query = """UPDATE persona SET nombre = ?,departamento_uuid = ? WHERE uuid = ?"""
            .trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.update(query, director.nombre, director.departamento,director.uuid)
        updateYearExperience(director)
        DataBaseManager.close()
        logger.debug { "Empleado actualizado: $director - Resultado: ${result == 1}" }
        return director
    }

    override fun save(entity: Director): Director {
        val director = findById(entity.uuid)
        director?.let {
            return update(entity)
        } ?: run {
            return insert(entity)
        }
    }

    override fun delete(entity: Director): Boolean {
        val query="DELETE FROM director WHERE empleado_uuid = ?"
        val query2 = "DELETE FROM persona WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.delete(query, entity.uuid)
        val result2= DataBaseManager.delete(query2, entity.uuid)
        DataBaseManager.close()
        logger.debug { "Director eliminado: $entity - Resultado: ${result == 1}" }
        logger.debug { "Persona eliminado: $entity - Resultado: ${result2 == 1}" }
        return result == 1
    }
}