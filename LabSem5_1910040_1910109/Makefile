KC= kotlinc
KFLAG = -cp
LIB=    libPlotRuntime
SRC= Ordenamiento.kt PruebaOrdenamiento.kt

all: PruebaOrdenamiento.class

PruebaOrdenamiento.class: PruebaOrdenamiento.kt Ordenamiento.kt
	$(KC) $(KFLAG) $(LIB)/libPlotRuntime.jar $(SRC) 

clean:
	rm -rf *.png *.class META-INF