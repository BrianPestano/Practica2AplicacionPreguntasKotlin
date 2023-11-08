package Estadisticas

data class EstadisticasUsuario(
    var aciertos: Int = 0,
    var fallos: Int = 0,
    var clicsTotales: Int = 0
) {
    val porcentajeAciertos: Float
        get() = if (clicsTotales > 0) (aciertos.toFloat() / clicsTotales) * 100 else 0f

    val porcentajeFallos: Float
        get() = if (clicsTotales > 0) (fallos.toFloat() / clicsTotales) * 100 else 0f
}