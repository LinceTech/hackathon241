package br.com.lince.hackathon.vehicle;

import br.com.lince.hackathon.manager.Manager;
import br.com.lince.hackathon.util.State;
import br.com.lince.hackathon.util.VehicleType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class VehicleViewData {

    private final Vehicle vehicle;
    private final List<Vehicle> vehicles;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;
    private final List<VehicleType> vehiclesType;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getPage() {
        return page + 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCount() {
        return count;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<VehicleType> getVehiclesType() {
        return vehiclesType;
    }

    public VehicleViewData(Vehicle vehicle, List<Vehicle> vehicles, LocalDateTime dateTime, int page, int pageSize,
                           int count, HashMap<String, String> errors, List<VehicleType> vehiclesType) {
        this.vehicle = vehicle;
        this.vehicles = vehicles;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = errors;
        this.vehiclesType = vehiclesType;
    }

    public VehicleViewData(List<Vehicle> vehicles, LocalDateTime dateTime, int page, int pageSize, int count, List<VehicleType> vehiclesType) {
        this.vehicle = null;
        this.vehicles = vehicles;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = null;
        this.vehiclesType = vehiclesType;
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
