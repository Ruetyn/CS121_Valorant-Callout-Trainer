import java.util.*;

public class Map {
	private String name;
	private List<String> angles;

	public Map(String name, List<String> angles) {
		this.name = name;
		this.angles = angles;
	} // end constructor
	
	public String getName() {
		return name;
	} // end getName()

	public List<String> getAngles() {
		return angles;
	} // end getAngles()
	
	// main for testing purposes
	public static void main(String[] args) {
		List<String> angles = List.of("A Lobby", "Mid Window", "A Site");
		Map map = new Map ("Haven", angles);

		System.out.println("Map name: " + map.getName());
		System.out.println("Angles:");
		for (String a : map.getAngles()) {
			System.out.println("- " + a);
		} // end for
	} // end main
} // end class definition
