package incendios.modelo;

import java.util.ArrayList;
import java.util.List;

public class SectorPoblado {
    private String nombreSector;
    private int poblacion;
    private int personasVulnerables;
    private double distanciaKm;
    private double direccionGrados;
    private String ubicacion;
    private String estadoEvacuacion;

    private List<Incendio> incendios = new ArrayList<>();

    public SectorPoblado(String nombreSector, int poblacion, int personasVulnerables, double distanciaKm,
                         double direccionGrados, String ubicacion, String estadoEvacuacion) {
        this.nombreSector = nombreSector;
        this.poblacion = poblacion;
        this.personasVulnerables = personasVulnerables;
        this.distanciaKm = distanciaKm;
        this.direccionGrados = direccionGrados;
        this.ubicacion = ubicacion;
        this.estadoEvacuacion = estadoEvacuacion;
    }

    public double calcularPorcentajeVulnerables() {
        if (poblacion == 0) return 0;
        return personasVulnerables * 100.0 / poblacion;
    }

    public String calcularVulnerabilidad() {
        double p = calcularPorcentajeVulnerables();
        if (p >= 30) return "Alta";
        if (p >= 10) return "Media";
        return "Baja";
    }

    public double factorVulnerabilidad() {
        String v = calcularVulnerabilidad();
        if (v.equals("Alta")) return 1.0;
        if (v.equals("Media")) return 0.6;
        return 0.3;
    }

    public void agregarIncendio(Incendio incendio) {
        if (incendio != null && !incendios.contains(incendio)) {
            incendios.add(incendio);
            incendio.agregarSectorPoblado(this);
        }
    }

    public int contarIncendios() { return incendios.size(); }

    public String monitorearAmenaza() {
        return nombreSector + " amenazado por " + incendios.size() + " incendio(s)";
    }

    public String alertarPoblacion() {
        return "ALERTA en " + nombreSector + ": " + poblacion + " personas";
    }

    public void evacuarSector() { this.estadoEvacuacion = "Evacuado"; }

    public String reportarSituacion() {
        return nombreSector + " | poblacion: " + poblacion + " | vulnerables: " + personasVulnerables
                + " (" + calcularVulnerabilidad() + ") | " + distanciaKm + " km a " + direccionGrados
                + " grados | evacuacion: " + estadoEvacuacion;
    }

    public String getNombreSector() { return nombreSector; }
    public void setNombreSector(String nombreSector) { this.nombreSector = nombreSector; }
    public int getPoblacion() { return poblacion; }
    public void setPoblacion(int poblacion) { this.poblacion = poblacion; }
    public int getPersonasVulnerables() { return personasVulnerables; }
    public void setPersonasVulnerables(int personasVulnerables) { this.personasVulnerables = personasVulnerables; }
    public double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(double distanciaKm) { this.distanciaKm = distanciaKm; }
    public double getDireccionGrados() { return direccionGrados; }
    public void setDireccionGrados(double direccionGrados) { this.direccionGrados = direccionGrados; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getEstadoEvacuacion() { return estadoEvacuacion; }
    public void setEstadoEvacuacion(String estadoEvacuacion) { this.estadoEvacuacion = estadoEvacuacion; }
    public List<Incendio> getIncendios() { return incendios; }

}
