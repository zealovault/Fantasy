package base.utilities;

import java.util.Random;

public class RandomBlock {	
	
	public static int Block(int start, int stop) {
		Random random = new Random();
		int r = (stop - start) + 1;
		int f = (int)(r * random.nextDouble());
		return start + f;
	}

}
