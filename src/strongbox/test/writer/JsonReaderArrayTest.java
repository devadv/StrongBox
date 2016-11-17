package strongbox.test.writer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReaderArrayTest {

	public static void main(String[] args) {
		File file = new File("res/test1.json");
		JSONParser parser = new JSONParser();
		JSONObject obj = new JSONObject();

		List<JSONObject> list = new ArrayList<>();

		try {
			FileReader reader = new FileReader(file);

			obj = (JSONObject) parser.parse(reader);
			System.out.println(obj.toJSONString());
			JSONArray jsonArray = new JSONArray();
			jsonArray = (JSONArray) obj.get("Providers");
			list.addAll(jsonArray);

			for (JSONObject jobj : list) {
				System.out.println(jobj.get("name"));
			}

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
