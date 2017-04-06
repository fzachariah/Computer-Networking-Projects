import java.io.Serializable;

public class SegmentData implements Serializable,Comparable<SegmentData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int seqNum;
	private int checkSum;
	private char payLoad;

	boolean last = false;

	
	@Override
	public String toString() {
		return "SegmentData [seqNum=" + seqNum + ", checkSum=" + checkSum + ", payLoad=" + payLoad + "]";
	}

	public int getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	public int getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(int checkSum) {
		this.checkSum = checkSum;
	}

	public char getPayLoad() {
		return payLoad;
	}

	public void setPayLoad(char payLoad) {
		this.payLoad = payLoad;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	@Override
	public int compareTo(SegmentData o) {
		
		return this.getSeqNum()-(o.getSeqNum());
	}

}
