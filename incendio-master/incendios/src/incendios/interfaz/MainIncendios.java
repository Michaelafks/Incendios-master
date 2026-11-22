package incendios.interfaz;

import incendios.modelo.*;
import incendios.utilitario.GestorIncendios;
import incendios.utilitario.Validacion;

import java.time.LocalDate;
import java.util.List;

public class MainIncendios {

    private static GestorIncendios gestor = new GestorIncendios();

    public static void main(String[] args) {
        int opc;
        do {
            menu();
            opc = Validacion.leerEnteroEntre("Ingrese una opcion", 0, 6);
            switch (opc) {
                case 1: menuRegistrar(); break;
                case 2: menuAsignar(); break;
                case 3: menuListar(); break;
                case 4: consultasIncendio(); break;
                case 5: menuBrigadistas(); break;
                case 6: menuGestion(); break;
                case 0: System.out.println("Saliendo del Sistema de Control de Incendios..."); break;
            }
        } while (opc != 0);
    }

    public static void menu() {
        System.out.println();
        System.out.println("********** Control de Incendios Forestales **********");
        System.out.println("1. Registrar (incendio, brigada, brigadista, sector, recurso, terreno)");
        System.out.println("2. Asignar (brigada, sector o recurso a un incendio)");
        System.out.println("3. Listar (incendios y sectores)");
        System.out.println("4. Consultas y reportes de un incendio");
        System.out.println("5. Brigadistas y fatiga");
        System.out.println("6. Gestion (estado del incendio, evacuacion, historial de recursos)");
        System.out.println("0. Salir");
    }

    public static void menuRegistrar() {
        System.out.println("---- Registrar ----");
        System.out.println("1. Incendio");
        System.out.println("2. Brigada");
        System.out.println("3. Brigadista (en una brigada)");
        System.out.println("4. Sector poblado");
        System.out.println("5. Recurso (pesado / manual)");
        System.out.println("6. Terreno de un incendio");
        System.out.println("0. Volver");
        switch (Validacion.leerEnteroEntre("Ingrese una opcion", 0, 6)) {
            case 1: registrarIncendio(); break;
            case 2: registrarBrigada(); break;
            case 3: registrarBrigadista(); break;
            case 4: registrarSector(); break;
            case 5: registrarRecurso(); break;
            case 6: registrarTerreno(); break;
        }
    }

    public static void menuAsignar() {
        System.out.println("---- Asignar a un incendio ----");
        System.out.println("1. Brigada");
        System.out.println("2. Sector poblado");
        System.out.println("3. Recurso");
        System.out.println("0. Volver");
        switch (Validacion.leerEnteroEntre("Ingrese una opcion", 0, 3)) {
            case 1: asignarBrigadaAIncendio(); break;
            case 2: asignarSectorAIncendio(); break;
            case 3: asignarRecursoAIncendio(); break;
        }
    }

    public static void menuListar() {
        System.out.println("---- Listar ----");
        System.out.println("1. Incendios");
        System.out.println("2. Sectores poblados");
        System.out.println("0. Volver");
        switch (Validacion.leerEnteroEntre("Ingrese una opcion", 0, 2)) {
            case 1: listarIncendios(); break;
            case 2: listarSectores(); break;
        }
    }

    public static void menuBrigadistas() {
        System.out.println("---- Brigadistas y fatiga ----");
        System.out.println("1. Evaluar brigadistas de una brigada");
        System.out.println("2. Actualizar horas y condicion de un brigadista");
        System.out.println("0. Volver");
        switch (Validacion.leerEnteroEntre("Ingrese una opcion", 0, 2)) {
            case 1: evaluarBrigadistas(); break;
            case 2: actualizarHorasBrigadista(); break;
        }
    }

    public static void menuGestion() {
        System.out.println("---- Gestion ----");
        System.out.println("1. Actualizar estado de un incendio");
        System.out.println("2. Evacuar sector");
        System.out.println("3. Historial de asignaciones de un recurso");
        System.out.println("0. Volver");
        switch (Validacion.leerEnteroEntre("Ingrese una opcion", 0, 3)) {
            case 1: actualizarEstadoIncendio(); break;
            case 2: evacuarSector(); break;
            case 3: historialRecurso(); break;
        }
    }

