package strongbox.test.googledrive;


import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import strongbox.encryption.Encryption;
import strongbox.model.Record;
import strongbox.test.io.csv.CSVInputOutput;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class GoogleDriveModel {

	/** Application name. */
	private static final String APPLICATION_NAME = "StrongBox";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".credentials/drive_strongbox");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/drive-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(
			DriveScopes.DRIVE_METADATA_READONLY, DriveScopes.DRIVE_FILE,
			DriveScopes.DRIVE_APPDATA);

	private static String fileID, folderID;

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}
	static ArrayList<Record> records = new ArrayList<>();

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public static Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = Quickstart.class
				.getResourceAsStream("/client_secret_strongbox.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
				JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(DATA_STORE_FACTORY)
				.setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to "
				+ DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	/**
	 * Build and return an authorized Drive client service.
	 * 
	 * @return an authorized Drive client service
	 * @throws IOException
	 */
	public static Drive getDriveService() throws IOException {
		Credential credential = authorize();
		return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}

	public static void createFolder() throws IOException {

		Drive service = getDriveService();

		File fileMetadata = new File();
		fileMetadata.setName("StrongBox");
		fileMetadata.setMimeType("application/vnd.google-apps.folder");

		File file = service.files().create(fileMetadata).setFields("id")
				.execute();
		setFolderID(file.getId());
		System.out.println("Folder ID: " + file.getId());

	}

	public static void createDataFile(String folderID) throws IOException {

		Drive service = getDriveService();

		String folderId = getFolderID();
		File fileMetadata = new File();
		fileMetadata.setName("data.csv");
		fileMetadata.setParents(Collections.singletonList(folderId));
		File file = service.files().create(fileMetadata)
				.setFields("id, parents").execute();
		System.out.println("File ID: " + file.getId());
	}

	public static void createDataAppFolder() throws IOException {

		Drive service = getDriveService();

		File fileMetadata = new File();
		fileMetadata.setName("data.csv");
		fileMetadata.setParents(Collections.singletonList("appDataFolder"));
		File file = service.files().create(fileMetadata).setFields("id")
				.execute();
		System.out.println("File ID: " + file.getId());

	}
	

	public static void listAppFolder() throws IOException {
		Drive service = getDriveService();

		FileList files = service.files().list().setSpaces("appDataFolder")
				.setFields("nextPageToken, files(id, name)").setPageSize(10)
				.execute();
		for (File file : files.getFiles()) {
			System.out.printf("Found file: %s (%s), \n", file.getName(),
					file.getId());

		}

	}

	public static String getFileID() throws IOException {

		Drive service = getDriveService();
		String fileId = "";
		FileList files = service.files().list().setSpaces("appDataFolder")
				.setFields("nextPageToken, files(id, name)").setPageSize(10)
				.execute();
		for (File file : files.getFiles()) {
			System.out.printf("Found file: %s (%s)\n", file.getName(),
					file.getId());
			if (file.getName().equals("data.csv")) {
				System.out.println("file exists");
				fileId = file.getId();
			}
		}
		return fileId;
	}

	public static void updateDataFile(String fileID) throws IOException {

		
		Drive service = getDriveService();
		File file = new File();
		file.setTrashed(true);
		java.io.File filePath = new java.io.File("res/data.csv");
		FileContent mediaContent = new FileContent("text/csv", filePath);
		File updatefile = service.files().update(fileID, file, mediaContent).execute();
		System.out.println("File ID: " + updatefile.getId());
	}
	
	public static void downloadDateFile(String fileID) throws IOException{
		Drive service = getDriveService();
		String fileId = fileID;
		OutputStream outputStream = new ByteArrayOutputStream();
		service.files().get(fileId)
		        .executeMediaAndDownloadTo(outputStream);
		String cvsSplitBy = ",";
		Encryption enc = new Encryption("hx8&2RlYz2rqn&N^oiyKZG#35&P1RMkQ");
		Scanner input = new Scanner(outputStream.toString());
		while(input.hasNextLine()){
			// separator
			String[] item = input.nextLine().split(cvsSplitBy);
			Record record = new Record(item[0], item[1], item[2], item[3],
					item[4], item[5]);
			records.add(record);
		}
		
		
		
		
	}
	public void writeRecordToLocalFile(){
		Encryption enc = new Encryption("hx8&2RlYz2rqn&N^oiyKZG#35&P1RMkQ");
		CSVInputOutput io = new CSVInputOutput();
		FileWriter writer = null;
		try {
			writer = new FileWriter("res/data.csv");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		io.writeFile(writer, records);
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Record> getRecords(){
		return records;
		
	}

	public static void deleteDataFile(String fileID) throws IOException {
		Drive service = getDriveService();
		service.files().delete(fileID).execute();
	}

	public static void setFileID(String fileID) {
		GoogleDriveModel.fileID = fileID;
	}

	public static String getFolderID() {
		return folderID;
	}

	public static void setFolderID(String folderID) {
		GoogleDriveModel.folderID = folderID;
	}

	public static void main(String[] args) {

		try {
			// createFolder();
			// createDataFile(getFolderID());
			//createDataAppFolder();
			// listAppFolder();
			//deleteDataFile("1BrVOUnYcsPdvicb77dWe0GNav9x0PflmcTHZk9WyQF9q");
			getFileID();
			//downloadDateFile(getFileID());
			//System.out.println(records.toString());
			updateDataFile(getFileID());
			
			//listAppFolder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
