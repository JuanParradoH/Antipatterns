# Taller Antipatrones - La Oficina de Visas del Caos (Java)

**Universidad Distrital Francisco José de Caldas**  
**Facultad de Ingeniería - Ingeniería de Sistemas**  
**Asignatura:** Modelos de Programación  
**Juan David Parrado Hernández - 20251020065**

---

## Parte 1. Cacería de antipatrones

Diagnóstico de los cuatro fragmentos del sistema heredado del Consulado Caótico:

| Fragmento | Antipatrón | Síntoma que lo delata | Consecuencia en el consulado y en el software | Solución / Patrón aplicado |
| :--- | :--- | :--- | :--- | :--- |
| **1. La ventanilla de Ramiro** (`OficinaDeTodo`) | **God Object / God Class** | Una sola clase (`OficinaDeTodo`) concentra cuatro responsabilidades distintas en un único método `procesar()`: validar el pasaporte, cobrar la tarifa, guardar el expediente y enviar el correo. | Si Ramiro se enferma o falla un detalle, todo el consulado se detiene. En el código genera alto acoplamiento y cambios arriesgados: modificar el formato del correo o la regla de cobro obliga a tocar la clase central y pone en riesgo todo el trámite. | **Principio de Responsabilidad Única (SRP)**: separación en clases cohesivas (`ValidadorPasaporte`, `ServicioCobro`, `RepositorioExpedientes`, `ServicioCorreo`) orquestadas por `ProcesadorTramiteVisa`. |
| **2. La tarifa clonada** (`CalculadoraDeTarifas`) | **Copy-Paste Programming** | La fórmula base (`double total = 50 * meses; total = total + 10;`) está triplicada exactamente igual en `visaTurista`, `visaEstudiante` y `visaTrabajo`, cambiando únicamente el factor final. | Mantenimiento propenso a errores e inconsistencias: cuando subieron la tarifa base, se actualizaron dos métodos y el tercero quedó con el precio viejo, generando cobros erróneos. | **Patrón Strategy + Principio DRY**: centralización de la tarifa base en `ConfiguracionTarifaria` y del cálculo en `CalculadoraTarifasStrategy`, delegando solo el factor multiplicador a implementaciones de `EstrategiaTarifa`. |
| **3. Los números misteriosos** (`EvaluadorMagico`) | **Magic Numbers** | Condicionales llenos de literales numéricos sin contexto semántico (`tipo == 1`, `tipo == 2`, `tipo == 3`, `edad > 17`, `edad > 15`, `edad < 31`, `edad < 66`). | Código difícil de comprender y mantener (nadie sabe con certeza qué representa cada número). Además, ante un tipo inexistente como `9`, el sistema retorna `"RECHAZADA"` en silencio sin distinguir entre un rechazo por edad y un código inválido. | **Enum con constantes con nombre** (`TipoVisa` y `EstadoEvaluacion`): encapsulación de códigos y rangos de edad con nombres explícitos y validación segura de tipos desconocidos. |
| **4. La cancelación en cadena** (`SolicitudAcoplada`) | **Acoplamiento fuerte** | `SolicitudAcoplada` instancia directamente con `new` a `CorreoServicio`, `CitasServicio` y `PagosServicio`, invocando cada aviso de forma manual en `cancelar()`. | Fragilidad operativa y rigidez: en `cancelar()` olvidaron invocar a `pagos`, haciendo que a un viajero se le perdiera su reembolso. Agregar o modificar un servicio exige editar directamente la clase de solicitud. | **Patrón Observer**: desacoplamiento entre el sujeto (`SolicitudVisa`) y los servicios suscritos (`ObservadorCorreo`, `ObservadorCitas`, `ObservadorPagos`) mediante la interfaz `ObservadorCancelacion`. |

---

## Parte 2. Estructura del proyecto (Organización por paquetes)

