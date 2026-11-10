package consulado.rescatado.ventanilla;

public class ServicioCobro {
    public void cobrar(String nombreViajero, double monto) {
        System.out.println("[ServicioCobro] Cobrando $" + monto + " a " + nombreViajero);
    }
}