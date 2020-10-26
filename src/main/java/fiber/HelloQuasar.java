package fiber;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;

public class HelloQuasar {
	static final int SIZE = 10000;
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		Runnable r = new Runnable() {
			public void run() {
				calc();
			}
		};

		Thread threads[] = new Thread[SIZE];

		for (int i = 0; i < SIZE; i++) {
			//calc();
			threads[i] = new Thread(r);
			threads[i].start();
		}

		for (int i = 0; i < SIZE; i++) {
			threads[i].join();
		}

		System.out.println("Thread Time: " + (System.currentTimeMillis() - start));
	}

	private static void calc() {
		int result = 0;
		for (int m = 0; m < 10000; m++) {
			for (int i = 0; i < 200; i++) result += i;
		}
	}
}
