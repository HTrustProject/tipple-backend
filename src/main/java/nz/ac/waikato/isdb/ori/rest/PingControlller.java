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
	public AppInfo ping() {
		return new AppInfo("1.0.0");
	}

	@SuppressWarnings("unused")
	class AppInfo {
		private String version;

		public AppInfo() {
		}

		public AppInfo(String version) {
			this.version = version;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}
	}
}
