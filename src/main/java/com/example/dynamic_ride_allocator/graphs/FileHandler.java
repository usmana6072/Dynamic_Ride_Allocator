package  com.example.dynamic_ride_allocator.graphs;

import com.example.dynamic_ride_allocator.Models.Driver;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FileHandler{

    static final String SAVE_FOLDER ="C:\\DRA";

    static final String DRIVERS_FILE   = SAVE_FOLDER + "\\drivers.txt";
    static final String LOCATIONS_FILE = SAVE_FOLDER + "\\locations.txt";
    static final String ROADS_FILE     = SAVE_FOLDER + "\\roads.txt";

    private static void ensureFolderExists() {
        new File(SAVE_FOLDER).mkdirs();
    }

    public static void saveAll() {
        ensureFolderExists();
        saveLocations();
        saveRoads();
    }

    private static void saveLocations() {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(LOCATIONS_FILE))) {
            for (String loc : CityGraph.allLocations) {
                w.write(loc);
                w.newLine();
            }
        } catch (IOException ignored) {}
    }

    private static void saveRoads() {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(ROADS_FILE))) {
            Set<String> seen = new HashSet<>();
            for (String node : CityGraph.adjacencyList.keySet()) {
                for (Road r : CityGraph.adjacencyList.get(node)) {
                    String key = node.compareTo(r.destination) < 0
                            ? node + "-" + r.destination
                            : r.destination + "-" + node;
                    if (!seen.contains(key)) {
                        seen.add(key);
                        w.write(node + "," + r.destination + "," + r.distance);
                        w.newLine();
                    }
                }
            }
        } catch (IOException ignored) {
        }
    }

    private static void saveDrivers() {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(DRIVERS_FILE))) {
            for (Driver d : DriverFinder.allDrivers) {
                w.write(d.getName() + ","
                        + d.getLocation() + ","
                        + d.isAvailable() + ","
                        + d.getPhone() + ","
                        + d.getVehicleType() + ","
                        + d.getVehicleNumber());
                w.newLine();
            }
        } catch (IOException ignored) {}
    }

    public static String loadAll() {
        File folder = new File(SAVE_FOLDER);
        if (!folder.exists()) return "NO_DATA";
        try {
            CityGraph.clearGraph();
            DriverFinder.allDrivers.clear();
            loadLocations();
            loadRoads();
            return "OK";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    private static void loadLocations() throws IOException {
        File f = new File(LOCATIONS_FILE);
        if (!f.exists()) return;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !CityGraph.adjacencyList.containsKey(line)) {
                    CityGraph.adjacencyList.put(line, new ArrayList<>());
                    CityGraph.allLocations.add(line);
                }
            }
        }
    }

    private static void loadRoads() throws IOException {
        File f = new File(ROADS_FILE);
        if (!f.exists()) return;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split(",");
                if (p.length == 3) {
                    String from = p[0], to = p[1];
                    double dist = Double.parseDouble(p[2]);
                    if (!CityGraph.adjacencyList.containsKey(from)) {
                        CityGraph.adjacencyList.put(from, new ArrayList<>());
                        CityGraph.allLocations.add(from);
                    }
                    if (!CityGraph.adjacencyList.containsKey(to)) {
                        CityGraph.adjacencyList.put(to, new ArrayList<>());
                        CityGraph.allLocations.add(to);
                    }
                    boolean exists = false;
                    for (Road road : CityGraph.adjacencyList.get(from))
                        if (road.destination.equals(to)) { exists = true; break; }
                    if (!exists) {
                        CityGraph.adjacencyList.get(from).add(new Road(to,   dist));
                        CityGraph.adjacencyList.get(to).add(new Road(from, dist));
                    }
                }
            }
        }
    }

    private static void loadDrivers() throws IOException {
        File f = new File(DRIVERS_FILE);
        if (!f.exists()) return;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split(",");
                if (p.length == 6) {
                    Driver d = new Driver(p[0], p[3], p[4], p[5], "");
                    d.setLocation(p[1]);
                    d.setAvailable(Boolean.parseBoolean(p[2]));
                    DriverFinder.allDrivers.add(d);
                }
            }
        }
    }

    public static String getSaveFolderPath() { return SAVE_FOLDER; }
}