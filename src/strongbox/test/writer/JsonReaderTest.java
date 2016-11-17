package strongbox.test.writer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JsonReaderTest {

	public static void main(String[] args) {
		
		File file = new File("res/test.json");
		JSONParser parser = new JSONParser();
		JSONObject obj = new JSONObject();
		try {
			FileReader reader = new FileReader(file);
			
			obj = (JSONObject) parser.parse(reader);
			
			System.out.println(obj.toJSONString());
			System.out.println(obj.get("username"));
			System.out.println(obj.get("note"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
