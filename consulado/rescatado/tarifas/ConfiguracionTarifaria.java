//package consulado.rescatado.tarifas;

public class ConfiguracionTarifaria {
    public static final double TARIFA_BASE_MENSUAL_INICIAL = 50.0;
    public static final double RECARGO_FIJO_ADMINISTRATIVO = 10.0;

    private double tarifaBaseMensual;
    private final double recargoFijo;
    private int lugaresEditados;

    public ConfiguracionTarifaria() {
        this.tarifaBaseMensual = TARIFA_BASE_MENSUAL_INICIAL;
        this.recargoFijo = RECARGO_FIJO_ADMINISTRATIVO;
        this.lugaresEditados = 0;
    }

    public void actualizarTarifaBaseMensual(double nuevaTarifaBase) {
        this.tarifaBaseMensual = nuevaTarifaBase;
        this.lugaresEditados++;
    }

    public double getTarifaBaseMensual() {
        return tarifaBaseMensual;
    }

    public double getRecargoFijo() {
        return recargoFijo;
    }

    public int getLugaresEditados() {
        return lugaresEditados;
    }
}