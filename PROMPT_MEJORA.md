# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/pragma/payments/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/payments/domain/port/PaymentProviderPort.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/service/PaymentService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/service/PaymentService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentProviderAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentProviderAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentProviderAdapter.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/FallbackPaymentProviderAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/FallbackPaymentProviderAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/FallbackPaymentProviderAdapter.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/payments/application/service/PaymentServiceTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/payments/infrastructure/adapter/PaymentProviderAdapterTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/service/PaymentService.java` — `Payment.transactionId`: Se invoca `transactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/service/PaymentService.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/service/PaymentService.java` — `Payment.status`: Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/service/PaymentService.java` — `Payment.providerTransactionId`: Se invoca `providerTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentProviderAdapter.java` — `Payment.transactionId`: Se invoca `transactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentProviderAdapter.java` — `Payment.providerTransactionId`: Se invoca `providerTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/FallbackPaymentProviderAdapter.java` — `Payment.transactionId`: Se invoca `transactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/FallbackPaymentProviderAdapter.java` — `Payment.providerTransactionId`: Se invoca `providerTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `RefundRequest.amount`: Se invoca `amount` sobre `RefundRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `RefundRequest.currency`: Se invoca `currency` sobre `RefundRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `RefundRequest.description`: Se invoca `description` sobre `RefundRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `RefundRequest.customerId`: Se invoca `customerId` sobre `RefundRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `RefundRequest.metadata`: Se invoca `metadata` sobre `RefundRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `PaymentService.getPayment`: Se invoca `getPayment` sobre `PaymentService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `PaymentService.refundPayment`: Se invoca `refundPayment` sobre `PaymentService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `RefundRequest.reason`: Se invoca `reason` sobre `RefundRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Payment.id`: Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Payment.currency`: Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Payment.description`: Se invoca `description` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Payment.status`: Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Payment.customerId`: Se invoca `customerId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Payment.providerTransactionId`: Se invoca `providerTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Payment.providerName`: Se invoca `providerName` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Payment.createdAt`: Se invoca `createdAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Payment.updatedAt`: Se invoca `updatedAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentProviderException.getMessage`: Se invoca `getMessage` sobre `PaymentProviderException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentProviderException.getBindingResult`: Se invoca `getBindingResult` sobre `PaymentProviderException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentProviderException.getErrorType`: Se invoca `getErrorType` sobre `PaymentProviderException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/PaymentProviderAdapterTest.java` — `ExternalPaymentGateway.processPayment`: Se invoca `processPayment` sobre `ExternalPaymentGateway`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/PaymentProviderAdapterTest.java` — `Payment.status`: Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/PaymentProviderAdapterTest.java` — `ExternalPaymentGateway.healthCheck`: Se invoca `healthCheck` sobre `ExternalPaymentGateway`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/PaymentProviderAdapterTest.java` — `ExternalPaymentGateway.rollbackPayment`: Se invoca `rollbackPayment` sobre `ExternalPaymentGateway`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollo de software, Tecnología Java, Senior L2

### Brecha de conocimiento
Construye Sistemas Resilientes: con Enfoque en M�ltiples Patrones. Usa adecuadamente al menos dos m�todos de tolerancia a fallos a trav�s de c�digo, como CircuitBreaker, Retry, FallBack, RateLimit y Bulkhead.

### Reto
- Tema: Resiliencia del servicio de pagos ante fallas de proveedores 202610191521
- Seniority: senior-l2
- Tipo: practical
- Título: Resiliencia del servicio de pagos ante fallas de proveedores
- Tiempo estimado: 4 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Identificación de fallas — objetivo: Identificar los puntos de falla en la integración con los proveedores externos. — entregable (NO resolver): Documento que detalla los puntos de falla y su impacto.
- Fase 2: Implementación de tolerancia a fallos — objetivo: Implementar al menos dos métodos de tolerancia a fallos para manejar las fallas identificadas. — entregable (NO resolver): Implementación de los métodos de tolerancia a fallos con pruebas que demuestren su efectividad.
- Fase 3: Validación de la implementación — objetivo: Validar que la implementación de los métodos de tolerancia a fallos cumpla con los requisitos. — entregable (NO resolver): Reporte de pruebas que detalla los resultados y cualquier ajuste necesario.
- Fase 4: Documentación y revisión — objetivo: Documentar la implementación y revisar los resultados con el equipo. — entregable (NO resolver): Documentación completa de la implementación y revisión de los resultados con el equipo.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>payments</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>payments</name>
    <description>Servicio de pagos con tolerancia a fallos en proveedores externos</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-reactor</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>central</id>
            <url>https://repo.maven.apache.org/maven2</url>
        </repository>
    </repositories>
</project>

// === ARCHIVO: src/main/java/com/pragma/payments/Application.java ===
package com.pragma.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import io.micrometer.core.instrument.MeterRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Health;
import com.pragma.payments.application.service.PaymentService;
import com.pragma.payments.infrastructure.adapter.PaymentProviderAdapter;
import com.pragma.payments.infrastructure.adapter.FallbackPaymentProviderAdapter;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@EnableAsync
public class Application {
    private final PaymentService paymentService;
    private final PaymentProviderAdapter paymentProviderAdapter;
    private final FallbackPaymentProviderAdapter fallbackPaymentProviderAdapter;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;
    private final MeterRegistry meterRegistry;

