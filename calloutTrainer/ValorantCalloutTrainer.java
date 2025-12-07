public class ValorantCalloutTrainer {
	public static void main(String[] args) {
		MapManager mapManager = new MapManager();
		mapManager.loadCSV("maps.csv");
		MainMenu mainMenu = new MainMenu(mapManager, "events.csv");
		mainMenu.start();
	} // end main
} // end ValorantCalloutTrainer
