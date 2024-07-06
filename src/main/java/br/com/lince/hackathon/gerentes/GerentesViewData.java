package br.com.lince.hackathon.gerentes;

import java.time.LocalDateTime;
import java.util.List;

public class GerentesViewData {
    List <Gerentes> gerentes;
    int page;
    int pageSize;
    int count;
    GerenteFiltro gerenteFiltro;

    public GerentesViewData(List<Gerentes> gerentes, int page, int pageSize, int count) {
        this.gerentes = gerentes;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
    }

    public GerentesViewData(List<Gerentes> gerentes, int page, int pageSize, int count, GerenteFiltro gerenteFiltro) {
        this.gerentes = gerentes;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.gerenteFiltro = gerenteFiltro;
    }

    public List<Gerentes> getGerentes() {
        return gerentes;
    }

    public int getPageSize() {
        return pageSize;
    }

    public GerentesViewData() {
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

    public GerenteFiltro getGerenteFiltro() {
        return gerenteFiltro;
    }

    public void setGerenteFiltro(GerenteFiltro gerenteFiltro) {
        this.gerenteFiltro = gerenteFiltro;
    }
}
