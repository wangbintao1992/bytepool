import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Test2 {
	public static void main(String[] args) throws Exception {
		byte[] b = ByteUtil.hexStringToByte("F1363843363341383334343436000000000000373CFFFF");
		System.out.println(Arrays.toString(b));
		
		Socket s = new Socket();
		s.connect(new InetSocketAddress(8002));
		for(int i = 0; i < 30000; i ++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						s.getOutputStream().write(b);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();;
		}
		
		TimeUnit.MINUTES.sleep(10);
	}
}
