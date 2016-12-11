package strongbox.test.googledrive;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
			DriveScopes.DRIVE_METADATA_READONLY, DriveScopes.DRIVE_FILE, DriveScopes.DRIVE_APPDATA);

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
	
	public static void createFolder() throws IOException{
		
		Drive service = getDriveService();
		
		File fileMetadata = new File();
		fileMetadata.setName("StrongBox");
		fileMetadata.setMimeType("application/vnd.google-apps.folder");

		File file = service.files().create(fileMetadata)
		        .setFields("id")
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
		        .setFields("id, parents")
		        .execute();
		System.out.println("File ID: " + file.getId());
	}
	
	public static void createDataAppFolder() throws IOException{
		
		Drive service = getDriveService();
		
		File fileMetadata = new File();
		fileMetadata.setName("data.csv");
		fileMetadata.setParents(Collections.singletonList("appDataFolder"));
		/*java.io.File filePath = new java.io.File("files/config.json");
		FileContent mediaContent = new FileContent("application/text", filePath);*/
		File file = service.files().create(fileMetadata)
		        .setFields("id")
		        .execute();
		System.out.println("File ID: " + file.getId());

	}
	
	public static void listAppFolder() throws IOException{
		Drive  service = getDriveService();
		
		FileList files = service.files().list()
		        .setSpaces("appDataFolder")
		        .setFields("nextPageToken, files(id, name)")
		        .setPageSize(10)
		        .execute();
		for(File file: files.getFiles()) {
		    System.out.printf("Found file: %s (%s)\n",
		            file.getName(), file.getId());
		}

		
	}

	public static String getFileID() {
		return fileID;
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
			//createFolder();
			//createDataFile(getFolderID());
			//createDataAppFolder();
			listAppFolder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
