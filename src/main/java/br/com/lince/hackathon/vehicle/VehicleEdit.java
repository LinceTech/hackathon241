package br.com.lince.hackathon.vehicle;

import br.com.lince.hackathon.manager.Manager;
import br.com.lince.hackathon.util.State;
import br.com.lince.hackathon.util.VehicleType;

import java.util.List;

public class VehicleEdit {

    private final Vehicle vehicle;
    private final List<VehicleType> vehiclesType;

    public VehicleEdit(Vehicle vehicle, List<VehicleType> vehicleType) {
        this.vehicle = vehicle;
        this.vehiclesType = vehicleType;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public List<VehicleType> getVehiclesType() {
        return vehiclesType;
    }
}
