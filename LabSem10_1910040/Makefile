KC= kotlinc
KFLAGS= -include-runtime
PROG= PruebaCodigoMorseAD.jar
SRC=  CodigoMorseAD.kt PruebaCodigoMorseAD.kt

all:
	$(KC) $(KFLAGS) $(SRC) -d $(PROG)

.PHONY: clean

clean:
	rm -rf *.jar