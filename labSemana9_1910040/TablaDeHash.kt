class TablaDeHash {
    private val table = Array<ArbolBinario<Int>>(10) { ArbolBinario() }

    private fun hashFunction(word: String): Int {
        var sum = 0
        for (char in word) {
            sum += char.toInt() - 'a'.toInt() + 1
        }
        return sum % table.size
    }

    fun insert(word: String) {
        val sanitizedWord = word.replace("\"", "")
        val hash = hashFunction(sanitizedWord)
        val tree = table[hash]
        val values = sanitizedWord.map { it.toInt() - 'a'.toInt() + 1 }
        for (i in 1 until values.size) {
            tree.insert(values[i - 1], values[i])
        }
    }

    fun imprimir() {
        for ((index, tree) in table.withIndex()) {
            println("Índice $index: ${tree.imprimir()}")
        }
    }

    fun search(word: String): Boolean {
        val sanitizedWord = word.replace("\"", "")
        val hash = hashFunction(sanitizedWord)
        val tree = table[hash]
        val values = sanitizedWord.map { it.toInt() - 'a'.toInt() + 1 }
        return tree.search(values)
    }
}