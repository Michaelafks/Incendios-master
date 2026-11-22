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
}