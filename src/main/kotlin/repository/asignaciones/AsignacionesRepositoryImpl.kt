package repository.asignaciones

import db.DataBaseManager
import model.Asignaciones
import model.Ordenador
import mu.KotlinLogging
import repository.empleados.EmpleadosRepositoryImpl

import repository.ordenadores.OrdenadoresRepositoryImpl
import java.time.LocalDate
import java.util.*

private val logger = KotlinLogging.logger {}

class AsignacionesRepositoryImpl : AsignacionesRepository {
    override fun findAll(): List<Asignaciones> {
        val sql = "SELECT * FROM asignaciones"

        DataBaseManager.open()
        val result = DataBaseManager.select(sql)

        val asignaciones = mutableListOf<Asignaciones>()
        result?.let {
            while (result.next()) {
                val asignacion = Asignaciones(
                    uuid = result.getObject("uuid") as UUID,
                    fechaAsignacion = LocalDate.parse(it.getObject("fechaAsignacion").toString()),
                    empleado = (it.getObject("uuid_empleado") as UUID).let { it1 ->
                        EmpleadosRepositoryImpl().findById(it1)
                    },
                    ordenador = (it.getObject("uuid_ordenador") as UUID).let { it2 ->
                        OrdenadoresRepositoryImpl().findById(it2)
                    },

                    )
                asignaciones.add(asignacion)
            }
        }
        DataBaseManager.close()
        logger.debug { "Asignaciones encontrados: ${asignaciones.size}" }
        return asignaciones.toList()

    }

    override fun findById(id: UUID): Asignaciones? {
        val query = "SELECT * FROM asignacion WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.select(query, id)
        var asignacion: Asignaciones? = null
        result?.let {
            if (result.next()) {
                asignacion = Asignaciones(
                    uuid = result.getObject("uuid") as UUID,
                    fechaAsignacion = LocalDate.parse(it.getObject("fechaAsignacion").toString()),
                    ordenador = (it.getObject("uuid_ordenador") as UUID).let { it1 ->
                        OrdenadoresRepositoryImpl().findById(it1)
                    },
                    empleado = (it.getObject("uuid_empleado") as UUID).let { it2 ->
                        EmpleadosRepositoryImpl().findById(it2)
                    }
                )
            }
        }
        DataBaseManager.close()
        logger.debug { "Empleado encontrado con $id: $asignacion" }
        return asignacion
    }

    private fun insert(asignacion: Asignaciones): Asignaciones {
        val query = """INSERT INTO asignaciones
            
            (uuid, fechaAsignacion, empleado_uuid, ordenador_uuid ) 
            VALUES (?, ?, ?, ?)"""
            .trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.insert(
            query,
            asignacion.uuid,
            asignacion.fechaAsignacion,
            asignacion.empleado,
            asignacion.ordenador
        )
        DataBaseManager.close()
        logger.debug { "Asignacion insertada: $asignacion - Resultado: ${result == 1}" }
        return asignacion
    }

    private fun update(asignacion: Asignaciones): Asignaciones {
        val query = """UPDATE departamento SET fechaAsignacion = ?,empleado_uuid = ? WHERE uuid = ?"""
            .trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.update(query, asignacion.fechaAsignacion, asignacion.empleado, asignacion.uuid)
        DataBaseManager.close()
        logger.debug { "Asignacion actualizada: $asignacion - Resultado: ${result == 1}" }
        return asignacion
    }

    override fun save(entity: Asignaciones): Asignaciones {
        val asignacion = findById(entity.uuid)
        asignacion?.let {
            return update(entity)
        } ?: run {
            return insert(entity)
        }
    }

    override fun delete(entity: Asignaciones): Boolean {
        val query = "DELETE FROM asignaciones WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.delete(query, entity.uuid)
        DataBaseManager.close()
        logger.debug { "Asignacion eliminado: $entity - Resultado: ${result == 1}" }
        return result == 1
    }
}