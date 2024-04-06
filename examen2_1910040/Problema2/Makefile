KC= kotlinc
KFLAGS= -include-runtime
PROG= Problema2.jar
SRC= Problema2.kt ListaSimplementeEnlazada.kt Nodo.kt

all:
	$(KC) $(KFLAGS) $(SRC) -d $(PROG)

.PHONY: clean

clean:
	rm -rf *.jar