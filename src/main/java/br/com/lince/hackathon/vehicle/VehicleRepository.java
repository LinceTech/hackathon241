package br.com.lince.hackathon.vehicle;

import br.com.lince.hackathon.client.Client;
import br.com.lince.hackathon.foo.Foo;
import br.com.lince.hackathon.vehicle.Vehicle;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;


public interface VehicleRepository {
    @RegisterBeanMapper(Vehicle.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, brand, model, plate, color, year_of_manufacture, daily_cost, promotion_description, type_fuel, type_vehicle \n" +
            "FROM vehicles ORDER BY id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Vehicle> selectPage(@Define("page") int page, @Define("count") int count);

    @RegisterBeanMapper(Vehicle.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, brand, model, plate, color, year_of_manufacture, daily_cost, promotion_description, type_fuel, type_vehicle FROM vehicles WHERE id = :id")
    Vehicle findByVehicleId(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO vehicles(brand, model, plate, color, year_of_manufacture, daily_cost, promotion_description, type_fuel, type_vehicle) VALUES (:vehicle.brand, " +
            ":vehicle.model, :vehicle.plate, :vehicle.color, :vehicle.year_of_manufacture, :vehicle.daily_cost, " +
            ":vehicle.promotion_description, :vehicle.type_fuel, :vehicle.type_vehicle)")
    void insert(@BindBean("vehicle") Vehicle vehicle);

    @UseFreemarkerEngine
    @SqlUpdate("UPDATE vehicles SET brand = :vehicle.brand, model = :vehicle.model, plate = :vehicle.plate, color = :vehicle.color, " +
            "year_of_manufacture = :vehicle.year_of_manufacture, daily_cost = :vehicle.daily_cost, promotion_description = :vehicle.promotion_description, " +
            "type_fuel = :vehicle.type_fuel, type_vehicle = :vehicle.type_vehicle WHERE plate = :vehicle.plate")
    void update(@BindBean("vehicle") Vehicle vehicle);

    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM vehicles WHERE ID = :id")
    void delete(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM vehicles WHERE plate = :plate), 1, 0)")
    boolean exists(@Bind("plate") String  plate);

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM vehicles")
    int count();
}
