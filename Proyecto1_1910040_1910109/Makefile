KC= kotlinc
KFLAGS= -include-runtime
PROG= DivideAndConquerPRMBSolver.jar
SRC= DivideAndConquerPRMBSolver.kt

all:
	$(KC) $(KFLAGS) $(SRC) -d $(PROG)

.PHONY: clean

clean:
	rm -rf *.jar