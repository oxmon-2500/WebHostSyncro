package at.noe.szb;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTest{
    JSONObject jsonObject;
	
	public static JSONObject readJsonSimpleDemo(String filename) throws Exception {
	    FileReader reader = new FileReader(filename);
	    JSONParser jsonParser = new JSONParser();
	    return (JSONObject)jsonParser.parse(reader);
	}
	@Test
    public void test_01() throws Exception{
    	jsonObject = readJsonSimpleDemo("src/test/resources/test_01.json");
    	String name = (String)jsonObject.get("name");
    	assertEquals(name, "Benjamin Watson");
    	JSONArray kids = (JSONArray)jsonObject.get("kids");
    	JSONObject kid = (JSONObject)kids.get(0);
    	assertEquals(kid.get("name"), "Billy");
    	kid = (JSONObject)kids.get(1);
    	assertEquals(kid.get("name"), "Milly");
    }
	@Test
    public void test_02() throws Exception{
    	jsonObject = readJsonSimpleDemo("src/test/resources/test_02.json");
    	String name = (String)jsonObject.get("source");
    	assertEquals(name, "src");
	}
	
	@Test
    public void test_03() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
    	Map<String, String> map = mapper.readValue(new File("src/test/resources/test_02.json"), Map.class);
    	assertEquals(map.get("source"), "src");
	}
}
