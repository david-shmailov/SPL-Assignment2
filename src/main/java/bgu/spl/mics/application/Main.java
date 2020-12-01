package bgu.spl.mics.application;

import bgu.spl.mics.User;
import bgu.spl.mics.User2;
import bgu.spl.mics.application.passiveObjects.Diary;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;


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
			for(int i=0; i<user.attacks.length;i++)
			user2[i]=gson.fromJson(user.attacks[i],User2.class);
			reader.close();
		}catch (Exception e){ e.printStackTrace();};
		Diary diary=Diary.getInstance();

		/**
		 * david to use any thing form the input file you need to write user.
		 * or to get the attacks info use tje user2[]. every one is attack
		 * */









		/**
		 * write to out-put file
		 */
		try {
			Gson gson=new Gson();
			Writer writer = Files.newBufferedWriter(Paths.get(args[1]));
			writer.write("There are "+diary.getTotalAttacks() +" attacks.\n");
			writer.write("HanSolo and C3PO finish their tasks ~"
					+diary.Finish()+
					" milliseconds one after the other.\n");
			writer.write("All threads terminate ~"+diary.Terminate()+" milliseconds later\n");
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
