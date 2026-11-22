package incendios.modelo;

public class Brigadista {
    public static final double LIMITE_H24 = 12;
    public static final double ADVERTENCIA_H24 = 8;
    public static final double LIMITE_HCONT = 4;

    private int idBrigadista;
    private String identificacion;
    private String nombre;
    private String especialidad;
    private String estado;
    private double horasServicio24;
    private double horasContinuas;
    private Brigada brigadaAsignada;

    public Brigadista(int idBrigadista, String identificacion, String nombre, String especialidad,
                      String estado, double horasServicio24, double horasContinuas) {
        this.idBrigadista = idBrigadista;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.estado = estado;
        this.horasServicio24 = horasServicio24;
        this.horasContinuas = horasContinuas;
    }

    public boolean estaExcluido() {
        return horasServicio24 >= LIMITE_H24
                || horasContinuas >= LIMITE_HCONT
                || estado.equals("Lesionado/No Apto");
    }

    public boolean soloLogistica() {
        boolean advertenciaHoras = horasServicio24 >= ADVERTENCIA_H24 && horasServicio24 < LIMITE_H24;
        return !estaExcluido() && (advertenciaHoras || estado.equals("Fatiga Leve"));
    }

    public boolean aptoPrimeraLinea() {
        return !estaExcluido() && !soloLogistica();
    }

    public String motivoExclusion() {
        if (horasServicio24 >= LIMITE_H24) return "H24 = " + horasServicio24 + " (limite 12 horas)";
        if (horasContinuas >= LIMITE_HCONT) return "Hcont = " + horasContinuas + " (limite 4 horas sin relevo)";
        if (estado.equals("Lesionado/No Apto")) return "Lesionado/No Apto";
        return "";
    }

    public String evaluarAsignacion() {
        if (estaExcluido()) return "EXCLUIDO: " + motivoExclusion();
        if (soloLogistica()) return "SOLO LOGISTICA / RETAGUARDIA (advertencia de fatiga)";
        return "APTO para primera linea";
    }

    public void asistirAIncendio() {
        if (brigadaAsignada != null && brigadaAsignada.getIncendio() != null) {
            System.out.println(nombre + " asiste al incendio #" + brigadaAsignada.getIncendio().getIdIncendio());
        }
    }

    public void realizarFuncion() {
        System.out.println(nombre + " realiza su funcion como " + especialidad);
    }

    public String reportarEstado() {
        return nombre + " (" + identificacion + ", " + especialidad + ") - " + estado
                + " | H24=" + horasServicio24 + " | Hcont=" + horasContinuas;
    }

    public void actualizarUbicacion(String ubicacion) {
        if (brigadaAsignada != null) brigadaAsignada.setUbicacionActual(ubicacion);
    }

    public int getIdBrigadista() { return idBrigadista; }
    public void setIdBrigadista(int idBrigadista) { this.idBrigadista = idBrigadista; }
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public double getHorasServicio24() { return horasServicio24; }
    public void setHorasServicio24(double horasServicio24) { this.horasServicio24 = horasServicio24; }
    public double getHorasContinuas() { return horasContinuas; }
    public void setHorasContinuas(double horasContinuas) { this.horasContinuas = horasContinuas; }
    public Brigada getBrigadaAsignada() { return brigadaAsignada; }
    public void setBrigadaAsignada(Brigada brigadaAsignada) { this.brigadaAsignada = brigadaAsignada; }
}
