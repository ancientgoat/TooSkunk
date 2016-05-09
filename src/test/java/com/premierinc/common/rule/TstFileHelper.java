package com.premierinc.common.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.premierinc.rule.commands.SkCondition;
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
	public static <C> C buildThings(String filePath, Class inClazz, String inRootName) {
		String json = "";
		try {
			ObjectMapper objectMapper;
			ClassPathResource resource = new ClassPathResource(filePath);
			File file = resource.getFile();
			if (!file.exists()) {
				throw new IllegalArgumentException(String.format("File '%s' does NOT exist.", file.getAbsolutePath()));
			}
			objectMapper = JsonMapperHelper.jsonMapper();
			C things = (C) objectMapper.readValue(file, inClazz);

			// Now turn the bean to json and back to a second bean.
			// This part is only for the tests.
			// Add 'actions' as the root name.
			json = JsonMapperHelper.beanToJsonString(things, inRootName);

			if (log.isInfoEnabled()) {
				log.info(json);
			}
			System.out.println(json);

			C things2 = (C) objectMapper.readValue(json, inClazz);
			return things2;

		} catch (Exception e) {
			throw new IllegalArgumentException(json, e);
		}
	}

}
