package strongbox.drive;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.time.Instant;
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
	/** Directory to store user data for this application. */
	private final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".strongbox");
	/** CSV file to store data with encrypted passwords */
	private final String pathMaster = DATA_STORE_DIR + "/data.csv";
	/** Properties file to store the passphrase */
	private final String pathPassphrase = DATA_STORE_DIR
			+ "/config.passphrase.properties";
	/** Global instance of the {@link FileDataStoreFactory}. */
	private FileDataStoreFactory DATA_STORE_FACTORY;
	/** Global instance of the JSON factory. */
	private final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();
	/** Global instance of the HTTP transport. */
	private HttpTransport HTTP_TRANSPORT;
	/** View and manage its own configuration data in your Google Drive. */
	private final List<String> SCOPES = Arrays
			.asList(DriveScopes.DRIVE_APPDATA);
	/** ArrayList to store googledrive data. */
	private ArrayList<Record> records = new ArrayList<>();
	// Manages files in Drive including uploading, downloading, searching,
	// detecting changes, and updating sharing permissions.
	private Drive service;
	/** data.csv file id to get access to file */
	private String dataFileId;
	/** config.passphrase.properties file id to get access to file */
	private String passphraseFileId;

	private Model model;

	private Instant start;

	public GoogleDriveModel(Model model) {
		start = Instant.now();
		this.model = model;
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR_DRIVE);
			service = getDriveService();

			if (!hasFile("data.csv")
					&& !hasFile("config.passphrase.properties")) {
				createFile("data.csv");
				createFile("config.passphrase.properties");
				getID("data.csv");
				getID("config.passphrase.properties");
				uploadData(pathPassphrase);
			} else {
				setDataFileId(getID("data.csv"));
				setPassphraseFileId(getID("config.passphrase.properties"));
				if (!passphraseExistsLocal()) {
					downloadPassphrase();
				}
				System.out.println("Data GoogleDrive Connected");
				Instant after = Instant.now();
				long delta = Duration.between(start, after).toMillis();
				System.out.println("Connecting time: " + delta) ;
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
		// System.out.println("Credentials saved to " +
		// DATA_STORE_DIR_DRIVE.getAbsolutePath());
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

		return dataFileId;
	}

	/**
	 * checks existence of data.csv in appDataFolder
	 * 
	 * @param String
	 *            filename name of file
	 * @return boolean true when data.csv exists
	 * @throws IOException
	 */
	public boolean hasFile(String filename) throws IOException {
		boolean dataExist = false;

		FileList files = service.files().list().setSpaces("appDataFolder")
				.setFields("nextPageToken, files(id, name)").execute();
		// System.out.println(files);
		for (File file : files.getFiles()) {
			if (file.getName().equals(filename)) {
				dataExist = true;
			}
		}
		return dataExist;
	}


	/**
	 * gets id of file
	 * 
	 * @param String
	 *            filename name of file
	 * @throws IOException
	 */
	public String getID(String filename) throws IOException {
		String fileid = "";
		FileList files = service.files().list().setSpaces("appDataFolder")
				.setFields("nextPageToken, files(id, name)").execute();

		for (File file : files.getFiles()) {

			if (file.getName().equals(filename)) {
				fileid = file.getId();
			}

		}
		return fileid;
	}

	/** create a file in the appDataFolder */
	public void createFile(String fileName) throws IOException {

		File fileMetadata = new File();
		fileMetadata.setName(fileName);
		fileMetadata.setParents(Collections.singletonList("appDataFolder"));
		/*File file = service.files().create(fileMetadata).setFields("id")
				.execute();
		// System.out.println("File ID: " + file.getId());
*/
	}

	public void downloadRecords() throws IOException {
		Instant beforedownloading = Instant.now();
		
		
		System.out.println("Start Downloading.... ") ;

		OutputStream outputStream = new ByteArrayOutputStream();
		service.files().get(getFileID())
				.executeMediaAndDownloadTo(outputStream);
		String cvsSplitBy = ",";
		// System.out.println("Outputstream : " + outputStream.toString());
		if (outputStream.toString().isEmpty()) {
			System.out.println("Empty data.csv");
		}
		Scanner input = new Scanner(outputStream.toString());

		while (input.hasNextLine()) {
			// separator
			String[] item = input.nextLine().split(cvsSplitBy);
			model.createNewRecord(item[0], item[1], item[2], item[3], item[4],
					item[5], 0);

		}
		// TODO is the following procedure necessary or has the model already
		// been updated
		records.addAll(model.getRecordList());
		// close scanner
		input.close();
		Instant end = Instant.now();
		long delta = Duration.between(beforedownloading, end).toMillis();
		System.out.println("Downloading time: " + delta) ;
		delta = Duration.between(start, end).toMillis();
		System.out.println("Total time: " + delta/1000.0 + " sec") ;
		
	}

	public void downloadPassphrase() throws IOException {

		// System.out.println("Start Outputstream");
		OutputStream outputStream = new ByteArrayOutputStream();
		service.files().get(passphraseFileId)
				.executeMediaAndDownloadTo(outputStream);
		// System.out.println(outputStream.toString());
		String content = outputStream.toString();
		java.io.File file = new java.io.File(pathPassphrase);

		try (FileOutputStream fop = new FileOutputStream(file)) {

			// if file doesn't exists, then create it
			if (!file.exists()) {
				System.out.println("Create " + pathPassphrase);
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean passphraseExistsLocal() {

		java.io.File file = new java.io.File(pathPassphrase);

		return file.exists();

	}

	public void uploadData(String path) throws IOException {

		File file = new File();
		file.setTrashed(true);
		java.io.File filePath = new java.io.File(path);

		if (path.equals(pathMaster)) {
			FileContent mediaContent = new FileContent("text/csv", filePath);
			service.files().update(dataFileId, file, mediaContent).execute();
		} else {
			FileContent mediaContent = new FileContent("text/plain", filePath);
			service.files().update(passphraseFileId, file, mediaContent)
					.execute();
		}

		System.out.println(path + " uploaded!");
	}

	public void deleteDataFile() throws IOException {

		service.files().delete(dataFileId).execute();
		service.files().delete(passphraseFileId).execute();
	}

	public ArrayList<Record> getRecords() {
		return records;

	}

	public void setDataFileId(String dataFileId) {
		this.dataFileId = dataFileId;
	}

	public void setPassphraseFileId(String propertiesFileId) {
		this.passphraseFileId = propertiesFileId;
	}

}
