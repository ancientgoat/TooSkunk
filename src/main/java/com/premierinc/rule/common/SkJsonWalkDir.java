package com.premierinc.rule.common;

/**
 *
 */

import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.apache.commons.io.IOUtils;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * We walk a directory tree and create a list of JSON files that can be fetched by the caller.
 */
public class SkJsonWalkDir implements FileVisitor<Path>, SkJsonReader<Path> {

	private Set<Path> jsonPaths = Sets.newHashSet();
	private SkJsonFilenameFilter filenameFilter;

	private SkJsonWalkDir() {
	}

	/**
	 *
	 */
	public SkJsonWalkDir(@NotNull File inTopDir) {
		initInstance(inTopDir, new SkJsonFilenameFilter() {
		});
	}

	/**
	 *
	 */
	public SkJsonWalkDir(@NotNull File inTopDir, @NotNull SkJsonFilenameFilter inFilenameFilter) {
		initInstance(inTopDir, inFilenameFilter);
	}

	/**
	 *
	 */
	private void initInstance(File inTopDir, SkJsonFilenameFilter inFilenameFilter) {
		if (!inTopDir.exists()) {
			throw new IllegalArgumentException(
					String.format("The directory '%s' doesn't exist", inTopDir.getAbsolutePath()));
		}
		this.filenameFilter = inFilenameFilter;
		try {
			Files.walkFileTree(Paths.get(inTopDir.getAbsolutePath()), this);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 *
	 */
	public Set<Path> getJsonPaths() {
		return jsonPaths;
	}

	/**
	 *
	 */
	@Override
	public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
		return CONTINUE;
	}

	/**
	 *
	 */
	@Override
	public FileVisitResult visitFile(final Path inPath, final BasicFileAttributes attrs) throws IOException {
		String fullFilename = inPath.toFile()
				.getAbsolutePath();
		if (this.filenameFilter.accept(fullFilename)) {
			this.jsonPaths.add(inPath);
		}
		return CONTINUE;
	}

	/**
	 *
	 */
	@Override
	public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
		return CONTINUE;
	}

	/**
	 *
	 */
	@Override
	public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
		return CONTINUE;
	}

	/**
	 *
	 */
	@Override
	public String entryToString(Path inEntry) {
		// Autoclosable is pretty neat!
		try (FileInputStream fileInputStream = new FileInputStream(inEntry.toFile())) {
			return IOUtils.toString(fileInputStream, "UTF-8");
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 *
	 */
	@Override
	public Integer size() {
		return this.jsonPaths.size();
	}

	/**
	 *
	 */
	@Override
	public Stream<Path> stream() {
		return this.jsonPaths.stream();
	}
}
