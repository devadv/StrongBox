package strongbox.test.io.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JsonWriterArrayTest {

	public static void main(String[] args) {

		JSONArray providers = new JSONArray();

		JSONObject obj = new JSONObject();
		obj.put("name", "Telfort");
		obj.put("username", "ansems");
		obj.put("password", "12345");
		obj.put("email", "ansems@telfort.nl");

		JSONObject obj1 = new JSONObject();
		obj1.put("name", "KPN");
		obj1.put("username", "devries");
		obj1.put("password", "54321");

		providers.add(obj);
		providers.add(obj1);

		JSONArray onlinewinkelen = new JSONArray();

		JSONObject obj2 = new JSONObject();
		obj2.put("name", "Zalando");
		obj2.put("username", "ansems");
		obj2.put("password", "12345");

		JSONObject obj3 = new JSONObject();
		obj3.put("name", "Sting");
		obj3.put("username", "devries");
		obj3.put("password", "54321");

		onlinewinkelen.add(obj2);
		onlinewinkelen.add(obj3);

		JSONObject mappen = new JSONObject();
		mappen.put("Providers", providers);
		mappen.put("Online Winkelen", onlinewinkelen);

		System.out.println(mappen.toJSONString());

		File file = new File("res/test1.json");
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(mappen.toJSONString());
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
