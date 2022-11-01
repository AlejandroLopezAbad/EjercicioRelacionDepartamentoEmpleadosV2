package repository.ordenadores

import db.DataBaseManager
import model.Departamento
import model.Ordenador
import mu.KotlinLogging

import java.util.*

private val logger = KotlinLogging.logger {}

class OrdenadoresRepositoryImpl : OrdenadoresRepository {
    override fun findAll(): List<Ordenador> {
        val sql = "SELECT * FROM ordenador"
        DataBaseManager.open()
        val result = DataBaseManager.select(sql)
        val ordenadores = mutableListOf<Ordenador>()
        result?.let {
            while (result.next()) {
                val ordenador = Ordenador(
                    uuid = result.getObject("uuid") as UUID,
                    modelo = result.getString("modelo"),
                    fechaCompra = result.getString("fechaCompra")
                )
                ordenadores.add(ordenador)
            }
        }
        DataBaseManager.close()
        logger.debug { "Ordenadores encontrados: ${ordenadores.size}" }
        return ordenadores.toList()
    }

    override fun findById(id: UUID): Ordenador? {
        val query = "SELECT * FROM ordenador WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.select(query, id)
        var ordenador: Ordenador? = null
        result?.let {
            if (result.next()) {
                ordenador = Ordenador(
                    uuid = result.getObject("uuid") as UUID,
                    modelo = result.getString("modelo"),
                    fechaCompra = result.getString("fechaCompra")
                )
            }
        }
        DataBaseManager.close()
        logger.debug { "Departamento encontrado con $id: $ordenador" }
        return ordenador
    }

    override fun save(entity: Ordenador): Ordenador {
        val ordenador = findById(entity.uuid)
        ordenador?.let {
            return update(entity)
        } ?: run {
            return insert(entity)
        }
    }

    private fun insert(ordenador: Ordenador): Ordenador {
        val query = """INSERT INTO ordenador 
            (uuid, modelo, fechaCompra) 
            VALUES (?, ?, ?)"""
            .trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.insert(query, ordenador.uuid, ordenador.modelo, ordenador.fechaCompra)
        DataBaseManager.close()
        logger.debug { "Ordenador insertado: $ordenador - Resultado: ${result == 1}" }
        return ordenador
    }


    private fun update(ordenador: Ordenador): Ordenador {
        val query = """UPDATE ordenador SET modelo = ? WHERE uuid = ?"""
            .trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.update(query, ordenador.modelo, ordenador.uuid)
        DataBaseManager.close()
        logger.debug { "Ordenador actualizado: $ordenador - Resultado: ${result == 1}" }
        return ordenador
    }

    override fun delete(entity: Ordenador): Boolean {
        val query = "DELETE FROM ordenador WHERE uuid = ?"
        val query2 = "DELETE FROM asignaciones WHERE ordenador_uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.delete(query, entity.uuid)
        val result2 = DataBaseManager.delete(query2, entity.uuid)
        DataBaseManager.close()
        logger.debug { "Ordenador eliminado: $entity - Resultado: ${result == 1}" }
        logger.debug { "Ordenador con asignacion eliminado: $entity - Resultado: ${result2 == 1}" }
        return result == 1


    }
}