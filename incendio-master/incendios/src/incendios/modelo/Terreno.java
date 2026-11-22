package incendios.modelo;

public class Terreno {
    public static final String[] ACCESIBILIDAD = {"Acceso Vehicular Pesado", "Trocha/4x4", "Solo Acceso Peatonal"};
    public static final String[] SUPERFICIES = {"Firme", "Rocoso/Irregular", "Humedo/Inestable"};

    private double pendiente;
    private String accesibilidad;
    private String superficie;

    public Terreno(double pendiente, String accesibilidad, String superficie) {
        this.pendiente = pendiente;
        this.accesibilidad = accesibilidad;
        this.superficie = superficie;
    }

    public boolean permiteRecursosPesados() {
        return accesibilidad.equals(ACCESIBILIDAD[0]) && pendiente <= 15 && superficie.equals(SUPERFICIES[0]);
    }

    public boolean manualesPrioritarios() {
        return pendiente > 30 || accesibilidad.equals(ACCESIBILIDAD[2]);
    }

    public double getPendiente() { return pendiente; }
    public void setPendiente(double pendiente) { this.pendiente = pendiente; }
    public String getAccesibilidad() { return accesibilidad; }
    public void setAccesibilidad(String accesibilidad) { this.accesibilidad = accesibilidad; }
    public String getSuperficie() { return superficie; }
    public void setSuperficie(String superficie) { this.superficie = superficie; }

    @Override
    public String toString() {
        return "Terreno{pendiente=" + pendiente + "%, acceso=" + accesibilidad + ", superficie=" + superficie + "}";
    }
}
