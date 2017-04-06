import java.io.Serializable;

public class InitiateTransfer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int type;

	private int windowSize;

	private long packetSize;

	private int numPackets;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

	public long getPacketSize() {
		return packetSize;
	}

	public void setPacketSize(long packetSize) {
		this.packetSize = packetSize;
	}

	public int getNumPackets() {
		return numPackets;
	}

	public void setNumPackets(int numPackets) {
		this.numPackets = numPackets;
	}

	@Override
	public String toString() {
		return "InitiateTransfer [type=" + type + ", windowSize=" + windowSize + ", packetSize=" + packetSize
				+ ", numPackets=" + numPackets + "]";
	}

}
