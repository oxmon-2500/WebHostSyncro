package at.noe.szb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Config {
    private String host;
    private String user;
    private String password;
  
    private String source; //local source path
    private String remote; // remote path
    private String ignore []; //files not to transfer 
    private int timeout;
    protected static Config create(final String json) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
		return objectMapper.readValue(json, Config.class);
	  } catch (JsonProcessingException e) {
		e.printStackTrace();
	  }
      return null;
    }
    public static Config create(final Path jsonPath) {
	  byte bf[]=null;
	  try {
		bf = Files.readAllBytes(jsonPath);
	  } catch (IOException e) {
		// TODO Auto-generated catch block
 		e.printStackTrace();
 		return null;
	  } 
      return Config.create(new String(bf));
    }
    protected static String stripComments(final String inp) {
	  int state = 0;
	  char ch;
	  StringBuffer ret = new StringBuffer();
	  for(int x=0; x<inp.length(); ++x) {
		  ch = inp.charAt(x);
		  switch(state) {
		  case 0:
			  switch(ch) {
			  case '"' : state = 1; break;
			  case '/' : state = 2; continue;
			  case '\\': state = 10; break;
			  }
			  ret.append(ch);
			  break;
		  case 10: // after \
			  ret.append(ch);
			  state = 0;
			  break;
		  case 1: //String  
			  ret.append(ch);
			  switch (ch) {
			  case '"' : state=0; continue;
			  case '\\': state=11; continue;
			  }
			  break;
		  case 11: // after "...\
			  ret.append(ch);
			  state = 1;
			  break;
		  case 2: // after /
			  switch (ch) {
			  case '/': state = 3; break;
			  case '*': state = 4; break;
			  }
			  break;
		  case 3: // after //
			  if (ch==0x0A || ch==0x0D) {
				  ret.append(ch);
				  state = 0;
				  break;
			  }
			  break;
		  case 4: // after /*
			  if (ch=='*') {
				  state=5;
				  break;
			  }
			  break;
		  case 5: //after /*....*
			  if (ch=='/') {
				  break;
			  }
			  ret.append(ch);
			  state=0;
			  break;
		  }
	  }
	  return ret.toString();
  }
  
    public String getHost()    {    return host;  }
    public String getUser()    {    return user;  }
    public String getPassword(){    return password;  }
    public String getSource()   {    return source;  }
    public String getRemote()   {    return remote;  }
    public int getTimeout()     {    return timeout;  }
    public String[] getIgnore() {    return ignore;  }
  
    public void setHost(String v)          {    this.host = v;  }
    public void setUser(String v)          {    this.user = v;  }
    public void setPassword(String v)      {    this.password = v;  }
    public void setSource(String source)   {    this.source = source;  }
    public void setRemote(String remote)   {    this.remote = remote;  }
    public void setIgnore(String[] ignore) {    this.ignore = ignore;  }
    public void setTimeout(int timeout)    {    this.timeout = timeout;  }
}
