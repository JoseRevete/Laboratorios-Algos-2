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
                var temp = A[j]
                A[j] = A[j - 1]
                A[j - 1 ] = temp}
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
            val temp = A[j]
            A[j] = A[j-1]
            A[j-1] = temp
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
        var temp = A[0]
        A[0] = A[i]
        A[i] = temp
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
fun buildMaxHeap(A : Array<Number>, n : Int) {
    for (i in (n / 2) downTo 0) {
        maxHeapify(A, i, n)
    }
}

// Funciones auxiliares de HeapSort
/**
* requires: A != null && A.size > 0
* ensures: true
*/
fun maxHeapify(A : Array<Number>, i : Int, heapSize : Int) {
    val l = 2 * i + 1
    val r = 2 * i + 2
    var largest = i
    if (l < heapSize && A[l].toDouble() > A[largest].toDouble()) {
        largest = l
    }
    if (r < heapSize && A[r].toDouble() > A[largest].toDouble()) {
        largest = r
    }
    if (largest != i) {
        var temp = A[i]
        A[i] = A[largest]
        A[largest] = temp
        maxHeapify(A, largest, heapSize)
    }
}