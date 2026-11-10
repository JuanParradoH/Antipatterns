package consulado;

import consulado.heredado.CalculadoraDeTarifas;
import consulado.heredado.EvaluadorMagico;
import consulado.heredado.OficinaDeTodo;
import consulado.heredado.SolicitudAcoplada;
import consulado.rescatado.cancelacion.ObservadorCitas;
import consulado.rescatado.cancelacion.ObservadorCorreo;
import consulado.rescatado.cancelacion.ObservadorPagos;
import consulado.rescatado.cancelacion.SolicitudVisa;
import consulado.rescatado.evaluacion.EstadoEvaluacion;
import consulado.rescatado.evaluacion.EvaluadorVisa;
import consulado.rescatado.evaluacion.TipoVisa;
import consulado.rescatado.tarifas.CalculadoraTarifasStrategy;
import consulado.rescatado.tarifas.ConfiguracionTarifaria;
import consulado.rescatado.tarifas.EstrategiaTarifa;
import consulado.rescatado.tarifas.EstrategiaVisaEstudiante;
import consulado.rescatado.tarifas.EstrategiaVisaTrabajo;
import consulado.rescatado.tarifas.EstrategiaVisaTurista;
import consulado.rescatado.ventanilla.ProcesadorTramiteVisa;
import consulado.rescatado.ventanilla.RepositorioExpedientes;
import consulado.rescatado.ventanilla.ServicioCobro;
import consulado.rescatado.ventanilla.ServicioCorreo;
import consulado.rescatado.ventanilla.ValidadorPasaporte;

public class Main {
    private static final double NUEVA_TARIFA_BASE = 60.0;
    private static final int MESES_PRUEBA = 3;
    private static final int EDAD_PRUEBA_ESTUDIANTE = 20;
    private static final int CODIGO_TIPO_INVALIDO = 9;

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("           TALLER ANTIPATRONES - LA OFICINA DE VISAS DEL CAOS (JAVA)            ");
        System.out.println("================================================================================\n");

