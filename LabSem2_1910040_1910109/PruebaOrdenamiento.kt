import kotlin.system.measureNanoTime
import kotlin.system.exitProcess
import kotlin.random.Random

// Funcion main que recibe los argumentos de entrada y llama a la funcion correspondiente
fun main(args: Array<String>) {
    // Se verifica que se hayan ingresado los argumentos de entrada
    var tnum : Int = 0
    var secuencia : String = ""
    var algoritmo : String = ""
    var number : String = ""
    var name : String = ""

    for (i in 0 until 8 step 2) {
        // Se verifica que los argumentos de entrada sean correctos
        try {
            if (args[i] == "-t") {tnum = args[i +1].toInt()}
            else if (args[i] == "-s") {secuencia = args[i +1]}
            else if (args[i] == "-a") {algoritmo = args[i +1]}
            else if (args[i] == "-n") {number = args[i +1]}
            else if (args[i] == "-g") {name = args[i +1]}
            else {println("Error al indicar los argumentos")
                exitProcess(1)}
        } // Si no se ingresan los argumentos de entrada correctamente, se imprime un mensaje de error
        catch(e: Exception) {
            println("Error al indicar los argumentos")
            exitProcess(1)}        
    }

    val numb = number.split(",")
    val num = numb.map { it.toInt() }
    for (i in 0 until num.size) {
        try {
            // Se verifica que los argumentos de entrada sean correctos
            if (tnum < 0 || num[i] < 0) {println("Error al indicar los argumentos")
                exitProcess(1)}
        } // Si no se ingresan los argumentos de entrada correctamente, se imprime un mensaje de error
        catch(e: Exception) {
            println("Error al indicar los argumentos")
            exitProcess(1)}
    }

    for (i in 0 until num.size) {
        // Se ejecuta la funcion correspondiente
        if (secuencia == "random") {random(tnum, num[i], algoritmo)}
        else if (secuencia == "randomd") {randomd(tnum, num[i], algoritmo)}
        else if (secuencia == "sorted") {sorted(tnum, num[i], algoritmo)}
        else if (secuencia == "sortedd") {sortedd(tnum, num[i], algoritmo)}
        else if (secuencia == "inv") {inv(tnum, num[i], algoritmo)}
        else if (secuencia == "zu") {zu(tnum, num[i], algoritmo)}
        else if (secuencia == "media") {media(tnum, num[i], algoritmo)}
        else {println("Error al indicar los argumentos")
            exitProcess(1)}
    }
}

// Funcion que verifica si un arreglo esta ordenado de forma ascendente
/**
* requires: secuencia != null && secuencia.size > 0
* ensures: (result == true) && (forall int i ; 0 <= i < secuencia.size - 1 ; secuencia[i] <= secuencia[i+1])
 */
fun estaEnOrdenAscendente(secuencia: Array<Number>): Boolean {
    val n = secuencia.size
    // Se verifica que el arreglo este ordenado de forma ascendente
    for (i in 0 until n-1) {
	    if (secuencia [i] > secuencia [i+1]) {return false} 
    }
    return true
}

