package consulado.heredado;

public class CalculadoraDeTarifas {
    private double tarifaBaseTurista = 50.0;
    private double tarifaBaseEstudiante = 50.0;
    private double tarifaBaseTrabajo = 50.0;
    private int lugaresEditados = 0;

    public double visaTurista(int meses) {
        double total = tarifaBaseTurista * meses;
        total = total + 10;
        return total;
    }

    public double visaEstudiante(int meses) {
        double total = tarifaBaseEstudiante * meses;
        total = total + 10;
        return total * 0.5;
    }

    public double visaTrabajo(int meses) {
        double total = tarifaBaseTrabajo * meses;
        total = total + 10;
        return total * 1.5;
    }

    public void actualizarTarifaBaseEnClones(double nuevaTarifaTurista, double nuevaTarifaEstudiante, double nuevaTarifaTrabajo) {
        this.tarifaBaseTurista = nuevaTarifaTurista;
        this.lugaresEditados++;
        this.tarifaBaseEstudiante = nuevaTarifaEstudiante;
        this.lugaresEditados++;
        this.tarifaBaseTrabajo = nuevaTarifaTrabajo;
        this.lugaresEditados++;
    }

    public int getLugaresEditados() {
        return lugaresEditados;
    }
}