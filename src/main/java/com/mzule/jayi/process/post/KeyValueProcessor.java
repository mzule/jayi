package com.mzule.jayi.process.post;

import java.util.List;

import com.mzule.jayi.entity.Post;

public class KeyValueProcessor implements PostProcessor {

	public Post process(Post post) {
		List<String> lines = post.getRaw().getLines();
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
		return post;
	}

}
