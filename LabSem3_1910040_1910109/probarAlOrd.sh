#!/usr/bin/bash

java -cp ".:$(printf %s: libPlotRuntime/*.jar)" PruebaOrdenamientoKt $*
