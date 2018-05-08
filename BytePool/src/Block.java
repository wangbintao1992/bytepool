import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Block {
	
	private int length;
	
	private int slice;
	
	private AtomicIntegerArray index;
	
	private int name;
	
	private byte[] block;
	
	private AtomicInteger live;
	
	public Block(int length, int slice,int name) {
		this.length = length;
		this.slice = slice;
		this.name = name;
		init();
	}
	
	public void init() {
		index = new AtomicIntegerArray(slice);
		block = new byte[length * slice];
		live = new AtomicInteger(slice);
	}
	
	public int readAbleIndex() {
		for(int i = 0; i < slice;i ++) {
			if(index.compareAndSet(i, 0, 1)) {
				live.decrementAndGet();
				//lock
				return i;
			}
		}
		
		return -1;
	}
	
	public boolean release(int i) {
		//unlock
		live.incrementAndGet();
		return index.compareAndSet(i, 1, 0);
	}
	
	public boolean isLive() {
		return live.get() == slice;
	}
	
	public byte[] get() {
		return block;
	}

	public int getSlice() {
		return slice;
	}

	public void setSlice(int slice) {
		this.slice = slice;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