    public static void registrarIncendio() {
        System.out.println("******* Registro de incendio *******");
        int id = Validacion.leerEnteroPositivo("Ingrese el id del incendio");
        if (gestor.buscarIncendio(id) != null) {
            System.out.println("Ya existe un incendio con el id " + id);
            return;
        }
        String ubicacion = Validacion.leerString("Ingrese la ubicacion");
        String estado = Validacion.leerOpcion("Estado del incendio:", "Activo", "Controlado", "Extinguido");
        String vegetacion = Validacion.leerOpcion("Tipo de vegetacion:", Incendio.TIPOS_VEGETACION);
        double temperatura = Validacion.leerDoubleEntre("Temperatura en grados C (-10 a 50)", -10, 50);
        double viento = Validacion.leerDoubleEntre("Velocidad del viento en km/h (0 a 150)", 0, 150);
        double direccionFuego = Validacion.leerDoubleEntre("Direccion proyectada del fuego en grados (0 a 360)", 0, 360);
        LocalDate fecha = Validacion.leerFecha("Ingrese la fecha de inicio");

        Incendio inc = new Incendio(id, ubicacion, estado, vegetacion, temperatura, viento, direccionFuego, fecha);
        if (gestor.agregarIncendio(inc)) {
            System.out.println("Incendio registrado");
            System.out.println("IEP = " + String.format("%.2f", inc.getSeveridad()) + " -> " + inc.evaluarSeveridad());
        } else {
            System.out.println("No fue posible registrar el incendio");
        }
    }

    public static void registrarBrigada() {
        System.out.println("******* Registro de brigada *******");
        int id = Validacion.leerEnteroPositivo("Ingrese el id de la brigada");
        if (gestor.buscarBrigada(id) != null) {
            System.out.println("Ya existe una brigada con el id " + id);
            return;
        }
        String nombre = Validacion.leerString("Ingrese el nombre de la brigada");
        String ubicacion = Validacion.leerString("Ingrese la ubicacion actual");
        String estado = Validacion.leerOpcion("Estado de la brigada:", "Disponible", "Movilizada", "Descanso");
        int capacidad = Validacion.leerEnteroEntre("Ingrese la capacidad total (1 a 500)", 1, 500);

        if (gestor.agregarBrigada(new Brigada(id, nombre, ubicacion, estado, capacidad))) {
            System.out.println("Brigada registrada con exito");
        } else {
            System.out.println("No fue posible registrar la brigada");
        }
    }

    public static void registrarBrigadista() {
        System.out.println("******* Registro de brigadista *******");
        Brigada brigada = pedirBrigada();
        if (brigada == null) return;
        int id = Validacion.leerEnteroPositivo("Ingrese el id del brigadista");
        if (gestor.buscarBrigadista(id) != null) {
            System.out.println("Ya existe un brigadista con el id " + id);
            return;
        }
        String identificacion = Validacion.leerString("Ingrese la identificacion");
        String nombre = Validacion.leerString("Ingrese el nombre");
        String especialidad = Validacion.leerString("Ingrese la especialidad");
        double h24 = Validacion.leerDoubleEntre("Horas de servicio en las ultimas 24 horas (0 a 24)", 0, 24);
        double hCont = Validacion.leerDoubleEntre("Horas de trabajo continuo en la linea de fuego (0 a 24)", 0, 24);
        String estado = Validacion.leerOpcion("Condicion fisica:", "Apto", "Fatiga Leve", "Lesionado/No Apto");

        Brigadista b = new Brigadista(id, identificacion, nombre, especialidad, estado, h24, hCont);
        if (gestor.agregarBrigadista(b)) {
            brigada.agregarBrigadista(b);
            System.out.println("Brigadista registrado en la brigada " + brigada.getNombreBrigada());
            System.out.println("Evaluacion: " + b.evaluarAsignacion());
        } else {
            System.out.println("No fue posible registrar el brigadista");
        }
    }

    public static void registrarSector() {
        System.out.println("******* Registro de sector poblado *******");
        String nombre = Validacion.leerString("Ingrese el nombre del sector");
        if (gestor.buscarSector(nombre) != null) {
            System.out.println("Ya existe el sector " + nombre);
            return;
        }
        int poblacion = Validacion.leerEnteroPositivo("Ingrese el numero de habitantes");
        int vulnerables = Validacion.leerEnteroEntre("Ingrese el numero de personas vulnerables (0 a " + poblacion + ")", 0, poblacion);
        double distancia = Validacion.leerDoublePositivo("Ingrese la distancia al foco del incendio (km)");
        double direccion = Validacion.leerDoubleEntre("Ingrese la direccion respecto al foco (0 a 360 grados)", 0, 360);
        String ubicacion = Validacion.leerString("Ingrese la ubicacion");
        String estado = Validacion.leerOpcion("Estado de evacuacion:", "Pendiente", "En proceso", "Evacuado");

        SectorPoblado sector = new SectorPoblado(nombre, poblacion, vulnerables, distancia, direccion, ubicacion, estado);
        if (gestor.agregarSector(sector)) {
            System.out.println("Sector registrado con exito. Vulnerabilidad: " + sector.calcularVulnerabilidad()
                    + " (" + String.format("%.1f", sector.calcularPorcentajeVulnerables()) + "% vulnerables)");
        } else {
            System.out.println("No fue posible registrar el sector");
        }
    }

