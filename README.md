# Actividad 7: Análisis sintáctico de estructuras de control

Traductores de Lenguaje · C++ · JavaCUP + JFlex
Docente: José Navarro Ríos (según el README anterior).
Equipo 2. Grupo: pendiente de completar.

## Integrantes

1. Cancelada de la O, Gerardo Alexander.
2. Castellanos Hernández, Alan.
3. Lara Jiménez, Pablo César.
4. Medina Robledo, Brandon Ernie.

## Qué se agregó

- `if`, `if/else`, cadenas `else if`, `switch/case/default/break`.
- `while`, `do...while`, `for`, con anidamiento mediante bloques recursivos.
- Relaciones `> < >= <= == !=`; lógica `&& || !`; aritmética `+ - * / %`.
- Precedencia por niveles de expresión y paréntesis agrupadores.
- `cin >> a`, `cout << "texto" << a`; `const`, `string`, `bool`.
- Incremento/decremento, asignación y asignación compuesta; declaraciones locales.
- Funciones sin parámetros con retorno `void` o tipo; `return`.
- Mensajes de reglas, diagnóstico con línea/columna y árbol textual de anidamiento.
- Recuperación con producciones `error` de CUP y sincronización en `;`, `)` y `}`.
- Resultado global VÁLIDO/INVÁLIDO y códigos de salida 0/1; 2 indica un problema de uso/archivo.

Se conservan las entradas de prácticas anteriores. `package`, `final`, `String`,
`boolean`, `leer` y `escribir` son extensiones heredadas, no sintaxis nativa de C++.
El respaldo original está en `../respaldo_actividad_anterior.zip`.
La gramática de expresiones anterior se reorganizó por precedencia para integrar
condiciones que combinan aritmética, comparación y lógica sin ambigüedades.

## Compilar en Windows

Instala un JDK (se verificó con Java 17), abre una terminal dentro de `proyecto`
y ejecuta:

```bat
chcp 65001
javac -encoding UTF-8 -cp ".;java-cup-11b-runtime.jar" Lexer.java sym.java Parser.java Main.java
```

También puedes ejecutar `compilar.bat`. Si `javac` no se reconoce, necesitas el
JDK y su carpeta `bin` en PATH. No basta con una instalación que solo ejecute Java.
Los archivos Java generados ya están incluidos; no hay que regenerarlos para probar.

## Pruebas de la actividad 7

```bat
java -cp ".;java-cup-11b-runtime.jar" Main prueba7_control_valido.txt
java -cp ".;java-cup-11b-runtime.jar" Main prueba8_control_invalido.txt
java -cp ".;java-cup-11b-runtime.jar" Main prueba9_anidamiento.txt
java -cp ".;java-cup-11b-runtime.jar" Main prueba10_recuperacion.txt
java -cp ".;java-cup-11b-runtime.jar" Main prueba11_combinado.txt
```

| Archivo | Resultado esperado | Qué demuestra |
|---|---|---|
| prueba7 | VÁLIDO | Todas las estructuras y condiciones |
| prueba8 | INVÁLIDO, análisis completo | Cinco errores y continuación |
| prueba9 | VÁLIDO | Jerarquía for / if / while y más anidamientos |
| prueba10 | INVÁLIDO, análisis completo | Recuperación y `CONTINUACION_RECUPERADA` |
| prueba11 | INVÁLIDO, análisis completo | Funciones anteriores + nuevas + error |

**Que una prueba con errores diga INVÁLIDO es lo correcto.** Recuperarse no convierte
el archivo en válido. Un bloque o método que contiene `[ERROR]` se muestra en el
árbol para ubicarlo, pero no recibe el mensaje positivo de regla reconocida.
`demo.bat` ejecuta estas cinco pruebas con pausas. Añade `--tokens` al final de un
comando para ver el análisis léxico, por ejemplo:

```bat
java -cp ".;java-cup-11b-runtime.jar" Main prueba7_control_valido.txt --tokens
```

Las pruebas anteriores siguen usando los mismos comandos que compartió Alan.
En Linux/macOS cambia `;` por `:` dentro del classpath.

## Regenerar después de editar la gramática o el lexer

Edita `Parser.cup` y `Lexer.jflex`, no las tablas generadas de `Parser.java`.

```bat
java -jar java-cup-11b.jar -parser Parser -symbols sym Parser.cup
java -jar jflex-full.jar Lexer.jflex
javac -encoding UTF-8 -cp ".;java-cup-11b-runtime.jar" Lexer.java sym.java Parser.java Main.java
```

O ejecuta `regenerar.bat`. CUP genera con **0 conflictos**; las advertencias de
terminales sin uso corresponden a tokens heredados reservados para otras etapas.

## Verificación y evidencias

Se ejecutaron 45 archivos: 20 válidos y 25 inválidos/mixtos. Los 45 tuvieron la
clasificación esperada. Además se comprobó la presencia de todas las reglas de
control, el anidamiento y la continuación posterior al error. Hay 33 pruebas
específicas en `pruebas/` y 12 entradas en la raíz (incluidas las anteriores).
`evidencias/` contiene salidas reales de ejecución, resumen JSON, tabla Markdown y
la salida del generador CUP. `python verificar.py` repite la suite tras compilar;
Python solo es necesario para esa verificación automática, no para el analizador.

## Alcance y límites

Es un analizador léxico/sintáctico educativo, no ejecuta los ciclos ni genera
código máquina. Los nombres de variables de algunos ejemplos se asumen existentes
porque no se comprueba la tabla de símbolos. No verifica tipos, que los `case`
sean constantes, duplicados de `case/default` ni el contexto semántico de `break`.
Los cuerpos de control requieren llaves. Las funciones son sin parámetros;
no se implementan llamadas generales, plantillas, punteros, arreglos, `std::`,
`for` por rango ni todo el estándar C++. Para `cin/cout`, usa `using namespace std;`.
La recuperación no garantiza continuar tras cualquier error: un archivo truncado
con llaves faltantes puede terminar como “análisis detenido”. Los diagnósticos
señalan el token donde se detecta el problema, no siempre donde comenzó.

## Entrega

El ZIP contiene el reporte PDF, fuente Markdown del reporte, guion del video,
proyecto y respaldo. Completa el grupo de portada y revisa los datos institucionales.
Graba el video con audio, máximo 5 minutos y mínimo 720p, con fecha y hora visibles;
publica el enlace público. Sube el proyecto al repositorio del equipo y entrega
su enlace. Esas publicaciones no se han realizado desde este entorno.

## Encabezado y evidencias actualizados

Main imprime la asignatura y los cuatro integrantes antes de cada análisis.
La salida de Java es UTF-8; en PowerShell ejecuta `chcp 65001` antes de las pruebas.
Para ejecutar el script en PowerShell escribe `.\demo.bat`, desde `proyecto`.
Las capturas aportadas por Pablo se conservan sin alterar en el reporte como
evidencias previas al ajuste del encabezado y de codificación.
