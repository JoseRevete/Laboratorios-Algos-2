KC=	kotlinc
KFLAG=	-cp
LIB=	lib

all:	AdministradoDeMusica.class

AdministradoDeMusica.class: AdministradoDeMusica.kt ListaReproduccion.kt ArbolDeCanciones.kt Reproductor.kt Cancion.kt
	$(KC) $(KFLAG) $(LIB)/jlayer-1.0.1.jar:$(LIB)/pausablePlayer.jar Reproductor.kt Cancion.kt ArbolDeCanciones.kt ListaReproduccion.kt AdministradoDeMusica.kt

clean:
	rm -rf *.class META-INF