package strongbox.test.io.json;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;


public class JsonWriterTest {
			
	
	public static void main(String[] args) {
		
				
		JSONObject obj = new JSONObject();
			obj.put("map", "Banken");
			obj.put("url", "Ing.nl");
			obj.put("username", "Pietje");
			obj.put("password", "123456");
			obj.put("note", "let op bankpas verloopt op 12-12-2018");
		
			System.out.println(obj.toJSONString());
		

		File file = new File("res/test.json");
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(obj.toJSONString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}

}
