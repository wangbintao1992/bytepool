import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceTest {

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        
        TimerTask timerTask = new TimerTask(2000); // ������Ҫ 2000 ms ����ִ�����

        System.out.printf("��ʼʱ�䣺%s\n\n", new SimpleDateFormat("HH:mm:ss").format(new Date()));

        // ��ʱ 1 ��󣬰� 3 �������ִ������
        timer.scheduleAtFixedRate(timerTask, 1000, 3000, TimeUnit.MILLISECONDS);
    }

    private static class TimerTask implements Runnable {

        private final int sleepTime;
        private final SimpleDateFormat dateFormat;

        public TimerTask(int sleepTime) {
            this.sleepTime = sleepTime;
            dateFormat = new SimpleDateFormat("HH:mm:ss");
        }

        @Override
        public void run() {
            System.out.println("����ʼ����ǰʱ�䣺" + dateFormat.format(new Date()));

            System.out.println("ģ����������...");

            System.out.println("�����������ǰʱ�䣺" + dateFormat.format(new Date()));
            System.out.println();
        }

    }
}