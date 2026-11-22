package incendios.modelo;

public class RecursoManual extends Recurso {
    private String[] herramientas;
    private int cantidadOperarios;
    private String equipoSeguridad;

    public RecursoManual(int idRecurso, String estado, String ubicacion, int cantidadDisponible,
                         String[] herramientas, int cantidadOperarios, String equipoSeguridad) {
        super(idRecurso, "Manual", estado, ubicacion, cantidadDisponible);
        this.herramientas = herramientas;
        this.cantidadOperarios = cantidadOperarios;
        this.equipoSeguridad = equipoSeguridad;
    }

    @Override
    public boolean esCompatible(Terreno terreno) {
        return true;
    }

    public String usarHerramientas() {
        return "Usando: " + String.join(", ", herramientas);
    }

    public void realizarAccionManual() { utilizarRecurso(); }

    public String[] getHerramientas() { return herramientas; }
    public void setHerramientas(String[] herramientas) { this.herramientas = herramientas; }
    public int getCantidadOperarios() { return cantidadOperarios; }
    public void setCantidadOperarios(int cantidadOperarios) { this.cantidadOperarios = cantidadOperarios; }
    public String getEquipoSeguridad() { return equipoSeguridad; }
    public void setEquipoSeguridad(String equipoSeguridad) { this.equipoSeguridad = equipoSeguridad; }
}
