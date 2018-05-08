import java.util.Iterator;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

//块，判断，同步   AtomicIntegerArray 
//块，状态改变，同步 AtomicIntegerArray 

//片，写，同步 AtomicIntegerArray 
//片，状态更改，同步 AtomicIntegerArray 

//扩容？ ReentrantLock
//整块添加，同步 ReentrantLock
//清理 ConcurrentLinkedDeque
public class BytePool {
	
	private int length;
	
	private int slice;
	
	private int minSize;
	
	private ConcurrentLinkedDeque<Block> pool;
	
	private AtomicInteger size;
	
	public BytePool(int length, int slice,int minSize) {
		this.length = length;
		this.slice = slice;
		this.minSize = minSize;
		
		init();
		recycle();
	}


	private void init() {
		pool = new ConcurrentLinkedDeque<Block>();
		
		for(int i = 0 ; i < minSize; i ++) {
			pool.push(new Block(length,slice,i));
		}
		
		size = new AtomicInteger(minSize);
	}
	
	public Slice readSlice() {
		System.out.println("===size===" + size.get());
		Slice s = new Slice();
		
		Iterator<Block> it = pool.iterator();
		while(it.hasNext()) {
			Block next = it.next();
			int index = next.readAbleIndex();
			if(index != -1) {
				s.setB(next);
				s.setIndex(index);
				return s;
			}
		}
		
		System.out.println("===分配失败===");
		
		expand();
		return readSlice();
	}
	
	public boolean release(Slice s) {
		return s.release();
	}
	
	public void recycleTask() {
		
		
	}
	
	public void recycle() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("开始清理==========");
				final ConcurrentLinkedDeque<Block> p = pool;
				pool.removeIf(new Predicate<Block>() {

					@Override
					public boolean test(Block t) {
						if(size.get() > minSize && t.isLive()) {
							System.out.println("开始清理=====开始清理=====");
							size.decrementAndGet();
							return true;
						}
						return false;
					}
				});
			}
		}, 1, 3, TimeUnit.SECONDS);
		
	}
	
	private void expand() {
		System.out.println("开始扩容");
		size.incrementAndGet();
		pool.push(new Block(length,slice,pool.size()));
	}
}