    public Application(PaymentService paymentService,
                      PaymentProviderAdapter paymentProviderAdapter,
                      FallbackPaymentProviderAdapter fallbackPaymentProviderAdapter,
                      CircuitBreakerRegistry circuitBreakerRegistry,
                      RetryRegistry retryRegistry,
                      MeterRegistry meterRegistry) {
        this.paymentService = paymentService;
        this.paymentProviderAdapter = paymentProviderAdapter;
        this.fallbackPaymentProviderAdapter = fallbackPaymentProviderAdapter;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.retryRegistry = retryRegistry;
        this.meterRegistry = meterRegistry;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public HealthIndicator paymentProviderHealthIndicator() {
        return () -> {
            CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("paymentProvider");
            Retry retry = retryRegistry.retry("paymentProvider");

            boolean isCircuitBreakerHealthy = circuitBreaker.getState() != CircuitBreaker.State.FORCED_OPEN &&
                                             circuitBreaker.getState() != CircuitBreaker.State.DISABLED;
            boolean isRetryHealthy = retry.getMetrics().getNumberOfSuccessfulCallsWithoutRetryAttempt() > 0 ||
                                    retry.getMetrics().getNumberOfSuccessfulCallsWithRetryAttempt() > 0;

            if (isCircuitBreakerHealthy && isRetryHealthy) {
                return Health.up()
                    .withDetail("circuitBreaker", circuitBreaker.getState())
                    .withDetail("retrySuccessCount", retry.getMetrics().getNumberOfSuccessfulCallsWithRetryAttempt())
                    .withDetail("retryFailureCount", retry.getMetrics().getNumberOfFailedCallsWithRetryAttempt())
                    .build();
            } else {
                return Health.down()
                    .withDetail("circuitBreaker", circuitBreaker.getState())
                    .withDetail("retrySuccessCount", retry.getMetrics().getNumberOfSuccessfulCallsWithRetryAttempt())
                    .withDetail("retryFailureCount", retry.getMetrics().getNumberOfFailedCallsWithRetryAttempt())
                    .build();
            }
        };
    }

    @Bean
    public AtomicInteger paymentRetryCounter() {
        return meterRegistry.gauge("payment.retry.attempts", new AtomicInteger(0));
    }

    @Bean
    public AtomicInteger paymentCircuitBreakerCounter() {
        return meterRegistry.gauge("payment.circuitbreaker.state.changes", new AtomicInteger(0));
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: payments-service
  datasource:
    url: jdbc:postgresql://localhost:5432/payments_db
    username: payments_user
    password: payments_password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

server:
  port: 8080

management:
  endpoints:
    web:
      exposure:
        include: health,metrics,info,circuitbreakers,retryevents
  endpoint:
    health:
      show-details: always
  health:
    circuitbreakers:
      enabled: true
    retry:
      enabled: true

resilience4j:
  circuitbreaker:
    configs:
      default:
        registerHealthIndicator: true
        slidingWindowType: COUNT_BASED
        slidingWindowSize: 10
        failureRateThreshold: 50
        waitDurationInOpenState: 5s
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        recordExceptions:
          - org.springframework.web.client.HttpServerErrorException
          - java.io.IOException
          - java.util.concurrent.TimeoutException
          - com.pragma.payments.infrastructure.exception.PaymentProviderException
    instances:
      paymentProvider:
        baseConfig: default
  retry:
    configs:
      default:
        maxAttempts: 3
        waitDuration: 1s
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
        retryExceptions:
          - org.springframework.web.client.HttpServerErrorException
          - java.io.IOException
          - java.util.concurrent.TimeoutException
          - com.pragma.payments.infrastructure.exception.PaymentProviderException
    instances:
      paymentProvider:
        baseConfig: default
  bulkhead:
    configs:
      default:
        maxConcurrentCalls: 20
        maxWaitDuration: 10ms
    instances:
      paymentProvider:
        baseConfig: default
  thread-pool-bulkhead:
    configs:
      default:
        maxThreadPoolSize: 4
        coreThreadPoolSize: 2
        queueCapacity: 10
    instances:
      paymentProvider:
        baseConfig: default

// === ARCHIVO: src/main/java/com/pragma/payments/domain/model/Payment.java ===
package com.pragma.payments.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Payment(
    UUID paymentId,
    String accountId,
    BigDecimal amount,
    String currency,
    PaymentStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String reference,
    String providerTransactionId,
    String providerName) {

    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED,
        ROLLBACK_INITIATED,
        ROLLED_BACK
    }

    public Payment {
        if (paymentId == null) {
            paymentId = UUID.randomUUID();
        }
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }
        if (status == null) {
            status = PaymentStatus.PENDING;
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
    }

    public Payment withStatus(PaymentStatus newStatus) {
        return new Payment(
            this.paymentId,
            this.accountId,
            this.amount,
            this.currency,
            newStatus,
            this.createdAt,
            LocalDateTime.now(),
            this.reference,
            this.providerTransactionId,
            this.providerName
        );
    }

    public Payment withProviderDetails(String providerTransactionId, String providerName) {
        return new Payment(
            this.paymentId,
            this.accountId,
            this.amount,
            this.currency,
            this.status,
            this.createdAt,
            LocalDateTime.now(),
            this.reference,
            providerTransactionId,
            providerName
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/port/PaymentProviderPort.java ===
package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Payment;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface PaymentProviderPort {
    Mono<Payment> processPayment(Payment payment, Map<String, String> providerMetadata);

    Mono<Payment> rollbackPayment(Payment payment, Map<String, String> providerMetadata);

    Mono<Boolean> healthCheck();

    String getProviderName();
}

// === ARCHIVO: src/main/java/com/pragma/payments/application/service/PaymentService.java ===
package com.pragma.payments.application.service;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.model.Payment.PaymentStatus;
import com.pragma.payments.infrastructure.adapter.FallbackPaymentProviderAdapter;
import com.pragma.payments.infrastructure.adapter.PaymentProviderAdapter;
import com.pragma.payments.infrastructure.exception.PaymentProviderException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private static final String PAYMENT_CIRCUIT_BREAKER_NAME = "paymentProvider";
    private static final String PAYMENT_RETRY_NAME = "paymentRetry";
    private static final Duration MAX_RESPONSE_TIME = Duration.ofSeconds(2);

    private final PaymentProviderAdapter primaryPaymentAdapter;
    private final FallbackPaymentProviderAdapter fallbackPaymentAdapter;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;
    private final MeterRegistry meterRegistry;
    private final AtomicInteger retryCounter;
    private final AtomicInteger circuitBreakerCounter;

    public PaymentService(PaymentProviderAdapter primaryPaymentAdapter,
                          FallbackPaymentProviderAdapter fallbackPaymentAdapter,
                          CircuitBreakerRegistry circuitBreakerRegistry,
                          RetryRegistry retryRegistry,
                          MeterRegistry meterRegistry,
                          AtomicInteger retryCounter,
                          AtomicInteger circuitBreakerCounter) {
        this.primaryPaymentAdapter = primaryPaymentAdapter;
        this.fallbackPaymentAdapter = fallbackPaymentAdapter;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.retryRegistry = retryRegistry;
        this.meterRegistry = meterRegistry;
        this.retryCounter = retryCounter;
        this.circuitBreakerCounter = circuitBreakerCounter;
    }

    public Mono<Payment> processPayment(Payment payment, Map<String, String> metadata) {
        log.info("Iniciando procesamiento de pago para transactionId: {}", payment.transactionId());
        validatePayment(payment);

        Timer.Sample sample = Timer.start(meterRegistry);
        
        return primaryPaymentAdapter.processPayment(payment, metadata)
                .doOnSuccess(result -> {
                    log.info("Pago procesado exitosamente: {}", result.transactionId());
                    recordSuccessMetrics(sample);
                })
                .onErrorResume(error -> {
                    log.error("Error en proveedor primario, intentando fallback: {}", error.getMessage());
                    return handleFallback(payment, metadata, error);
                });
    }

    private Mono<Payment> handleFallback(Payment payment, Map<String, String> metadata, Throwable error) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(PAYMENT_CIRCUIT_BREAKER_NAME);
        
        if (circuitBreaker.getState() == CircuitBreaker.State.OPEN) {
            log.warn("Circuit breaker ABIERTO para proveedor primario, usando fallback directo");
            circuitBreakerCounter.incrementAndGet();
        }

        return fallbackPaymentAdapter.processPayment(payment, metadata)
                .doOnSuccess(result -> {
                    log.info("Pago procesado via fallback: {}", result.transactionId());
                })
                .doOnError(err -> {
                    log.error("Fallback también falló: {}", err.getMessage());
                    throw new PaymentProviderException("Todos los proveedores de pago fallaron", err);
                });
    }

    public Mono<Payment> rollbackPayment(Payment payment, Map<String, String> metadata) {
        log.info("Iniciando rollback para transactionId: {}", payment.transactionId());
        validatePaymentForRollback(payment);

        return primaryPaymentAdapter.rollbackPayment(payment, metadata)
                .switchIfEmpty(Mono.defer(() -> fallbackPaymentAdapter.rollbackPayment(payment, metadata)))
                .doOnSuccess(result -> log.info("Rollback exitoso para: {}", payment.transactionId()))
                .doOnError(error -> log.error("Rollback fallido: {}", error.getMessage()));
    }

    public Mono<Boolean> checkProviderHealth() {
        return primaryPaymentAdapter.healthCheck()
                .switchIfEmpty(Mono.just(false))
                .onErrorResume(error -> {
                    log.warn("Proveedor primario no disponible, verificando fallback: {}", error.getMessage());
                    return fallbackPaymentAdapter.healthCheck();
                });
    }

    private void validatePayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("El pago no puede ser null");
        }
        if (payment.transactionId() == null || payment.transactionId().isBlank()) {
            throw new IllegalArgumentException("El transactionId es requerido");
        }
        if (payment.amount() == null || payment.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        if (payment.status() != PaymentStatus.PENDING) {
            throw new IllegalArgumentException("Solo se pueden procesar pagos en estado PENDING");
        }
    }

    private void validatePaymentForRollback(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("El pago no puede ser null para rollback");
        }
        if (payment.providerTransactionId() == null || payment.providerTransactionId().isBlank()) {
            throw new IllegalArgumentException("No se puede hacer rollback sin providerTransactionId");
        }
        if (payment.status() != PaymentStatus.COMPLETED && payment.status() != PaymentStatus.FAILED) {
            throw new IllegalArgumentException("Solo se puede hacer rollback de pagos COMPLETED o FAILED");
        }
    }

    private void recordSuccessMetrics(Timer.Sample sample) {
        Timer.stop(meterRegistry.timer("payment.processing.time"), sample);
        meterRegistry.counter("payment.processed.success").increment();
    }

    private void recordFailureMetrics(Timer.Sample sample, Throwable error) {
        Timer.stop(meterRegistry.timer("payment.processing.time"), sample);
        meterRegistry.counter("payment.processed.failure").increment();
        log.error("Métricas de fallo registradas para error: {}", error.getMessage());
    }

    public Payment createPayment(BigDecimal amount, String currency, String description) {
        Payment payment = new Payment(
                UUID.randomUUID().toString(),
                amount,
                currency,
                PaymentStatus.PENDING,
                description,
                null,
                null,
                null
        );
        log.info("Pago creado: id={}, monto={} {}", payment.transactionId(), amount, currency);
        return payment;
    }

    public Mono<Payment> processPaymentWithRetry(Payment payment, Map<String, String> metadata) {
        Retry retry = retryRegistry.retry(PAYMENT_RETRY_NAME);
        
        return Mono.defer(() -> processPayment(payment, metadata))
                .retryWhen(retry)
                .doOnRetry(retryContext -> {
                    log.warn("Reintento {} ejecutado", retryContext.getRetryCount());
                    retryCounter.incrementAndGet();
                    meterRegistry.counter("payment.retry").increment();
                });
    }

    public CircuitBreaker getCircuitBreaker() {
        return circuitBreakerRegistry.circuitBreaker(PAYMENT_CIRCUIT_BREAKER_NAME);
    }

    public Retry getRetry() {
        return retryRegistry.retry(PAYMENT_RETRY_NAME);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/PaymentProviderAdapter.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.model.Payment.PaymentStatus;
import com.pragma.payments.domain.port.PaymentProviderPort;
import com.pragma.payments.infrastructure.exception.PaymentProviderException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PaymentProviderAdapter implements PaymentProviderPort {

    private static final Logger log = LoggerFactory.getLogger(PaymentProviderAdapter.class);
    private static final String PROVIDER_NAME = "PrimaryPaymentProvider";
    private static final double FAILURE_RATE_THRESHOLD = 50.0;
    private static final int WAIT_DURATION_IN_OPEN_STATE = 30;
    private static final int SLIDING_WINDOW_SIZE = 10;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final Duration RETRY_WAIT_DURATION = Duration.ofMillis(500);

    private final CircuitBreaker circuitBreaker;
    private final Retry retry;

    public PaymentProviderAdapter(CircuitBreakerRegistry circuitBreakerRegistry, RetryRegistry retryRegistry) {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(FAILURE_RATE_THRESHOLD)
                .waitDurationInOpenState(Duration.ofSeconds(WAIT_DURATION_IN_OPEN_STATE))
                .slidingWindowSize(SLIDING_WINDOW_SIZE)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
        
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker(PROVIDER_NAME, config);
        
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(MAX_RETRY_ATTEMPTS)
                .waitDuration(RETRY_WAIT_DURATION)
                .retryExceptions(PaymentProviderException.class, RuntimeException.class)
                .ignoreExceptions(IllegalArgumentException.class)
                .build();
        
        this.retry = retryRegistry.retry(PROVIDER_NAME + "-retry", retryConfig);
        
        log.info("PaymentProviderAdapter inicializado con CircuitBreaker: {}", PROVIDER_NAME);
    }

    @Override
    public Mono<Payment> processPayment(Payment payment, Map<String, String> providerMetadata) {
        log.info("Procesando pago con proveedor primario: {}", payment.transactionId());
        
        return Mono.fromCallable(() -> simulateProviderCall(payment, providerMetadata))
                .subscribeOn(Schedulers.boundedElastic())
                .transformDeferred(mono -> circuitBreaker.executeMono(mono))
                .transformDeferred(mono -> retry.executeMono(mono))
                .doOnSuccess(result -> log.info("Pago procesado exitosamente por proveedor: {}", result.providerTransactionId()))
                .doOnError(error -> {
                    if (error instanceof CallNotPermittedException) {
                        log.error("Circuit breaker abierto, no se permiten llamadas");
                    } else {
                        log.error("Error procesando pago: {}", error.getMessage());
                    }
                })
                .onErrorResume(error -> {
                    if (error instanceof CallNotPermittedException) {
                        return Mono.error(new PaymentProviderException("Circuit breaker abierto", error));
                    }
                    return Mono.error(error);
                });
    }

    @Override
    public Mono<Payment> rollbackPayment(Payment payment, Map<String, String> providerMetadata) {
        log.info("Ejecutando rollback para transactionId: {}", payment.transactionId());
        
        if (payment.providerTransactionId() == null) {
            return Mono.empty();
        }
        
        return Mono.fromCallable(() -> simulateRollbackCall(payment, providerMetadata))
                .subscribeOn(Schedulers.boundedElastic())
                .transformDeferred(mono -> circuitBreaker.executeMono(mono))
                .doOnSuccess(result -> log.info("Rollback ejecutado: {}", result.providerTransactionId()))
                .doOnError(error -> log.error("Error en rollback: {}", error.getMessage()))
                .onErrorResume(error -> Mono.empty());
    }

    @Override
    public Mono<Boolean> healthCheck() {
        log.debug("Ejecutando health check del proveedor: {}", PROVIDER_NAME);
        
        return Mono.fromCallable(() -> {
            simulateLatency(50);
            boolean isHealthy = ThreadLocalRandom.current().nextDouble() > 0.3;
            if (!isHealthy) {
                throw new RuntimeException("Proveedor no disponible");
            }
            return true;
        })
        .subscribeOn(Schedulers.boundedElastic())
        .timeout(Duration.ofSeconds(3))
        .doOnSuccess(result -> log.debug("Health check exitoso: {}", result))
        .doOnError(error -> log.warn("Health check fallido: {}", error.getMessage()));
    }

    @Override
    public String getProviderName() {
        return PROVIDER_NAME;
    }

    private Payment simulateProviderCall(Payment payment, Map<String, String> metadata) {
        simulateLatency(200);
        
        boolean shouldFail = ThreadLocalRandom.current().nextDouble() < 0.4;
        
        if (shouldFail) {
            throw new PaymentProviderException("Error simulado del proveedor de pagos");
        }
        
        String providerTransactionId = UUID.randomUUID().toString();
        String providerName = PROVIDER_NAME;
        
        return payment
                .withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails(providerTransactionId, providerName);
    }

    private Payment simulateRollbackCall(Payment payment, Map<String, String> metadata) {
        simulateLatency(150);
        
        boolean shouldFail = ThreadLocalRandom.current().nextDouble() < 0.2;
        
        if (shouldFail) {
            throw new PaymentProviderException("Error en rollback del proveedor");
        }
        
        return payment.withStatus(PaymentStatus.ROLLED_BACK);
    }

    private void simulateLatency(long maxMillis) {
        try {
            long latency = ThreadLocalRandom.current().nextLong(50, maxMillis);
            Thread.sleep(latency);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PaymentProviderException("Llamada interrumpida", e);
        }
    }

    public CircuitBreaker getCircuitBreaker() {
        return circuitBreaker;
    }

    public Retry getRetry() {
        return retry;
    }

    public boolean isAvailable() {
        return circuitBreaker.getState() != CircuitBreaker.State.OPEN;
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/FallbackPaymentProviderAdapter.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.model.Payment.PaymentStatus;
import com.pragma.payments.domain.port.PaymentProviderPort;
import com.pragma.payments.infrastructure.exception.PaymentProviderException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class FallbackPaymentProviderAdapter implements PaymentProviderPort {

    private static final Logger log = LoggerFactory.getLogger(FallbackPaymentProviderAdapter.class);
    private static final String FALLBACK_PROVIDER_NAME = "FallbackPaymentProvider";
    private static final double FALLBACK_FAILURE_RATE = 10.0;
    private static final Duration FALLBACK_TIMEOUT = Duration.ofSeconds(5);

    private final CircuitBreaker circuitBreaker;

    public FallbackPaymentProviderAdapter(CircuitBreakerRegistry circuitBreakerRegistry) {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(FALLBACK_FAILURE_RATE)
                .waitDurationInOpenState(Duration.ofSeconds(15))
                .slidingWindowSize(5)
                .minimumNumberOfCalls(2)
                .permittedNumberOfCallsInHalfOpenState(2)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
        
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker(FALLBACK_PROVIDER_NAME, config);
        
        log.info("FallbackPaymentProviderAdapter inicializado");
    }

    @Override
    public Mono<Payment> processPayment(Payment payment, Map<String, String> providerMetadata) {
        log.info("Procesando pago con proveedor FALLBACK: {}", payment.transactionId());
        
        return Mono.fromCallable(() -> simulateFallbackProviderCall(payment, providerMetadata))
                .subscribeOn(Schedulers.boundedElastic())
                .timeout(FALLBACK_TIMEOUT)
                .transformDeferred(mono -> circuitBreaker.executeMono(mono))
                .doOnSuccess(result -> log.info("Pago procesado via fallback: {}", result.providerTransactionId()))
                .doOnError(error -> log.error("Error en proveedor fallback: {}", error.getMessage()))
                .onErrorResume(error -> {
                    log.error("Proveedor fallback también falló, almacenando para reprocesamiento");
                    return storeForLaterRetry(payment, providerMetadata, error);
                });
    }

    @Override
    public Mono<Payment> rollbackPayment(Payment payment, Map<String, String> providerMetadata) {
        log.info("Ejecutando rollback via fallback para: {}", payment.transactionId());
        
        if (payment.providerTransactionId() == null) {
            return Mono.empty();
        }
        
        return Mono.fromCallable(() -> simulateFallbackRollback(payment, providerMetadata))
                .subscribeOn(Schedulers.boundedElastic())
                .timeout(FALLBACK_TIMEOUT)
                .doOnSuccess(result -> log.info("Rollback via fallback exitoso: {}", result.providerTransactionId()))
                .doOnError(error -> log.warn("Rollback fallback falló, marcando para revisión manual: {}", error.getMessage()))
                .onErrorResume(error -> Mono.just(payment.withStatus(PaymentStatus.PENDING_REVIEW)));
    }

    @Override
    public Mono<Boolean> healthCheck() {
        log.debug("Ejecutando health check del proveedor fallback");
        
        return Mono.fromCallable(() -> {
            simulateLatency(30);
            boolean isHealthy = ThreadLocalRandom.current().nextDouble() > 0.15;
            if (!isHealthy) {
                throw new RuntimeException("Proveedor fallback no disponible");
            }
            return true;
        })
        .subscribeOn(Schedulers.boundedElastic())
        .timeout(Duration.ofSeconds(2))
        .doOnSuccess(result -> log.debug("Health check fallback exitoso: {}", result))
        .doOnError(error -> log.warn("Health check fallback fallido: {}", error.getMessage()));
    }

    @Override
    public String getProviderName() {
        return FALLBACK_PROVIDER_NAME;
    }

    private Payment simulateFallbackProviderCall(Payment payment, Map<String, String> metadata) {
        simulateLatency(300);
        
        boolean shouldFail = ThreadLocalRandom.current().nextDouble() < 0.15;
        
        if (shouldFail) {
            throw new PaymentProviderException("Proveedor fallback no disponible temporalmente");
        }
        
        String providerTransactionId = "FALLBACK-" + UUID.randomUUID().toString();
        String providerName = FALLBACK_PROVIDER_NAME;
        
        return payment
                .withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails(providerTransactionId, providerName);
    }

    private Payment simulateFallbackRollback(Payment payment, Map<String, String> metadata) {
        simulateLatency(100);
        
        boolean shouldFail = ThreadLocalRandom.current().nextDouble() < 0.1;
        
        if (shouldFail) {
            throw new PaymentProviderException("Error en rollback fallback");
        }
        
        return payment.withStatus(PaymentStatus.ROLLED_BACK);
    }

    private Mono<Payment> storeForLaterRetry(Payment payment, Map<String, String> metadata, Throwable error) {
        log.warn("Almacenando pago {} para reprocesamiento posterior: {}", 
                payment.transactionId(), error.getMessage());
        
        return Mono.just(payment.withStatus(PaymentStatus.PENDING_REVIEW));
    }

    private void simulateLatency(long maxMillis) {
        try {
            long latency = ThreadLocalRandom.current().nextLong(20, maxMillis);
            Thread.sleep(latency);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PaymentProviderException("Llamada fallback interrumpida", e);
        }
    }

    public CircuitBreaker getCircuitBreaker() {
        return circuitBreaker;
    }

    public boolean isAvailable() {
        return circuitBreaker.getState() != CircuitBreaker.State.OPEN;
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/config/Resilience4jConfig.java ===
package com.pragma.payments.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry(CircuitBreakerConfig config) {
        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
    }

    @Bean
    public RetryRegistry retryRegistry(RetryConfig config) {
        return RetryRegistry.of(config);
    }

    @Bean
    public RetryConfig retryConfig() {
        return RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .build();
    }

    @Bean
    public BulkheadRegistry bulkheadRegistry(BulkheadConfig config) {
        return BulkheadRegistry.of(config);
    }

    @Bean
    public BulkheadConfig bulkheadConfig() {
        return BulkheadConfig.custom()
                .maxConcurrentCalls(10)
                .maxWaitDuration(Duration.ofMillis(200))
                .build();
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java ===
package com.pragma.payments.infrastructure.controller;

import com.pragma.payments.application.service.PaymentService;
import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.model.Payment.PaymentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(
            @Valid @RequestBody PaymentRequest request) {
        Payment payment = new Payment(
                UUID.randomUUID().toString(),
                request.amount(),
                request.currency(),
                request.description(),
                PaymentStatus.PENDING,
                request.customerId(),
                null,
                null,
                null
        );
        
        Payment processedPayment = paymentService.processPayment(payment, request.metadata());
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PaymentResponse.fromPayment(processedPayment));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable String paymentId) {
        Payment payment = paymentService.getPayment(paymentId);
        
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(PaymentResponse.fromPayment(payment));
    }

    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentResponse> refundPayment(
            @PathVariable String paymentId,
            @RequestBody(required = false) RefundRequest request) {
        Payment payment = paymentService.getPayment(paymentId);
        
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }
        
        Payment refundedPayment = paymentService.refundPayment(payment, 
                request != null ? request.reason() : null);
        
        return ResponseEntity.ok(PaymentResponse.fromPayment(refundedPayment));
    }

    @GetMapping("/health/provider")
    public ResponseEntity<Map<String, Object>> providerHealth() {
        boolean healthy = paymentService.checkProviderHealth();
        
        return ResponseEntity.ok(Map.of(
                "healthy", healthy,
                "provider", "external-payment-gateway",
                "timestamp", System.currentTimeMillis()
        ));
    }

    public record PaymentRequest(
            @NotNull @Positive BigDecimal amount,
            @NotBlank String currency,
            @NotBlank String description,
            @NotBlank String customerId,
            Map<String, String> metadata
    ) {}

    public record RefundRequest(String reason) {}

    public record PaymentResponse(
            String paymentId,
            BigDecimal amount,
            String currency,
            String description,
            String status,
            String customerId,
            String providerTransactionId,
            String providerName,
            String createdAt,
            String updatedAt
    ) {
        public static PaymentResponse fromPayment(Payment payment) {
            return new PaymentResponse(
                    payment.id(),
                    payment.amount(),
                    payment.currency(),
                    payment.description(),
                    payment.status().name(),
                    payment.customerId(),
                    payment.providerTransactionId(),
                    payment.providerName(),
                    payment.createdAt() != null ? payment.createdAt().toString() : null,
                    payment.updatedAt() != null ? payment.updatedAt().toString() : null
            );
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java ===
package com.pragma.payments.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentProviderException.class)
    public ResponseEntity<ErrorResponse> handlePaymentProviderException(PaymentProviderException ex) {
        HttpStatus status = determineHttpStatus(ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                ex.getMessage(),
                "PAYMENT_PROVIDER_ERROR",
                Instant.now().toString(),
                Map.of("provider", ex.getProviderName())
        );
        
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null 
                                ? error.getDefaultMessage() 
                                : "Invalid value",
                        (existing, replacement) -> existing
                ));
        
        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                fieldErrors,
                Instant.now().toString()
        );
        
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                "INVALID_ARGUMENT",
                Instant.now().toString(),
                Map.of()
        );
        
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                "INTERNAL_ERROR",
                Instant.now().toString(),
                Map.of()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private HttpStatus determineHttpStatus(PaymentProviderException ex) {
        return switch (ex.getErrorType()) {
            case TIMEOUT -> HttpStatus.GATEWAY_TIMEOUT;
            case UNAVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
            case REJECTED -> HttpStatus.PRECONDITION_FAILED;
            case INVALID_RESPONSE -> HttpStatus.BAD_GATEWAY;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    public record ErrorResponse(
            int status,
            String message,
            String code,
            String timestamp,
            Map<String, Object> details
    ) {}

    public record ValidationErrorResponse(
            int status,
            String message,
            Map<String, String> fieldErrors,
            String timestamp
    ) {}
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/exception/PaymentProviderException.java ===
package com.pragma.payments.infrastructure.exception;

import java.time.Instant;
import java.util.Map;

public class PaymentProviderException extends RuntimeException {

    private final String providerName;
    private final String errorCode;
    private final Instant timestamp;
    private final Map<String, String> metadata;

    public PaymentProviderException(String message, String providerName, String errorCode) {
        super(message);
        this.providerName = providerName;
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
        this.metadata = Map.of();
    }

    public PaymentProviderException(String message, String providerName, String errorCode, Throwable cause) {
        super(message, cause);
        this.providerName = providerName;
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
        this.metadata = Map.of();
    }

    public PaymentProviderException(String message, String providerName, String errorCode, 
                                     Map<String, String> metadata, Throwable cause) {
        super(message, cause);
        this.providerName = providerName;
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
        this.metadata = metadata != null ? Map.copyOf(metadata) : Map.of();
    }

    public String getProviderName() {
        return providerName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public boolean isRetryable() {
        return "TIMEOUT".equals(errorCode) || 
               "CONNECTION_ERROR".equals(errorCode) ||
               "TEMPORARY_UNAVAILABLE".equals(errorCode);
    }

    public boolean isCircuitBreakerFailure() {
        return "CIRCUIT_BREAKER_OPEN".equals(errorCode) ||
               "CIRCUIT_BREAKER_HALF_OPEN".equals(errorCode);
    }
}

// === ARCHIVO: src/test/java/com/pragma/payments/application/service/PaymentServiceTest.java ===
package com.pragma.payments.application.service;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.model.Payment.PaymentStatus;
import com.pragma.payments.infrastructure.adapter.FallbackPaymentProviderAdapter;
import com.pragma.payments.infrastructure.adapter.PaymentProviderAdapter;
import com.pragma.payments.infrastructure.exception.PaymentProviderException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PaymentService - Pruebas unitarias de tolerancia a fallos")
class PaymentServiceTest {

    @Mock
    private PaymentProviderAdapter paymentProviderAdapter;

    @Mock
    private FallbackPaymentProviderAdapter fallbackPaymentProviderAdapter;

    private CircuitBreakerRegistry circuitBreakerRegistry;
    private RetryRegistry retryRegistry;
    private MeterRegistry meterRegistry;
    private PaymentService paymentService;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
        retryRegistry = RetryRegistry.ofDefaults();
        meterRegistry = new SimpleMeterRegistry();

        paymentService = new PaymentService(
            paymentProviderAdapter,
            fallbackPaymentProviderAdapter,
            circuitBreakerRegistry,
            retryRegistry,
            meterRegistry
        );

        testPayment = new Payment(
            UUID.randomUUID().toString(),
            "ORDER-001",
            new BigDecimal("100.00"),
            "USD",
            PaymentStatus.PENDING,
            null,
            null
        );
    }

    @Nested
    @DisplayName("Escenarios de procesamiento exitoso")
    class SuccessfulProcessing {

        @Test
        @DisplayName("Debería procesar pago exitosamente cuando el proveedor responde")
        void shouldProcessPaymentSuccessfully() {
            Payment completedPayment = testPayment.withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails("TXN-12345", "PRIMARY_PROVIDER");

            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.just(completedPayment));

            StepVerifier.create(paymentService.processPayment(testPayment, Map.of("channel", "WEB")))
                .assertNext(payment -> {
                    assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED);
                    assertThat(payment.providerTransactionId()).isEqualTo("TXN-12345");
                })
                .verifyComplete();

            verify(paymentProviderAdapter).processPayment(eq(testPayment), any(Map.class));
            verify(fallbackPaymentProviderAdapter, never()).processPayment(any(), any());
        }

        @Test
        @DisplayName("Debería usar fallback cuando el proveedor principal falla")
        void shouldUseFallbackWhenPrimaryProviderFails() {
            Payment fallbackPayment = testPayment.withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails("FALLBACK-TXN-67890", "FALLBACK_PROVIDER");

            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(new PaymentProviderException(
                    "Provider unavailable", "PRIMARY_PROVIDER", "TEMPORARY_UNAVAILABLE")));

            when(fallbackPaymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.just(fallbackPayment));

            StepVerifier.create(paymentService.processPayment(testPayment, Map.of("channel", "MOBILE")))
                .assertNext(payment -> {
                    assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED);
                    assertThat(payment.providerTransactionId()).isEqualTo("FALLBACK-TXN-67890");
                })
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Escenarios de falla del proveedor")
    class ProviderFailureScenarios {

        @Test
        @DisplayName("Debería manejar timeout del proveedor y marcar pago como FAILED")
        void shouldHandleProviderTimeout() {
            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(new PaymentProviderException(
                    "Connection timeout", "PRIMARY_PROVIDER", "TIMEOUT",
                    Map.of("retryCount", "3"), new RuntimeException("Connection timeout"))));

            when(fallbackPaymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(new PaymentProviderException(
                    "Fallback also failed", "FALLBACK_PROVIDER", "TEMPORARY_UNAVAILABLE"))));

            StepVerifier.create(paymentService.processPayment(testPayment, Map.of()))
                .expectError(PaymentProviderException.class)
                .verify();
        }

        @Test
        @DisplayName("Debería manejar error de circuit breaker abierto")
        void shouldHandleCircuitBreakerOpen() {
            PaymentProviderException circuitBreakerException = new PaymentProviderException(
                "Circuit breaker is open", "PRIMARY_PROVIDER", "CIRCUIT_BREAKER_OPEN");

            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(circuitBreakerException));

            when(fallbackPaymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.just(testPayment.withStatus(PaymentStatus.COMPLETED)));

            StepVerifier.create(paymentService.processPayment(testPayment, Map.of()))
                .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED))
                .verifyComplete();
        }

