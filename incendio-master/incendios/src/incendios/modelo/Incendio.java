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