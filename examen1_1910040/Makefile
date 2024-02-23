KC= kotlinc
KFLAGS= -include-runtime
PROG= BusquedaMatrizNoRepetido.jar
SRC= BusquedaMatrizNoRepetido.kt

all:
	$(KC) $(KFLAGS) $(SRC) -d $(PROG)

.PHONY: clean

clean:
	rm -rf *.jar