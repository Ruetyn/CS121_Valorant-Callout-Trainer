import java.io.*;
import java.util.*;
import java.time.Instant;
import java.time.Duration;

public class AngleTrainer implements HasMenu {
	private MapManager mapManager;
	private Scanner scanner;
	private Random random;
	private java.util.Map<String, List<String>> eventsByType;

	public AngleTrainer(MapManager mapManager, String eventsCSV) {
		this.mapManager = mapManager;
		this.scanner = new Scanner(System.in);
		this.random = new Random();
		this.eventsByType = new HashMap<>();
		loadEvents(eventsCSV);
	} // end constructor
	
	private void loadEvents(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				String type = parts[0].trim();
				for (int i =1; i <parts.length; i++) {
					String event = parts[i].trim();
					eventsByType.computeIfAbsent(type, k -> new ArrayList<>()).add(event);
				} // end for
			} // end while
		} catch (IOException e) {
			System.out.println("Error reading events CSV: " + e.getMessage());
		} // end try/catch
	} // end loadEvents()
	
	private String getRandomEvent() {
		List<String> types = new ArrayList<>(eventsByType.keySet());
		String selectedType = types.get(random.nextInt(types.size()));
		List<String> events = eventsByType.get(selectedType);
		return events.get(random.nextInt(events.size()));
	} // end getRandomEvent()
	
	private Map pickMap() {
		List<Map> maps = mapManager.getAllMaps();
		System.out.println("Pick a map:");
		for (int i = 0; i <maps.size(); i++) {
			System.out.println(i + ") " + maps.get(i).getName());
		} // end for
		int choice = -1;
		while (choice < 0 || choice >= maps.size()) {
			System.out.print("> ");
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				choice = -1;
			} // end try/catch
		} // end while
		return maps.get(choice);
	} // end pickMap()
	
	private String simplifyAngle(String angle) {
		String result = angle;
		String lower = angle.toLowerCase();

		if (lower.contains("attacker spawn")) {
			result = "T";
		} else if (lower.contains("defender spawn")) {
			result = "CT";
		} else if (lower.startsWith("a ")) {
			result = angle.substring(2).trim();
		} else if (lower.startsWith("b ")) {
                        result = angle.substring(2).trim();
		} else if (lower.startsWith("c ")) {
                        result = angle.substring(2).trim();
		} else if (lower.contains("link")) {
			result = "Link";
		} else if (lower.contains("heaven")) {
			result = "Heaven";
		} else if (lower.contains("hell")) {
			result = "Hell";
		} else if (lower.contains("main")) {
			result = "Mid";
		} else if (lower.startsWith("mid ")) {
			result = angle.substring(4).trim();
		}
		return result;
	} // end simplifyAngle()
	
	@Override
	public String menu() {
		System.out.println("--- Training Menu ---");
		System.out.println("0) Exit Training");
		System.out.println("1) Train on competitive map pool (random map)");
		System.out.println("2) Train on all maps (random map)");
		System.out.println("3) Pick map manually");
		return scanner.nextLine();
	} // end menu()
	
	@Override
	public void start() {
		boolean keepTraining = true;
		boolean keepScenario = true;
		while (keepTraining) {
			String choice = menu();
			Map currentMap = null;

			if (choice.equals("0")) {
				keepTraining = false;
				keepScenario = false;
			} else if (choice.equals("1")) {
				currentMap = mapManager.getRandomMap(true);
				keepScenario = true;
			} else if (choice.equals("2")) {
				currentMap = mapManager.getRandomMap(false);
				keepScenario = true;
			} else if (choice.equals("3")) {
				currentMap = pickMap();
				keepScenario = true;
			} else {
				System.out.println("Invalid option");
			} // end if
		
			while (keepScenario) {
				String angle = mapManager.getRandomAngle(currentMap);
				int enemyCount = random.nextInt(5) + 1;
				String event = getRandomEvent();
				String simpleAngle = simplifyAngle(angle);

				System.out.println("You see " + enemyCount + " enemies at " + angle + " on " + currentMap.getName() + " and they used a " + event + ".");
				System.out.println("Speak your callout aloud and press Enter when done...");

				Instant start = Instant.now();
				scanner.nextLine();
				Instant end = Instant.now();

				Duration duration = Duration.between(start, end);
				double seconds = duration.toMillis() / 1000.0;
				System.out.println("Communication time: " + seconds + " seconds");
				System.out.println("Example callout: \"" + enemyCount + " " + simpleAngle + " used a " + event + "\"");

				System.out.println("Press Enter to repeat scenario, or type M to return to menu.");
				String repeatChoice = scanner.nextLine().trim();
				if (repeatChoice.equalsIgnoreCase("M")) {
					keepScenario = false;
				} // end if
			} // end while
		} // end while
	} // end start()
	
	public static void main(String[] args) {
		try {
			MapManager manager = new MapManager();
			manager.loadCSV("maps.csv");

			AngleTrainer trainer = new AngleTrainer(manager, "events.csv");

			Map map = manager.getRandomMap(true); // test random map
			System.out.println("Random competitive map: " + map.getName());

			String angle = manager.getRandomAngle(map); // test random angle from map
			System.out.println("Random angle from map: " + angle);

			String event = trainer.getRandomEvent(); // test random event
			System.out.println("Random event: " + event);

			System.out.println("\n--- Sample Generated Scenario ---");
			Map chosenMap = manager.getRandomMap(true);
			String chosenAngle = manager.getRandomAngle(chosenMap);
			int enemies = new Random().nextInt(5) + 1;
			String chosenEvent = trainer.getRandomEvent();

			System.out.println("Map: " + chosenMap.getName());
			System.out.println("Angle: " + chosenAngle);
			System.out.println("Enemies: " + enemies);
			System.out.println("Event: " + chosenEvent);

			System.out.println("\nPretend user speaks and presses Enter...");
			new java.util.Scanner(System.in).nextLine();

			System.out.println("Example callout: \"" + enemies + " " + chosenAngle + " - " + chosenEvent + "\"");
		} catch (Exception e) {
			e.printStackTrace();
		} // end try/catch
	} // end main
} // end class declaration
