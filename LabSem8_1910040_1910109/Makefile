KC= kotlinc
KFLAGS= -include-runtime
PROG= Lista.jar
SRC= Main.kt ListaDoblementeEnlazadaCircular.kt Nodo.kt Pila.kt Cola.kt

all:
	$(KC) $(KFLAGS) $(SRC) -d $(PROG)

.PHONY: clean

clean:
	rm -rf *.jar