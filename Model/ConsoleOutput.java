package model;

public class ConsoleOutput implements SystemOutput {
	
	public ConsoleOutput () {
		
	}
	
	@Override
	public void displayMessage(String message) {
		
		System.out.println(message);
		
	}

}
