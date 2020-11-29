package bgu.spl.mics.application;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.Reader;
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
			Map<?, ?> map = gson.fromJson(reader, Map.class);

			System.out.println(map.get("R2D2"));
			System.out.println(map.get("Lando"));



			reader.close();

		}catch (Exception e){ e.printStackTrace();};
	}
}
