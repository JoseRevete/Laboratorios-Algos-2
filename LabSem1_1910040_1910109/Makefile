KC= kotlinc
KFLAGS= -include-runtime
PROG= PruebaAlgOrd.jar
SRC= Ordenamiento.kt PruebaOrdenamiento.kt

all:
	$(KC) $(SRC) $(KFLAGS) -d $(PROG)

.PHONY: clean

clean:
	rm -rf $(PROG)
