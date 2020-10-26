public class TestVolatile {
	private volatile static int a = 0;

	public static void main(String[] args) throws  Exception{
		Thread t1 =
		new Thread(()->{
			for (int i = 0; i < 100000; i++){
//				synchronized (TestVolatile.class) {
//					a++;
//				}
				a++;
			}
		});
		Thread t2 = new Thread(()->{
			for (int i = 0; i < 100000; i++){
//				synchronized (TestVolatile.class) {
//					a++;
//				}
				a++;
			}
		});

		t1.start();
		t2.start();
		t1.join();
		t2.join();

		System.out.println(a);
	}
}
