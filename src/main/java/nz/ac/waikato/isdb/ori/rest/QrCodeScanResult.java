package nz.ac.waikato.isdb.ori.rest;

@SuppressWarnings("unused")
public class QrCodeScanResult {
	private int code;

	public QrCodeScanResult() {
	}

	public QrCodeScanResult(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
