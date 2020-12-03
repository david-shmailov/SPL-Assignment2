package bgu.spl.mics.application;

import bgu.spl.mics.User;
import bgu.spl.mics.User2;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;

import java.util.List;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;


/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		/**
		 * read input file to user1 and user2[]
		 */
		try {
			Gson gson = new Gson();
			Reader reader = Files.newBufferedReader(Paths.get(args[0]));
			User user = gson.fromJson(reader,User.class);
			User2 user2[] = new User2[user.attacks.length];
			for(int i=0; i<user.attacks.length;i++) {
				user2[i] = gson.fromJson(user.attacks[i], User2.class);
			}
			reader.close();
			/*
			this mess first initializes an array of Attacks. then it must go through each user2[i] "Attack"
			and make a real attack object using the relevant data. to do this it also has to transfer the current array of ints
			into a List data structure (chosen here to be vector). it then creates an attack object with said vector and the attack length,
			and pushes that new Attack object into the attacks array we created. this is all so we can initialize leia.
			TODO try to make this part more efficient , maybe create this attack array somehow straight from the json.
			 */
			Attack attacks[] = new Attack[user.attacks.length];
			for (int i = 0; i < user.attacks.length ; i++){
				List<Integer> list = new Vector<Integer>();
				for (int j = 0; j< user2[i].serials.length; j++){
					list.add(user2[i].serials[j]);
				}
				attacks[i] = new Attack(list , user2[i].serials.length);
			}



			//Main logic
			//init objects
			Ewoks ewokHome = new Ewoks(user.Ewoks);
			HanSoloMicroservice Han = new HanSoloMicroservice(ewokHome);
			C3POMicroservice C3PO = new C3POMicroservice(ewokHome);
			LeiaMicroservice Leia = new LeiaMicroservice(attacks);
			LandoMicroservice Lando = new LandoMicroservice(user.Lando);
			R2D2Microservice R2D2 = new R2D2Microservice(user.R2D2);
			Diary diary=Diary.getInstance();

			//init threads
			Thread han = new Thread(Han);
			Thread c3po = new Thread(C3PO);
			Thread leia = new Thread(Leia);
			Thread lando = new Thread(Lando);
			Thread r2d2 = new Thread(R2D2);
			//start the party
			han.start();
			c3po.start();
			leia.start();
			lando.start();
			r2d2.start();
			//wait for all of them to finish
			han.join();
			c3po.join();
			leia.join();
			lando.join();
			r2d2.join();



			gson=new Gson();
			Writer writer = Files.newBufferedWriter(Paths.get(args[1]));
			/**
			 writer.write("There are "+diary.getTotalAttacks() +" attacks.\n");
			 writer.write("HanSolo and C3PO finish their tasks ~"
			 +diary.Finish()+
			 " milliseconds one after the other.\n");
			 writer.write("All threads  terminate ~"+diary.Terminate()+" milliseconds later\n");
			 */

			writer.write(diary.toString());
			writer.close();

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
