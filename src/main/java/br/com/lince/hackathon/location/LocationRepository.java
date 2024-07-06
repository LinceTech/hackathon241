package br.com.lince.hackathon.location;

import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface LocationRepository {

    @RegisterBeanMapper(Location.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM locations ORDER BY id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Location> selectPage(@Define("page") int page, @Define("count") int count);

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM locations")
    int count();

    @RegisterBeanMapper(Location.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM locations WHERE id = :id")
    Location findByLocation(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM locations WHERE id = :id), 1, 0)")
    boolean exists(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM locations WHERE id = :id")
    void delete(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO locations(id_client, id_manager, id_vehicles, date_start, date_delivery, day_value, " +
            "commission_percentage, total_value, date_pay) VALUES (:location.id_client, :location.id_maneger, " +
            ":location.id_vehicles,:location.date_start, :location.date_delivery, :location.day_value, " +
            ":location.commission_percentage, :location.total_value,:location.date_pay)")
    void insert(@BindBean("location") Location location);

    @UseFreemarkerEngine
    @SqlUpdate("UPDATE locations SET id_client = :location.id_client, id_manager = :location.id_manager, " +
            "id_vehicles = :location.id_vehicles, date_start = :location.date_start, date_delivery = :location.date_delivery, " +
            "day_value = :location.day_value, commission_percentage = :location.commission_percentage, total_value = :location.total_value, " +
            "date_pay = :location.date_pay WHERE id = :location.id")
    void update(@BindBean("location") Location location);
}
