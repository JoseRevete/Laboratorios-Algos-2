#!/usr/bin/bash

kotlin -J-Xmx2g -J-Xss1g -cp ".:$(printf %s: libPlotRuntime/*.jar)" PruebaOrdenamientoKt $*