    public static void registrarRecurso() {
        System.out.println("******* Registro de recurso *******");
        int id = Validacion.leerEnteroPositivo("Ingrese el id del recurso");
        if (gestor.buscarRecurso(id) != null) {
            System.out.println("Ya existe un recurso con el id " + id);
            return;
        }
        String tipo = Validacion.leerOpcion("Tipo de recurso:", "Pesado (vehiculo)", "Manual");
        String estado = Validacion.leerOpcion("Estado del recurso:", "Disponible", "En uso", "Mantenimiento");
        String ubicacion = Validacion.leerString("Ingrese la ubicacion");
        int cantidad = Validacion.leerEnteroPositivo("Ingrese la cantidad disponible");

        Recurso r;
        if (tipo.startsWith("Pesado")) {
            String modelo = Validacion.leerString("Ingrese el modelo del vehiculo");
            String tipoVeh = Validacion.leerOpcion("Tipo de vehiculo:", "Autobomba", "Camion cisterna", "Otro");
            double carga = Validacion.leerDoublePositivo("Ingrese la capacidad de carga (litros)");
            r = new RecursoPesado(id, estado, ubicacion, cantidad, modelo, (float) carga, tipoVeh);
        } else {
            String lista = Validacion.leerString("Ingrese las herramientas separadas por coma");
            String[] herramientas = lista.split("\\s*,\\s*");
            int operarios = Validacion.leerEnteroEntre("Ingrese la cantidad de operarios (1 a 100)", 1, 100);
            String equipo = Validacion.leerString("Ingrese el equipo de seguridad");
            r = new RecursoManual(id, estado, ubicacion, cantidad, herramientas, operarios, equipo);
        }

        if (gestor.agregarRecurso(r)) {
            System.out.println("Recurso registrado");
            if (!gestor.getBrigadas().isEmpty()
                    && Validacion.leerOpcion("Asignarlo a una brigada?", "Si", "No").equals("Si")) {
                Brigada b = pedirBrigada();
                if (b != null) {
                    b.agregarRecurso(r);
                    System.out.println("Recurso asignado a la brigada " + b.getNombreBrigada());
                }
            }
        } else {
            System.out.println("No fue posible registrar el recurso");
        }
    }

    public static void asignarBrigadaAIncendio() {
        Incendio inc = pedirIncendio();
        if (inc == null) return;
        Brigada b = pedirBrigada();
        if (b == null) return;
        if (b.getIncendio() != null && b.getIncendio() != inc) {
            System.out.println("La brigada ya atiende el incendio #" + b.getIncendio().getIdIncendio());
            return;
        }
        inc.agregarBrigada(b);
        b.movilizar();
        System.out.println("Brigada " + b.getNombreBrigada() + " atiende ahora el incendio #" + inc.getIdIncendio());
    }

    public static void asignarSectorAIncendio() {
        Incendio inc = pedirIncendio();
        if (inc == null) return;
        SectorPoblado s = pedirSector();
        if (s == null) return;
        inc.agregarSectorPoblado(s);
        System.out.println("Sector " + s.getNombreSector() + " afectado por el incendio #" + inc.getIdIncendio());
    }

    public static void asignarRecursoAIncendio() {
        Incendio inc = pedirIncendio();
        if (inc == null) return;
        Recurso r = pedirRecurso();
        if (r == null) return;
        LocalDate fecha = Validacion.leerFecha("Ingrese la fecha de inicio de la asignacion");
        inc.asignarRecurso(r, fecha);
        r.utilizarRecurso();
        System.out.println("Recurso #" + r.getIdRecurso() + " asignado al incendio #" + inc.getIdIncendio());
    }

    public static void listarIncendios() {
        if (gestor.getIncendios().isEmpty()) {
            System.out.println("No existe ningun incendio aun");
            return;
        }
        for (Incendio i : gestor.getIncendios()) {
            System.out.println(i.reportarIncendio());
        }
    }

    public static void listarSectores() {
        if (gestor.getSectores().isEmpty()) {
            System.out.println("No existe ningun sector aun");
            return;
        }
        for (SectorPoblado s : gestor.getSectores()) {
            System.out.println(s.reportarSituacion() + " | incendios: " + s.contarIncendios());
        }
    }

