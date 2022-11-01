package model

import java.util.UUID

data class Ordenador (
    val uuid: UUID= UUID.randomUUID(),
    val modelo:String,
    val fechaCompra:String,
        )
