package Componentes
import Preguntas.Preguntas
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.example.practica2aplicacionpreguntas.R
import kotlin.random.Random
@Composable
fun Juego(){
    //Declaramos una variable de tipo var llamado lista y la igualamos al arraylist que tenemos en la clase PreguntasArray
    var lista = ArrayList<Preguntas>()

    //Creamos las diferentes preguntas,con las imagenes y respuestas
    lista.add(Preguntas("¿Es Goku el ser mas poderoso?", R.drawable.gokumondongo, true))
    lista.add(Preguntas("¿Es verdad que Cristiano tiene 2 piernas en este FC 24?", R.drawable.quichiano, false))
    lista.add(Preguntas("¿Es este el profesor?", R.drawable.profesor, false))
    lista.add(Preguntas("¿Es este el gato cosmico?", R.drawable.doraemon, true))
    lista.add(Preguntas("¿Es este LMDShow?", R.drawable.ilojuan, true))

    //Creamos diferentes variables que son las que van a estar cambiando a lo largo del ejercicio
    var numeroRandom by remember { mutableStateOf(Random.nextInt(3)) }
    var retroalimentacionTexto by remember { mutableStateOf("") }
    var retroalimentacionColor by remember { mutableStateOf(Color.Transparent) }
    var respuestaUsuario by remember { mutableStateOf(false) }
    var indicePregunta by remember { mutableStateOf(0) }

    // Variable para almacenar el índice de la pregunta aleatoria
    var preguntaAleatoria by remember { mutableStateOf(Random.nextInt(5)) }
    
    //Creamos una columna que almacene tod0 lo que necesitamos para que se vea de forma vertical
    //Le añadimos un modificador a la columna para que tenga tod0 el espacio, aparte de que verticalmente tengamos el SpaceBetween
    //para que se tenga el mismo espacio para todos lados, y luego horizontalmente que tod0 lo que este dentro este centrado.
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Texto arriba, que es la pregunta, centrado
        Text(
            //Aqui tenemos las preguntas de forma aleatoria que se iran mostrando, con un padding y tamaño mas grande, también centramos el texto
            text = lista[preguntaAleatoria].preguntas, // Usa preguntaAleatoria en lugar de numeroRandom
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp
        )
        // Imagen centrada y de mayor tamaño
        Image(
            painter = painterResource(id = lista[preguntaAleatoria].imagen), // Usa preguntaAleatoria
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .padding(16.dp)
        )
        // Espacio entre la imagen y los botones
        Spacer(modifier = Modifier.height(16.dp))

        // Texto de retroalimentación
        Text(
            text = retroalimentacionTexto,
            color = retroalimentacionColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            fontSize = 20.sp
        )

        //Creamos otra columna para los botones, para ponerlos como se nos indica en la imagen, donde luego le pondremos
        //un row(fila), para que los 2 botones se vean uno al lado del otro y el de next y demas abajo
        Column(verticalArrangement = Arrangement.Bottom) {
            // Botones de true y false centrados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                //Dentro de la funcion del boton tenemos que cuando pulsemos haga que si pulsamos bien el color se ponga
                //en verde(del texto) y salga un mensaje diciendo que has respondido bien o incorrecto.
                Button(
                    onClick = {
                        if (lista[preguntaAleatoria].respuesta) {
                            retroalimentacionTexto = "Bien hecho"
                            retroalimentacionColor = Color.Green
                        } else {
                            retroalimentacionTexto = "Incorrecto"
                            retroalimentacionColor = Color.Red
                        }
                    },
                    //ponemos un modificador para que tenga un padding entre botones, para que no esten pegados
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text("Si")
                }
                //Lo mismo que el boton de arriba, pero el boton de al lado
                Button(
                    onClick = {
                        if (!lista[preguntaAleatoria].respuesta) {
                            retroalimentacionTexto = "Bien hecho"
                            retroalimentacionColor = Color.Green
                        } else {
                            retroalimentacionTexto = "Incorrecto"
                            retroalimentacionColor = Color.Red
                        }
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text("No")
                }
            }
            // Botones de PREV y NEXT
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        retroalimentacionTexto = ""
                        retroalimentacionColor = Color.Transparent

                        if (preguntaAleatoria > 0) {
                            preguntaAleatoria--
                        } else {
                            preguntaAleatoria = lista.size - 1
                        }
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "")
                        Text(text = "PREV")
                    }
                }
                Button(
                    onClick = {
                        retroalimentacionTexto = ""
                        retroalimentacionColor = Color.Transparent

                        if (preguntaAleatoria < lista.size - 1) {
                            preguntaAleatoria++
                        } else {
                            preguntaAleatoria = 0
                        }
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "")
                        Text(text = "NEXT")
                    }
                }
                // Botón "RANDOM" para mostrar una pregunta al azar
                Button(
                    onClick = {
                        //Indice aleatorio diferente al actual
                        preguntaAleatoria = Random.nextInt(3)
                    },
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text("RANDOM")
                }
            }
        }
    }
}
//Funcion para ejecutar la aplicacion
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewJuego(){
    Juego()
}
//Funcion para verificar el color si has acertado la respuesta o no
fun verificarColor(acierto: Boolean): Color {
    if (acierto) {
        return Color.Green
    } else {
        return Color.Red
    }
}
//Esta funcion la busque con el ChatGPT ya que estuve trabado por un tiempo y tenia que avanzar
@Composable
fun BotonRespuesta(texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp),
    ) {
        Text(text = texto)
    }
}
//Funcion para verificar la respuesta
fun verificarRespuesta(pregunta: Preguntas, respuestaUsuario: Boolean) {
    val esCorrecta = pregunta.respuesta == respuestaUsuario
    // Debes manejar las variables de retroalimentación en esta función o pasarlas como argumentos.
}
//Funcion para avanzar a la siguiente pregunta
fun avanzarAPreguntaSiguiente(numeroRandom: Int, lista: List<Preguntas>): Int {
    if (numeroRandom < lista.size - 1) {
        return numeroRandom + 1
    } else {
        return numeroRandom
    }
}

