operator fun Number.compareTo(other: Number) = this.toDouble().compareTo(other.toDouble())

// Funcion de ordenamiento BubbleSort
/**
* requires: A != null && A.size > 0
* ensures: result != null && (forall int i; 0 <= i < result.size-1; result[i] <= result[i+1])
 */
fun bubbleSort(A: Array<Number>): Array<Number> {
    for ( i in 0..(A.size-1)) {
        for (j in (A.size-1) downTo (i + 1)) {
            if (A[j] < A[j - 1]) {
                swap(A, j, j - 1)}
        }}
    return A
}

// Funcion de ordenamiento InsertionSort
/**
* requires: A != null && A.size > 0
* ensures: result != null && (forall int i; 0 <= i < result.size-1; result[i] <= result[i+1])
 */
fun insertionSort(A: Array<Number>): Array<Number> {
    for (i in 1 until A.size) {
        var j = i
        while (j > 0 && A[j] < A[j - 1]) {
            swap(A, j, j - 1)
            j -= 1
        }
    }
    return A
}

// Funcion de ordenamiento MergeSort
/**
* requires: A != null && A.size > 0
* ensures: result != null && (forall int i; 0 <= i < result.size-1; result[i] <= result[i+1])
 */
fun mergeSort(A: Array<Number>): Array<Number> {
    if (A.size <= 1) {
        return A}
    val medio = A.size / 2
    val izq = A.sliceArray(0 until medio)
    val der = A.sliceArray(medio until A.size)
    var arrayFinal = merge(mergeSort(izq), mergeSort(der))
    return arrayFinal
}

// Funcion swap
/**
* requires: A != null && A.size > 0 && i >= 0 && j >= 0 && i < A.size && j < A.size
* ensures: true
*/
fun swap(A: Array<Number>, i: Int, j: Int) {
    val temp = A[i]
    A[i] = A[j]
    A[j] = temp
}

// Funcion auxiliar de MergeSort
/**
* requires: A != null && A.size > 0 && B != null && B.size > 0
* ensures: result != null && (forall int i; 0 <= i < result.size-1; result[i] <= result[i+1])
*/
fun merge(A: Array<Number>, B: Array<Number>): Array<Number> {
    val C = Array<Number>(A.size + B.size) { 0 }
    var i = 0
    var j = 0
    var k = 0
    while (i < A.size && j < B.size) {
        if (A[i] <= B[j]) {
            C[k] = A[i]
            i++
        } else {
            C[k] = B[j]
            j++
        }
        k++
    }
    while (i < A.size) {
        C[k] = A[i]
        i++
        k++
    }
    while (j < B.size) {
        C[k] = B[j]
        j++
        k++
    }
    return C
}

// Funcion de ordenamiento MergeSort-Iterativo
/**
* requires: A != null && A.size > 0
* ensures: result != null && (forall int i; 0 <= i < result.size-1; result[i] <= result[i+1])
*/
fun mergeSortIterativo(A: Array<Number>): Array<Number> {
    var n = A.size
    var i = 1
    while (i < n) {
        var j = 0
        while (j < n - i) {
            var izq = j
            var der = j + i
            var fin = Math.min(j + 2 * i, n)
            var B = merge(A.sliceArray(izq until der), A.sliceArray(der until fin))
            for (k in izq until fin) {
                A[k] = B[k - izq]
            }
            j += 2 * i
        }
        i *= 2
    }
    return A
}

// Funcion de ordenamiento HeapSort
/**
* requires: A != null && A.size > 0
* ensures: result != null && (forall int i; 0 <= i < result.size-1; result[i] <= result[i+1])
*/
fun heapSort(A : Array<Number>) : Array<Number> {
    var heapSize = A.size
    buildMaxHeap(A, heapSize)
    for (i in (heapSize - 1) downTo 1) {
        swap(A, 0, i)
        heapSize -= 1
        maxHeapify(A, 0, heapSize)
    }
    return A
}

