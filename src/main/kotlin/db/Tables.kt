package db


fun createTableDepartamento() = """
    CREATE TABLE IF NOT EXISTS departamento (
            uuid UUID PRIMARY KEY,
            nombre VARCHAR(255) NOT NULL,
            presupuesto DOUBLE NOT NULL
        )
    """.trimIndent()

fun createTablePersona() = """
    CREATE TABLE IF NOT EXISTS persona (
      uuid UUID PRIMARY KEY,
      nombre VARCHAR(255) NOT NULL,
      fechaAlta DATE NOT NULL,
      departamento_uuid UUID,
      FOREING KEY(departamento_uuid) reference departamento(uuid), 
)
    """.trimIndent()

fun createTableEmpleados() = """
    CREATE TABLE IF NOT EXISTS empleado (
     empleado_uuid UUID NOT NULL,
      rol VARCHAR(255) NOT NULL,
      FOREING KEY(empleado_uuid) reference persona(uuid),
)
    """.trimIndent()

fun createTableDirectores() = """
   CREATE TABLE IF NOT EXISTS director (
    director_uuid UUID NOT NULL,
    yearExperience INT unsigned NOT NULL,
    FOREING KEY(director_uuid) reference persona(uuid),
    FOREING KEY(departamento_uuid) reference departamento(uuid),
)
    """.trimIndent()

fun createTableOrdenador() = """
   CREATE TABLE IF NOT EXISTS ordenador (
       uuid UUID PRIMARY KEY,
       modelo VARCHAR(255) NOT NULL,
       fechaCompra VARCHAR(255) NOT NULL,
)
    """.trimIndent()

fun createTableAsignaciones() = """
    CREATE TABLE IF NOT EXISTS asignaciones (
        uuid UUID PRIMARY KEY,
        fechaAsignacion DATE NOT NULL,
        empleado_uuid UUID NOT NULL,
        ordenador_uuid UUID NOT NULL,
        FOREING KEY(empleado_uuid) reference persona(uuid),
        FOREING KEY(ordenador_uuid) reference ordenador(uuid),
    )
""".trimIndent()
