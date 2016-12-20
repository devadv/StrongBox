package strongbox.test.googledrive;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import strongbox.encryption.Encryption;
import strongbox.model.Model;
import strongbox.model.Record;

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
import com.google.api.client.util.DateTime;
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
			// TODO check scope only with DRIVE_APPDATA
			DriveScopes.DRIVE_METADATA_READONLY, DriveScopes.DRIVE_FILE,
			DriveScopes.DRIVE_APPDATA);

	

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

	public static void createDataFile() {

		Drive service = null;
		try {
			service = getDriveService();
			File fileMetadata = new File();
			fileMetadata.setName("data.csv");
			fileMetadata.setParents(Collections.singletonList("appDataFolder"));
			File file = service.files().create(fileMetadata).setFields("id")
					.execute();
			System.out.println("File ID: " + file.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public static void downLoadData() {
		Drive service;
		try {
			service = getDriveService();
			
			OutputStream outputStream = new ByteArrayOutputStream();
			service.files().get(getFileID()).executeMediaAndDownloadTo(outputStream);
			String cvsSplitBy = ",";
			Scanner input = new Scanner(outputStream.toString());
			while (input.hasNextLine()) {
				// separator
				String[] item = input.nextLine().split(cvsSplitBy);
				Record record = new Record(item[0], item[1], item[2], item[3],
						item[4], item[5]);
				records.add(record);
			}
		} catch (IOException e ) {
			
			e.printStackTrace();
		}

	}

	public static void uploadData() {
		Drive service;
		try {
			service = getDriveService();
			Date date = new Date();
			DateTime dt = new DateTime(date);
			System.out.println();
			File file = new File();
			file.setTrashed(true);
			file.setModifiedTime(dt);
			java.io.File filePath = new java.io.File("res/data.csv");
			FileContent mediaContent = new FileContent("text/csv", filePath);

			File updatefile = service.files().update(getFileID(), file, mediaContent).execute();
			
			System.out.println("File ID: " + updatefile.getId());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static ArrayList<Record> getRecords() {
		return records;

	}

	public static void deleteData() {
		System.out.println("Data deleted!");
	}

	public static void main(String[] args) {
		Encryption enc = new Encryption("hx8&2RlYz2rqn&N^oiyKZG#35&P1RMkQ");
		GoogleDriveModel.downLoadData();
		//System.out.println(GoogleDriveModel.getRecords());
		Model model = new Model();
		model.writeRecordsToFile(GoogleDriveModel.getRecords());
		System.out.println(model.getRecordList().toString());
		//model.createNewRecord("Kaasboer", "kaas.nl", "boertje", "karnemelk12", "Food", "boerenkaas");
		model.createNewRecord("Groenteboer", "groeten.nl", "boertje", "komkommer12", "Food", "bio");
		model.writeRecordsToFile();
		uploadData();
		GoogleDriveModel.downLoadData();
		System.out.println(GoogleDriveModel.getRecords());
	}

}
