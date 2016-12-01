package strongbox.test.login.mvc;

public class MainLogin {

	public static void main(String[] args) {
		
		PropertiesModel model = new PropertiesModel();
		LoginController controller = new LoginController( model);
		controller.login();
		
		
	}

}
