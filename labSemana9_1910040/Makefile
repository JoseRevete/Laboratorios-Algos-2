KC= kotlinc
KFLAGS= -include-runtime
PROG= CodificacionTelefonos.jar
SRC=  Nodo.kt  ListaDoblementeEnlazada.kt Hash.kt CodificacionTelefonos.kt

all:
	$(KC) $(KFLAGS) $(SRC) -d $(PROG)

.PHONY: clean

clean:
	rm -rf *.jar