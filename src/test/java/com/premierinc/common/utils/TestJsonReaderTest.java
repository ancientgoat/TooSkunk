package com.premierinc.common.utils;

import com.premierinc.rule.common.SkJsonFilenameFilter;
import com.premierinc.rule.common.SkJsonWalkDir;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 *
 */
public class TestJsonReaderTest {

	static final String JSON_PATH_DISK = "NumericOneRuleTest.json";
	static final String JSON_PATH_JAR = "testjar/TestJar.jar";

	private static Logger log = LoggerFactory.getLogger(TestJsonReaderTest.class);

	/**
	 *
	 */
	@Test
	public void testReadJsonFilesFromDiskTest() {
		try {
			ClassPathResource resource = new ClassPathResource(JSON_PATH_DISK);
			File file = resource.getFile();
			File parentFile = file.getParentFile();
			log.info(parentFile.getAbsolutePath());

			SkJsonFilenameFilter filenameFilter = new SkJsonFilenameFilter() {
			};
			SkJsonWalkDir walkDir = new SkJsonWalkDir(parentFile, filenameFilter);
			Integer size = walkDir.size();
			log.info(String.format("Size : " + size));

			Assert.assertTrue("We expected some '.json' files.", 0 < size);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	//	/**
	//	 *
	//	 */
	//	@Test
	//	public void testReadJsonFilesFromJarTest() {
	//		try {
	//			ClassPathResource resource = new ClassPathResource(JSON_PATH_DISK);
	//
	//			System.out.println("------------------------------------");
	//			System.out.println(resource);
	//			System.out.println(resource.getPath());
	//			System.out.println(resource.getFilename());
	//			System.out.println(resource.toString());
	//			System.out.println("------------------------------------");
	//
	//			File file = resource.getFile();
	//			File parentFile = file;//.getParentFile();
	//			String absolutePath = parentFile.getAbsolutePath()
	//					.replace(File.separator, "/");
	//			absolutePath = String.format("file:///%s", absolutePath);
	//			log.info(absolutePath);
	//
	//			SkJsonFilenameFilter filenameFilter = new SkJsonFilenameFilter() {
	//			};
	//			SkJsonReaderHelper readerHelper = new SkJsonReaderHelper();
	//			SkJarFile jarFile = readerHelper.readJarredJsonFiles(absolutePath, filenameFilter);
	//			Integer size = jarFile.size();
	//			log.info(String.format("Size : %d", size));
	//
	//			Assert.assertTrue("We expected some '.json' files.", 0 < size);
	//		} catch (Exception e) {
	//			throw new IllegalArgumentException(e);
	//		}
	//	}
}
