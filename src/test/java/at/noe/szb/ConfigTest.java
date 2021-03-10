package at.noe.szb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
// see: https://github.com/FasterXML/jackson-core/tree/jackson-core-2.12.2/src
public class ConfigTest {
    final String config_json = 
            "{"+
            "\"source\" : \"src\","+
            "\"remote\"  : \"/httpdocs/oxmon.com\","+
            "\"ignore\"  : [\".gitignore\", \".git/\", \"szb*\"],"+
            "\"timeout\" : 12"+
         "}";
    @Test
    public void test01() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = objectMapper.readValue(config_json, Config.class);
        assertEquals(config.getSource(), "src");
        assertEquals(config.getIgnore().length, 3);
    }        
    @Test
    public void test02() throws Exception{
        Config cfg = Config.create(config_json);
        assertEquals(cfg.getSource(), "src");
        assertEquals(cfg.getIgnore().length, 3);
    }
    @Test
    public void test_comments() throws Exception{
    	assertEquals(Config.stripComments(""), "");
    	assertEquals(Config.stripComments(" "), " ");
    	assertEquals(Config.stripComments("\r\n"), "\r\n");
    	assertEquals(Config.stripComments("{ //comment\r\n\"a\":\"x\" }")  , "{ \r\n\"a\":\"x\" }");
    	assertEquals(Config.stripComments("{ /*comment*/\r\n\"a\":\"x\" }"), "{ \r\n\"a\":\"x\" }");
    	assertEquals(Config.stripComments("{ \r\n\"a\"/*comment*/:\"x\" }"), "{ \r\n\"a\":\"x\" }");
    }
}
