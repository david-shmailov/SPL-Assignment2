package bgu.spl.mics.application;

import bgu.spl.mics.User;
import bgu.spl.mics.User2;
import com.google.gson.Gson;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;


/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		try { /** here we are reading the input file, its save now at user and attaks inf in usr2[]    */
			Gson gson = new Gson();
			Reader reader = Files.newBufferedReader(Paths.get("input.json"));
			User user = gson.fromJson(reader,User.class);
			User2 user2[] = new User2[user.attacks.length];
			for(int i=0; i<user.attacks.length;i++)
			user2[i]=gson.fromJson(user.attacks[i],User2.class);
			reader.close();
		}catch (Exception e){ e.printStackTrace();};




	}
}
