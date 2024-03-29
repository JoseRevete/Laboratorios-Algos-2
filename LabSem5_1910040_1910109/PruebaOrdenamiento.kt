import kotlin.system.measureNanoTime
import kotlin.system.exitProcess
import kotlin.random.Random

// Funcion main que recibe los argumentos de entrada y llama a la funcion correspondiente
fun main(args: Array<String>) : Unit {
    // Se verifica que se hayan ingresado los argumentos de entrada
    var tnum : Int = 0
    var secuencia : String = ""
    var algoritmo : String = ""
    var number : String = ""
    var name : String = ""
    var verificar : Boolean = false

    for (i in 0 until args.size step 2) {
        // Se verifica que los argumentos de entrada sean correctos
        try {
            if (args[i] == "-t") {tnum = args[i +1].toInt()}
            else if (args[i] == "-s") {secuencia = args[i +1]}
            else if (args[i] == "-a") {algoritmo = args[i +1]}
            else if (args[i] == "-n") {number = args[i +1]}
            else if (args[i] == "-g") {name = args[i +1]
                            verificar = true}
            else {println("Error al indicar los argumentos, recordar que los argumentos de entrada son: -t, -s, -a, -n, -g (-g es opcional)")
                exitProcess(1)} 
        } // Si no se ingresan los argumentos de entrada correctamente, se imprime un mensaje de error
        catch(e: Exception) {
            println("Error al indicar los argumentos, recordar que cada flag debe tener un argumento de entrada.")
            exitProcess(1)}        
    }

    // Se separan los argumentos de entrada
    val numb = number.split(",")
    val num = numb.map { it.toInt() }
    for (i in 0 until num.size) {
        try {
            // Se verifica que los argumentos de entrada sean correctos
            if (tnum < 0 || num[i] < 0) {println("Error al indicar los argumentos, recordar que los argumentos de -t y -n deben ser mayores a 0.")
                exitProcess(1)}
        } // Si no se ingresan los argumentos de entrada correctamente, se imprime un mensaje de error
        catch(e: Exception) {
            println("Error al indicar los argumentos, recordar que los argumentos de -t y -n deben ser números enteros positivos.")
            exitProcess(1)}
    }
    
    // Se separan los argumentos de entrada
    val division = algoritmo.split(",")
    // Se guardan los nombres de los algoritmos de entrada
    var arrayNombre : Array<String> = Array<String>(num.size*division.size) { "" }
    var g = 0
    // Se asignan los nombres de los algoritmos de entrada
    for (i in 0 until division.size) {
        repeat(num.size) {
            if (division[i] == "ms") { arrayNombre[g] = "Merge Sort"}
            else if (division[i] == "mi") { arrayNombre[g] = "Merge Sort Iterativo"}
            else if (division[i] == "is") { arrayNombre[g] = "Insertion Sort"}
            else if (division[i] == "bs") { arrayNombre[g] = "Bubble Sort"}
            else if (division[i] == "hs") { arrayNombre[g] = "Heap Sort"}
            else if (division[i] == "qc") { arrayNombre[g] = "Quick Sort Clásico"}
            else if (division[i] == "qd") { arrayNombre[g] = "Dual-Pivot Quick Sort"}
            else if (division[i] == "cs") { arrayNombre[g] = "Counting Sort"}
            else if (division[i] == "rs") { arrayNombre[g] = "Radix Sort"}
            else {println("Error al indicar los argumentos, recordar que los algoritmos de entrada son: ms, mi, is, bs, hs, qc, qd, cs, rs")
                exitProcess(1)}
            g++
        }}

    
    // Se crean arreglos para guardar los tiempos de ejecucion
    var arrayTMin = Array<Double>(num.size*division.size) { 0.0 }
    var arrayTMax = Array<Double>(num.size*division.size) { 0.0 }
    var arrayTP = Array<Double>(num.size*division.size) { 0.0 }
    var arrayTiempos = Triple(arrayTMin, arrayTP, arrayTMax)

    // Se crean arreglos para guardar los numeros de elementos
    var arrayNumeros : Array<Int> = Array<Int>(num.size*division.size) { 0 }
    for (i in 0 until num.size) {
        println("____________________________________")
        println("Número de elementos: " + num[i].toString())
        // Se ejecuta la funcion correspondiente
        if (secuencia == "random") {arrayTiempos = random(tnum, num[i], algoritmo, num.size, arrayTP, i, arrayTMin, arrayTMax)}
        else if (secuencia == "randomd") {arrayTiempos = randomd(tnum, num[i], algoritmo, num.size, arrayTP, i, arrayTMin, arrayTMax)}
        else if (secuencia == "sorted") {arrayTiempos = sorted(tnum, num[i], algoritmo, num.size, arrayTP, i, arrayTMin, arrayTMax)}
        else if (secuencia == "sortedd") {arrayTiempos = sortedd(tnum, num[i], algoritmo, num.size, arrayTP, i, arrayTMin, arrayTMax)}
        else if (secuencia == "inv") {arrayTiempos = inv(tnum, num[i], algoritmo, num.size, arrayTP, i, arrayTMin, arrayTMax)}
        else if (secuencia == "zu") {arrayTiempos = zu(tnum, num[i], algoritmo, num.size, arrayTP, i, arrayTMin, arrayTMax)}
        else if (secuencia == "media") {arrayTiempos = media(tnum, num[i], algoritmo, num.size, arrayTP, i, arrayTMin, arrayTMax)}
        else {println("Error al indicar los argumentos, recordar que las secuencias de entrada son: random, randomd, sorted, sortedd, inv, zu, media")
            exitProcess(1)}
    }
    g = 0
    // Se guardan los numeros de elementos de entrada en un arreglo
    repeat(division.size) {
        for (i in 0 until num.size) {
            arrayNumeros[g] = num[i]
            g++
        }
    }
    
    // Se verifica que se hayan ingresado los argumentos de entrada para generar la grafica
    if (name != "" && num.size > 1 && verificar == true) {
        plotRuntime("Gráficas de tiempo de algoritmos de ordenamiento",
            ".",
            name.toString()+".png",
            "Resultados de las secuencias",
            "Número de elementos",
            "Tiempo (segundos)",
            arrayNombre,
            arrayNumeros,
            arrayTiempos.first,
            arrayTiempos.second,
            arrayTiempos.third)}
    else if (verificar == true && num.size <= 1) { println("Error al indicar los argumentos, recordar que se debe ingresar mas de un número de elementos para generar la gráfica.")
        exitProcess(1)}
    else {}
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
fun algoritmoAEjecutar(array : Array<Number>, tnum: Int, algoritmo: String, num : Int, arrayTP: Array<Double>, s: Int, arrayTMin : Array<Double>, arrayTMax : Array<Double>) : Triple<Array<Double>, Array<Double>, Array<Double>> {
    var arrayAlgoritmos : Array<Double>

    // Se comparan los argumentos de entrada para ejecutar el algoritmo correspondiente
    val division = algoritmo.split(",")
    val copies = Array(tnum*division.size) { array.copyOf() }
    val copiasTiempo = Array(tnum*division.size) { 0.0 }
    var k = s
    var h = 0

    for (i in 0 until division.size) {
        // Se ejecuta el algoritmo de ordenamiento correspondiente
        if  (division[i] == "is" || division[i] == "bs" || division[i] == "ms" || division[i] == "mi" || division[i] == "hs" || division[i] == "qc" || division[i] == "qd" || division[i] == "cs" || division[i] == "rs") {
            var retorno = ejecutarAlgoritmos(division[i], tnum, copies, copiasTiempo, h)
            arrayAlgoritmos = retorno.first
            h = retorno.second}

        // Si no se ingresan los argumentos de entrada correctamente, se imprime un mensaje de error
        else {println("Error al indicar los argumentos, recordar que los algoritmos de entrada son: ms, mi, is, bs, hs, qc, qd, cs, rs")
            exitProcess(1)}

        // Se guardan los tiempos de ejecucion
        arrayTP[k] = arrayAlgoritmos[1]
        arrayTMin[k] = arrayAlgoritmos[0]
        arrayTMax[k] = arrayAlgoritmos[2]
        k += num
    }

    var arrayTiempos = Triple(arrayTMin, arrayTP, arrayTMax)
    return arrayTiempos
}





fun ejecutarAlgoritmos(algoritmo : String, tnum: Int, copies : Array<Array<Number>>, copiasTiempo : Array<Double>, h : Int) : Pair<Array<Double>, Int> {
    println("-------------------------------------")
    println("")
    // Se inicializan las variables
    var array2 : Array<Number>
    var menorTiempo : Double = Double.MAX_VALUE
    var mayorTiempo : Double = 0.0
    var u : Double
    var suma : Double
    var elapsedTime1 : Double
    var desviacionEstandar : Double
    var t = h
    var timeProm = 0.0

    // Se ejecuta el algoritmo de ordenamiento correspondiente
    repeat(tnum) {
        if (algoritmo == "is") {
            // Se mide el tiempo de ejecucion
            val tiemposEjecucion = measureNanoTime {array2 = insertionSort(copies[t])}
            elapsedTime1 = tiemposEjecucion.toDouble() / 1000000000.0
            copiasTiempo[t] = elapsedTime1.toDouble()
            println("Insertion Sort ---> Tiempo transcurrido: " + elapsedTime1.toString() + " segundos.")
        }
        else if (algoritmo == "bs") {
            // Se mide el tiempo de ejecucion
            val tiempoEjecucion = measureNanoTime {array2 = bubbleSort(copies[t])}
            elapsedTime1 = tiempoEjecucion.toDouble() / 1000000000.0
            copiasTiempo[t] = elapsedTime1.toDouble()
            println("Bubble Sort ---> Tiempo transcurrido: " + elapsedTime1.toString() + " segundos.")
        }
        else if (algoritmo == "ms") {
            // Se mide el tiempo de ejecucion
            val tiempoEjecucion = measureNanoTime {array2 = mergeSort(copies[t])}
            elapsedTime1 = tiempoEjecucion.toDouble() / 1000000000.0
            copiasTiempo[t] = elapsedTime1.toDouble()
            println("Merge Sort ---> Tiempo transcurrido: " + elapsedTime1.toString() + " segundos.")
        }
        else if (algoritmo == "mi") {
            // Se mide el tiempo de ejecucion
            val tiempoEjecucion = measureNanoTime {array2 = mergeSortIterativo(copies[t])}
            elapsedTime1 = tiempoEjecucion.toDouble() / 1000000000.0
            copiasTiempo[t] = elapsedTime1.toDouble()
            println("Merge Sort Iterativo---> Tiempo transcurrido: " + elapsedTime1.toString() + " segundos.")
        }
        else if (algoritmo == "hs") {
            // Se mide el tiempo de ejecucion
            val tiempoEjecucion = measureNanoTime {array2 = heapSort(copies[t])}
            elapsedTime1 = tiempoEjecucion.toDouble() / 1000000000.0
            copiasTiempo[t] = elapsedTime1.toDouble()
            println("Heap Sort---> Tiempo transcurrido: " + elapsedTime1.toString() + " segundos.")
        }
        else if (algoritmo == "qc") {
            // Se mide el tiempo de ejecucion
            val tiempoEjecucion = measureNanoTime {array2 = quicksort(copies[t])}
            elapsedTime1 = tiempoEjecucion.toDouble() / 1000000000.0
            copiasTiempo[t] = elapsedTime1.toDouble()
            println("QuickSort Clásico ---> Tiempo transcurrido: " + elapsedTime1.toString() + " segundos.")
        }
        else if (algoritmo == "qd") {
            // Se mide el tiempo de ejecucion
            val tiempoEjecucion = measureNanoTime {array2 = dualPivotQuicksort(copies[t])}
            elapsedTime1 = tiempoEjecucion.toDouble() / 1000000000.0
            copiasTiempo[t] = elapsedTime1.toDouble()
            println("DualPivotQuickSort ---> Tiempo transcurrido: " + elapsedTime1.toString() + " segundos.")
        }
        else if (algoritmo == "cs") {
            // Se mide el tiempo de ejecucion
            val tiempoEjecucion = measureNanoTime {array2 = countingSort(copies[t])}
            elapsedTime1 = tiempoEjecucion.toDouble() / 1000000000.0
            copiasTiempo[t] = elapsedTime1.toDouble()
            println("Counting Sort ---> Tiempo transcurrido: " + elapsedTime1.toString() + " segundos.")
        }
        else if (algoritmo == "rs") {
            // Se mide el tiempo de ejecucion
            val tiempoEjecucion = measureNanoTime {array2 = radixSort(copies[t])}
            elapsedTime1 = tiempoEjecucion.toDouble() / 1000000000.0
            copiasTiempo[t] = elapsedTime1.toDouble()
            println("Radix Sort ---> Tiempo transcurrido: " + elapsedTime1.toString() + " segundos.")
        }
        else {println("Error al indicar los argumentos.")
            exitProcess(1)}

        // Se verifica que el arreglo este ordenado de forma ascendente
        if (estaEnOrdenAscendente(array2)) {println("El arreglo está ordenado.")}
        else {println("El arreglo NO está ordenado.")
            exitProcess(1)}
        timeProm += elapsedTime1
        // Se guardan los tiempos de ejecucion mas cortos y mas largos
        if (elapsedTime1 < menorTiempo) {menorTiempo = elapsedTime1.toDouble()}
        if (elapsedTime1 > mayorTiempo) {mayorTiempo = elapsedTime1.toDouble()}
        t++
        println("")
    }

    // Se calcula la desviacion estandar
    u = timeProm/tnum
    suma = 0.0
    for (j in 0 until tnum) {suma = suma + (copiasTiempo[j] - u) * (copiasTiempo[j] - u)}
    desviacionEstandar = kotlin.math.sqrt(suma/tnum)
    // Se calcula el tiempo promedio de ejecucion del algoritmo si se ejecuta mas de una vez
    if (algoritmo == "is") {
        if (tnum > 1) {println("Tiempo promedio Insertion Sort: " + (timeProm/tnum).toString() + " segundos.")
                println("Desviación estándar Insertion Sort: " + desviacionEstandar.toString())}
                println("")}
    else if (algoritmo == "bs") {
        if (tnum > 1) {println("Tiempo promedio Bubble Sort: " + (timeProm/tnum).toString() + " segundos.")
                println("Desviación estándar Bubble Sort: " + desviacionEstandar.toString())}
                println("")}
    else if (algoritmo == "ms") {
        if (tnum > 1) {println("Tiempo promedio Merge Sort: " + (timeProm/tnum).toString() + " segundos.")
                println("Desviación estándar Merge Sort: " + desviacionEstandar.toString())}
                println("")}
    else if (algoritmo == "mi") {
        if (tnum > 1) {println("Tiempo promedio Merge Sort Iterativo: " + (timeProm/tnum).toString() + " segundos.")
                println("Desviación estándar Merge Sort Iterativo: " + desviacionEstandar.toString())}
                println("")}
    else if (algoritmo == "hs") {
        if (tnum > 1) {println("Tiempo promedio Heap Sort: " + (timeProm/tnum).toString() + " segundos.")
                println("Desviación estándar Heap Sort: " + desviacionEstandar.toString())}
                println("")}
    else if (algoritmo == "qc") {
        if (tnum > 1) {println("Tiempo promedio QuickSort Clasico: " + (timeProm/tnum).toString() + " segundos.")
                println("Desviación estándar QuickSort Clasico: " + desviacionEstandar.toString())}
                println("")}
    else if (algoritmo == "qd") {
        if (tnum > 1) {println("Tiempo promedio DualPivotQuickSort: " + (timeProm/tnum).toString() + " segundos.")
                println("Desviación estándar DualPivotQuickSort: " + desviacionEstandar.toString())}
                println("")}
    else if (algoritmo == "cs") {
        if (tnum > 1) {println("Tiempo promedio Counting Sort: " + (timeProm/tnum).toString() + " segundos.")
                println("Desviación estándar Counting Sort: " + desviacionEstandar.toString())}
                println("")}
    else if (algoritmo == "rs") {
        if (tnum > 1) {println("Tiempo promedio Radix Sort: " + (timeProm/tnum).toString() + " segundos.")
                println("Desviación estándar Radix Sort: " + desviacionEstandar.toString())}
                println("")}
    // Se guardan los tiempos de ejecucion
    var arrayF = arrayOf(menorTiempo, timeProm/tnum, mayorTiempo)
    var arrayFinal = Pair(arrayF, t)
    return arrayFinal
}





// Funcion que genera arreglo con numeros aleatorios enteros
/**
* requires: tnum >= 0 && N >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures true
 */
fun random(tnum: Int, N: Int, algoritmo: String, num: Int, arrayTP: Array<Double>, i: Int, arrayTMin : Array<Double>, arrayTMax : Array<Double>) : Triple<Array<Double>, Array<Double>, Array<Double>> {
    val A = Array<Number>(N) { Random.nextInt(0, N+1) }
    // Se ejecuta la funcion algoritmoAEjecutar
    var arrayTiempos = algoritmoAEjecutar(A, tnum, algoritmo, num, arrayTP, i, arrayTMin, arrayTMax)
    return arrayTiempos
}



// Funcion que genera arreglo con numeros aleatorios reales
/**
* requires: tnum >= 0 && N >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures true
 */
fun randomd(tnum: Int, N: Int, algoritmo: String, num: Int, arrayTP: Array<Double>, i: Int, arrayTMin : Array<Double>, arrayTMax : Array<Double>) : Triple<Array<Double>, Array<Double>, Array<Double>>{
    val A = Array<Number>(N) { Random.nextDouble(0.0, 1.00000000000000000000000001) }
    // Se ejecuta la funcion algoritmoAEjecutar
    var arrayTiempos = algoritmoAEjecutar(A, tnum, algoritmo, num, arrayTP, i, arrayTMin, arrayTMax)
    return arrayTiempos
}



// Funcion que genera arreglo con numeros enteros ordenados de forma ascendente
/**
* requires: tnum >= 0 && N >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures true
 */
fun sorted(tnum: Int, N: Int, algoritmo: String, num: Int, arrayTP: Array<Double>, i: Int, arrayTMin : Array<Double>, arrayTMax : Array<Double>) : Triple<Array<Double>, Array<Double>, Array<Double>>{
    val A = Array<Number>(N) { it + 1 }
    // Se ejecuta la funcion algoritmoAEjecutar
    var arrayTiempos = algoritmoAEjecutar(A, tnum, algoritmo, num, arrayTP, i, arrayTMin, arrayTMax)
    return arrayTiempos
}



// Funcion que genera arreglo con numeros reales ordenados de forma ascendente
/**
* requires: tnum >= 0 && N >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures true
 */
fun sortedd(tnum: Int, N: Int, algoritmo: String, num: Int, arrayTP: Array<Double>, i: Int, arrayTMin : Array<Double>, arrayTMax : Array<Double>) : Triple<Array<Double>, Array<Double>, Array<Double>>{
    val A = if (N == 1) Array<Number>(N) { 1.0 } else Array<Number>(N) { it.toDouble() / (N - 1) }
    // Se ejecuta la funcion algoritmoAEjecutar
    var arrayTiempos = algoritmoAEjecutar(A, tnum, algoritmo, num, arrayTP, i, arrayTMin, arrayTMax)
    return arrayTiempos
}



// Funcion que genera arreglo con numeros enteros ordenados de forma descendente
/**
* requires: tnum >= 0 && N >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures true
 */
fun inv(tnum: Int, N: Int, algoritmo: String, num: Int, arrayTP: Array<Double>, i: Int, arrayTMin : Array<Double>, arrayTMax : Array<Double>) : Triple<Array<Double>, Array<Double>, Array<Double>>{
    val A = Array<Number>(N) { N - it }
    // Se ejecuta la funcion algoritmoAEjecutar
    var arrayTiempos = algoritmoAEjecutar(A, tnum, algoritmo, num, arrayTP, i, arrayTMin, arrayTMax)
    return arrayTiempos
}



// Funcion que genera arreglo con ceros y unos
/**
* requires: tnum >= 0 && N >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures true
 */
fun zu(tnum: Int, N: Int, algoritmo: String, num: Int, arrayTP: Array<Double>, i: Int, arrayTMin : Array<Double>, arrayTMax : Array<Double>) : Triple<Array<Double>, Array<Double>, Array<Double>>{
    val A = Array<Number>(N) { Random.nextInt(2) }
    // Se ejecuta la funcion algoritmoAEjecutar
    var arrayTiempos = algoritmoAEjecutar(A, tnum, algoritmo, num, arrayTP, i, arrayTMin, arrayTMax)
    return arrayTiempos
}



// Funcion que genera arreglo con numeros enteros ordenados de forma ascendente y descendente
/**
* requires: tnum >= 0 && N >= 0 && algoritmo == "is,bs" || algoritmo == "is" || algoritmo == "bs"
* ensures: true
 */
fun media(tnum: Int, N: Int, algoritmo: String, num: Int, arrayTP: Array<Double>, i: Int, arrayTMin : Array<Double>, arrayTMax : Array<Double>) : Triple<Array<Double>, Array<Double>, Array<Double>>{
    val A = Array<Number>(N) {
        if (it < N / 2) it + 1
        else N - it}
    // Se ejecuta la funcion algoritmoAEjecutar
    var arrayTiempos = algoritmoAEjecutar(A, tnum, algoritmo, num, arrayTP, i, arrayTMin, arrayTMax)
    return arrayTiempos
}