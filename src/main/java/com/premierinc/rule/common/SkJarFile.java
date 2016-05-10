package com.premierinc.rule.common;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SkJarFile implements SkJsonReader<JarEntry> {

	private Logger log = LoggerFactory.getLogger(SkJarFile.class);
	private JarFile jarFile;
	private List<JarEntry> entryList = Lists.newArrayList();

	public JarFile getJarFile() {
		return jarFile;
	}

	public void setJarFile(final JarFile inJarFile) {
		jarFile = inJarFile;
	}

	public List<JarEntry> getEntryList() {
		return entryList;
	}

	public void setEntryList(final List<JarEntry> inEntryList) {
		entryList = inEntryList;
	}

	public void addEntry(final JarEntry inEntry) {
		entryList.add(inEntry);
	}

	/**
	 *
	 */
	@Override
	public Integer size() {
		return entryList.size();
	}

	/**
	 *
	 */
	@Override
	public String entryToString(JarEntry inJarEntry) {
		try {
			return IOUtils.toString(jarFile.getInputStream(inJarEntry), "UTF-8");
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 *
	 */
	@Override
	public Stream<JarEntry> stream() {
		return this.entryList.stream();
	}
}
