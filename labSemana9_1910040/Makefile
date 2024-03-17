KC= kotlinc
KFLAGS= -include-runtime
PROG= CodificacionTelefonos.jar
SRC=  Nodo.kt TADPila.kt ListaEnlazadaSimple.kt

all:
	$(KC) $(KFLAGS) $(SRC) -d $(PROG)

.PHONY: clean

clean:
	rm -rf *.jar%