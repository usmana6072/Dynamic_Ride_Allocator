package  com.example.dynamic_ride_allocator.graphs;
import com.example.dynamic_ride_allocator.Models.Driver;

import java.util.*;

public class RideAllocator {

    public static String initializeApp() {
        return FileHandler.loadAll();
    }

    public static boolean addLocation(String name) { return CityGraph.addLocation(name); }
    public static String addRoad(String from, String to, double distanceKm) { return CityGraph.addRoad(from, to, distanceKm); }
    public static boolean removeLocation(String name) { return CityGraph.removeLocation(name); }
    public static ArrayList<String> getAllLocations() { return CityGraph.getAllLocations(); }
    public static ArrayList<String> getAllRoads() { return CityGraph.getAllRoadsAsStrings(); }
    public static ArrayList<String> getNeighbours(String location) { return CityGraph.getNeighbours(location); }
    public static boolean locationExists(String name) { return CityGraph.locationExists(name); }

    public static ArrayList<Driver> getAllDrivers() { return DriverFinder.getAllDrivers(); }
    public static ArrayList<Driver> getAvailableDrivers() { return DriverFinder.getAvailableDrivers(); }

    public static ArrayList<Driver> findNearbyDrivers(String riderLocation, int maxHops) {
        return DriverFinder.findNearbyDrivers(riderLocation, maxHops);
    }

    public static Driver assignClosestDriver(ArrayList<Driver> nearbyDrivers) {
        if (nearbyDrivers == null || nearbyDrivers.isEmpty()) return null;
        DriverMinHeap.clear();
        DriverMinHeap.insertAll(nearbyDrivers);
        Driver assigned = DriverMinHeap.extractMin();
        if (assigned != null) {
            assigned.setAvailable(false);
            FileHandler.saveAll();
        }
        return assigned;
    }

    public static Driver requestRide(String riderLocation, int maxHops) {
        ArrayList<Driver> nearby = findNearbyDrivers(riderLocation, maxHops);
        return assignClosestDriver(nearby);
    }

    public static ArrayList<Driver> getHeapSnapshot(ArrayList<Driver> nearbyDrivers) {
        DriverMinHeap.clear();
        DriverMinHeap.insertAll(new ArrayList<>(nearbyDrivers));
        return DriverMinHeap.getAllInHeap();
    }

    public static int getLocationIndex(String locationName) {
        return DriverFinder.getLocationIndex(locationName);
    }

    public static String getLocationName(int index) {
        return DriverFinder.getLocationName(index);
    }

    public static String getDataFolderPath() {
        return FileHandler.getSaveFolderPath();
    }
    public static void saveNow() {
        FileHandler.saveAll();
    }


    // this is to load sample data to city map
    public static void loadSampleCityData() {

        // ── Add Locations ─────────────────────────────────────
        RideAllocator.addLocation("LDA");
        RideAllocator.addLocation("Johar Town");
        RideAllocator.addLocation("Wapda Town");
        RideAllocator.addLocation("Model Town");
        RideAllocator.addLocation("Gulberg");
        RideAllocator.addLocation("DHA");
        RideAllocator.addLocation("Bahria Town");
        RideAllocator.addLocation("Valencia");
        RideAllocator.addLocation("Cantt");
        RideAllocator.addLocation("Iqbal Town");
        RideAllocator.addLocation("Sabzazar");
        RideAllocator.addLocation("Township");
        RideAllocator.addLocation("Faisal Town");
        RideAllocator.addLocation("Garden Town");
        RideAllocator.addLocation("Shadman");
        RideAllocator.addLocation("Askari");
        RideAllocator.addLocation("Samanabad");
        RideAllocator.addLocation("Mughalpura");
        RideAllocator.addLocation("Paragon City");
        RideAllocator.addLocation("Airport");

        // ── Add Roads ────────────────────────────────────────

        RideAllocator.addRoad("LDA", "Johar Town", 2.5);
        RideAllocator.addRoad("LDA", "Wapda Town", 3.0);

        RideAllocator.addRoad("Johar Town", "Model Town", 4.2);
        RideAllocator.addRoad("Johar Town", "Faisal Town", 2.8);

        RideAllocator.addRoad("Wapda Town", "Valencia", 5.1);
        RideAllocator.addRoad("Wapda Town", "Township", 3.6);

        RideAllocator.addRoad("Model Town", "Garden Town", 2.3);
        RideAllocator.addRoad("Model Town", "Gulberg", 6.0);

        RideAllocator.addRoad("Garden Town", "Shadman", 3.1);
        RideAllocator.addRoad("Shadman", "Samanabad", 4.0);

        RideAllocator.addRoad("Samanabad", "Iqbal Town", 3.7);
        RideAllocator.addRoad("Iqbal Town", "Sabzazar", 2.9);

        RideAllocator.addRoad("Gulberg", "Cantt", 5.5);
        RideAllocator.addRoad("Cantt", "DHA", 4.4);

        RideAllocator.addRoad("DHA", "Askari", 3.2);
        RideAllocator.addRoad("Askari", "Airport", 6.5);

        RideAllocator.addRoad("DHA", "Paragon City", 5.0);
        RideAllocator.addRoad("Paragon City", "Airport", 4.8);

        RideAllocator.addRoad("Bahria Town", "Valencia", 7.2);
        RideAllocator.addRoad("Bahria Town", "Township", 8.1);

        RideAllocator.addRoad("Township", "Faisal Town", 2.5);
        RideAllocator.addRoad("Faisal Town", "Gulberg", 5.7);

        RideAllocator.addRoad("Mughalpura", "Cantt", 4.6);
        RideAllocator.addRoad("Mughalpura", "Shadman", 5.4);

        RideAllocator.addRoad("Airport", "Cantt", 7.0);

        System.out.println("Sample city graph loaded successfully.");
    }
}