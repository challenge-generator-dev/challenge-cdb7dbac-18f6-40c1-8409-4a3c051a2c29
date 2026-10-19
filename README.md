# Resiliencia del servicio de pagos ante fallas de proveedores

El sistema de pagos de la institución financiera debe procesar transacciones de manera confiable incluso cuando los proveedores externos (como el banco central o el gateway de pagos) fallan. El sistema procesa un volumen de 10 000 transacciones por hora con una tasa de error aceptable del 0.1%. El objetivo es asegurar que el servicio de pagos siga operando con un tiempo de respuesta máximo de 2 segundos ante fallas de los proveedores.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Resiliencia del servicio de pagos ante fallas de proveedores 202610191521 |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 4 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Identificación de fallas

**Objetivo:** Identificar los puntos de falla en la integración con los proveedores externos.

**Tiempo estimado:** 1 hora

**Instrucciones:**

- Analiza el flujo actual de transacciones y determina dónde pueden ocurrir fallas.
- Documenta cada punto de falla identificando su impacto en el sistema de pagos.

**Entregable:** Documento que detalla los puntos de falla y su impacto.

<details>
<summary>Pistas de conocimiento</summary>

- Considera los diferentes tipos de fallas que pueden ocurrir (timeout, errores de red, etc.).
- Evalúa el impacto de cada falla en la operación del sistema.

</details>

### Fase 2: Implementación de tolerancia a fallos

**Objetivo:** Implementar al menos dos métodos de tolerancia a fallos para manejar las fallas identificadas.

**Tiempo estimado:** 1 hora

**Instrucciones:**

- Elige al menos dos métodos de tolerancia a fallos (como reintentos, corte de circuito, alternativa, límite de tasa y aislamiento) y aplica cada uno en los puntos de falla identificados.
- Verifica que el sistema siga operando con un tiempo de respuesta máximo de 2 segundos ante fallas de los proveedores.

**Entregable:** Implementación de los métodos de tolerancia a fallos con pruebas que demuestren su efectividad.

<details>
<summary>Pistas de conocimiento</summary>

- Considera los pros y contras de cada método de tolerancia a fallos.
- Evalúa el impacto de cada método en la operación del sistema.

</details>

### Fase 3: Validación de la implementación

**Objetivo:** Validar que la implementación de los métodos de tolerancia a fallos cumpla con los requisitos.

**Tiempo estimado:** 1 hora

**Instrucciones:**

- Inyecta fallas en los proveedores externos y verifica que el sistema siga operando con un tiempo de respuesta máximo de 2 segundos.
- Documenta los resultados de las pruebas y cualquier ajuste necesario.

**Entregable:** Reporte de pruebas que detalla los resultados y cualquier ajuste necesario.

<details>
<summary>Pistas de conocimiento</summary>

- Considera diferentes escenarios de falla para probar la resiliencia del sistema.
- Evalúa si los métodos de tolerancia a fallos cumplen con los requisitos del sistema.

</details>

### Fase 4: Documentación y revisión

**Objetivo:** Documentar la implementación y revisar los resultados con el equipo.

**Tiempo estimado:** 1 hora

**Instrucciones:**

- Documenta la implementación de los métodos de tolerancia a fallos y los resultados de las pruebas.
- Revisa los resultados con el equipo y discute cualquier mejora o ajuste necesario.

**Entregable:** Documentación completa de la implementación y revisión de los resultados con el equipo.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la claridad y la precisión de la documentación.
- Evalúa la efectividad de la comunicación con el equipo.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué son los métodos de tolerancia a fallos y por qué son importantes en un sistema de pagos?
- **paraQueSirve**: ¿Para qué sirve cada método de tolerancia a fallos implementado en el sistema de pagos?
- **comoSeUsa**: ¿Cómo se usa cada método de tolerancia a fallos en el sistema de pagos?
- **erroresComunes**: ¿Cuáles son los errores comunes que pueden ocurrir al implementar métodos de tolerancia a fallos en un sistema de pagos?
- **queDecisionesImplica**: ¿Qué decisiones implica la elección de cada método de tolerancia a fallos en el sistema de pagos?

## Criterios de Evaluacion

- Identificación correcta de los puntos de falla en la integración con los proveedores externos.
- Implementación efectiva de al menos dos métodos de tolerancia a fallos.
- Verificación de que el sistema sigue operando con un tiempo de respuesta máximo de 2 segundos ante fallas de los proveedores.
- Documentación clara y precisa de la implementación y los resultados de las pruebas.
- Revisión efectiva de los resultados con el equipo y discusión de cualquier mejora o ajuste necesario.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
