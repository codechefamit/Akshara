
import java.io.InputStream;
import java.util.Scanner;

class SpellCheck {
	Trie t;
	double elapsedTime;
	SpellCheck() {
		long startTime,stopTime;
		t = new Trie();
		InputStream i=getClass().getResourceAsStream("res/words.txt");
		Scanner in;
		in = new Scanner(i);
		startTime=System.nanoTime();
		while(in.hasNext()){
			t.insert(in.next());
		}
		stopTime=System.nanoTime();
		elapsedTime=(double)stopTime-startTime;
		in.close();
	}
	
	boolean check(String str){
		if(t.search(str))
			return true;
		else
			return false;
	}
}
