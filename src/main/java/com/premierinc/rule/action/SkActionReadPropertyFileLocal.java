package com.premierinc.rule.action;

import com.premierinc.rule.action.enums.SkActionContext;
import com.premierinc.rule.run.SkRuleRunner;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;

import static org.apache.camel.component.xslt.XsltOutput.file;

/**
 * Read a property file into the GLOBAL Context.
 */
public class SkActionReadPropertyFileLocal extends SkAction {

	private String filename;

	@Override
	public void run(SkRuleRunner inRunner) {
		try {
			filename = (String) inRunner.getValue(filename, filename);

			System.out.println("=========================================");
			System.out.println("filename : " + filename);
			System.out.println("=========================================");
			ClassPathResource resource = new ClassPathResource(filename);
			File file = resource.getFile();
			Properties properties = new Properties();
			properties.load(new FileReader(file));
			properties.stringPropertyNames()
					.forEach(name -> {
						String value = properties.getProperty(name);
						inRunner.setValue(name.toUpperCase(), value);
					});
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(final String inFilename) {
		filename = inFilename;
	}

	public SkActionContext getActionContext() {
		return SkActionContext.NOW;
	}
}
