package db

import model.Departamento
import model.Director
import model.Empleado
import model.Ordenador
import model.enums.Rol
import repository.departamento.DepartamentoRepositoryImpl
import java.time.LocalDate
import java.util.*

fun createDepartamentos():List<Departamento>{
    return listOf(
        Departamento(
            uuid = UUID.randomUUID(),
            nombre = "RRHH",
            presupuesto = 6000.2
        ),
        Departamento(
            uuid = UUID.randomUUID(),
            nombre = "Testing",
            presupuesto = 10000.0
        ),
        Departamento(
            uuid = UUID.randomUUID(),
            nombre = "Administración",
            presupuesto = 30000.0
        ),
        Departamento(
            uuid = UUID.randomUUID(),
            nombre = "Dirección",
            presupuesto = 100.0
        ),
        Departamento(
            uuid = UUID.randomUUID(),
            nombre = "Ventas",
            presupuesto = 25000.0
        ),
    )
}

fun createEmpleados():List<Empleado>{
    return listOf(
        Empleado(uuid = UUID.randomUUID(), "Alfredo", LocalDate.now(), DepartamentoRepositoryImpl().findByName("Programacion"), Rol.PROGRAMADOR),
        Empleado(uuid = UUID.randomUUID(), "Ruben", LocalDate.now(),
            DepartamentoRepositoryImpl().findByName("Moviles"), Rol.TESTEADOR),
        Empleado(uuid = UUID.randomUUID(), "Alejandro", LocalDate.now(),
            DepartamentoRepositoryImpl().findByName("Ventas"), Rol.ANALISTA),
        Empleado(uuid = UUID.randomUUID(), "Mireya", LocalDate.now(),
            DepartamentoRepositoryImpl().findByName("Ventas"), Rol.ANALISTA),
        Empleado(uuid = UUID.randomUUID(), "Alvaro", LocalDate.now(),
            DepartamentoRepositoryImpl().findByName("Direccion"), Rol.TESTEADOR),
        Empleado(uuid = UUID.randomUUID(), "Jorge", LocalDate.now(),
            DepartamentoRepositoryImpl().findByName("Ventas"), Rol.PROGRAMADOR),
        Empleado(uuid = UUID.randomUUID(), "Daniel", LocalDate.now(),
            DepartamentoRepositoryImpl().findByName("Moviles"), Rol.ANALISTA),
        Empleado(uuid = UUID.randomUUID(), "Isabel", LocalDate.now(),
            DepartamentoRepositoryImpl().findByName("Empresa"), Rol.PROGRAMADOR),
        Empleado(uuid = UUID.randomUUID(), "Diego", LocalDate.now(),
            DepartamentoRepositoryImpl().findByName("Programacion"), Rol.ANALISTA),

        )
}
fun getDirectores(): List<Director> {
    return listOf(
        Director(UUID.randomUUID(), "Alfredo", LocalDate.now(), DepartamentoRepositoryImpl().findByName("RRHH"), 2),
        Director(UUID.randomUUID(), "Marta", LocalDate.now(), DepartamentoRepositoryImpl().findByName("Testing"), 1),
        Director(UUID.randomUUID(), "Javier", LocalDate.now(), DepartamentoRepositoryImpl().findByName("Administración"), 4),
        Director(UUID.randomUUID(), "Lydia", LocalDate.now(), DepartamentoRepositoryImpl().findByName("Dirección"), 5),
        Director(UUID.randomUUID(), "Carlos", LocalDate.now(), DepartamentoRepositoryImpl().findByName("Ventas"), 3)
    )
}



fun createOrdenadores():List<Ordenador>{
    return listOf(
        Ordenador(
         uuid= UUID.randomUUID(),
          modelo=  "Intel",
        fechaCompra = "11-06-2020"
        ),
        Ordenador(
            uuid= UUID.randomUUID(),
            modelo=  "Intel",
            fechaCompra = "01-08-2018"
        ),
        Ordenador(
            uuid= UUID.randomUUID(),
            modelo=  "Apple",
            fechaCompra = "01-12-2021"
        ),
        Ordenador(
            uuid= UUID.randomUUID(),
            modelo=  "Lenovo",
            fechaCompra = "01-12-2021"
        ),
        Ordenador(
            uuid= UUID.randomUUID(),
            modelo=  "Apple",
            fechaCompra = "10-10-2022"
        ),
        Ordenador(
            uuid= UUID.randomUUID(),
            modelo=  "Intel",
            fechaCompra = "10-10-2022"
        ),
        Ordenador(
            uuid= UUID.randomUUID(),
            modelo=  "Intel",
            fechaCompra = "06-06-2016"
        )

    )



}

