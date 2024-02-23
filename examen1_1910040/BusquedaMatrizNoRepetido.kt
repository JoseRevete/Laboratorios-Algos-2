import java.io.File

// Programa que busca un elemento en una matriz de enteros, en donde cada fila y columna están ordenadas de manera ascendente
fun main(args: Array<String>) : Unit {
    // Se lee el archivo que contiene la matriz
    val nombreArchivo = args[0]
    val archivo = File(nombreArchivo)
    // Se lee la matriz y se guarda en una lista de listas
    val matriz = archivo.bufferedReader().readLines().drop(1).dropLast(1).map { it.split(" ") }
    // la ultima linea del archivo tiene un entero que representa al primer elemento de la fila en donde se debe buscar
    val elementoABuscar = archivo.bufferedReader().readLines().last().toInt()
    // Se busca la columna en la que se encuentra el elemento a buscar
    val columnasPrimerElemento = matriz.map { it.first() }
    var col = buscarColumna(columnasPrimerElemento, 0, columnasPrimerElemento.size, elementoABuscar)
    // Se busca el elemento que no se repite en la columna encontrada
    var fila = buscarElemntoQueNoSeRepite(matriz[col], 0, matriz[col].size - 1)
    println(fila)

}

// Busca la columna en la que se encuentra el elemento a buscar
fun buscarColumna(arrayColumnas: List<String>, i: Int, j: Int, elemento: Int) : Int{
    // Si el elemento a buscar es igual al primer elemento de la columna, se retorna la columna
    if (i == j) {
        return i}
    val mitad = (i + j) / 2
    // Si el elemento a buscar es menor o igual al elemento en la mitad de la columna, se busca en la primera mitad
    if (elemento <= arrayColumnas[mitad].toInt()) {
        return buscarColumna(arrayColumnas, i, mitad, elemento)}
    // Si el elemento a buscar es mayor al elemento en la mitad de la columna, se busca en la segunda mitad
    else {
        return buscarColumna(arrayColumnas, mitad + 1, j, elemento)}    
}

// Busca el elemento que no se repite en un arreglo de enteros
fun buscarElemntoQueNoSeRepite(arrayFilas: List<String>, i : Int, j : Int) : Int{
    // Si el elemento en la mitad del arreglo es diferente a los elementos adyacentes, se retorna el elemento
    if (i == j) {
        return arrayFilas[i].toInt()}
    val mitad = (i + j) / 2
    // Si el elemento en la mitad del arreglo es diferente a los elementos adyacentes, se retorna el elemento
    if (mitad > 0) {
        if (arrayFilas[mitad] != arrayFilas[mitad + 1] && arrayFilas[mitad] != arrayFilas[mitad - 1]) {
            return arrayFilas[mitad].toInt()}}
    // Si el elemento en la mitad del arreglo es igual al elemento adyacente, se busca en la segunda mitad
    else {
        if (arrayFilas[mitad] != arrayFilas[mitad + 1]) {
            return arrayFilas[mitad].toInt()}}
    // Si la mitad es par, se busca en la segunda mitad
    if (mitad % 2 == 0) {
        if (arrayFilas[mitad] == arrayFilas[mitad + 1]) {
            return buscarElemntoQueNoSeRepite(arrayFilas, mitad + 2, j)}
        else {
            return buscarElemntoQueNoSeRepite(arrayFilas, i, mitad)}
    }
    // Si la mitad es impar, se busca en la primera mitad
    else {
        if (arrayFilas[mitad] == arrayFilas[mitad - 1]) {
            return buscarElemntoQueNoSeRepite(arrayFilas, mitad + 1, j)}
        else {
            return buscarElemntoQueNoSeRepite(arrayFilas, i, mitad - 1)}
    }
}