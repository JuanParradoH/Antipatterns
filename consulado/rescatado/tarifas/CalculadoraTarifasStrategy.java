package consulado.rescatado.tarifas;

public class CalculadoraTarifasStrategy {
    private final ConfiguracionTarifaria configuracion;

    public CalculadoraTarifasStrategy(ConfiguracionTarifaria configuracion) {
        this.configuracion = configuracion;
    }

    
    public double calcularPrecio(int meses, EstrategiaTarifa estrategia) {
        double subtotal = (configuracion.getTarifaBaseMensual() * meses) + configuracion.getRecargoFijo();
        return subtotal * estrategia.obtenerFactorMultiplicador();
    }
}