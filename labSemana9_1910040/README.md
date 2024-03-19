## Descripción: TablaHash
Este archivo (**TablaHash.kt**) es una implementación de una tabla hash en Kotlin. Toma un parámetro de tamañoTabla que determina el tamaño de la tabla hash.
La tabla hash se implementa como un arreglo de _listas doblemente enlazadas_. Cada elemento en la tabla es una lista doblemente enlazada que contiene arreglos de cadenas.
- La función `insertar` permite insertar un elemento en la tabla hash.Calcula la posición del elemento utilizando la función `calcularPosicion` y luego agrega el elemento a la lista en esa posición.
- La función `get` permite obtener un elemento de la tabla hash dado un índice. Devuelve la lista doblemente enlazada en esa posición.
- La función `buscar` permite buscar un elemento en la tabla hash. Calcula la posición del elemento utilizando la función `calcularPosicion` y luego recorre la lista en esa posición para buscar el elemento. Devuelve `true` si se encuentra el elemento y false en caso contrario.
- La función `imprimir` imprime la tabla hash, mostrando la posición y el contenido de cada lista.
- La función `calcularPosicion` calcula la posición de un elemento en la tabla hash. Convierte el elemento a una cadena de caracteres, calcula la suma ponderada de los dígitos de la cadena y luego aplica el operador módulo para obtener la posición en la tabla.


## Descripción: ListaDoblementeEnlazada
Este archivo (**ListaDoblementeEnlazada.kt**) es una implementación de una lista doblemente enlazada en Kotlin. **Métodos y propiedades empleados:**
- `cabeza` y `cola`: Son las referencias al primer y último nodo de la lista, respectivamente.
- `agregar(dato: T)`: Este método crea un nuevo nodo con el dato proporcionado y lo agrega al final de la lista.
- `estaVacia()`: Este método devuelve `true` si la lista está vacía (es decir, si `cabeza` es `null`), y `false` en caso contrario.
- `agregarAlFinal(dato: T)`: Este método agrega un nuevo nodo con el dato proporcionado al final de la lista. Internamente, simplemente llama al método `agregar`.
- `agregarAlInicio(dato: T)`: Este método crea un nuevo nodo con el dato proporcionado y lo agrega al inicio de la lista.
- `eliminarPrimero()`: Este método elimina el primer nodo de la lista.
- `modificarUnNodo(nodo: Nodo<T>, dato: T)`: Este método modifica el dato de un nodo existente.
- `eliminarUltimo()`: Este método elimina el último nodo de la lista.
- `siguiente(nodo: Nodo<T>)`: Este método devuelve el nodo siguiente a un nodo dado.
- `primero()`: Este método devuelve el primer nodo de la lista.
- `ultimo()`: Este método devuelve el último nodo de la lista.
- `tamano()`: Este método devuelve el número de nodos en la lista.
- `imprimir()`: Este método devuelve una representación en cadena de la lista, con los datos de los nodos separados por espacios.
- `iterator()`: Este método devuelve un iterador para la lista, que permite recorrer la lista utilizando un bucle `for` o las funciones `next` y `hasNext`.


## Descripcion: Nodo para lista doblemente enlazada
Este archivo (**Nodo.kt**) es una implementación de un nodo para una lista doblemente enlazada en Kotlin. **Métodos y propiedades empleados:**
- `dato`: Es una variable de tipo genérico `T` que almacena el dato del nodo.
- `siguiente` y `anterior`: Son referencias al siguiente y al anterior nodo en la lista, respectivamente. Ambas son inicializadas como `null`.
- `toString()`: Este método sobrescribe el método `toString` de la clase `Any`. Devuelve una representación en cadena del nodo. Si el dato del nodo es un array, convierte el array a una lista y luego a una cadena. Si el dato no es un array, simplemente lo convierte a una cadena.


## Descripcion: CodificacionTelefonos
Este archivo (**CodificacionTelefonos.kt**), lleva a cabo la resolucion general del laboratorio, realizando llamadas a funciones y clases definidas con estructuras de datos necesarias para la realizacion correcta enunciado planteado. **Funciones empleadas:**
- `main()`: Este es el punto de entrada del programa. Lee los nombres de los archivos del diccionario y de la agenda de los argumentos de la línea de comandos, lee las líneas de estos archivos, codifica las palabras del diccionario y los números de teléfono de la agenda, y luego imprime los resultados.
- `dameTamanoTabla()`: Esta función devuelve el tamaño de la tabla hash, que es igual al número de líneas en el diccionario.
- `codificacionTelefonos()`: Esta función codifica los números de teléfono. Toma una lista de caracteres de un número de teléfono, una tabla hash que contiene las palabras del diccionario y una lista original de los caracteres del número de teléfono. Devuelve una lista de las posibles codificaciones del número de teléfono.
- `imprimirResultados()`: Esta función imprime los resultados. Toma una lista de las posibles codificaciones de un número de teléfono y el número de teléfono original, y luego imprime cada codificación junto con el número de teléfono.
- `depurar()`: Esta función depura las codificaciones de los números de teléfono. Toma una codificación de un número de teléfono y devuelve la codificación depurada. Si la codificación contiene dos números seguidos, la función devuelve "-1".
El programa utiliza una tabla hash para almacenar las palabras del diccionario y sus codificaciones correspondientes, lo que permite una búsqueda rápida de las palabras cuando se codifican los números de teléfono. También utiliza listas doblemente enlazadas para almacenar los caracteres de los números de teléfono y las posibles codificaciones de los números de teléfono.



# **_DISEÑO DE SOLUCION:_**
En primer lugar se leen los archivos de entrada y se almanecenan en listas, para luego tomar los elemntos del archivo "diccionario" y convertirlos en su equivalente en numeros y asi almanecenarlos. Estos se almacenaran en una tabla de Hash, empleando las funciones pertinentes para cumplir ello, es importante saber que se almacenan en un array de dos elementos `(palabra en numero, palabra en String)`. Luego tomamos el primer elemento de la "agendaTelefinica" y generamos una _lista doblemente enlazada_ con los elementos.
Luego hacemos un bucle tal que cada vez que se cumpla una iteracion, se le elimina el primer numero al numero de telefono, luego con los numeros eliminados se creara una lista doblemente enlzada para asi llamar a la funcion `codificacionTelefonos()` la cual es una funcion basada en **Divide AND Conquer**, esta recibe un numero de telefono y chequea mediante la tabla de Hash si existe otro numero telefonico en dicha tabla que sea exactamente igual que él; de no ser asi, elimina el ultimo elemento del numero de telefono y crea una _lista doblemente enlazada_ con este para asi aplicar una llamada recursiva a estos dos nuevos numeros de telefonos. Al final, tendremos dos listas doblemente enlazadas que debemos unir y verificar su cumplen con las propiedades necesarias expresadas en el laboratorio, de esto se encarga la funcion `depurar()`, para luego devolverle a la funcion `imprimirResultados()` las palabras que se pueden imprimir.