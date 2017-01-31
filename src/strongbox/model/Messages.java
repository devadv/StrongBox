package strongbox.model;

import java.util.ArrayList;

/**
 * This class provides the text for the status messages and dialog messages
 * which are served to the user to clarify and notify things.
 * @version 31-01-2017
 */
public class Messages {

	private ArrayList<String> statusMessages = new ArrayList<>();

	public Messages() {
		createStatusMsgList();
	}
	
	// This is a longer message message. <-- Don't make 'm any longer than this!
	private void createStatusMsgList() {
		statusMessages.add("Create a new record");                 // statusMessages (0)
		statusMessages.add("Edit this record");                    // statusMessages (1)
		statusMessages.add("Delete this record");                  // statusMessages (2)
		statusMessages.add("Delete ALL records");                  // statusMessages (3)
		statusMessages.add("About StrongBox");                     // statusMessages (4)
		statusMessages.add("New record created");                  // statusMessages (5)
		statusMessages.add("Record edited");                       // statusMessages (6)
		statusMessages.add("Record deleted");                      // statusMessages (7)
		statusMessages.add("ALL records deleted");                 // statusMessages (8)
		statusMessages.add("Please enter the record's details");   // statusMessages (9)
		statusMessages.add("Record Saved!");                       // statusMessages (10)
		statusMessages.add("Changes Canceled");                    // statusMessages (11)
		statusMessages.add("Creation of new record canceled");     // statusMessages (12)
		statusMessages.add("Show Password");                       // statusMessages (13)
		statusMessages.add("Hide Password");                       // statusMessages (14)
		statusMessages.add("Right-click password to copy");        // statusMessages (15)
		statusMessages.add("Copied to clipboard!");                // statusMessages (16)
		statusMessages.add("Generate a random password");          // statusMessages (17)
		statusMessages.add("Password generation unavailable");     // statusMessages (18)
	}
	
	public String getStatus(int index) {
		return statusMessages.get(index);
	}
}
