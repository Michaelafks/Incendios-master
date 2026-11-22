package incendios.modelo;

import java.time.LocalDate;

public class AsignacionRecurso {
    private Incendio incendio;
    private Recurso recurso;
    private LocalDate fechaInicio;

    public AsignacionRecurso(Incendio incendio, Recurso recurso, LocalDate fechaInicio) {
        this.incendio = incendio;
        this.recurso = recurso;
        this.fechaInicio = fechaInicio;
    }

    public Incendio getIncendio() { return incendio; }
    public void setIncendio(Incendio incendio) { this.incendio = incendio; }
    public Recurso getRecurso() { return recurso; }
    public void setRecurso(Recurso recurso) { this.recurso = recurso; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
}
