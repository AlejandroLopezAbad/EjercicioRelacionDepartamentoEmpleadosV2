package controller

import db.*
import model.Departamento
import model.Empleado
import repository.departamento.DepartamentoRepositoryImpl
import repository.empleados.EmpleadosRepositoryImpl

class ControllerEmpresa() {
    private val departamentos= createDepartamentos()

    fun initDataBase(){
        DataBaseManager.open()
        DataBaseManager.createTables(createTableDepartamento())
        DataBaseManager.createTables(createTablePersona())
        DataBaseManager.createTables(createTableEmpleados())
        DataBaseManager.createTables(createTableDirectores())
        DataBaseManager.createTables(createTableOrdenador())
        DataBaseManager.createTables(createTableAsignaciones())
        DataBaseManager.close()
    }

    fun addDepartamentos() {
        departamentos.forEach{
            DepartamentoRepositoryImpl().save(it)
        }
    }

    fun addEmpleados() {
        createEmpleados().forEach{
            EmpleadosRepositoryImpl().save(it)
        }.toString()
    }

    fun deleteDepartamentos() {
        DepartamentoRepositoryImpl().delete(departamentos[0])
    }
    fun getAllEmpleados(): List<Empleado> {
        return EmpleadosRepositoryImpl().findAll()
    }
    fun getAllDepartamentos(): List<Departamento> {
        return DepartamentoRepositoryImpl().findAll()
    }


}