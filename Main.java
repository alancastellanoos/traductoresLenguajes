import java.io.*;
import java.nio.charset.StandardCharsets;

/** Actividad 7. Analiza sintaxis; no ejecuta el programa C++. */
public class Main {
    private static void imprimirEncabezado() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("TRADUCTORES DE LENGUAJE - PRÁCTICA: ANALIZADOR LÉXICO Y SINTÁCTICO");
        System.out.println("INTEGRANTES:");
        System.out.println("1. Cancelada de la O Gerardo Alexander");
        System.out.println("2. Castellanos Hernández Alan");
        System.out.println("3. Lara Jiménez Pablo César");
        System.out.println("4. Medina Robledo Brandon Ernie");
        System.out.println("--------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        // La terminal también debe usar UTF-8: en PowerShell, chcp 65001.
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
        imprimirEncabezado();
        if (args.length == 0) {
            System.out.println("Uso: java -cp <classpath> Main archivo.txt [--tokens]");
            System.exit(2);
        }
        int codigo = 2;
        try (Reader entrada = new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8)) {
            Lexer lexer = new Lexer(entrada);
            lexer.mostrarTokens = args.length > 1 && args[1].equals("--tokens");
            Parser parser = new Parser(lexer);
            System.out.println("ACTIVIDAD 7 - ESTRUCTURAS DE CONTROL C++\nArchivo: " + args[0]);
            boolean completo = true;
            try { parser.parse(); }
            catch (Exception e) { completo = false; }
            boolean valido = completo && parser.numErrores == 0 && lexer.numErrores == 0;
            System.out.println("RESULTADO: " + (valido ? "VÁLIDO" : "INVÁLIDO")
                + " | errores sintácticos: " + parser.numErrores
                + " | errores léxicos: " + lexer.numErrores
                + " | análisis " + (completo ? "completo" : "detenido"));
            codigo = valido ? 0 : 1;
        } catch (IOException e) { System.err.println("No se pudo leer el archivo: " + e.getMessage()); }
        System.exit(codigo);
    }
}
