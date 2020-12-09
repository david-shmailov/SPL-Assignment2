package bgu.spl.mics.application;

import bgu.spl.mics.User;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;


/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	static final CountDownLatch latch = new CountDownLatch(2);
	public static void main(String[] args) {
		/**
		 * read input file to user
		 */
		try {
			Gson gson = new Gson();
			Reader reader = Files.newBufferedReader(Paths.get(args[0]));
			User user = gson.fromJson(reader,User.class);
			Attack attacks[] = new Attack[user.attacks.length];
			for(int i=0; i<user.attacks.length;i++) {
				attacks[i] = gson.fromJson(user.attacks[i], Attack.class);
			}
			reader.close();



			//Main logic
			//init objects

			Diary diary=Diary.getInstance();
			Ewoks ewokHome = new Ewoks(user.Ewoks);
			HanSoloMicroservice Han = new HanSoloMicroservice(ewokHome,latch);
			C3POMicroservice C3PO = new C3POMicroservice(ewokHome,latch);
			LeiaMicroservice Leia = new LeiaMicroservice(attacks);
			LandoMicroservice Lando = new LandoMicroservice(user.Lando);
			R2D2Microservice R2D2 = new R2D2Microservice(user.R2D2);

			//init threads
			Thread han = new Thread(Han);
			Thread c3po = new Thread(C3PO);
			Thread leia = new Thread(Leia);
			Thread lando = new Thread(Lando);
			Thread r2d2 = new Thread(R2D2);
			//start the party
			han.start();
			c3po.start();
			latch.await(); // yair said in the forum we can and should use this method
			lando.start();
			r2d2.start();
			leia.start();
			//wait for all of them to finish
			han.join();
			c3po.join();
			lando.join();
			r2d2.join();
			leia.join();

			Writer writer = Files.newBufferedWriter(Paths.get(args[1]));
			writer.write(diary.toString());
			writer.close();

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