        @Test
        @DisplayName("Debería fallar cuando ambos proveedores fallan consecutivamente")
        void shouldFailWhenBothProvidersFail() {
            PaymentProviderException primaryException = new PaymentProviderException(
                "Primary provider error", "PRIMARY_PROVIDER", "CONNECTION_ERROR");
            PaymentProviderException fallbackException = new PaymentProviderException(
                "Fallback provider error", "FALLBACK_PROVIDER", "CONNECTION_ERROR");

            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(primaryException));

            when(fallbackPaymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(fallbackException));

            StepVerifier.create(paymentService.processPayment(testPayment, Map.of()))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(PaymentProviderException.class);
                    PaymentProviderException pe = (PaymentProviderException) error;
                    assertThat(pe.getErrorCode()).isEqualTo("CONNECTION_ERROR");
                    assertThat(pe.isRetryable()).isTrue();
                })
                .verify();
        }
    }

    @Nested
    @DisplayName("Verificación de métricas y configuración de resiliencia")
    class ResilienceConfigurationTests {

        @Test
        @DisplayName("Debería registrar métricas de retry en el MeterRegistry")
        void shouldRegisterRetryMetrics() {
            Payment completedPayment = testPayment.withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails("TXN-METRICS", "PRIMARY_PROVIDER");

            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.just(completedPayment));

            paymentService.processPayment(testPayment, Map.of()).block();

            assertThat(meterRegistry.get("payments.retry.attempts").counter()).isNotNull();
        }

        @Test
        @DisplayName("Debería registrar métricas de circuit breaker en el MeterRegistry")
        void shouldRegisterCircuitBreakerMetrics() {
            Payment completedPayment = testPayment.withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails("TXN-CB", "PRIMARY_PROVIDER");

            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.just(completedPayment));

            paymentService.processPayment(testPayment, Map.of()).block();

            assertThat(meterRegistry.get("payments.circuitbreaker.state").gauge()).isNotNull();
        }
    }

    @Nested
    @DisplayName("Escenarios de rollback")
    class RollbackScenarios {

        @Test
        @DisplayName("Debería ejecutar rollback correctamente cuando se solicita")
        void shouldExecuteRollbackSuccessfully() {
            Payment completedPayment = testPayment.withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails("TXN-ROLLBACK", "PRIMARY_PROVIDER");

            when(paymentProviderAdapter.rollbackPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.just(completedPayment.withStatus(PaymentStatus.REFUNDED)));

            StepVerifier.create(paymentService.rollbackPayment(completedPayment, Map.of("reason", "CUSTOMER_REQUEST")))
                .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.REFUNDED))
                .verifyComplete();
        }

        @Test
        @DisplayName("Debería manejar falla en rollback gracefully")
        void shouldHandleRollbackFailure() {
            when(paymentProviderAdapter.rollbackPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(new PaymentProviderException(
                    "Rollback failed", "PRIMARY_PROVIDER", "ROLLBACK_ERROR")));

            StepVerifier.create(paymentService.rollbackPayment(testPayment, Map.of()))
                .expectError(PaymentProviderException.class)
                .verify();
        }
    }

    @Nested
    @DisplayName("Validación de estado del proveedor")
    class HealthCheckTests {

        @Test
        @DisplayName("Debería retornar health check del proveedor primario")
        void shouldReturnPrimaryProviderHealth() {
            when(paymentProviderAdapter.healthCheck())
                .thenReturn(Mono.just(true));

            StepVerifier.create(paymentService.checkProviderHealth())
                .assertNext(result -> assertThat(result).isTrue())
                .verifyComplete();
        }

        @Test
        @DisplayName("Debería retornar false cuando health check falla")
        void shouldReturnFalseWhenHealthCheckFails() {
            when(paymentProviderAdapter.healthCheck())
                .thenReturn(Mono.just(false));

            StepVerifier.create(paymentService.checkProviderHealth())
                .assertNext(result -> assertThat(result).isFalse())
                .verifyComplete();
        }
    }
}

