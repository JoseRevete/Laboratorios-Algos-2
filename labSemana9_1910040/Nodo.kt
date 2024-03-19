// Se crea una clase Nodo que se utiliza en las clases ListaDoblementeEnlazada y TablaHash
class Nodo<T>(var dato: T){
    // Se inicializan las variables siguiente y anterior
    var siguiente: Nodo<T>? = null
    var anterior: Nodo<T>? = null

    // Se sobreescribe el método toString
    override fun toString(): String {
        return if (dato is Array<*>) {
            "Nodo(dato=${(dato as Array<*>).toList()})"
        } else {
            "Nodo(dato=$dato)"
        }
    }
}