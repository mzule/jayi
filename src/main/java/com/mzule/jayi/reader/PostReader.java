package com.mzule.jayi.reader;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.mzule.jayi.context.Context;
import com.mzule.jayi.entity.Post;

public class PostReader {

	private static final Logger log = Logger.getLogger(PostReader.class);

	public static List<Post> read() {
		String baseDir = Context.getBaseDir();
		List<Post> posts = new ArrayList<Post>();
		File dir = new File(baseDir, "_posts");
		File[] files = dir.listFiles(new FileFilter() {
			public boolean accept(File f) {
				String fname = f.getName().toLowerCase();
				return fname.endsWith(".html") || fname.endsWith(".md")
						|| fname.endsWith(".markdown");
			}
		});
		for (File file : files) {
			try {
				log.debug("reading " + file.getName());
				posts.add(parseFile(file));
			} catch (IOException e) {
				log.error("Error while parsing " + file.getName(), e);
			}
		}
		return posts;
	}

	private static Post parseFile(File f) throws IOException {
		Post post = new Post();
		List<String> lines = FileUtils.readLines(f);
		int i = 0;
		// build key value pairs
		for (; i < lines.size(); i++) {
			String line = lines.get(i).trim();
			if (line.equals("---")) {
				break;
			}
			String[] kv = line.split(":");
			if (kv.length > 1) {
				post.addKeyValue(kv[0], line.substring(kv[0].length() + 1));
			}
		}
		i++;// pass the "---" line;
		// build content
		StringBuilder sb = new StringBuilder();
		for (; i < lines.size(); i++) {
			sb.append(lines.get(i));
			sb.append('\n');
		}
		post.setContent(sb.toString());
		post.setFileName(f.getName());
		// build builde in key values
		post.addKeyValue("link", "posts/" + post.getSavedFileName());
		post.addKeyValue("summary", post.generateSummary());
		return post;
	}
}
