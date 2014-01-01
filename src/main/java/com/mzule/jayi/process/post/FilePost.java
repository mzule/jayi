package com.mzule.jayi.process.post;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.mzule.jayi.entity.Post;
import com.mzule.jayi.entity.RawPost;

public class FilePost {
	private static final Logger log = Logger.getLogger(FilePost.class);
	private Post post;

	public FilePost(File file) {
		RawPost raw = new RawPost();
		try {
			List<String> lines = FileUtils.readLines(file);
			raw.setLastModified(new Date(file.lastModified()));
			raw.setFileName(file.getName());
			raw.setLines(lines);
		} catch (IOException e) {
			log.error("Error while compile post:" + file.getName(), e);
		}
		post = new Post();
		post.setRaw(raw);
		post.setBaseDir(file.getParentFile().getParentFile().getAbsolutePath());
	}

	public Post post() {
		return post;
	}

}
