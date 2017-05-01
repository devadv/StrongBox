package strongbox.drive;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class GoogleDriveModel {
	/** Application name. */
	private final String APPLICATION_NAME = "StrongBox";

	/** Directory to store user credentials for this application. */
	private final java.io.File DATA_STORE_DIR_DRIVE = new java.io.File(
			System.getProperty("user.home"), ".credentials/drive_strongbox");

	private String path = DATA_STORE_DIR_DRIVE + "/data.csv";

	/** Global instance of the {@link FileDataStoreFactory}. */
	private FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/drive-java-quickstart
	 */
	private final List<String> SCOPES = Arrays.asList(
	// TODO check scope only with DRIVE_APPDATA
	// DriveScopes.DRIVE_METADATA_READONLY, DriveScopes.DRIVE_FILE,
			DriveScopes.DRIVE_APPDATA);

	private ArrayList<Record> records = new ArrayList<>();
	private Drive service;
	private String fileId;

	public GoogleDriveModel() {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR_DRIVE);
			service = getDriveService();

			if (!hasDatafile()) {
				createDataFile();
			} else {
				System.out.println("Data GoogleDrive Connected");
			}
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = GoogleDriveModel.class
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
				+ DATA_STORE_DIR_DRIVE.getAbsolutePath());
		return credential;
	}

	/**
	 * Build and return an authorized Drive client service.
	 * 
	 * @return an authorized Drive client service
	 * @throws IOException
	 */
	public Drive getDriveService() throws IOException {
		Credential credential = authorize();
		return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}

	public String getFileID() throws IOException {

		return fileId;
	}

	public boolean hasDatafile() throws IOException {
		boolean dataExist = false;

		FileList files = service.files().list().setSpaces("appDataFolder")
				.setFields("nextPageToken, files(id, name)").execute();
		System.out.println(files);
		for (File file : files.getFiles()) {
			file.getId();
			if (file.getName().equals("data.csv")) {
				this.fileId = file.getId();

				dataExist = true;
			}
		}
		return dataExist;
	}

	public void createDataFile() throws IOException {

		File fileMetadata = new File();
		fileMetadata.setName("data.csv");
		fileMetadata.setParents(Collections.singletonList("appDataFolder"));
		File file = service.files().create(fileMetadata).setFields("id")
				.execute();
		System.out.println("File ID: " + file.getId());

	}

	public void downloadData() throws IOException {

		OutputStream outputStream = new ByteArrayOutputStream();
		service.files().get(getFileID())
				.executeMediaAndDownloadTo(outputStream);
		String cvsSplitBy = ",";
		Scanner input = new Scanner(outputStream.toString());
		while (input.hasNextLine()) {
			// separator
			String[] item = input.nextLine().split(cvsSplitBy);
			Record record = new Record(item[0], item[1], item[2], item[3],
					item[4], item[5], 0);
			records.add(record);
		}
		input.close();
	}

	public void uploadData() throws IOException {
		
		File file = new File();
		file.setTrashed(true);
		java.io.File filePath = new java.io.File(path);
		FileContent mediaContent = new FileContent("text/csv", filePath);
		service.files().update(getFileID(), file, mediaContent).execute();
		System.out.println("Data uploaded!");
	}

	public void deleteDataFile() throws IOException {
		
		service.files().delete(fileId).execute();
	}

	public ArrayList<Record> getRecords() {
		return records;

	}

	/*public static void main(String[] args) {

		Encryption enc = new Encryption("hx8&2RlYz2rqn&N^oiyKZG#35&P1RMkQ");
		GoogleDriveModel gdo = new GoogleDriveModel();

		try {
			// gdo.createDataFile();
			gdo.downloadData();
			// ArrayList<Record> records = new ArrayList<Record>();
			for (Record record : gdo.getRecords()) {
				System.out.println(record.toString());

			}

			
			 * gdo.writeRecordsToFile(gdo.getRecords());
			 * 
			 * System.out.println(gdo.getRecordList().toString());
			 * gdo.createNewRecord("Kaasboer", "kaas.nl", "boertje",
			 * "karnemelk12", "Food", "boerenkaas");
			 * gdo.createNewRecord("Groenteboer", "groenten.nl", "boertje",
			 * "komkommer12", "Food", "bio"); gdo.writeRecordsToFile();
			 * 
			 * gdo.uploadData();
			 
			// System.out.println(gdo.getFileID());
			// gdo.deleteDataFile();
			// System.out.println(gdo.hasDatafile());
		} catch (IOException e) {

			e.printStackTrace();
		}

	}*/

}
