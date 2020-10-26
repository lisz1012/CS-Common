package fiber;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;

import java.util.concurrent.ExecutionException;

public class HelloQuasar3 {
	private static final int SIZE = HelloQuasar.SIZE;
	public static void main(String[] args) throws Exception {
		int threadSize = 10;
		Thread threads[] = new Thread[threadSize];
		Runnable r = new Runnable() {
			@Override
			public void run() {
				int fiberSize = SIZE / threadSize;
				Fiber<Void> fibers[] = new Fiber[fiberSize];
				for (int i = 0; i < fiberSize; i++) {
					fibers[i] = new Fiber<>(new SuspendableRunnable(){
						@Override
						public void run() throws SuspendExecution, InterruptedException {
							calc();
						}
					});
				}
				for (int i = 0; i < fiberSize; i++) {
					fibers[i].start();
				}
				for (int i = 0; i < fiberSize; i ++) {
					try {
						fibers[i].join();
					} catch (ExecutionException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		long start = System.currentTimeMillis();
		for (int i = 0; i < threadSize; i++) {
			threads[i] = new Thread(r);
		}
		for (int i = 0; i < threadSize; i++) {
			threads[i].start();
		}
		for (int i = 0; i < threadSize; i++) {
			threads[i].join();
		}
		System.out.println("Time: " + (System.currentTimeMillis() - start));
	}

	private static void calc() {
		int result = 0;
		for (int m = 0; m < 10000; m++) {
			for (int i = 0; i < 200; i++) result += i;
		}
	}
}
