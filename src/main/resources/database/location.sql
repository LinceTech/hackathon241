create table locations (
    id INTEGER NOT NULL IDENTITY (1,1),
    id_client INTEGER NOT NULL,
    id_manager INTEGER NOT NULL,
    id_vehicles INTEGER NOT NULL,
    date_start DATE NULL,
    date_delivery DATE NULL,
    day_value DECIMAL NULL,
    commission_percentage DECIMAL NULL,
    total_value DECIMAL NULL,
    date_pay DATE NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (id_client) REFERENCES client(id),
    FOREIGN KEY (id_manager) REFERENCES MANAGER(id),
    FOREIGN KEY (id_vehicles) REFERENCES vehicles(id)
)