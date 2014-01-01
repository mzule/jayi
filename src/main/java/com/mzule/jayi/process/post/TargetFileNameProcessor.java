package com.mzule.jayi.process.post;

import com.mzule.jayi.entity.Post;

public class TargetFileNameProcessor implements PostProcessor {

	public Post process(Post post) {
		String[] parts = post.getRaw().getFileName().split("\\.");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.length - 1; i++) {
			sb.append(parts[i]);
		}
		String targetName = sb.toString() + ".html";
		post.setHtmlFileName(targetName);
		return post;
	}

}
