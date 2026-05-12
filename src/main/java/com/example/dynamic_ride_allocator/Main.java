package com.example.dynamic_ride_allocator;

import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Admin;
import com.example.dynamic_ride_allocator.graphs.CityGraph;
import com.example.dynamic_ride_allocator.graphs.RideAllocator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        createFiles();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Layouts/welcome.fxml")
        );
        Parent parent=loader.load();
        Scene scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    private void createFiles() throws IOException {
        RideAllocator.loadSampleCityData();
        File folder=new File("C:\\DRA");
        if(!folder.exists()){
            folder.mkdir();
        }
        File adminFile=new File(folder,"admin.txt");
        File customerFile=new File(folder,"rider.txt");
        File driverFile=new File(folder,"driver.txt");
        File riderHistoryFile=new File(folder,"riderHistory.txt");
        File driverHistory=new File(folder,"driverHistory.txt");


        if(!customerFile.exists())
            customerFile.createNewFile();
        if(!driverFile.exists())
            driverFile.createNewFile();
        if(!riderHistoryFile.exists())
            riderHistoryFile.createNewFile();
        if(!driverFile.exists())
            driverFile.createNewFile();

        if(!adminFile.exists()){
            adminFile.createNewFile();
            Admin admin=new Admin(1,"Usman","usman@gmail.com","1234","03068559502","set UP By Your self");
            try (ObjectOutputStream out =
                         new ObjectOutputStream(
                                 new FileOutputStream(adminFile))){
                out.writeObject(admin);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        new Thread(UsersData::loadData).start();

    }
}
