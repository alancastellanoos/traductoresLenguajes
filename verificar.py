"""Ejecutar después de compilar. No requiere paquetes de terceros."""
from pathlib import Path
import os, subprocess, sys
p = Path(__file__).resolve().parent
fallos = 0
for f in sorted(list(p.glob('prueba*.txt')) + list((p/'pruebas').glob('*.txt'))):
    esperado = 1 if any(x in f.name for x in ['invalido','incompleta','prueba3','prueba6','prueba8','prueba10','prueba11']) else 0
    r = subprocess.run(['java','-cp','.'+os.pathsep+'java-cup-11b-runtime.jar','Main',str(f.relative_to(p))], cwd=p, encoding='utf-8', stdout=subprocess.PIPE, stderr=subprocess.STDOUT, timeout=15)
    ok = r.returncode == esperado
    if f.name == 'prueba7_control_valido.txt':
        ok = ok and all('Regla reconocida: '+x in r.stdout for x in ['seleccion_simple','seleccion_doble','seleccion_multiple','ciclo_mientras','ciclo_hacer_mientras','ciclo_para','condicion','expresion_logica'])
    if f.name == 'prueba9_anidamiento.txt':
        ok = ok and 'Regla reconocida: estructura_anidada' in r.stdout and 'Jerarquía del método' in r.stdout
    if f.name == 'prueba10_recuperacion.txt':
        ok = ok and all(x in r.stdout for x in ['Recuperación:', 'CONTINUACION_RECUPERADA','Regla reconocida: ciclo_mientras','análisis completo'])
        ok = ok and 'Regla reconocida: seleccion_simple' not in r.stdout
    if f.name == 'prueba11_combinado.txt':
        ok = ok and 'CONTINUACION_COMBINADA' in r.stdout and 'análisis completo' in r.stdout
    fallos += not ok
    print(('OK ' if ok else 'FALLO ') + str(f.relative_to(p)))
print('Fallos:', fallos)
sys.exit(1 if fallos else 0)
