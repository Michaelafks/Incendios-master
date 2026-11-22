package incendios.utilitario;

import incendios.modelo.*;

import java.util.ArrayList;
import java.util.List;

public class GestorIncendios {
    private List<Incendio> incendios = new ArrayList<>();
    private List<Brigada> brigadas = new ArrayList<>();
    private List<Brigadista> brigadistas = new ArrayList<>();
    private List<SectorPoblado> sectores = new ArrayList<>();
    private List<Recurso> recursos = new ArrayList<>();

    public boolean agregarIncendio(Incendio i) {
        if (i != null && buscarIncendio(i.getIdIncendio()) == null) {
            incendios.add(i);
            return true;
        }
        return false;
    }

    public Incendio buscarIncendio(int id) {
        for (Incendio i : incendios) if (i.getIdIncendio() == id) return i;
        return null;
    }

    public boolean agregarBrigada(Brigada b) {
        if (b != null && buscarBrigada(b.getIdBrigada()) == null) {
            brigadas.add(b);
            return true;
        }
        return false;
    }

    public Brigada buscarBrigada(int id) {
        for (Brigada b : brigadas) if (b.getIdBrigada() == id) return b;
        return null;
    }

    public boolean agregarBrigadista(Brigadista b) {
        if (b != null && buscarBrigadista(b.getIdBrigadista()) == null) {
            brigadistas.add(b);
            return true;
        }
        return false;
    }

    public Brigadista buscarBrigadista(int id) {
        for (Brigadista b : brigadistas) if (b.getIdBrigadista() == id) return b;
        return null;
    }

    public boolean agregarSector(SectorPoblado s) {
        if (s != null && buscarSector(s.getNombreSector()) == null) {
            sectores.add(s);
            return true;
        }
        return false;
    }

    public SectorPoblado buscarSector(String nombre) {
        for (SectorPoblado s : sectores) if (s.getNombreSector().equalsIgnoreCase(nombre)) return s;
        return null;
    }

    public boolean agregarRecurso(Recurso r) {
        if (r != null && buscarRecurso(r.getIdRecurso()) == null) {
            recursos.add(r);
            return true;
        }
        return false;
    }

    public Recurso buscarRecurso(int id) {
        for (Recurso r : recursos) if (r.getIdRecurso() == id) return r;
        return null;
    }

    public List<Incendio> getIncendios() {
        return incendios;
    }
    public List<Brigada> getBrigadas() {
        return brigadas;
    }
    public List<Brigadista> getBrigadistas() {
        return brigadistas;
    }
    public List<SectorPoblado> getSectores() {
        return sectores;
    }
    public List<Recurso> getRecursos() {
        return recursos;
    }
}
