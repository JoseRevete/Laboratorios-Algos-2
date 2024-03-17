import java.io.File

fun main(args: Array<String>) : Unit {
    val nombreDiccionario = args[1]
    val diccionario = File(nombreDiccionario)
    val nombreAgenda = args[0]
    val agenda = File(nombreAgenda)