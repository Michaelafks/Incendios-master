package incendios.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Incendio {
    public static final String[] TIPOS_VEGETACION = {
            "Paramo / matorral seco", "Bosque de pino o similar", "Pastizal", "Bosque humedo"};
    private static final double[] FACTORES_VEGETACION = {1.5, 1.3, 1.1, 0.7};
    private static final double ANGULO_RIESGO = 45;

    private int idIncendio;
    private String ubicacion;
    private String estado;
    private String tipoVegetacion;
    private double temperatura;
    private double viento;
    private double direccionFuego;
    private double severidad;
    private LocalDate fechaInicio;
    private Terreno terreno;

    private List<Brigada> brigadas = new ArrayList<>();
    private List<SectorPoblado> sectoresAfectados = new ArrayList<>();
    private List<incendios.modelo.AsignacionRecurso> asignaciones = new ArrayList<>();

    public Incendio(int idIncendio, String ubicacion, String estado, String tipoVegetacion,
                    double temperatura, double viento, double direccionFuego, LocalDate fechaInicio) {
        this.idIncendio = idIncendio;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.tipoVegetacion = tipoVegetacion;
        this.temperatura = temperatura;
        this.viento = viento;
        this.direccionFuego = direccionFuego;
        this.fechaInicio = fechaInicio;
        this.severidad = calcularIEP();
    }

    public double factorVegetacion() {
        for (int i = 0; i < TIPOS_VEGETACION.length; i++) {
            if (TIPOS_VEGETACION[i].equals(tipoVegetacion)) return FACTORES_VEGETACION[i];
        }
        return 1.0;
    }

    public double calcularIEP() {
        return factorVegetacion() * (temperatura / 20 + viento / 15);
    }

    public String evaluarSeveridad() {
        if (severidad < 1.8) return "Bajo";
        if (severidad <= 3.2) return "Medio";
        return "Critico";
    }

    public String reportarIncendio() {
        return "Incendio #" + idIncendio + " en " + ubicacion + " | estado: " + estado
                + " | " + tipoVegetacion + ", " + temperatura + " C, viento " + viento + " km/h"
                + " | IEP: " + String.format("%.2f", severidad) + " (" + evaluarSeveridad() + ")"
                + " | inicio: " + fechaInicio;
    }

    public void actualizarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public String monitorearProgreso() {
        return "Incendio #" + idIncendio + " [" + estado + "] brigadas=" + contarBrigadas()
                + ", sectores=" + contarSectores() + ", evacuados=" + contarSectoresEvacuados()
                + ", recursos=" + contarRecursos();
    }

    public void agregarBrigada(Brigada brigada) {
        if (brigada != null && !brigadas.contains(brigada)) {
            brigadas.add(brigada);
            brigada.setIncendio(this);
        }
    }

    public void quitarBrigada(Brigada brigada) {
        if (brigadas.remove(brigada)) {
            brigada.setIncendio(null);
        }
    }

    public Brigada buscarBrigadaPorId(int idBrigada) {
        return brigadas.stream().filter(b -> b.getIdBrigada() == idBrigada).findFirst().orElse(null);
    }

    public List<Brigada> buscarBrigadasPorEstado(String estadoBrigada) {
        return brigadas.stream()
                .filter(b -> b.getEstado().equalsIgnoreCase(estadoBrigada))
                .collect(Collectors.toList());
    }

    public List<Brigada> buscarBrigadasPorCapacidadMinima(int capacidadMin) {
        return brigadas.stream()
                .filter(b -> b.getCapacidadTotal() >= capacidadMin)
                .collect(Collectors.toList());
    }

    public int contarBrigadas() {
        return brigadas.size();
    }

    public Brigada obtenerBrigadaMayorCapacidad() {
        return brigadas.stream().max(Comparator.comparingInt(Brigada::getCapacidadTotal)).orElse(null);
    }

    public List<Brigadista> listarBrigadistas() {
        List<Brigadista> todos = new ArrayList<>();
        for (Brigada b : brigadas) {
            todos.addAll(b.getBrigadistas());
        }
        return todos;
    }

    public int contarBrigadistas() {
        return listarBrigadistas().size();
    }

    public void agregarSectorPoblado(SectorPoblado sector) {
        if (sector != null && !sectoresAfectados.contains(sector)) {
            sectoresAfectados.add(sector);
            sector.agregarIncendio(this);
        }
    }

    public SectorPoblado buscarSectorPorNombre(String nombre) {
        return sectoresAfectados.stream()
                .filter(s -> s.getNombreSector().equalsIgnoreCase(nombre))
                .findFirst().orElse(null);
    }

    public int contarSectores() {
        return sectoresAfectados.size();
    }

    public int contarSectoresEvacuados() {
        return (int) sectoresAfectados.stream()
                .filter(s -> s.getEstadoEvacuacion().equalsIgnoreCase("Evacuado"))
                .count();
    }

    public int calcularPoblacionTotal() {
        return sectoresAfectados.stream().mapToInt(SectorPoblado::getPoblacion).sum();
    }

    public int calcularPersonasAfectadas() {
        return sectoresAfectados.stream()
                .filter(s -> !s.getEstadoEvacuacion().equalsIgnoreCase("Evacuado"))
                .mapToInt(SectorPoblado::getPoblacion).sum();
    }

    public int calcularPersonasAfectadas(SectorPoblado sector) {
        return sectoresAfectados.contains(sector) ? sector.getPoblacion() : 0;
    }

    public double radioBase() {
        String clasificacion = evaluarSeveridad();
        if (clasificacion.equals("Bajo")) return 0.5;
        if (clasificacion.equals("Medio")) return 1.5;
        return 3.0;
    }

    public double calcularRadioSeguridad() {
        return radioBase() + 0.05 * viento;
    }

    private double diferenciaAngular(double a, double b) {
        double d = Math.abs(a - b) % 360;
        return d > 180 ? 360 - d : d;
    }

    public boolean sectorEnRiesgo(SectorPoblado s) {
        return s.getDistanciaKm() <= calcularRadioSeguridad()
                && diferenciaAngular(direccionFuego, s.getDireccionGrados()) <= ANGULO_RIESGO;
    }

    public double calcularPrioridadEvacuacion(SectorPoblado s) {
        return (calcularRadioSeguridad() / s.getDistanciaKm()) * 50
                + s.factorVulnerabilidad() * 30
                + (s.getPoblacion() / 1000.0) * 20;
    }

    public String clasificarPrioridad(double pevac) {
        if (pevac >= 80) return "Critica";
        if (pevac >= 55) return "Alta";
        if (pevac >= 30) return "Media";
        return "Baja";
    }

    public List<SectorPoblado> listarSectoresEnRiesgo() {
        return sectoresAfectados.stream()
                .filter(this::sectorEnRiesgo)
                .sorted(Comparator.comparingDouble(this::calcularPrioridadEvacuacion).reversed())
                .collect(Collectors.toList());
    }

    public List<SectorPoblado> listarSectoresFueraDeRiesgo() {
        return sectoresAfectados.stream()
                .filter(s -> !sectorEnRiesgo(s))
                .collect(Collectors.toList());
    }

    public String generarReporteEvacuacion() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== PERIMETRO DE SEGURIDAD - INCENDIO #").append(idIncendio).append(" =====\n");
        sb.append("Clasificacion: ").append(evaluarSeveridad())
                .append(" | Rbase: ").append(radioBase()).append(" km")
                .append(" | Rseg: ").append(String.format("%.2f", calcularRadioSeguridad())).append(" km\n");
        sb.append("Direccion del fuego: ").append(direccionFuego).append(" grados\n");
        sb.append("--- Sectores en riesgo (por prioridad) ---\n");
        List<SectorPoblado> enRiesgo = listarSectoresEnRiesgo();
        if (enRiesgo.isEmpty()) sb.append("Ninguno\n");
        for (SectorPoblado s : enRiesgo) {
            double p = calcularPrioridadEvacuacion(s);
            sb.append(clasificarPrioridad(p)).append(" (Pevac=").append(String.format("%.1f", p)).append(") - ")
                    .append(s.getNombreSector()).append(" | ").append(s.getPoblacion()).append(" hab. | vulnerabilidad ")
                    .append(s.calcularVulnerabilidad()).append("\n");
        }
        sb.append("--- Sectores fuera de riesgo ---\n");
        List<SectorPoblado> fuera = listarSectoresFueraDeRiesgo();
        if (fuera.isEmpty()) sb.append("Ninguno\n");
        for (SectorPoblado s : fuera) sb.append(s.getNombreSector()).append("\n");
        return sb.toString();
    }

    public incendios.modelo.AsignacionRecurso asignarRecurso(Recurso recurso, LocalDate fechaInicio) {
        incendios.modelo.AsignacionRecurso asignacion = new incendios.modelo.AsignacionRecurso(this, recurso, fechaInicio);
        asignaciones.add(asignacion);
        recurso.agregarAsignacion(asignacion);
        return asignacion;
    }

    public List<Recurso> listarRecursos() {
        return asignaciones.stream().map(incendios.modelo.AsignacionRecurso::getRecurso).distinct().collect(Collectors.toList());
    }

    public int contarRecursos() {
        return listarRecursos().size();
    }

    public int contarRecursosPorTipo(String tipoRecurso) {
        return (int) listarRecursos().stream()
                .filter(r -> r.getTipoRecurso().equalsIgnoreCase(tipoRecurso)).count();
    }

    public int contarVehiculos() {
        return (int) listarRecursos().stream().filter(r -> r instanceof RecursoPesado).count();
    }

    public List<Recurso> listarRecursosCompatibles() {
        if (terreno == null) return new ArrayList<>();
        return listarRecursos().stream().filter(r -> r.esCompatible(terreno)).collect(Collectors.toList());
    }

    public List<Recurso> listarRecursosNoCompatibles() {
        if (terreno == null) return new ArrayList<>();
        return listarRecursos().stream().filter(r -> !r.esCompatible(terreno)).collect(Collectors.toList());
    }

    public String verificarRecursosPorTerreno() {
        if (terreno == null) return "El incendio no tiene terreno registrado";
        StringBuilder sb = new StringBuilder(terreno.toString()).append("\n");
        sb.append("Recursos pesados: ").append(terreno.permiteRecursosPesados() ? "HABILITADOS" : "NO habilitados").append("\n");
        sb.append("Recursos manuales: habilitados")
                .append(terreno.manualesPrioritarios() ? " (uso exclusivo y prioritario)" : "").append("\n");
        sb.append("--- Compatibles ---\n");
        List<Recurso> ok = listarRecursosCompatibles();
        if (ok.isEmpty()) sb.append("Ninguno\n");
        for (Recurso r : ok) sb.append(r.reportarDisponibilidad()).append("\n");
        sb.append("--- No compatibles ---\n");
        List<Recurso> no = listarRecursosNoCompatibles();
        if (no.isEmpty()) sb.append("Ninguno\n");
        for (Recurso r : no) sb.append(r.reportarDisponibilidad()).append("\n");
        return sb.toString();
    }

