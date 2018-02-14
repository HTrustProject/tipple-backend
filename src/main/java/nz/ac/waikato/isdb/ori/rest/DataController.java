package nz.ac.waikato.isdb.ori.rest;

import nz.ac.waikato.isdb.ori.domain.QrCodeScan;
import nz.ac.waikato.isdb.ori.service.DataException;
import nz.ac.waikato.isdb.ori.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author ralf.heese
 * @since 07-Jun-16
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping(path = "/data")
public class DataController {
	static final int SUCCESS = 0;
	static final int ERROR = -1;

	private DataService dataService;

	@Autowired
	public DataController(DataService dataService) {
		this.dataService = dataService;
	}

	@PostMapping()
	public QrCodeScanResult store(@RequestBody QrCodeScan scan) throws DataException {
		QrCodeScan updated = dataService.save(scan);
		return new QrCodeScanResult(updated.getValid() ? SUCCESS : ERROR);
	}

	@GetMapping("/count")
	public CountResult count() throws DataException {
		return new CountResult(dataService.count());
	}

	@PostMapping("/test")
	public QrCodeScan test(@RequestBody QrCodeScan scan) {
		scan.setValid(dataService.isValidHash(scan));
		scan.setComputedHash(dataService.getHash(scan));
		scan.setFormattedDate(dataService.getFormattedDate(scan.getTime()));
		return scan;
	}

}
