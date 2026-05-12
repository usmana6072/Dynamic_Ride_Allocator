module com.example.dynamic_ride_allocator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.sql;
    requires annotations;

    opens com.example.dynamic_ride_allocator to javafx.fxml;
    exports com.example.dynamic_ride_allocator;
    exports com.example.dynamic_ride_allocator.Controllers;
    opens com.example.dynamic_ride_allocator.Controllers to javafx.fxml;
    exports com.example.dynamic_ride_allocator.Controllers.DriverControllers;
    opens com.example.dynamic_ride_allocator.Controllers.DriverControllers to javafx.fxml;
    exports com.example.dynamic_ride_allocator.Controllers.RiderControllers;
    opens com.example.dynamic_ride_allocator.Controllers.RiderControllers to javafx.fxml;
    exports com.example.dynamic_ride_allocator.Controllers.AdminControllers;
    opens com.example.dynamic_ride_allocator.Controllers.AdminControllers to javafx.fxml;
}