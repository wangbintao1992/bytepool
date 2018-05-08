
public class Slice {
	
	private Block b;
	private int index;
	
	public int offset(){
		return index * b.getLength();
	}
	public int length() {
		return b.getLength();
	}
	
	public Block getB() {
		return b;
	}
	public void setB(Block b) {
		this.b = b;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public boolean release() {
		return b.release(index);
	}
}
