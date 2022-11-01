package repository.empleados

import db.DataBaseManager
import model.Departamento
import model.Empleado
import model.Persona
import model.enums.Rol
import mu.KotlinLogging
import repository.departamento.DepartamentoRepositoryImpl
import java.time.LocalDate
import java.util.*

private val logger = KotlinLogging.logger {}

class EmpleadosRepositoryImpl :EmpleadosRepository{


    override fun findAll(): List<Empleado> {
        val sql = "SELECT * FROM empleado"
        val sql2= "SELECT * FROM persona"


        DataBaseManager.open()
        val result = DataBaseManager.select(sql)
        var result2=DataBaseManager.select(sql2)
        val auxresult= result2
        val empleados = mutableListOf<Empleado>()
        result?.let {
            while (result.next()) {
                result2?.let{
                    while(it.next()){
                    if(result.getObject("empleado_uuid") as UUID=== it.getObject("uuid") as UUID){
                        val empleado = Empleado(
                            uuid = it.getObject("uuid") as UUID,
                            nombre = it.getString("nombre"),
                            fechaAlta = LocalDate.parse(it.getObject("fechaAlta").toString()),
                            departamento = (it.getObject("uuid_departamento") as UUID?)?.let {
                                    it1 -> DepartamentoRepositoryImpl().findById(it1)
                            },
                            rol = result.getObject("rol") as Rol
                        )
                        empleados.add(empleado)
                    }
                }}
                result2=auxresult
            }
        }
        DataBaseManager.close()
        logger.debug { "Empleados encontrados: ${empleados.size}" }
        return empleados.toList()
    }

    override fun findById(id: UUID): Empleado? {
        val query = "SELECT * FROM empleado WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.select(query, id)
        var empleado: Empleado? = null
        result?.let {
            if (result.next()) {
                empleado = Empleado(
                    uuid = result.getObject("uuid") as UUID,
                    nombre = result.getString("nombre"),
                    fechaAlta =LocalDate.parse(result.getObject("fechaAlta").toString()),
                    departamento = DepartamentoRepositoryImpl().findById(result.getObject("uuid_departamento") as UUID),
                    rol = result.getObject("rol") as Rol
                )
            }
        }
        DataBaseManager.close()
        logger.debug { "Empleado encontrado con $id: $empleado" }
        return empleado
    }


    private fun insert(empleado:Empleado):Empleado{
        val query="""INSERT INTO persona
            (uuid,nombre,fechaAlta,uuid_departamento)
            VALUES(?,?,?,?)
        """.trimMargin()
        val query2="""INSERT INTO empleado 
            (uuid_empleado,rol)
            VALUES(?,?)
        """.trimMargin()
        DataBaseManager.open()
        val result= empleado.departamento?.let {
            DataBaseManager.insert(query,empleado.uuid,empleado.nombre,empleado.fechaAlta,empleado.departamento)
            DataBaseManager.insert(query2,empleado.uuid,empleado.rol.toString())
        }
        DataBaseManager.close()
        logger.debug { "Empleado insertado: $empleado - Resultado: ${result == 1}" }
        return empleado

    }



    private fun updateRol(empleado: Empleado): Empleado {
        val query = """UPDATE empleado SET rol = ? WHERE uuid = ?"""
            .trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.update(query, empleado.rol, empleado.uuid)
        DataBaseManager.close()
        logger.debug { "Empleado actualizado: $empleado - Resultado: ${result == 1}" }
        return empleado
    }


    private fun update(empleado:Empleado):Empleado{
        val query = """UPDATE persona SET nombre = ?,departamento_uuid = ? WHERE uuid = ?"""
            .trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.update(query, empleado.nombre, empleado.departamento,empleado.uuid)
        updateRol(empleado)
        DataBaseManager.close()
        logger.debug { "Empleado actualizado: $empleado - Resultado: ${result == 1}" }
        return empleado
    }


    override fun save(entity: Empleado): Empleado {
        val empleado = findById(entity.uuid)
        empleado?.let {
            return update(entity)
        } ?: run {
            return insert(entity)
        }
    }

    override fun delete(entity: Empleado): Boolean {
        val query="DELETE FROM empleado WHERE empleado_uuid = ?"
        val query2 = "DELETE FROM persona WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.delete(query, entity.uuid)
        val result2= DataBaseManager.delete(query2, entity.uuid)
        DataBaseManager.close()
        logger.debug { "Empleado eliminado: $entity - Resultado: ${result == 1}" }
        logger.debug { "Persona eliminado: $entity - Resultado: ${result2 == 1}" }
        return result == 1
    }


}