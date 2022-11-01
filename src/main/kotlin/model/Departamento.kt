package model

import java.util.UUID


data class Departamento(
  val uuid : UUID = UUID.randomUUID(),
  val nombre:String,
  val presupuesto : Double
 )