<pre>
consulado/
├── Main.java
├── heredado/
│   ├── CalculadoraDeTarifas.java
│   ├── CitasServicio.java
│   ├── CorreoServicio.java
│   ├── EvaluadorMagico.java
│   ├── OficinaDeTodo.java
│   ├── PagosServicio.java
│   └── SolicitudAcoplada.java
└── rescatado/
    ├── cancelacion/
    │   ├── EstadoSolicitud.java
    │   ├── ObservadorCancelacion.java
    │   ├── ObservadorCitas.java
    │   ├── ObservadorCorreo.java
    │   ├── ObservadorPagos.java
    │   └── SolicitudVisa.java
    ├── evaluacion/
    │   ├── EstadoEvaluacion.java
    │   ├── EvaluadorVisa.java
    │   └── TipoVisa.java
    ├── tarifas/
    │   ├── CalculadoraTarifasStrategy.java
    │   ├── ConfiguracionTarifaria.java
    │   ├── EstrategiaTarifa.java
    │   ├── EstrategiaVisaEstudiante.java
    │   ├── EstrategiaVisaTrabajo.java
    │   └── EstrategiaVisaTurista.java
    └── ventanilla/
        ├── ProcesadorTramiteVisa.java
        ├── RepositorioExpedientes.java
        ├── ServicioCobro.java
        ├── ServicioCorreo.java
        └── ValidadorPasaporte.java
</pre>

---

## Parte 3. La prueba de fuego (Salida en consola de los 5 casos)

<pre>
================================================================================
           TALLER ANTIPATRONES - LA OFICINA DE VISAS DEL CAOS (JAVA)            
================================================================================

--------------------------------------------------------------------------------
CASO 1: GOD OBJECT (OficinaDeTodo vs. Responsabilidad Única - SRP)
--------------------------------------------------------------------------------
>>> [Versión Heredada - OficinaDeTodo (Ramiro hace todo)]:
[Heredado - Ramiro/OficinaDeTodo] Cobrando $160.0 a Laura Gómez
[Heredado - Ramiro/OficinaDeTodo] Guardando expediente de Laura Gómez
[Heredado - Ramiro/OficinaDeTodo] Correo para Laura Gómez: su visa va en camino

>>> [Versión Rescatada - Clases separadas por responsabilidad (SRP)]:
[ValidadorPasaporte] Pasaporte 'COL987654' verificado correctamente.
[ServicioCobro] Cobrando $160.0 a Laura Gómez
[RepositorioExpedientes] Guardando expediente de Laura Gómez (Pasaporte: COL987654)
[ServicioCorreo] Correo para Laura Gómez: su visa va en camino

--------------------------------------------------------------------------------
CASO 2: COPY-PASTE PROGRAMMING (Subir tarifa base de 50 a 60 para 3 meses)
--------------------------------------------------------------------------------
>>> [Versión Heredada - CalculadoraDeTarifas]:
    Lugares editados para cambiar tarifa base: 3
    Precio Turista (3 meses):    $190.0
    Precio Estudiante (3 meses): $95.0
    Precio Trabajo (3 meses):    $285.0

>>> [Versión Rescatada - Patrón Strategy + DRY]:
    Lugares editados para cambiar tarifa base: 1
    Precio Turista (3 meses):    $190.0
    Precio Estudiante (3 meses): $95.0
    Precio Trabajo (3 meses):    $285.0

--------------------------------------------------------------------------------
CASO 3: MAGIC NUMBERS (Evaluar tipo 2 / edad 20 y probar tipo inválido 9)
--------------------------------------------------------------------------------
>>> [Versión Heredada - EvaluadorMagico]:
    Evaluar(tipo=2, edad=20): APROBADA
    Evaluar(tipo=9, edad=20): RECHAZADA -> (Rechazo silencioso: no distingue entre edad no apta y tipo inexistente)

>>> [Versión Rescatada - TipoVisa Enum + Constantes con nombre]:
    Evaluar(TipoVisa.ESTUDIANTE, edad=20): APROBADA
    EvaluarPorCodigoSeguro(codigo=9, edad=20): ERROR DE VALIDACIÓN: Código de visa inválido (9). Los tipos permitidos son: 1 (TURISTA), 2 (ESTUDIANTE), 3 (TRABAJO).

--------------------------------------------------------------------------------
CASO 4: ACOPLAMIENTO FUERTE (SolicitudAcoplada vs. Patrón Observer)
--------------------------------------------------------------------------------
>>> [Versión Heredada - SolicitudAcoplada]:
[Heredado - CorreoServicio] Enviando correo de cancelación al viajero.
[Heredado - CitasServicio] Liberando cupo en la agenda de citas.
    [ALERTA EVIDENCIA]: SolicitudAcoplada olvidó avisar a PagosServicio. ¡Se perdió el reembolso!

