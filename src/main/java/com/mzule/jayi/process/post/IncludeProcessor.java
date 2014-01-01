package com.mzule.jayi.process.post;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.mzule.jayi.entity.Post;

public class IncludeProcessor implements PostProcessor {

	public Post process(Post post) {
		try {
			String content = post.getContent();
			// deal with includes
			File includes = new File(post.getBaseDir(), "_includes");
			File[] files = includes.listFiles();
			if (files != null) {
				for (File f : files) {
					content = content.replace("{" + f.getName() + "}",
							FileUtils.readFileToString(f));
				}
			}
			post.setContent(content);
		} catch (IOException e) {
		}
		return post;
	}

}
