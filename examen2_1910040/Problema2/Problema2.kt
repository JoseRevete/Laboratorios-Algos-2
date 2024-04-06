fun main(args: Array<String>) {
    val entrada = ListaEnlazadaSimple<Int>()
    val count = ListaEnlazadaSimple<Int>()

    for (arg in args) {
        val num = arg.toInt()
        entrada.agregarAlFinal(num)

        var encontrado = false
        for (i in 0 until count.numElementos()) {
            if (count.obtener(i) == num) {
                encontrado = true
                break
            }
        }

        if (!encontrado) {
            count.agregarAlFinal(num)
        }
    }

    var maxCount = 0
    var maxNum = 0
    for (i in 0 until count.numElementos()) {
        var currCount = 0
        for (j in 0 until entrada.numElementos()) {
            if (entrada.obtener(j) == count.obtener(i)) {
                currCount++
            }
        }

        if (currCount > maxCount) {
            maxCount = currCount
            maxNum = count.obtener(i)!!
        }
    }

    if (maxCount > entrada.numElementos() / 2) {
        println("$maxNum")
    } else {
        println("No hay elemento mayoritario.")
    }
}