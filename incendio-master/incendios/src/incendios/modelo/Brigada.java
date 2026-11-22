package incendios.modelo;

import java.util.ArrayList;
import java.util.List;

public class Brigada {
    private int idBrigada;
    private String nombreBrigada;
    private String ubicacionActual;
    private String estado;
    private int capacidadTotal;

    private Incendio incendio;
    private List<Brigadista> brigadistas = new ArrayList<>();
    private List<Recurso> recursos = new ArrayList<>();

    public Brigada(int idBrigada, String nombreBrigada, String ubicacionActual, String estado, int capacidadTotal) {
        this.idBrigada = idBrigada;
        this.nombreBrigada = nombreBrigada;
        this.ubicacionActual = ubicacionActual;
        this.estado = estado;
        this.capacidadTotal = capacidadTotal;
    }

    public void movilizar() { this.estado = "Movilizada"; }
    public void desmovilizar() { this.estado = "Disponible"; }

    public void asignarIncendio(Incendio incendio) {
        incendio.agregarBrigada(this);
    }

    public String informarEstado() {
        return "Brigada " + nombreBrigada + " [" + estado + "] en " + ubicacionActual
                + " | brigadistas: " + brigadistas.size();
    }

    public String reportarRecursos() {
        StringBuilder sb = new StringBuilder("Recursos de " + nombreBrigada + ":");
        for (Recurso r : recursos) sb.append("\n  - ").append(r.reportarDisponibilidad());
        return sb.toString();
    }

    public void agregarBrigadista(Brigadista b) {
        if (b != null && !brigadistas.contains(b)) {
            brigadistas.add(b);
            b.setBrigadaAsignada(this);
        }
    }

    public int contarBrigadistas() { return brigadistas.size(); }

    public List<Brigadista> listarAptosPrimeraLinea() {
        List<Brigadista> lista = new ArrayList<>();
        for (Brigadista b : brigadistas) if (b.aptoPrimeraLinea()) lista.add(b);
        return lista;
    }

    public List<Brigadista> listarSoloLogistica() {
        List<Brigadista> lista = new ArrayList<>();
        for (Brigadista b : brigadistas) if (b.soloLogistica()) lista.add(b);
        return lista;
    }

    public List<Brigadista> listarExcluidos() {
        List<Brigadista> lista = new ArrayList<>();
        for (Brigadista b : brigadistas) if (b.estaExcluido()) lista.add(b);
        return lista;
    }

    public String generarAsignacionBrigadistas() {
        StringBuilder sb = new StringBuilder("Asignacion de la brigada " + nombreBrigada + ":\n");
        sb.append("--- Primera linea de fuego ---\n");
        for (Brigadista b : listarAptosPrimeraLinea()) sb.append(b.reportarEstado()).append("\n");
        sb.append("--- Solo logistica / retaguardia ---\n");
        for (Brigadista b : listarSoloLogistica()) sb.append(b.reportarEstado()).append("\n");
        sb.append("--- Excluidos ---\n");
        for (Brigadista b : listarExcluidos()) sb.append(b.reportarEstado()).append(" -> ").append(b.motivoExclusion()).append("\n");
        return sb.toString();
    }

    public void agregarRecurso(Recurso r) {
        if (r != null && !recursos.contains(r)) {
            recursos.add(r);
            r.setBrigada(this);
        }
    }

    public int getIdBrigada() { return idBrigada; }
    public void setIdBrigada(int idBrigada) { this.idBrigada = idBrigada; }
    public String getNombreBrigada() { return nombreBrigada; }
    public void setNombreBrigada(String nombreBrigada) { this.nombreBrigada = nombreBrigada; }
    public String getUbicacionActual() { return ubicacionActual; }
    public void setUbicacionActual(String ubicacionActual) { this.ubicacionActual = ubicacionActual; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getCapacidadTotal() { return capacidadTotal; }
    public void setCapacidadTotal(int capacidadTotal) { this.capacidadTotal = capacidadTotal; }
    public Incendio getIncendio() { return incendio; }
    public void setIncendio(Incendio incendio) { this.incendio = incendio; }
    public List<Brigadista> getBrigadistas() { return brigadistas; }
    public List<Recurso> getRecursos() { return recursos; }
}