// Funciones auxiliares de HeapSort
/**
* requires: A != null && A.size > 0
* ensures: true
*/
fun buildMaxHeap(A : Array<Number>, n : Int) : Unit{
    for (i in (n / 2) downTo 0) {
        maxHeapify(A, i, n)
    }
}

// Funciones auxiliares de HeapSort
/**
* requires: A != null && A.size > 0
* ensures: true
*/
fun maxHeapify(A : Array<Number>, i : Int, heapSize : Int) : Unit {
    val l = 2 * i + 1
    val r = 2 * i + 2
    var largo = i
    if (l < heapSize && A[l].toDouble() > A[largo].toDouble()) {
        largo = l
    }
    if (r < heapSize && A[r].toDouble() > A[largo].toDouble()) {
        largo = r
    }
    if (largo != i) {
        swap(A, i, largo)
        maxHeapify(A, largo, heapSize)
    }
}

// Funcion de ordenamiento QuickSort
/**
* requires: A != null && A.size > 0
* ensures: result != null && (forall int i; 0 <= i < result.size-1; result[i] <= result[i+1])
*/
fun quicksort(A: Array<Number>) : Array<Number> {
    println(" array antes de quicksort: " + A[0].toString()+" "+A[1].toString()+" "+A[2].toString()+" "+A[3].toString()+" "+A[4].toString()+" "+A[5].toString())
    quicksortAux(A, 0, A.size - 1)
    println(" array despues de quicksort: " + A[0].toString()+" "+A[1].toString()+" "+A[2].toString()+" "+A[3].toString()+" "+A[4].toString()+" "+A[5].toString())
    return A
}

fun quicksortAux(A: Array<Number>, start: Int, end: Int) : Array<Number> {
    println(" start: " + start.toString() + " end: " + end.toString())
    if (start < end) {
        val q = particion(A, start, end)
        quicksortAux(A, start, q - 1)
        quicksortAux(A, q + 1, end)
    }
    return A
}

// Funcion auxiliar de QuickSort
/**
* requires: A != null && A.size > 0
* ensures: true
*/
fun particion(A: Array<Number>, p: Int, r: Int): Int {
    var x = A[r]
    var i = p - 1
    for (j in p until r) {
        if (A[j] <= x) {
            i += 1
            swap(A, i, j)
        }
    }
    swap(A, i + 1, r)
    return i + 1
}

// Funcion de ordenamiento DualPivotQuickSort
/**
* requires: A != null && A.size > 0
* ensures: result != null && (forall int i; 0 <= i < result.size-1; result[i] <= result[i+1])
*/
fun dualPivotQuicksort(A: Array<Number>) : Array<Number> {
    dualPivotQuicksortAux(A, 0, A.size - 1)
    return A
}

// Funcion de ordenamiento DualPivotQuickSort
/**
* requires: A != null && A.size > 0
* ensures: result != null && (forall int i; 0 <= i < result.size-1; result[i] <= result[i+1])
*/
fun dualPivotQuicksortAux(A: Array<Number>, izq : Int, der : Int): Array<Number> {
    var P : Number
    var Q : Number
    if (der - izq >= 1) {
        if (A[izq] >= A[der]) {P = A[der]; Q = A[izq]}
        else {P = A[izq]; Q = A[der]}
        var k = izq + 1
        var l = k
        var g = der - 1
        while (k <= g) {
            if (A[k] < P) {
                swap(A, k, l)
                l += 1}
            else if (A[k] >= Q) {
                while (A[g] > Q && k < g) {
                    g -= 1
                }
                swap(A, k, g)
                g -= 1
                if (A[k] < P) {
                    swap(A, k, l)
                    l += 1
                }
            }
            k += 1
        }
        l -= 1
        g += 1
        A[izq] = A[l]
        A[l] = P
        A[der] = A[g]
        A[g] = Q
        dualPivotQuicksortAux(A, izq, l-1)
        dualPivotQuicksortAux(A, l+1, g-1)
        dualPivotQuicksortAux(A, g+1, der)
    }
    return A
}