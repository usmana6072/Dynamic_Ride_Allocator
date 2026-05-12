package com.example.dynamic_ride_allocator.DataLayer;

import com.example.dynamic_ride_allocator.Models.Admin;
import com.example.dynamic_ride_allocator.Models.Driver;
import com.example.dynamic_ride_allocator.Models.Rider;
import com.example.dynamic_ride_allocator.Helpers.AppendableObjectOutputStream;
import com.example.dynamic_ride_allocator.Models.Trip;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersData {
    public static Map<String, Admin> adminData=new HashMap<>();
    public static Map<String, Rider> riderData=new HashMap<>();
    public static Map<String, Driver> driverData=new HashMap<>();
    public static Map<String,ArrayList<Trip>> riderHistory=new HashMap<>();
    public static Map<String,ArrayList<Trip>> driverHistory=new HashMap<>();

    public static String adminPath="C:\\DRA\\admin.txt";
    public static String riderPath="C:\\DRA\\rider.txt";
    public static String driverPath="C:\\DRA\\driver.txt";
    public static String riderHistoryPath="C:\\DRA\\riderHistory.txt";
    public static String driverHistoryPath="C:\\DRA\\driverHistory.txt";


    public static void loadData(){
        loadAdmin();
        loadRider();
        loadDriver();
        loadDriverHistory();
        loadRiderHistory();
    }
    public static ArrayList<Driver> getAllDrivers() {
        return new ArrayList<>(driverData.values());
    }

    private static void loadDriver() {
        File file=new File(driverPath);
        try(ObjectInputStream input=new ObjectInputStream(new FileInputStream(file))){
            while(true) {
                Driver driver = (Driver) input.readObject();
                if (driver != null)
                    driverData.put(driver.getEmail(), driver);
            }
        }
        catch (EOFException e){

        }
        catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private static void loadRider() {
        File file=new File(riderPath);
        try(ObjectInputStream input=new ObjectInputStream(new FileInputStream(file))){
            while(true) {
                Rider rider = (Rider) input.readObject();
                if (rider != null)
                    riderData.put(rider.getEmail(), rider);
            }
        }catch (EOFException e){

        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private static void loadAdmin() {
        File file=new File(adminPath);
        try(ObjectInputStream input=new ObjectInputStream(new FileInputStream(file))){
            while(true) {
                Admin admin = (Admin) input.readObject();
                if (admin != null)
                    adminData.put(admin.getEmail(), admin);
            }
        }catch (EOFException e){

        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void loadRiderHistory() {
        File file=new File(riderHistoryPath);
        try(ObjectInputStream input=new ObjectInputStream(new FileInputStream(file))){
            riderHistory=(Map<String, ArrayList<Trip>>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadDriverHistory() {
        File file=new File(driverHistoryPath);
        try(ObjectInputStream input=new ObjectInputStream(new FileInputStream(file))){
            driverHistory=(Map<String, ArrayList<Trip>>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeAdmin(){
        File file=new File(adminPath);
        try(ObjectOutputStream output=new ObjectOutputStream(new FileOutputStream(file))){
            for(String s:adminData.keySet()){
                Admin admin=adminData.get(s);
                if(admin!=null){
                    output.writeObject(admin);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void writeRider(){
        File file=new File(riderPath);
        try(ObjectOutputStream output=new ObjectOutputStream(new FileOutputStream(file))){
            for(String s:riderData.keySet()){
                Rider rider=riderData.get(s);
                if(rider!=null){
                    output.writeObject(rider);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void writeDriver(){
        File file=new File(driverPath);
        try(ObjectOutputStream output=new ObjectOutputStream(new FileOutputStream(file))){
            for(String s:driverData.keySet()){
                Driver driver=driverData.get(s);
                if(driver!=null){
                    output.writeObject(driver);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void writeRiderHistory(){
        File file=new File(riderHistoryPath);
        try (ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(file))){
            if(riderHistory!=null)
                out.writeObject(riderHistory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDriverHistory(){
        File file=new File(driverHistoryPath);
        try (ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(file))){
            if(driverHistory!=null)
                out.writeObject(driverHistory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendDriver(String email){
        File file=new File(driverPath);
        try(ObjectOutputStream out=(file.exists() && file.length() > 0)
                ? new AppendableObjectOutputStream(new FileOutputStream(file, true))
                : new ObjectOutputStream(new FileOutputStream(file))) {
            Driver driver=driverData.get(email);
            if(driver!=null)
                out.writeObject(driver);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendRider(String email){
        File file=new File(riderPath);
        try(ObjectOutputStream out=(file.exists() && file.length() > 0)
                ? new AppendableObjectOutputStream(new FileOutputStream(file, true))
                : new ObjectOutputStream(new FileOutputStream(file))) {
            Rider rider=riderData.get(email);
            if(rider!=null)
                out.writeObject(rider);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendAdmin(String email){
        File file=new File(adminPath);
        try(ObjectOutputStream out=(file.exists() && file.length() > 0)
                ? new AppendableObjectOutputStream(new FileOutputStream(file, true))
                : new ObjectOutputStream(new FileOutputStream(file))) {
            Admin admin=adminData.get(email);
            if(admin!=null)
                out.writeObject(admin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
