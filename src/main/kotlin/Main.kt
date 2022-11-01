import controller.ControllerEmpresa
import db.*
import org.jetbrains.kotlinx.dataframe.api.print
import org.jetbrains.kotlinx.dataframe.api.toDataFrame

fun main(){



    val controller = ControllerEmpresa()
    controller.initDataBase()
    controller.addDepartamentos()
    controller.addEmpleados()
    var listaEmpleados = controller.getAllEmpleados()
    var listaDepartamentos = controller.getAllDepartamentos()
    listaDepartamentos.toDataFrame().print()
    listaEmpleados.toDataFrame().print()
    controller.deleteDepartamentos()
    listaEmpleados = controller.getAllEmpleados()
    listaDepartamentos = controller.getAllDepartamentos()
    listaDepartamentos.toDataFrame().print()
    listaEmpleados.toDataFrame().print()
    Thread.sleep(20000)




}



