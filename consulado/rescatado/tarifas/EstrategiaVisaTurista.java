/package consulado.rescatado.tarifas;

public class EstrategiaVisaTurista implements EstrategiaTarifa {
    private static final double FACTOR_ESTANDAR_TURISTA = 1.0;
    private static final String MODALIDAD_TURISTA = "TURISTA";

    @Override
    public String getNombreModalidad() {
        return MODALIDAD_TURISTA;
    }

    @Override
    public double obtenerFactorMultiplicador() {
        return FACTOR_ESTANDAR_TURISTA;
    }
}