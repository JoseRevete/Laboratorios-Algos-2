operator fun Number.compareTo(other: Number) = this.toDouble().compareTo(other.toDouble())

// Funcion de ordenamiento BubbleSort
/**
* requires: A != null && A.size > 0
* ensures: result != null && (forall int i; 0 <= i < result.size-1; result[i] <= result[i+1])
 */
fun bubbleSort(A: Array<Number>): Array<Number> {
    for ( i in 0..(A.size-1)) {
        for (j in (A.size-1) downTo i + 1) {
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
    for (j in 1..(A.size-1)){
        var key = A[j]
        var i = j - 1
        while (i >= 0 && A[i] > key) {
            A[i + 1] = A[i]
            i = i - 1}
        A[i + 1] = key}
    return A
}