// Funcion que ejecuta el algoritmo de ordenamiento correspondiente
/**
* requires: array != null && array.size > 0 && tnum >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures: (forall int i ; 0 <= i < array.size - 1 ; array[i] <= array[i+1])
*/
fun algoritmoAEjecutar(array : Array<Number>, tnum: Int, algoritmo: String) {
    var array1 : Array<Number>
    var array2 : Array<Number>
    var timeProm : Double
    var u : Double
    var suma : Double
    var desviacionEstandar : Double

    // Se comparan los argumentos de entrada para ejecutar el algoritmo correspondiente
    val division = algoritmo.split(",")
    val copies = Array(tnum*division.size) { array.copyOf() }
    val copiasTiempo = Array(tnum*division.size) { 0.0 }
    var tiemposEjecucion = Array(tnum*division.size) { 0.0 }
    for (i in 0 until division.size) {
        
        // Se comparan los argumentos de entrada para ejecutar el algoritmo correspondiente
        if (division[i] == "is") {
            // Se crean copias del arreglo original para ejecutar los algoritmos
            var h = 0
            timeProm = 0.0
            // Se ejecuta el algoritmo de ordenamiento correspondiente
            repeat(tnum) {
                val elapsedTime1 = measureNanoTime {array1 = insertionSort(copies[i])}
                copiasTiempo[h] = elapsedTime1.toDouble()
                println("Insertion Sort ---> Tiempo transcurrido: " + elapsedTime1.toString() + " ns")
                // Se verifica que el arreglo este ordenado de forma ascendente
                if (estaEnOrdenAscendente(array1)) {println("El arreglo está ordenado")}
                else {println("El arreglo no está ordenado")
                    exitProcess(1)}
                timeProm += elapsedTime1
                h++
                println("")
            }

            // Se calcula la desviacion estandar
            u = timeProm/tnum
            suma = 0.0
            for (j in 0 until tnum) {suma = suma + (copiasTiempo[j] - u) * (copiasTiempo[j] - u)}
            desviacionEstandar = kotlin.math.sqrt(suma/tnum)

            // Se calcula el tiempo promedio de ejecucion del algoritmo si se ejecuta mas de una vez
            if (tnum > 1) {println("Tiempo promedio Insertion Sort: " + (timeProm/tnum).toString() + " ns")
            println("Desviación estándar Insertion Sort: " + desviacionEstandar.toString())}
            println("")
        }

        // Se comparan los argumentos de entrada para ejecutar el algoritmo correspondiente
        else if (division[i] == "bs") {
            // Se crean copias del arreglo original para ejecutar los algoritmos
            var h = 0
            timeProm = 0.0
            // Se ejecuta el algoritmo de ordenamiento correspondiente
            repeat(tnum) {
                val elapsedTime2 = measureNanoTime {array2 = bubbleSort(copies[h])}
                copiasTiempo[h] = elapsedTime2.toDouble()
                println("Bubble Sort ---> Tiempo transcurrido: " + elapsedTime2.toString() + " ns")
                // Se verifica que el arreglo este ordenado de forma ascendente
                if (estaEnOrdenAscendente(array2)) {println("El arreglo está ordenado")}
                else {println("El arreglo no está ordenado")
                    exitProcess(1)}
                timeProm += elapsedTime2
                h++
                println("")
            }

            // Se calcula la desviacion estandar
            u = timeProm/tnum
            suma = 0.0
            for (j in 0 until tnum) {suma = suma + (copiasTiempo[j] - u) * (copiasTiempo[j] - u)}
            desviacionEstandar = kotlin.math.sqrt(suma/tnum)

            // Se calcula el tiempo promedio de ejecucion del algoritmo si se ejecuta mas de una vez
            if (tnum > 1) {println("Tiempo promedio Bubble Sort: " + (timeProm/tnum).toString() + " ns")
            println("Desviación estándar Bubble Sort: " + desviacionEstandar.toString())}
            println("")
        }

        else if (division[i] == "ms") {
            // Se crean copias del arreglo original para ejecutar los algoritmos
            var h = 0
            timeProm = 0.0
            // Se ejecuta el algoritmo de ordenamiento correspondiente
            repeat(tnum) {
                val elapsedTime3 = measureNanoTime {array2 = mergeSort(copies[h])}
                copiasTiempo[h] = elapsedTime3.toDouble()
                println("Merge Sort ---> Tiempo transcurrido: " + elapsedTime3.toString() + " ns")
                // Se verifica que el arreglo este ordenado de forma ascendente
                if (estaEnOrdenAscendente(array2)) {println("El arreglo está ordenado")}
                else {println("El arreglo no está ordenado")
                    exitProcess(1)}
                timeProm += elapsedTime3
                h++
                println("")
            }

            // Se calcula la desviacion estandar
            u = timeProm/tnum
            suma = 0.0
            for (j in 0 until tnum) {suma = suma + (copiasTiempo[j] - u) * (copiasTiempo[j] - u)}
            desviacionEstandar = kotlin.math.sqrt(suma/tnum)

            // Se calcula el tiempo promedio de ejecucion del algoritmo si se ejecuta mas de una vez
            if (tnum > 1) {println("Tiempo promedio Merge Sort: " + (timeProm/tnum).toString() + " ns")
            println("Desviación estándar Merge Sort: " + desviacionEstandar.toString())}
            println("")
        }

        else if (division[i] == "mi") {
            // Se crean copias del arreglo original para ejecutar los algoritmos
            var h = 0
            timeProm = 0.0
            // Se ejecuta el algoritmo de ordenamiento correspondiente
            repeat(tnum) {
                val elapsedTime4 = measureNanoTime {array2 = mergeSortIterativo(copies[h])}
                copiasTiempo[h] = elapsedTime4.toDouble()
                println("Merge Sort Iterativo---> Tiempo transcurrido: " + elapsedTime4.toString() + " ns")
                // Se verifica que el arreglo este ordenado de forma ascendente
                if (estaEnOrdenAscendente(array2)) {println("El arreglo está ordenado")}
                else {println("El arreglo no está ordenado")
                    exitProcess(1)}
                timeProm += elapsedTime4
                h++
                println("")
            }

            // Se calcula la desviacion estandar
            u = timeProm/tnum
            suma = 0.0
            for (j in 0 until tnum) {suma = suma + (copiasTiempo[j] - u) * (copiasTiempo[j] - u)}
            desviacionEstandar = kotlin.math.sqrt(suma/tnum)

            // Se calcula el tiempo promedio de ejecucion del algoritmo si se ejecuta mas de una vez
            if (tnum > 1) {println("Tiempo promedio Merge Sort Iterativo: " + (timeProm/tnum).toString() + " ns")
            println("Desviación estándar Merge Sort Iterativo: " + desviacionEstandar.toString())}
            println("")
        }

        // Si no se ingresan los argumentos de entrada correctamente, se imprime un mensaje de error
        else {println("Error al indicar los argumentos")}
    }
}

