package  com.example.dynamic_ride_allocator.graphs;

import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Driver;

import java.util.*;

public class DriverFinder {

    static ArrayList<Driver> allDrivers = UsersData.getAllDrivers();

    public static ArrayList<Driver> getAvailableDrivers() {
        ArrayList<Driver> available = new ArrayList<>();
        for (Driver d : allDrivers)
            if (d.isAvailable()) available.add(d);
        return available;
    }

    public static ArrayList<Driver> findNearbyDrivers(String riderLocation, int maxHops) {
        ArrayList<Driver> nearbyDrivers = new ArrayList<>();
        if (!CityGraph.locationExists(riderLocation)) return nearbyDrivers;


        // checks if driver and rider are at same location
        for (Driver driver : getAvailableDrivers()) {

            String driverLocationName = driver.getLocation();

            if (driver.isAvailable()
                    && driverLocationName.equalsIgnoreCase(riderLocation)) {

                driver.setDistance(0.0);
                nearbyDrivers.add(driver);
            }
        }
        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> hops = new HashMap<>();
        Map<String, Double> dist = new HashMap<>();

        queue.add(riderLocation);
        hops.put(riderLocation, 0);
        dist.put(riderLocation, 0.0);

        while (!queue.isEmpty()) {
            String current    = queue.poll();
            int    currentHop = hops.get(current);
            if (currentHop >= maxHops) continue;

            for (Road road : CityGraph.adjacencyList.get(current)) {
                String neighbour = road.destination;
                if (!hops.containsKey(neighbour)) {
                    int    newHop  = currentHop + 1;
                    double newDist = dist.get(current) + road.distance;
                    hops.put(neighbour, newHop);
                    dist.put(neighbour, newDist);
                    queue.add(neighbour);

                    for (Driver driver : allDrivers) {
                        // I have updated the location to string now can get directly from driver
                        String driverLocationName =driver.getLocation();

                        if (driver.isAvailable() && driverLocationName.equals(neighbour)) {
                            driver.setDistance(newDist);
                            nearbyDrivers.add(driver);
                        }
                    }
                }
            }
        }

        nearbyDrivers.sort((a, b) -> Double.compare(a.getDistance(), b.getDistance()));
        return nearbyDrivers;
    }

    public static int getLocationIndex(String locationName) {
        return CityGraph.allLocations.indexOf(locationName);
    }

    public static String getLocationName(int index) {
        if (index >= 0 && index < CityGraph.allLocations.size())
            return CityGraph.allLocations.get(index);
        return "";
    }

    public static ArrayList<Driver> getAllDrivers() {
        return allDrivers;
    }
}