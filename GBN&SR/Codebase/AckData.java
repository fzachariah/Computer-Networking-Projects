import java.io.Serializable;

public class AckData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int ackNo;

	public int getAckNo() {
		return ackNo;
	}

	public void setAckNo(int ackNo) {
		this.ackNo = ackNo;
	}

	@Override
	public String toString() {
		return "AckData [ackNo=" + ackNo + "]";
	}
	
	
	
	

}
