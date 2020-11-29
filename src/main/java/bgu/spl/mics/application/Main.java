package bgu.spl.mics.application;

import bgu.spl.mics.User;
import bgu.spl.mics.application.services.C3POMicroservice;
import bgu.spl.mics.application.services.LandoMicroservice;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		try {
			Gson gson = new Gson();
			Reader reader = Files.newBufferedReader(Paths.get("input.json"));
			User user = gson.fromJson(reader,User.class);
			System.out.println(user.Ewoks);
			System.out.println(user.attacks[0].get("duration"));


			reader.close();

		}catch (Exception e){ e.printStackTrace();};
	}
}
