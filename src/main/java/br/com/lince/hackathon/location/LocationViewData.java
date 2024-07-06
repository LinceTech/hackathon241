package br.com.lince.hackathon.location;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * Classe utilizada para agregar todos os dados necessários para alimentar o template fooView.hbs
 */
public class LocationViewData {
    /**
     * Construtor utilizado para renderizar a página com a lista de locations.
     *
     * @param locations     lista de locations
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de locations
     */
    public LocationViewData(
            List<Location> locations,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.locations = locations;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = null;
        this.location = null;
    }

    /**
     * Construtor utilizado para renderizar a página com a lista de locations.
     *
     * @param errors   mapa contendo os errors que ocorreram no formulário
     * @param location      item a ser alimentado no formulário de cadastro/edição
     * @param locations     lista de locations
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de locations
     */
    public LocationViewData(
            HashMap<String, String> errors,
            Location location, List<Location> locations,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.locations = locations;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = errors;
        this.location = location;
    }

    private final Location location;
    private final List<Location> locations;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public Location getLocation() {
        return location;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getIndex() {
        return page;
    }

    public int getPage() {
        return page + 1;
    }

    public int getCount() {
        return count;
    }

    public int getTotalPages() {
        return Double.valueOf(Math.ceil(((Integer.valueOf(count).doubleValue()) / pageSize))).intValue();
    }

    public boolean getHasPrevious() {
        return page > 0;
    }

    public int getPrevious() {
        return page - 1;
    }

    public int getNext() {
        return getPage() < getTotalPages() ? page + 1 : 0;
    }
}
