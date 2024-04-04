KC=	kotlinc
KFLAG=	-cp
LIB=	lib

all:	ListaReproduccion.class

ListaReproduccion.class: ListaReproduccion.kt ArbolDeCanciones.kt Reproductor.kt Cancion.kt
	$(KC) $(KFLAG) $(LIB)/jlayer-1.0.1.jar:$(LIB)/pausablePlayer.jar Reproductor.kt Cancion.kt ArbolDeCanciones.kt ListaReproduccion.kt

clean:
	rm -rf *.class META-INF