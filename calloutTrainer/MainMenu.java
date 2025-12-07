import java.util.*;

public class MainMenu implements HasMenu {
	private MapManager mapManager;
	private AngleTrainer trainer;
	private Scanner scanner;

	public MainMenu(MapManager mapManager, String eventsCSV) {
		this.mapManager = mapManager;
		this.scanner = new Scanner(System.in);
		trainer = new AngleTrainer(mapManager, eventsCSV);
	} // end constructor
	
	@Override
	public String menu() {
		System.out.println("--- Main Menu ---");
		System.out.println("0) Exit");
		System.out.println("1) Start training");
		return scanner.nextLine();
	} // end menu()
	
	@Override
	public void start() {
		boolean keepGoing = true;
		while (keepGoing) {
			String choice = menu();
			if (choice.equals("0")) {
				keepGoing = false;
			} else if (choice.equals("1")) {
				trainer.start();
			} else {
				System.out.println("Invalid option");
			} // end if
		} // end while
	} // end start()
} // end class declaration
