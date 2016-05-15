package com.premierinc.common.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.premierinc.rule.base.SkRuleBase;
import com.premierinc.rule.base.SkRules;
import com.premierinc.rule.common.JsonMapperHelper;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 *
 */
public class TstFileHelper {

	private static Logger log = LoggerFactory.getLogger(TstFileHelper.class);

	private TstFileHelper() {
	}

	/**
	 * Read Actions from file.
	 */
	public static SkRules buildThingsFromDirectory(String filePath) {

		String json = "";
		SkRules returnRules = new SkRules();
		try {
			ObjectMapper objectMapper;
			ClassPathResource resource = new ClassPathResource(filePath);
			File topDir = resource.getFile();
			if (!topDir.exists()) {
				throw new IllegalArgumentException(
						String.format("File '%s' does NOT exist.", topDir.getAbsolutePath()));
			}

			File[] files = topDir.listFiles();
			for (File file : files) {

				SkRuleBase rule = null;
				SkRules rules = null;

				System.out.println("--------------------------------------------------------");
				String fullFilePath = file.getAbsolutePath();
				System.out.println(String.format("File : %s", fullFilePath));
				System.out.println("--------------------------------------------------------");

				if (!fullFilePath.toLowerCase()
						.endsWith(".json")) {
					continue;
				}

				try {
					rule = buildThings(fullFilePath, SkRuleBase.class, "rule");
				} catch (Exception e) {
					rules = buildThings(fullFilePath, SkRules.class, "rules");
				}
				if (null != rule) {
					returnRules.addRule(rule);
				}
				if (null != rules) {
					returnRules.addRules(rules);
				}
			}
			return returnRules;

		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Read Actions from file.
	 */
	public static <C> C buildThings(String filePath, Class inClazz, String inRootName) {
		String json = "";
		C things = null;
		try {
			ObjectMapper objectMapper;

			File file = null;

			try {
				ClassPathResource resource = new ClassPathResource(filePath);
				file = resource.getFile();
			} catch (Exception e) {
				file = new File(filePath);
			}
			if (null == file && !file.exists()) {
				throw new IllegalArgumentException(String.format("File '%s' does NOT exist.", file.getAbsolutePath()));
			}
			objectMapper = JsonMapperHelper.jsonMapper();
			things = (C) objectMapper.readValue(file, inClazz);

			// Now turn the bean to json and back to a second bean.
			// This part is only for the tests.
			// Add inRootName as the root name.
			json = JsonMapperHelper.beanToJsonString(things, inRootName);

			if (log.isInfoEnabled()) {
				log.info(json);
			}

			C things2 = (C) objectMapper.readValue(json, inClazz);
			return things2;

		} catch (Exception e) {
			String prettyJson = JsonMapperHelper.beanToJsonPretty(things, inRootName);
			log.error(String.format("\n%s\n%s\n%s", e.toString(), prettyJson, json));
			throw new IllegalArgumentException(json, e);
		}
	}
}
