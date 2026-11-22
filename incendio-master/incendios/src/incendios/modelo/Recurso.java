package incendios.modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Recurso {
    private int idRecurso;
    private String tipoRecurso;
    private String estado;
    private String ubicacion;
    private int cantidadDisponible;

    private Brigada brigada;
    private List<AsignacionRecurso> asignaciones = new ArrayList<>();

    public Recurso(int idRecurso, String tipoRecurso, String estado, String ubicacion, int cantidadDisponible) {
        setIdRecurso(idRecurso);
        setTipoRecurso(tipoRecurso);
        setEstado(estado);
        setUbicacion(ubicacion);
        setCantidadDisponible(cantidadDisponible);
    }

    public abstract boolean esCompatible(Terreno terreno);

    public void movilizar(String nuevaUbicacion) {
        this.ubicacion = nuevaUbicacion;
        this.estado = "En uso";
    }

    public void utilizarRecurso() { this.estado = "En uso"; }
    public void mantenerRecurso() { this.estado = "Mantenimiento"; }
    public void liberarRecurso() { this.estado = "Disponible"; }

    public String reportarDisponibilidad() {
        return tipoRecurso + " #" + idRecurso + " [" + estado + "] en " + ubicacion
                + " | cantidad: " + cantidadDisponible;
    }

    public void agregarAsignacion(AsignacionRecurso a) {
        if (a != null && !asignaciones.contains(a)) asignaciones.add(a);
    }

    public int contarAsignaciones() { return asignaciones.size(); }

    public AsignacionRecurso obtenerPrimeraAsignacion() {
        return asignaciones.stream().min(Comparator.comparing(AsignacionRecurso::getFechaInicio)).orElse(null);
    }

    public AsignacionRecurso obtenerUltimaAsignacion() {
        return asignaciones.stream().max(Comparator.comparing(AsignacionRecurso::getFechaInicio)).orElse(null);
    }

    public int getIdRecurso() { return idRecurso; }
    public void setIdRecurso(int idRecurso) { this.idRecurso = idRecurso; }
    public String getTipoRecurso() { return tipoRecurso; }
    public void setTipoRecurso(String tipoRecurso) { this.tipoRecurso = tipoRecurso; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public int getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(int cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }
    public Brigada getBrigada() { return brigada; }
    public void setBrigada(Brigada brigada) { this.brigada = brigada; }
    public List<AsignacionRecurso> getAsignaciones() { return asignaciones; }
}
