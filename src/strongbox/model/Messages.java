package strongbox.model;

import java.util.ArrayList;

/**
 * This class provides the text for the status messages and dialog messages
 * which are served to the user to clarify and notify things.
 * @version 04-04-2017
 */
public class Messages {

	private ArrayList<String> dialogMessages = new ArrayList<>();
	private ArrayList<String> statusMessages = new ArrayList<>();

	public Messages() {
		createDialogMsgList();
		createStatusMsgList();
	}
	
	public String getStatus(int index) {
		return statusMessages.get(index);
	}
	
	public String getDialog(int index) {
		return dialogMessages.get(index);
	}
	
	public void createDialogMsgList() {
		dialogMessages.add("There was a problem with the data" +
				" you entered. \n\n" + "Most likely cause of the " + 
				"problem: \n\n-You entered a " +
				"title that is already in use (within that same folder)\n" + 
				"-You left one of the fields blank (you may opt to " +
				"leave \"note\" blank)\n" +
				"-You used comma's, which you shouldn't do");                  // dialogMessages (0)
	}
	
	// Don't make the statusmessages longer than 33 characters!
	private void createStatusMsgList() {
		statusMessages.add("Create a new record");                 // statusMessages (0)
		statusMessages.add("Edit this record");                    // statusMessages (1)
		statusMessages.add("Delete this record");                  // statusMessages (2)
		statusMessages.add("Delete ALL records");                  // statusMessages (3)
		statusMessages.add("About StrongBox");                     // statusMessages (4)
		statusMessages.add("Record deleted");                      // statusMessages (5)
		statusMessages.add("ALL records deleted");                 // statusMessages (6)
		statusMessages.add("Please enter the record's details");   // statusMessages (7)
		statusMessages.add("Record Saved!");                       // statusMessages (8)
		statusMessages.add("Changes canceled");                    // statusMessages (9)
		statusMessages.add("Creation of new record canceled");     // statusMessages (10)
		statusMessages.add("Show Password");                       // statusMessages (11)
		statusMessages.add("Hide Password");                       // statusMessages (12)
		statusMessages.add("Right-click to copy password");        // statusMessages (13)
		statusMessages.add("Copied to clipboard!");                // statusMessages (14)
		statusMessages.add("Generate a random password");          // statusMessages (15)
		statusMessages.add("Password generation unavailable");     // statusMessages (16)
		statusMessages.add("Search for records");                  // statusMessages (17)
		statusMessages.add("Edit a record");                       // statusMessages (18)
		statusMessages.add("Delete a record");                     // statusMessages (19)
		statusMessages.add("Right-click to copy address");         // statusMessages (20)
		statusMessages.add("Right-click to copy login");           // statusMessages (21)
	}

}
