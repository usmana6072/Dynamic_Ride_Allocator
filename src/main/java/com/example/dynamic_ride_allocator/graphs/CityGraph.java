package  com.example.dynamic_ride_allocator.graphs;
import java.util.*;

public class CityGraph {
    static Map<String, List<Road>> adjacencyList = new LinkedHashMap<>();
    static ArrayList<String> allLocations = new ArrayList<>();

    public static boolean addLocation(String location) {
        location = location.trim();
        if (location.isEmpty())
            return false;
        if (adjacencyList.containsKey(location))
            return false;
        adjacencyList.put(location, new ArrayList<>());
        allLocations.add(location);
        return true;
    }

    public static String addRoad(String from, String to, double distance) {
        from = from.trim();
        to = to.trim();
        if (from.equalsIgnoreCase(to))
            return "SAME NODE";
        if (!adjacencyList.containsKey(from))
            addLocation(from);
        if (!adjacencyList.containsKey(to))
            addLocation(to);
        for (Road r : adjacencyList.get(from)) {
            if (r.destination.equals(to))
                return "ALREADY EXISTS";
        }
        adjacencyList.get(from).add(new Road(to, distance));
        adjacencyList.get(to).add(new Road(from, distance));
        return "OK";
    }

    public static boolean removeLocation(String location) {
        if (!adjacencyList.containsKey(location))
            return false;
        for (String node : adjacencyList.keySet())
            adjacencyList.get(node).removeIf(r -> r.destination.equals(location));
        adjacencyList.remove(location);
        allLocations.remove(location);
        return true;
    }

    public static boolean locationExists(String location) {
        return adjacencyList.containsKey(location);
    }

    public static ArrayList<String> getAllLocations() {
        return allLocations;
    }

    public static ArrayList<String> getNeighbours(String location) {
        ArrayList<String> neighbours = new ArrayList<>();
        if (adjacencyList.containsKey(location))
            for (Road r : adjacencyList.get(location))
                neighbours.add(r.destination);
        return neighbours;
    }

    public static double getRoadDistance(String from, String to) {
        if (!adjacencyList.containsKey(from))
            return Double.MAX_VALUE;
        for (Road r : adjacencyList.get(from))
            if (r.destination.equals(to))
                return r.distance;
        return Double.MAX_VALUE;
    }

    public static ArrayList<String> getAllRoadsAsStrings() {
        ArrayList<String> result = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (String node : adjacencyList.keySet()) {
            for (Road r : adjacencyList.get(node)) {
                String roadKey;
                if (node.compareTo(r.destination) < 0)
                    roadKey = node + "-" + r.destination;
                else
                    roadKey = r.destination + "-" + node;
                if (seen.contains(roadKey)) continue;
                seen.add(roadKey);
                result.add(node + " -> " + r.destination + " (" + r.distance + " km)");
            }
        }
        return result;
    }

    public static void clearGraph() {
        adjacencyList.clear();
        allLocations.clear();
    }
}