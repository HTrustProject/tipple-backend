package nz.ac.waikato.isdb.ori.domain;

import java.util.Date;

@SuppressWarnings("unused")
public class QrCodeScan {
	private Integer studentId;
	private String group;
	private String qr;
	private Date time;
	private String hash;

	private Boolean valid;
	private String computedHash;

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getQr() {
		return qr;
	}

	public void setQr(String qr) {
		this.qr = qr;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getComputedHash() {
		return computedHash;
	}

	public void setComputedHash(String computedHash) {
		this.computedHash = computedHash;
	}
}
