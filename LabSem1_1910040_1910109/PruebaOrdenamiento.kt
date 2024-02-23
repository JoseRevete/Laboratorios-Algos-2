import kotlin.system.measureTimeMillis
import kotlin.system.exitProcess
import kotlin.random.Random

// Funcion main que recibe los argumentos de entrada y llama a la funcion correspondiente
fun main(args: Array<String>) {
    // Se verifica que se hayan ingresado los argumentos de entrada
    var tnum : Int = 0
    var secuencia : String = ""
    var algoritmo : String = ""
    var num : Int = 0

    for (i in 0 until 8 step 2) {
        // Se verifica que los argumentos de entrada sean correctos
        try {
            if (args[i] == "-t") {tnum = args[i +1].toInt()}
            else if (args[i] == "-s") {secuencia = args[i +1]}
            else if (args[i] == "-a") {algoritmo = args[i +1]}
            else if (args[i] == "-n") {num = args[i +1].toInt()}
            else {println("Error al indicar los argumentos")
                exitProcess(1)}
        } // Si no se ingresan los argumentos de entrada correctamente, se imprime un mensaje de error
        catch(e: Exception) {
            println("Error al indicar los argumentos")
            exitProcess(1)}        
    }

    // Se verifica que los argumentos de entrada sean correctos
    if (tnum < 0 || num < 0) {println("Error al indicar los argumentos")
        exitProcess(1)}

    // Se ejecuta la funcion correspondiente
    if (secuencia == "random") {random(tnum, num, algoritmo)}
    else if (secuencia == "randomd") {randomd(tnum, num, algoritmo)}
    else if (secuencia == "sorted") {sorted(tnum, num, algoritmo)}
    else if (secuencia == "sortedd") {sortedd(tnum, num, algoritmo)}
    else if (secuencia == "inv") {inv(tnum, num, algoritmo)}
    else if (secuencia == "zu") {zu(tnum, num, algoritmo)}
    else if (secuencia == "media") {media(tnum, num, algoritmo)}
    else {println("Error al indicar los argumentos")
        exitProcess(1)}
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

    // Se calcula la desviacion estandar
    val mediaArray = array.map { it.toDouble() }.average()
    val diferenciasCuadradas = Array<Double>(array.size) { 0.0 }
    for (i in array.indices) {
        val diferencia = array[i].toDouble() - mediaArray
        diferenciasCuadradas[i] = diferencia * diferencia}
    val mediaDiferenciasCuadradas = diferenciasCuadradas.average()
    val desviacionEstandar = kotlin.math.sqrt(mediaDiferenciasCuadradas)

    // Se comparan los argumentos de entrada para ejecutar el algoritmo correspondiente
    if (algoritmo == "is,bs") {

        // Se crean copias del arreglo original para ejecutar los algoritmos
        val copies = Array(tnum*2) { array.copyOf() }
        var i = 0
        timeProm = 0.0
        repeat(tnum) {
            // Se ejecuta el algoritmo de ordenamiento correspondiente
            val elapsedTime1 = measureTimeMillis {array1 = insertionSort(copies[i])}
            println("Insertion Sort ---> Tiempo transcurrido: " + elapsedTime1.toString() + " ms")
            // Se verifica que el arreglo este ordenado de forma ascendente
            if (estaEnOrdenAscendente(array1)) {println("El arreglo está ordenado")}
            else {println("El arreglo no está ordenado")
                exitProcess(1)}
            timeProm += elapsedTime1
            i++
            println("")
        }
        // Se calcula el tiempo promedio de ejecucion del algoritmo si se ejecuta mas de una vez
        if (tnum > 1) {println("Tiempo promedio Insertion Sort: " + (timeProm/tnum).toString() + " ms")}
        println("Desviación estándar Insertion Sort: " + desviacionEstandar.toString())
        println("------------------------------------")
        println("")

        timeProm = 0.0
        // Se ejecuta el algoritmo de ordenamiento correspondiente
        repeat(tnum) {
            val elapsedTime2 = measureTimeMillis {array2 = bubbleSort(copies[i])}
            println("Bubble Sort ---> Tiempo transcurrido: " + elapsedTime2.toString() + " ms")
            // Se verifica que el arreglo este ordenado de forma ascendente
            if (estaEnOrdenAscendente(array2)) {println("El arreglo está ordenado")}
            else {println("El arreglo no está ordenado")
                exitProcess(1)}
            timeProm += elapsedTime2
            i++
            println("")
        }
        // Se calcula el tiempo promedio de ejecucion del algoritmo si se ejecuta mas de una vez
        if (tnum > 1) {println("Tiempo promedio Bubble Sort: " + (timeProm/tnum).toString() + " ms")}
        println("Desviación estándar Bubble Sort: " + desviacionEstandar.toString())
        println("")
    }

    // Se comparan los argumentos de entrada para ejecutar el algoritmo correspondiente
    else if (algoritmo == "is") {
        // Se crean copias del arreglo original para ejecutar los algoritmos
        val copies = Array(tnum) { array.copyOf() }
        var i = 0
        timeProm = 0.0
        // Se ejecuta el algoritmo de ordenamiento correspondiente
        repeat(tnum) {
            val elapsedTime1 = measureTimeMillis {array1 = insertionSort(copies[i])}
            println("Insertion Sort ---> Tiempo transcurrido: " + elapsedTime1.toString() + " ms")
            // Se verifica que el arreglo este ordenado de forma ascendente
            if (estaEnOrdenAscendente(array1)) {println("El arreglo está ordenado")}
            else {println("El arreglo no está ordenado")
                exitProcess(1)}
            timeProm += elapsedTime1
            i++
            println("")
        }
        // Se calcula el tiempo promedio de ejecucion del algoritmo si se ejecuta mas de una vez
        if (tnum > 1) {println("Tiempo promedio Insertion Sort: " + (timeProm/tnum).toString() + " ms")}
        println("Desviación estándar Insertion Sort: " + desviacionEstandar.toString())
        println("")
    }

    // Se comparan los argumentos de entrada para ejecutar el algoritmo correspondiente
    else if (algoritmo == "bs") {
        // Se crean copias del arreglo original para ejecutar los algoritmos
        val copies = Array(tnum) { array.copyOf() }
        var i = 0
        timeProm = 0.0
        // Se ejecuta el algoritmo de ordenamiento correspondiente
        repeat(tnum) {
            val elapsedTime2 = measureTimeMillis {array2 = bubbleSort(copies[i])}
            println("Bubble Sort ---> Tiempo transcurrido: " + elapsedTime2.toString() + " ms")
            // Se verifica que el arreglo este ordenado de forma ascendente
            if (estaEnOrdenAscendente(array2)) {println("El arreglo está ordenado")}
            else {println("El arreglo no está ordenado")
                exitProcess(1)}
            timeProm += elapsedTime2
            i++
        }
        // Se calcula el tiempo promedio de ejecucion del algoritmo si se ejecuta mas de una vez
        if (tnum > 1) {println("Tiempo promedio Bubble Sort: " + (timeProm/tnum).toString() + " ms")}
        println("Desviación estándar Bubble Sort: " + desviacionEstandar.toString())
    }
    // Si no se ingresan los argumentos de entrada correctamente, se imprime un mensaje de error
    else {println("Error al indicar los argumentos")}
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