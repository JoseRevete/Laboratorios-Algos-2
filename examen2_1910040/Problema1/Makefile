KC= kotlinc
KFLAGS= -include-runtime
PROG= Problema1.jar
SRC= TablaDeHash.kt Problema1.kt

all:
	$(KC) $(KFLAGS) $(SRC) -d $(PROG)

.PHONY: clean

clean:
	rm -rf *.jar