    public static void consultasIncendio() {
        Incendio inc = pedirIncendio();
        if (inc == null) return;
        int opc;
        do {
            System.out.println("---- Consultas del incendio #" + inc.getIdIncendio() + " ----");
            System.out.println("1. Reporte general");
            System.out.println("2. Cantidad de brigadas y brigada de mayor capacidad");
            System.out.println("3. Buscar brigadas por estado");
            System.out.println("4. Buscar brigadas por capacidad minima");
            System.out.println("5. Listado de brigadistas");
            System.out.println("6. Sectores, evacuados y poblacion");
            System.out.println("7. Recursos y vehiculos");
            System.out.println("8. Progreso del incendio");
            System.out.println("9. Verificar recursos segun el terreno");
            System.out.println("10. Perimetro de seguridad y prioridad de evacuacion");
            System.out.println("0. Volver");
            opc = Validacion.leerEnteroEntre("Ingrese una opcion", 0, 10);
            switch (opc) {
                case 1:
                    System.out.println(inc.generarReporte());
                    break;
                case 2: {
                    System.out.println("Brigadas atendiendo: " + inc.contarBrigadas());
                    Brigada mayor = inc.obtenerBrigadaMayorCapacidad();
                    System.out.println("Mayor capacidad: " + (mayor == null ? "N/A"
                            : mayor.getNombreBrigada() + " (" + mayor.getCapacidadTotal() + ")"));
                    break;
                }
                case 3: {
                    String e = Validacion.leerOpcion("Estado:", "Disponible", "Movilizada", "Descanso");
                    imprimirBrigadas(inc.buscarBrigadasPorEstado(e));
                    break;
                }
                case 4: {
                    int min = Validacion.leerEnteroPositivo("Capacidad minima");
                    imprimirBrigadas(inc.buscarBrigadasPorCapacidadMinima(min));
                    break;
                }
                case 5: {
                    List<Brigadista> lista = inc.listarBrigadistas();
                    if (lista.isEmpty()) System.out.println("No hay brigadistas");
                    for (Brigadista b : lista) System.out.println(b.reportarEstado());
                    break;
                }
                case 6:
                    System.out.println("Sectores afectados: " + inc.contarSectores());
                    System.out.println("Sectores evacuados: " + inc.contarSectoresEvacuados());
                    System.out.println("Poblacion total: " + inc.calcularPoblacionTotal());
                    System.out.println("Personas aun afectadas: " + inc.calcularPersonasAfectadas());
                    break;
                case 7:
                    System.out.println("Recursos: " + inc.contarRecursos());
                    System.out.println("Vehiculos: " + inc.contarVehiculos());
                    for (Recurso r : inc.listarRecursos()) System.out.println("  - " + r.reportarDisponibilidad());
                    break;
                case 8:
                    System.out.println(inc.monitorearProgreso());
                    break;
                case 9:
                    System.out.println(inc.verificarRecursosPorTerreno());
                    break;
                case 10:
                    if (inc.contarSectores() == 0) {
                        System.out.println("El incendio no tiene sectores poblados asignados");
                    } else {
                        System.out.println(inc.generarReporteEvacuacion());
                    }
                    break;
            }
        } while (opc != 0);
    }

    private static void imprimirBrigadas(List<Brigada> lista) {
        if (lista.isEmpty()) {
            System.out.println("Ninguna brigada cumple el criterio");
            return;
        }
        for (Brigada b : lista) {
            System.out.println(b.informarEstado() + " | capacidad: " + b.getCapacidadTotal());
        }
    }

    public static void historialRecurso() {
        Recurso r = pedirRecurso();
        if (r == null) return;
        System.out.println(r.reportarDisponibilidad());
        System.out.println("Asignaciones: " + r.contarAsignaciones());
        if (r.contarAsignaciones() > 0) {
            AsignacionRecurso p = r.obtenerPrimeraAsignacion();
            AsignacionRecurso u = r.obtenerUltimaAsignacion();
            System.out.println("Primera: incendio #" + p.getIncendio().getIdIncendio() + " el " + p.getFechaInicio());
            System.out.println("Ultima : incendio #" + u.getIncendio().getIdIncendio() + " el " + u.getFechaInicio());
        }
    }

    public static void evacuarSector() {
        SectorPoblado s = pedirSector();
        if (s == null) return;
        s.evacuarSector();
        System.out.println(s.reportarSituacion());
    }

    public static void actualizarEstadoIncendio() {
        Incendio inc = pedirIncendio();
        if (inc == null) return;
        inc.actualizarEstado(Validacion.leerOpcion("Nuevo estado:", "Activo", "Controlado", "Extinguido"));
        System.out.println(inc.reportarIncendio());
    }

