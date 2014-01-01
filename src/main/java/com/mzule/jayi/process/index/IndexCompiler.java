package com.mzule.jayi.process.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import com.mzule.jayi.entity.Post;

public class IndexCompiler {

	private List<Post> posts;
	private String baseDir;

	public IndexCompiler(List<Post> posts) {
		this.posts = posts;
		this.baseDir = posts.get(0).getBaseDir();
	}

	public String compile() {
		try {
			File file = new File(baseDir, "index.html");
			List<String> lines = FileUtils.readLines(file);
			int i = 0;
			// compile
			StringBuilder sb = new StringBuilder();
			List<String> parsableLines = new ArrayList<String>();
			for (; i < lines.size(); i++) {
				String line = lines.get(i).trim();
				if (!line.equals("---")) {
					sb.append(line);
					sb.append('\n');
				} else {
					for (i++; i < lines.size(); i++) {
						line = lines.get(i).trim();
						if (line.equals("---")) {
							sb.append("{content}\n");
							break;
						} else {
							parsableLines.add(line);
						}
					}
				}
			}
			// parse parsable lines.
			StringBuilder parseResult = new StringBuilder();
			for (Post post : posts) {
				if (!post.isRegular()) {
					continue;
				}
				Map<String, String> keyValues = post.getKeyValues();
				for (String line : parsableLines) {
					for (Entry<String, String> kv : keyValues.entrySet()) {
						line = line.replace("{" + kv.getKey() + "}",
								kv.getValue());
					}
					parseResult.append(line);
					parseResult.append('\n');
				}
			}
			String res = sb.toString().replace("{content}",
					parseResult.toString());
			// deal with includes
			File includes = new File(baseDir, "_includes");
			File[] files = includes.listFiles();
			if (files != null) {
				for (File f : files) {
					res = res.replace("{" + f.getName() + "}",
							FileUtils.readFileToString(f));
				}
			}
			return res;
		} catch (IOException e) {
		}
		return null;
	}
}
