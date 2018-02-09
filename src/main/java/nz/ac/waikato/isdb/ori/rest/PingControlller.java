package nz.ac.waikato.isdb.ori.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rheese
 */
@RestController
@RequestMapping(path = "/public")
public class PingControlller {

	@RequestMapping(path = "/ping", method = {RequestMethod.GET})
	public QrCodeScanResult ping() {
		return new QrCodeScanResult(4711);
	}
}