    public static void registrarTerreno() {
        Incendio inc = pedirIncendio();
        if (inc == null) return;
        double pendiente = Validacion.leerDoubleEntre("Pendiente promedio del terreno en % (0 a 100)", 0, 100);
        String acceso = Validacion.leerOpcion("Nivel de accesibilidad vial:", Terreno.ACCESIBILIDAD);
        String superficie = Validacion.leerOpcion("Tipo de superficie:", Terreno.SUPERFICIES);
        inc.setTerreno(new Terreno(pendiente, acceso, superficie));
        System.out.println("Terreno registrado: " + inc.getTerreno());
    }

    public static void evaluarBrigadistas() {
        Brigada b = pedirBrigada();
        if (b == null) return;
        if (b.contarBrigadistas() == 0) {
            System.out.println("La brigada no tiene brigadistas");
            return;
        }
        System.out.println(b.generarAsignacionBrigadistas());
    }

    public static void actualizarHorasBrigadista() {
        if (gestor.getBrigadistas().isEmpty()) {
            System.out.println("Primero registre un brigadista");
            return;
        }
        System.out.println("Brigadistas disponibles:");
        for (Brigadista x : gestor.getBrigadistas()) {
            System.out.println("  id " + x.getIdBrigadista() + " - " + x.getNombre() + " [" + x.getEstado() + "]");
        }
        Brigadista b = gestor.buscarBrigadista(Validacion.leerEntero("Ingrese el id del brigadista"));
        if (b == null) {
            System.out.println("No se encontro el brigadista");
            return;
        }
        b.setHorasServicio24(Validacion.leerDoubleEntre("Horas de servicio en las ultimas 24 horas (0 a 24)", 0, 24));
        b.setHorasContinuas(Validacion.leerDoubleEntre("Horas de trabajo continuo (0 a 24)", 0, 24));
        b.setEstado(Validacion.leerOpcion("Condicion fisica:", "Apto", "Fatiga Leve", "Lesionado/No Apto"));
        System.out.println(b.reportarEstado());
        System.out.println("Evaluacion: " + b.evaluarAsignacion());
    }

    private static Incendio pedirIncendio() {
        if (gestor.getIncendios().isEmpty()) {
            System.out.println("Primero registre un incendio");
            return null;
        }
        System.out.println("Incendios disponibles:");
        for (Incendio x : gestor.getIncendios()) {
            System.out.println("  id " + x.getIdIncendio() + " - " + x.getUbicacion() + " [" + x.getEstado() + "]");
        }
        Incendio i = gestor.buscarIncendio(Validacion.leerEntero("Ingrese el id del incendio"));
        if (i == null) System.out.println("No se encontro el incendio");
        return i;
    }

    private static Brigada pedirBrigada() {
        if (gestor.getBrigadas().isEmpty()) {
            System.out.println("Primero registre una brigada");
            return null;
        }
        System.out.println("Brigadas disponibles:");
        for (Brigada x : gestor.getBrigadas()) {
            System.out.println("  id " + x.getIdBrigada() + " - " + x.getNombreBrigada() + " [" + x.getEstado() + "]");
        }
        Brigada b = gestor.buscarBrigada(Validacion.leerEntero("Ingrese el id de la brigada"));
        if (b == null) System.out.println("No se encontro la brigada");
        return b;
    }

    private static SectorPoblado pedirSector() {
        if (gestor.getSectores().isEmpty()) {
            System.out.println("Primero registre un sector poblado");
            return null;
        }
        System.out.println("Sectores disponibles:");
        for (SectorPoblado x : gestor.getSectores()) {
            System.out.println("  " + x.getNombreSector() + " (" + x.getPoblacion() + " hab., " + x.getEstadoEvacuacion() + ")");
        }
        SectorPoblado s = gestor.buscarSector(Validacion.leerString("Ingrese el nombre del sector"));
        if (s == null) System.out.println("No se encontro el sector");
        return s;
    }

    private static Recurso pedirRecurso() {
        if (gestor.getRecursos().isEmpty()) {
            System.out.println("Primero registre un recurso");
            return null;
        }
        System.out.println("Recursos disponibles:");
        for (Recurso x : gestor.getRecursos()) {
            System.out.println("  id " + x.getIdRecurso() + " - " + x.getTipoRecurso() + " [" + x.getEstado() + "] en " + x.getUbicacion());
        }
        Recurso r = gestor.buscarRecurso(Validacion.leerEntero("Ingrese el id del recurso"));
        if (r == null) System.out.println("No se encontro el recurso");
        return r;
    }
}