// === ARCHIVO: src/test/java/com/pragma/payments/infrastructure/adapter/PaymentProviderAdapterTest.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.model.Payment.PaymentStatus;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PaymentProviderAdapter - Pruebas de integración con Resilience4j")
class PaymentProviderAdapterTest {

    @Mock
    private ExternalPaymentGateway mockGateway;

    private CircuitBreakerRegistry circuitBreakerRegistry;
    private RetryRegistry retryRegistry;
    private PaymentProviderAdapter adapter;

    private Payment testPayment;
    private static final String PROVIDER_NAME = "EXTERNAL_GATEWAY";

    @BeforeEach
    void setUp() {
        circuitBreakerRegistry = CircuitBreakerRegistry.of(
            CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .build()
        );

        retryRegistry = RetryRegistry.of(
            RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .retryExceptions(IOException.class, TimeoutException.class)
                .build()
        );

        adapter = new PaymentProviderAdapter(mockGateway, circuitBreakerRegistry, retryRegistry);

        testPayment = new Payment(
            UUID.randomUUID().toString(),
            "ORDER-TEST-001",
            new BigDecimal("250.00"),
            "USD",
            PaymentStatus.PENDING,
            null,
            null
        );
    }

    @Nested
    @DisplayName("Procesamiento de pagos con Circuit Breaker")
    class CircuitBreakerTests {