>>> [Versión Rescatada - Patrón Observer]:
[SolicitudVisa] Estado cambiado a CANCELADA. Notificando a 3 observadores suscritos...
[ObservadorCorreo] Enviando correo a Carlos Mendoza: su solicitud fue cancelada.
[ObservadorCitas] Liberando cupo en la agenda de citas de Carlos Mendoza.
[ObservadorPagos] Reembolsando automáticamente $95.0 a Carlos Mendoza.

--------------------------------------------------------------------------------
CASO 5: INTEGRACIÓN COMPLETA (Viaje de extremo a extremo con la versión nueva)
--------------------------------------------------------------------------------
1. Solicitud recibida para Mateo Ríos | Visa: TRABAJO | Edad: 24
2. Resultado de Evaluación: APROBADA
3. Tarifa calculada con Strategy (4 meses): $315.0
[ValidadorPasaporte] Pasaporte 'COL123456' verificado correctamente.
[ServicioCobro] Cobrando $315.0 a Mateo Ríos
[RepositorioExpedientes] Guardando expediente de Mateo Ríos (Pasaporte: COL123456)
[ServicioCorreo] Correo para Mateo Ríos: su visa va en camino
4. El viajero decide cancelar su solicitud antes del viaje:
[SolicitudVisa] Estado cambiado a CANCELADA. Notificando a 3 observadores suscritos...
[ObservadorCorreo] Enviando correo a Mateo Ríos: su solicitud fue cancelada.
[ObservadorCitas] Liberando cupo en la agenda de citas de Mateo Ríos.
[ObservadorPagos] Reembolsando automáticamente $315.0 a Mateo Ríos.

================================================================================
                    TABLA FINAL DE ANTIPATRONES CAPTURADOS                      
================================================================================
ANTIPATRÓN               | SÍNTOMA DETECTADO              | SOLUCIÓN APLICADA   
-------------------------+--------------------------------+---------------------
1. God Object            | OficinaDeTodo hace 4 tareas    | SRP (4 clases)      
2. Copy-Paste Prog.      | Fórmula repetida en 3 métodos  | Patrón Strategy+DRY 
3. Magic Numbers         | Tipos 1,2,3 y edades sin nombre | Enum TipoVisa+Const 
4. Acoplamiento Fuerte   | Llamadas manuales, olvida pago | Patrón Observer     
================================================================================
</pre>

---

## Parte 4. Reflexión: ¿Cómo esta implementación evita caer de nuevo en los antipatrones?

La nueva arquitectura previene la reaparición de antipatrones y la acumulación de deuda técnica porque reemplaza las soluciones improvisadas por principios de diseño donde cada cambio futuro tiene un único lugar de impacto. En primer lugar, el **Principio de Responsabilidad Única (SRP)** impide el resurgimiento de un *God Object* como `OficinaDeTodo` al aislar la validación de pasaporte, el cobro, el almacenamiento de expedientes y la notificación por correo en clases independientes, garantizando que un cambio en cualquiera de esos servicios no afecte al resto del trámite. En segundo lugar, la combinación del **patrón Strategy** con el **principio DRY** elimina el *Copy-Paste Programming* al centralizar la fórmula base y los valores tarifarios en `ConfiguracionTarifaria` y `CalculadoraTarifasStrategy`; si mañana sube la tarifa base o se agrega una nueva categoría de visa, se edita un único punto o se añade una nueva implementación de `EstrategiaTarifa` sin tocar ni duplicar lógica existente. En tercer lugar, el uso del **enum `TipoVisa` con constantes semánticas** erradica los *Magic Numbers*, haciendo que las reglas de negocio sean legibles por sí mismas y que cualquier código de visa inexistente sea reportado explícitamente en vez de fallar en silencio. Finalmente, el **patrón Observer** rompe el acoplamiento fuerte al hacer que `SolicitudVisa` dependa únicamente de la interfaz `ObservadorCancelacion`, permitiendo suscribir nuevos avisos (como auditoría o contabilidad) sin modificar la clase de solicitud ni correr el riesgo de olvidar llamadas manuales en cadena.