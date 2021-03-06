package nz.ac.waikato.isdb.ori.service;

import nz.ac.waikato.isdb.ori.domain.QrCodeScan;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class DataService {
	private final static String DATA_FIR = "/home/ralfh/ori";
	private final static String DATA_FILE = "ori-2018.txt";

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final String lock = "data_file_lock";

	private static final String QR_VALID_PREFIX = "18_uow_module_";

	public QrCodeScan save(QrCodeScan scan) throws DataException {
		boolean valid = isValidHash(scan);
		scan.setValid(valid);

		if (valid) {
			String data = String.format("%d\t\"%s\"\t\"%s\"\t\"%s\"\n",
					scan.getStudentId(), scan.getGroup(), scan.getQr(), getFormattedDate(scan.getTime()));
			writeData(data);
		}

		return scan;
	}

	protected void writeData(String data) throws DataException {
		File dataFile = getDataFile();
		synchronized (lock) {
			try (FileWriter fw = new FileWriter(dataFile, true)) {
				fw.append(data);
				fw.flush();
			} catch (IOException e) {
				throw new DataException(e);
			}
		}
	}

	public int count() throws DataException {
		File file = getDataFile();
		if (!file.exists() || !file.canRead()) {
			return -1;
		}

		try {
			List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
			return lines.size();
		} catch (IOException e) {
			throw new DataException(e);
		}
	}

	protected File getDataFile() {
		return Paths.get(DATA_FIR, DATA_FILE).toFile();
	}

	public boolean isValidHash(QrCodeScan scan) {
		return isValid(scan) && scan.getHash().equals(getHash(scan));
	}

	public boolean isValid(QrCodeScan scan) {
		return scan != null && scan.getStudentId() != null && isNotBlank(scan.getGroup()) &&
				isNotBlank(scan.getQr()) && scan.getTime() != null && isNotBlank(scan.getHash()) &&
				scan.getQr().startsWith(QR_VALID_PREFIX);
	}

	public String getHash(QrCodeScan scan) {
		if (!isValid(scan)) {
			return null;
		}

		try {
			String toBeValidated = String.format("%d#%s#%s#%d",
					scan.getStudentId(), scan.getGroup(), scan.getQr(), scan.getTime().getTime());
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(toBeValidated.getBytes());
			return DatatypeConverter.printHexBinary(digest).toLowerCase();
		} catch (NoSuchAlgorithmException ignored) {
			return null;
		}
	}

	public String getFormattedDate(Date ms) {
		return dateFormat.format(ms);
	}
}