// Funcion que genera arreglo con numeros aleatorios enteros
/**
* requires: tnum >= 0 && num >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures true
 */
fun random(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = Array<Number>(N) { Random.nextInt(0, N+1) }
    // Se ejecuta la funcion algoritmoAEjecutar
    algoritmoAEjecutar(A, tnum, algoritmo)
}

// Funcion que genera arreglo con numeros aleatorios reales
/**
* requires: tnum >= 0 && num >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures true
 */
fun randomd(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = Array<Number>(N) { Random.nextDouble(0.0, 1.1) }
    // Se ejecuta la funcion algoritmoAEjecutar
    algoritmoAEjecutar(A, tnum, algoritmo)
}

// Funcion que genera arreglo con numeros enteros ordenados de forma ascendente
/**
* requires: tnum >= 0 && num >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures true
 */
fun sorted(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = Array<Number>(N) { it + 1 }
    // Se ejecuta la funcion algoritmoAEjecutar
    algoritmoAEjecutar(A, tnum, algoritmo)
}

// Funcion que genera arreglo con numeros reales ordenados de forma ascendente
/**
* requires: tnum >= 0 && num >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures true
 */
fun sortedd(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = if (N == 1) Array<Number>(N) { 1.0 } else Array<Number>(N) { it.toDouble() / (N - 1) }
    // Se ejecuta la funcion algoritmoAEjecutar
    algoritmoAEjecutar(A, tnum, algoritmo)
}

// Funcion que genera arreglo con numeros enteros ordenados de forma descendente
/**
* requires: tnum >= 0 && num >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures true
 */
fun inv(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = Array<Number>(N) { N - it }
    // Se ejecuta la funcion algoritmoAEjecutar
    algoritmoAEjecutar(A, tnum, algoritmo)
}

// Funcion que genera arreglo con ceros y unos
/**
* requires: tnum >= 0 && num >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures true
 */
fun zu(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = Array<Number>(N) { Random.nextInt(2) }
    // Se ejecuta la funcion algoritmoAEjecutar
    algoritmoAEjecutar(A, tnum, algoritmo)
}

// Funcion que genera arreglo con numeros enteros ordenados de forma ascendente y descendente
/**
* requires: tnum >= 0 && num >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures true
 */
fun media(tnum: Int, num: Int, algoritmo: String) {
    val N = num
    val A = Array<Number>(N) {
        if (it < N / 2) it + 1
        else N - it}
    // Se ejecuta la funcion algoritmoAEjecutar
    algoritmoAEjecutar(A, tnum, algoritmo)
}

fun dataGeneration() : Triple<Array<String>, Array<Int>, Triple< Array<Double>,  Array<Double>,  Array<Double>>> {

}