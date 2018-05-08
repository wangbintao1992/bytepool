import java.util.concurrent.TimeUnit;


public class Test {
	public static void main(String[] args) throws Exception {
		
		//int sliceSize, int slice,int num
		//5�㣬3�ֿ飬21��
		BytePool p = new BytePool(21, 3, 5);
		
		for(int i = 0; i < 1000; i ++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
						for(int j = 0; j < 10; j ++) {
							Slice r = p.readSlice();
							System.out.println(Thread.currentThread().getName() + " index:" + r.getIndex() + " offset:" + r.offset() + " name:" + r.getB().getName());
							
							/*try {
								TimeUnit.SECONDS.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}*/
							System.out.println(r.release());
						}
				}
			}).start();
		}
		
		
		
		TimeUnit.MINUTES.sleep(3);
	}
}
