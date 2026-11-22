package incendios.modelo;

public class RecursoPesado extends Recurso {
    private String modeloVehiculo;
    private float capacidadCarga;
    private String tipoVehiculo;

    public RecursoPesado(int idRecurso, String estado, String ubicacion, int cantidadDisponible,
                         String modeloVehiculo, float capacidadCarga, String tipoVehiculo) {
        super(idRecurso, "Pesado", estado, ubicacion, cantidadDisponible);
        this.modeloVehiculo = modeloVehiculo;
        this.capacidadCarga = capacidadCarga;
        this.tipoVehiculo = tipoVehiculo;
    }

    @Override
    public boolean esCompatible(Terreno terreno) {
        return terreno.permiteRecursosPesados();
    }

    public void operarRecursoPesado() { utilizarRecurso(); }

    public String transportarCarga() {
        return tipoVehiculo + " " + modeloVehiculo + " transporta hasta " + capacidadCarga + " L";
    }

    public String getModeloVehiculo() { return modeloVehiculo; }
    public void setModeloVehiculo(String modeloVehiculo) { this.modeloVehiculo = modeloVehiculo; }
    public float getCapacidadCarga() { return capacidadCarga; }
    public void setCapacidadCarga(float capacidadCarga) { this.capacidadCarga = capacidadCarga; }
    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }
}
