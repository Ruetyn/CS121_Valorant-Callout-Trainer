import java.io.*;
import java.util.*;

public class MapManager {
	private List<Map> maps = new ArrayList<>();
	private List<String> compPool = Arrays.asList("Abyss", "Bind", "Corrode", "Haven", "Pearl", "Split", "Sunset");
	private Random random = new Random();

	public void loadCSV(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				String name = parts[0];
				List<String> angles = new ArrayList<>(Arrays.asList(parts).subList(1, parts.length));
				maps.add(new Map(name, angles));
			} // end while
		} catch (IOException e) {
			System.out.println("Error reading CSV: " + e.getMessage());
		} // end try/catch
	} // end loadCSV()
	
	public List<Map> getAllMaps() {
		return maps;
	} // end getAllMaps()
	
	public List<Map> getCompetitiveMaps() {
		List<Map> list = new ArrayList<>();
		for (Map m : maps) {
			if (compPool.contains(m.getName())) {
				list.add(m);
			} // end if
		} // end for
		return list;
	} // end getCompetitiveMaps()
	
	public Map getRandomMap(boolean fromCompPool) {
		List<Map> list = fromCompPool ? getCompetitiveMaps() : maps;
		return list.get(random.nextInt(list.size()));
	} // end getRandomMap()
	
	public String getRandomAngle(Map map) {
		List<String> angles = map.getAngles();
		return angles.get(random.nextInt(angles.size()));
	} // end getRandomAngle()
	
	// main for testing
	public static void main(String[] args) {
		MapManager manager = new MapManager();
		manager.loadCSV("maps.csv");

		System.out.println("All maps loaded:");
		for (Map m : manager.getAllMaps()) {
			System.out.println("- " + m.getName() + ": " + m.getAngles());
		} // end for

		System.out.println("\nCompetitive Maps:");
		for (Map m : manager.getCompetitiveMaps()) {
			System.out.println("- " + m.getName());
		} // end for

		Map randomMap = manager.getRandomMap(true);
		System.out.println("\nRandom competitive map: " + randomMap.getName());
		System.out.println("Random angle from that map: " + manager.getRandomAngle(randomMap));
	} // end main
} // end class declaration