        ejecutarCaso1GodObject();
        ejecutarCaso2CopyPaste();
        ejecutarCaso3MagicNumbers();
        ejecutarCaso4AcoplamientoFuerte();
        ejecutarCaso5IntegracionCompleta();
    }

    private static void ejecutarCaso1GodObject() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("CASO 1: GOD OBJECT (OficinaDeTodo vs. Responsabilidad Única - SRP)");
        System.out.println("--------------------------------------------------------------------------------");

        System.out.println(">>> [Versión Heredada - OficinaDeTodo (Ramiro hace todo)]:");
        OficinaDeTodo oficinaHeredada = new OficinaDeTodo();
        oficinaHeredada.procesar("Laura Gómez", "COL987654", 160.0);

        System.out.println("\n>>> [Versión Rescatada - Clases separadas por responsabilidad (SRP)]:");
        ProcesadorTramiteVisa procesadorRescatado = new ProcesadorTramiteVisa(
                new ValidadorPasaporte(),
                new ServicioCobro(),
                new RepositorioExpedientes(),
                new ServicioCorreo()
        );
        procesadorRescatado.procesarTramite("Laura Gómez", "COL987654", 160.0);
        System.out.println();
    }

    private static void ejecutarCaso2CopyPaste() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("CASO 2: COPY-PASTE PROGRAMMING (Subir tarifa base de 50 a 60 para " + MESES_PRUEBA + " meses)");
        System.out.println("--------------------------------------------------------------------------------");

        CalculadoraDeTarifas calcHeredada = new CalculadoraDeTarifas();
        calcHeredada.actualizarTarifaBaseEnClones(NUEVA_TARIFA_BASE, NUEVA_TARIFA_BASE, NUEVA_TARIFA_BASE);

        ConfiguracionTarifaria configStrategy = new ConfiguracionTarifaria();
        configStrategy.actualizarTarifaBaseMensual(NUEVA_TARIFA_BASE);
        CalculadoraTarifasStrategy calcRescatada = new CalculadoraTarifasStrategy(configStrategy);

        EstrategiaTarifa estTurista = new EstrategiaVisaTurista();
        EstrategiaTarifa estEstudiante = new EstrategiaVisaEstudiante();
        EstrategiaTarifa estTrabajo = new EstrategiaVisaTrabajo();

        System.out.println(">>> [Versión Heredada - CalculadoraDeTarifas]:");
        System.out.println("    Lugares editados para cambiar tarifa base: " + calcHeredada.getLugaresEditados());
        System.out.println("    Precio Turista (3 meses):    $" + calcHeredada.visaTurista(MESES_PRUEBA));
        System.out.println("    Precio Estudiante (3 meses): $" + calcHeredada.visaEstudiante(MESES_PRUEBA));
        System.out.println("    Precio Trabajo (3 meses):    $" + calcHeredada.visaTrabajo(MESES_PRUEBA));

        System.out.println("\n>>> [Versión Rescatada - Patrón Strategy + DRY]:");
        System.out.println("    Lugares editados para cambiar tarifa base: " + configStrategy.getLugaresEditados());
        System.out.println("    Precio Turista (3 meses):    $" + calcRescatada.calcularPrecio(MESES_PRUEBA, estTurista));
        System.out.println("    Precio Estudiante (3 meses): $" + calcRescatada.calcularPrecio(MESES_PRUEBA, estEstudiante));
        System.out.println("    Precio Trabajo (3 meses):    $" + calcRescatada.calcularPrecio(MESES_PRUEBA, estTrabajo));
        System.out.println();
    }

    private static void ejecutarCaso3MagicNumbers() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("CASO 3: MAGIC NUMBERS (Evaluar tipo 2 / edad 20 y probar tipo inválido 9)");
        System.out.println("--------------------------------------------------------------------------------");

        EvaluadorMagico evaluadorHeredado = new EvaluadorMagico();
        EvaluadorVisa evaluadorRescatado = new EvaluadorVisa();

        System.out.println(">>> [Versión Heredada - EvaluadorMagico]:");
        System.out.println("    Evaluar(tipo=2, edad=20): " + evaluadorHeredado.evaluar(2, EDAD_PRUEBA_ESTUDIANTE));
        System.out.println("    Evaluar(tipo=9, edad=20): " + evaluadorHeredado.evaluar(CODIGO_TIPO_INVALIDO, EDAD_PRUEBA_ESTUDIANTE)
                + " -> (Rechazo silencioso: no distingue entre edad no apta y tipo inexistente)");

        System.out.println("\n>>> [Versión Rescatada - TipoVisa Enum + Constantes con nombre]:");
        System.out.println("    Evaluar(TipoVisa.ESTUDIANTE, edad=20): "
                + evaluadorRescatado.evaluar(TipoVisa.ESTUDIANTE, EDAD_PRUEBA_ESTUDIANTE));
        System.out.println("    EvaluarPorCodigoSeguro(codigo=9, edad=20): "
                + evaluadorRescatado.evaluarPorCodigoSeguro(CODIGO_TIPO_INVALIDO, EDAD_PRUEBA_ESTUDIANTE));
        System.out.println();
    }

    private static void ejecutarCaso4AcoplamientoFuerte() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("CASO 4: ACOPLAMIENTO FUERTE (SolicitudAcoplada vs. Patrón Observer)");
        System.out.println("--------------------------------------------------------------------------------");

        System.out.println(">>> [Versión Heredada - SolicitudAcoplada]:");
        SolicitudAcoplada solicitudHeredada = new SolicitudAcoplada();
        solicitudHeredada.cancelar();
        System.out.println("    [ALERTA EVIDENCIA]: SolicitudAcoplada olvidó avisar a PagosServicio. ¡Se perdió el reembolso!");

        System.out.println("\n>>> [Versión Rescatada - Patrón Observer]:");
        SolicitudVisa solicitudRescatada = new SolicitudVisa("Carlos Mendoza", 95.0);
        solicitudRescatada.suscribir(new ObservadorCorreo());
        solicitudRescatada.suscribir(new ObservadorCitas());
        solicitudRescatada.suscribir(new ObservadorPagos());
        solicitudRescatada.cancelar();
        System.out.println();
    }

    private static void ejecutarCaso5IntegracionCompleta() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("CASO 5: INTEGRACIÓN COMPLETA (Viaje de extremo a extremo con la versión nueva)");
        System.out.println("--------------------------------------------------------------------------------");

        String viajero = "Mateo Ríos";
        String pasaporte = "COL123456";
        int edad = 24;
        int mesesEstadia = 4;
        TipoVisa tipoSolicitado = TipoVisa.TRABAJO;

        System.out.println("1. Solicitud recibida para " + viajero + " | Visa: " + tipoSolicitado + " | Edad: " + edad);

        EvaluadorVisa evaluador = new EvaluadorVisa();
        EstadoEvaluacion resultadoEval = evaluador.evaluar(tipoSolicitado, edad);
        System.out.println("2. Resultado de Evaluación: " + resultadoEval);

        if (resultadoEval == EstadoEvaluacion.APROBADA) {
            ConfiguracionTarifaria config = new ConfiguracionTarifaria();
            CalculadoraTarifasStrategy calculadora = new CalculadoraTarifasStrategy(config);
            double tarifaTotal = calculadora.calcularPrecio(mesesEstadia, new EstrategiaVisaTrabajo());
            System.out.println("3. Tarifa calculada con Strategy (" + mesesEstadia + " meses): $" + tarifaTotal);

            ProcesadorTramiteVisa procesador = new ProcesadorTramiteVisa(
                    new ValidadorPasaporte(),
                    new ServicioCobro(),
                    new RepositorioExpedientes(),
                    new ServicioCorreo()
            );
            boolean tramiteExitoso = procesador.procesarTramite(viajero, pasaporte, tarifaTotal);

            if (tramiteExitoso) {
                System.out.println("4. El viajero decide cancelar su solicitud antes del viaje:");
                SolicitudVisa solicitudActiva = new SolicitudVisa(viajero, tarifaTotal);
                solicitudActiva.suscribir(new ObservadorCorreo());
                solicitudActiva.suscribir(new ObservadorCitas());
                solicitudActiva.suscribir(new ObservadorPagos());
                solicitudActiva.cancelar();
            }
        }

        System.out.println("\n================================================================================");
        System.out.println("                    TABLA FINAL DE ANTIPATRONES CAPTURADOS                      ");
        System.out.println("================================================================================");
        System.out.printf("%-24s | %-30s | %-20s%n", "ANTIPATRÓN", "SÍNTOMA DETECTADO", "SOLUCIÓN APLICADA");
        System.out.println("-------------------------+--------------------------------+---------------------");
        System.out.printf("%-24s | %-30s | %-20s%n", "1. God Object", "OficinaDeTodo hace 4 tareas", "SRP (4 clases)");
        System.out.printf("%-24s | %-30s | %-20s%n", "2. Copy-Paste Prog.", "Fórmula repetida en 3 métodos", "Patrón Strategy+DRY");
        System.out.printf("%-24s | %-30s | %-20s%n", "3. Magic Numbers", "Tipos 1,2,3 y edades sin nombre", "Enum TipoVisa+Const");
        System.out.printf("%-24s | %-30s | %-20s%n", "4. Acoplamiento Fuerte", "Llamadas manuales, olvida pago", "Patrón Observer");
        System.out.println("================================================================================");
    }
}