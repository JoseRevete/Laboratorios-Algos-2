## Archivos del Proyecto:

### Reproductor:
La clase tiene tres propiedades: `actual`, que representa la canción actualmente cargada en el reproductor; `player`, que es un objeto de tipo `PausablePlayer` utilizado para reproducir la canción; y `reproduciendo`, que indica si el reproductor está reproduciendo una canción en ese momento.

El constructor de la clase recibe una canción como parámetro y la asigna a la propiedad `actual`, inicializando `reproduciendo` como falso.

La función `cargarCancion` carga una nueva canción en el reproductor. Verifica si la ubicación de la canción es válida y, si es así, intenta abrir el archivo de la canción y crear un `PausablePlayer` para reproducirla.

Las funciones `reproducir`, `parar`, `resumir` y `pausa` controlan la reproducción de la canción actual. `reproducir` inicia la reproducción, `parar` detiene la reproducción, `resumir` reanuda la reproducción después de una pausa, y `pausa` pausa la reproducción.

La función `estaTocandoCancion` devuelve verdadero si el reproductor está reproduciendo una canción en ese momento.

### Nodo:
La clase `Nodo` tiene las siguientes propiedades:

- `c`: Una instancia de `TAD_Cancion`, que representa la canción asociada con este nodo.
- `izq`: Referencia al nodo hijo izquierdo en el árbol.
- `der`: Referencia al nodo hijo derecho en el árbol.
- `padre`: Referencia al nodo padre en el árbol.
- `valor`: Referencia a otro nodo. La utilidad de esta propiedad no está clara y podría ser un error o un uso particular del programador.

El constructor de la clase `Nodo` recibe una canción (`TAD_Cancion`) y la asigna a la propiedad `c`.

La función `toString()` está sobrescrita para devolver una representación en cadena del objeto `Nodo`, que en este caso simplemente llama al método `toString()` de la canción (`c`) asociada al nodo.

### ListaReproduccion:
La clase tiene las siguientes propiedades:

- `nombre`: Un `String` que representa el nombre de la lista de reproducción.
- `canciones`: Un objeto de tipo `Arbol_Canciones`, que se utiliza para almacenar las canciones en forma de un árbol binario de búsqueda.

El constructor de la clase toma un parámetro `nombre` y lo asigna a la propiedad del mismo nombre. Además, inicializa la propiedad `canciones` como un nuevo objeto de tipo `Arbol_Canciones`.

La función `agregarLista(cancion: TAD_Cancion)` agrega una canción a la lista de reproducción utilizando el método `agregar()` del objeto `canciones`.

La función `eliminarCancion(interprete: String, titulo: String): Boolean` intenta eliminar una canción de la lista de reproducción utilizando el método `eliminar()` del objeto `canciones`. Devuelve `true` si la operación es exitosa, `false` en caso contrario.

La función `obtenerLR(): List<TAD_Cancion>` devuelve una lista de las canciones de la lista de reproducción, convirtiendo el árbol en una secuencia utilizando la función `deArbolASecuencia(nodo)`.

La función `mostrarLR()` imprime la lista de reproducción utilizando el método `imprimirArbolCompleto()` del objeto `canciones`.

Las funciones `minInterprete`, `maxInterprete`, `minTitulo` y `maxTitulo` obtienen los valores mínimos y máximos de intérprete y título en la lista de reproducción, respectivamente.

La función `esArbolDeBusqCancion(nodo: Nodo?): Boolean` verifica si el árbol de canciones es un árbol de búsqueda válido.

La función `deArbolASecuencia(nodo: Nodo?): List<TAD_Cancion>` convierte el árbol en una secuencia de canciones, primero recorriendo los nodos izquierdos, luego añadiendo el nodo actual y finalmente recorriendo los nodos derechos.

### Cancion:
La clase tiene las siguientes propiedades:

- `titulo`: Un `String` que representa el título de la canción.
- `interprete`: Un `String` que representa el intérprete de la canción.
- `ubicacion`: Un `String` que representa la ubicación del archivo de la canción.

El constructor de la clase toma tres parámetros (`titulo`, `interprete` y `ubicacion`) y los asigna a las propiedades correspondientes.

Las funciones `obtenerTitulo`, `obtenerInterprete` y `obtenerUbicacion` devuelven el título, el intérprete y la ubicación de la canción, respectivamente.

La función `esUbicacionValida` verifica si la ubicación del archivo de la canción es válida, comprobando si el archivo existe en el sistema de archivos.

La función `toString` devuelve una representación en cadena de la canción, mostrando su título, intérprete y ubicación en un formato específico.

### ArbolDeCanciones:
La clase tiene las siguientes propiedades:

- `raiz`: Un nodo que representa la raíz del árbol.
- `izq`: Una referencia a un nodo hijo izquierdo.
- `der`: Una referencia a un nodo hijo derecho.
- `padre`: Una referencia a un nodo padre.
- `valor`: Una referencia a otro nodo. Esta propiedad parece ser redundante y no se utiliza en el código proporcionado.
- `minInterprete`, `minTitulo`, `maxInterprete` y `maxTitulo`: Variables que almacenan los valores mínimos y máximos de intérprete y título en el árbol. Estas variables tampoco parecen ser utilizadas en el código proporcionado.

La función `agregar(c: TAD_Cancion)` agrega una nueva canción al árbol manteniendo su propiedad de ser un árbol binario de búsqueda.

La función `buscar(c: TAD_Cancion): Nodo?` busca una canción en el árbol y devuelve el nodo que la contiene, o `null` si la canción no se encuentra en el árbol.

La función `eliminar(interprete: String, titulo: String): Boolean` elimina una canción del árbol basándose en su intérprete y título.

Las funciones `imprimirArbolCompleto()` e `imprimirArbol(nodo: Nodo?)` imprimen el contenido del árbol de manera ordenada.

La función `toString()` y `toString(nodo: Nodo?): String` convierten el árbol en una cadena de texto.

### AdministradorDeMusica:
El programa comienza imprimiendo un mensaje de bienvenida y solicitando al usuario que ingrese el número de la opción que desea realizar.

Utiliza un Scanner para leer la entrada del usuario.

Verifica que se haya ingresado un archivo como argumento de línea de comandos y lee las canciones del archivo, separándolas por ;.

Crea una lista de reproducción (TAD_Lista_De_Reproduccion) y agrega las canciones a esta lista.

Inicializa un reproductor de música (TAD_Reproductor) con la primera canción de la lista.

Muestra un menú de opciones que permite al usuario realizar diversas acciones, como cargar una lista de reproducción desde un archivo, mostrar la lista actual, eliminar una canción, reproducir, pausar, detener, reproducir la siguiente canción, volver a reproducir la lista desde el principio, mostrar ayuda y salir del administrador de música.

El programa maneja las opciones ingresadas por el usuario y realiza la acción correspondiente según la opción seleccionada.

El bucle while se ejecuta continuamente hasta que el usuario decide salir del programa.

Cada acción realizada por el usuario se imprime en la consola, junto con mensajes informativos y de error cuando corresponda.

### DISEÑO DE SOLUCION:

Primero convertir el archivo de entrada en lsitas leibles, para asi editarlas a conveniencia. Luego almacenamos las canciones en la lista de reproducion. Ofrecemos al usuario las opciones de reproducciones planteadas. Se realizan comprobaciones como si la cancion existe, la ubicacion es valida, no es vacio, etc. Luego de que el usuario escoga, se realiza la accion correspondiente.

El lista de reproduccion se basa en un arbol binario de busqueda, este a su vez de saba en un TAD_CANCIONES las cuales son administradas por el TAD_Reproductor.
