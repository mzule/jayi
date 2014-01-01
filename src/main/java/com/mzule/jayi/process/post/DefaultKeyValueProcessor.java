package com.mzule.jayi.process.post;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mzule.jayi.entity.Post;
import com.mzule.jayi.entity.RawPost;

/**
 * <p>
 * Set default values for one post.
 * </p>
 * <p>
 * Such as title, time and stick etc.
 * </p>
 * 
 * @author jason
 * 
 */
public class DefaultKeyValueProcessor implements PostProcessor {
	private String pattern = "yyyy-MM-dd HH:mm:ss";

	public Post process(Post post) {
		RawPost raw = post.getRaw();
		post.addKeyValue(Post.KEY_TITLE, removeExtension(raw.getFileName()));
		post.addKeyValue(Post.KEY_TIME, format(raw.getLastModified()));
		post.addKeyValue(Post.KEY_LINK, "posts/" + post.getHtmlFileName());
		post.addKeyValue(Post.KEY_REGULAR_POST, "true");
		return post;
	}

	private String format(Date creationTime) {
		return new SimpleDateFormat(pattern).format(creationTime);
	}

	private String removeExtension(String fileName) {
		String[] parts = fileName.split("\\.");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.length - 1; i++) {
			sb.append(parts[i]);
		}
		return sb.toString();
	}

}