        @Test
        @DisplayName("Debería abrir el circuit breaker después de alcanzar el umbral de errores")
        void shouldOpenCircuitBreakerAfterFailureThreshold() {
            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.error(new IOException("Network error")));

            for (int i = 0; i < 6; i++) {
                StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                    .expectError(IOException.class)
                    .verify();
            }

            CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(PROVIDER_NAME);
            assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);
        }

        @Test
        @DisplayName("Debería permitir llamada cuando el circuit breaker está полуоткрыт")
        void shouldAllowCallWhenCircuitBreakerIsHalfOpen() {
            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.error(new IOException("Network error")));

            for (int i = 0; i < 5; i++) {
                StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                    .expectError(IOException.class)
                    .verify();
            }

            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.just(createSuccessfulResponse()));

            StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED))
                .verifyComplete();
        }

        @Test
        @DisplayName("Debería cerrar el circuit breaker después de exitosos")
        void shouldCloseCircuitBreakerAfterSuccess() {
            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.just(createSuccessfulResponse()));

            for (int i = 0; i < 8; i++) {
                StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                    .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED))
                    .verifyComplete();
            }

            CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(PROVIDER_NAME);
            assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.CLOSED);
            assertThat(circuitBreaker.getMetrics().getFailureRate()).isLessThan(50.0f);
        }
    }

    @Nested
    @DisplayName("Procesamiento de pagos con Retry")
    class RetryTests {

        @Test
        @DisplayName("Debería reintentar la operación hasta 3 veces en caso de falla")
        void shouldRetryUpToThreeTimes() {
            when(mockGateway.processPayment(any()))
                .thenReturn(
                    Mono.error(new IOException("Temporary failure")),
                    Mono.error(new IOException("Temporary failure")),
                    Mono.just(createSuccessfulResponse())
                );

            StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED))
                .verifyComplete();

            verify(mockGateway, times(3)).processPayment(any());
        }

        @Test
        @DisplayName("Debería fallar después de agotar reintentos")
        void shouldFailAfterExhaustingRetries() {
            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.error(new IOException("Persistent failure")));

            StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                .expectError(IOException.class)
                .verify();

            verify(mockGateway, times(3)).processPayment(any());
        }

        @Test
        @DisplayName("Debería usar backoff exponencial entre reintentos")
        void shouldUseExponentialBackoff() {
            when(mockGateway.processPayment(any()))
                .thenReturn(
                    Mono.error(new IOException("First attempt failed")),
                    Mono.error(new IOException("Second attempt failed")),
                    Mono.just(createSuccessfulResponse())
                );

            long startTime = System.currentTimeMillis();
            StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED))
                .verifyComplete();
            long elapsedTime = System.currentTimeMillis() - startTime;

            assertThat(elapsedTime).isGreaterThanOrEqualTo(500);
        }
    }

    @Nested
    @DisplayName("Integración de Circuit Breaker y Retry")
    class CombinedResilienceTests {

        @Test
        @DisplayName("Debería manejar falla con circuit breaker y retry juntos")
        void shouldHandleCombinedCircuitBreakerAndRetry() {
            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.error(new IOException("Provider unavailable")));

            StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                .expectError(IOException.class)
                .verify();

            verify(mockGateway, atLeast(3)).processPayment(any());
        }

        @Test
        @DisplayName("Debería registrar métricas de ambas resiliencias")
        void shouldRegisterMetricsForBothResiliences() {
            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.just(createSuccessfulResponse()));

            adapter.processPayment(testPayment, Map.of()).block();

            CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker(PROVIDER_NAME);
            Retry retry = retryRegistry.retry(PROVIDER_NAME);

            assertThat(cb.getMetrics().getNumberOfSuccessfulCalls()).isGreaterThan(0);
            assertThat(retry.getMetrics().getNumberOfSuccessfulCallsWithRetryAttempt()).isGreaterThan(0);
        }
    }

    @Nested
    @DisplayName("Health check del proveedor")
    class HealthCheckTests {

        @Test
        @DisplayName("Debería retornar true cuando el health check del gateway es exitoso")
        void shouldReturnTrueWhenGatewayHealthCheckSucceeds() {
            when(mockGateway.healthCheck()).thenReturn(Mono.just(true));

            StepVerifier.create(adapter.healthCheck())
                .assertNext(result -> assertThat(result).isTrue())
                .verifyComplete();
        }

        @Test
        @DisplayName("Debería manejar falla en health check gracefully")
        void shouldHandleHealthCheckFailure() {
            when(mockGateway.healthCheck())
                .thenReturn(Mono.error(new IOException("Health check endpoint unavailable")));

            StepVerifier.create(adapter.healthCheck())
                .assertNext(result -> assertThat(result).isFalse())
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Rollback de pagos")
    class RollbackTests {

        @Test
        @DisplayName("Debería ejecutar rollback exitosamente")
        void shouldExecuteRollbackSuccessfully() {
            Payment completedPayment = testPayment.withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails("TXN-123", PROVIDER_NAME);

            when(mockGateway.rollbackPayment(any()))
                .thenReturn(Mono.just(createSuccessfulResponse()));

            StepVerifier.create(adapter.rollbackPayment(completedPayment, Map.of("reason", "CUSTOMER_DISPUTE")))
                .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED))
                .verifyComplete();

            verify(mockGateway).rollbackPayment(any());
        }

        @Test
        @DisplayName("Debería propagar error de rollback correctamente")
        void shouldPropagateRollbackError() {
            when(mockGateway.rollbackPayment(any()))
                .thenReturn(Mono.error(new IOException("Rollback failed at provider")));

            StepVerifier.create(adapter.rollbackPayment(testPayment, Map.of()))
                .expectError(IOException.class)
                .verify();
        }
    }

    private Payment createSuccessfulResponse() {
        return testPayment.withStatus(PaymentStatus.COMPLETED)
            .withProviderDetails("PROVIDER-TXN-" + UUID.randomUUID(), PROVIDER_NAME);
    }

    interface ExternalPaymentGateway {
        Mono<Payment> processPayment(Payment payment);
        Mono<Payment> rollbackPayment(Payment payment);
        Mono<Boolean> healthCheck();
    }

    static class IOException extends java.io.IOException {
        public IOException(String message) {
            super(message);
        }
    }

    static class TimeoutException extends java.util.concurrent.TimeoutException {
        public TimeoutException(String message) {
            super(message);
        }
    }
}
```
