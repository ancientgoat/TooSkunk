package com.premierinc.rule.action;

import com.premierinc.rule.action.enums.SkActionContext;
import com.premierinc.rule.action.enums.SkActionType;
import com.premierinc.rule.run.SkGlobalContext;
import com.premierinc.rule.run.SkRuleRunner;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;

import static jdk.nashorn.internal.codegen.OptimisticTypesPersistence.load;

/**
 *
 */
public class SkActionReadPropertyFile extends SkAction {

	private String filename;

	@Override
	public SkActionType getActionType() {
		return SkActionType.READPROPERTYFILE;
	}

	@Override
	public void execute(SkRuleRunner inRunner) {
		try {
			ClassPathResource resource = new ClassPathResource(filename);
			File file = resource.getFile();
			Properties properties = new Properties();
			properties.load(new FileReader(file));
			properties.stringPropertyNames()
					.forEach(name -> {
						String value = properties.getProperty(name);
						SkGlobalContext.addValue(name, value);
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
