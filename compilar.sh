#!/bin/sh
set -eu
cd "$(dirname "$0")"
javac -encoding UTF-8 -cp '.:java-cup-11b-runtime.jar' Lexer.java sym.java Parser.java Main.java
