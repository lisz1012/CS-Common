package fiber;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;

public class HelloQuasar2 {
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		Fiber<Void> fibers [] = new Fiber[HelloQuasar.SIZE];

		for (int i = 0; i < HelloQuasar.SIZE; i++) {
			// 用户空间，只在JVM级别上切换纤程，轻量级.OS级别的线程切换太重
			fibers[i] = new Fiber<Void>(new SuspendableRunnable() {
				public void run() throws SuspendExecution, InterruptedException {
					calc();
				}
			});
			fibers[i].start();
		}

		for (int i = 0; i < HelloQuasar.SIZE; i++) {
			fibers[i].join();
		}

		System.out.println("Fiber Time: " + (System.currentTimeMillis() - start));
	}

	private static void calc() {
		int result = 0;
		for (int m = 0; m < 10000; m++) {
			for (int i = 0; i < 200; i++) result += i;
		}
	}
}
