package com.mzule.jayi.process.post;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.markdown4j.Markdown4jProcessor;

import com.mzule.jayi.entity.Post;

public class MarkdownProcessor implements PostProcessor {

	private static final Logger log = Logger.getLogger(MarkdownProcessor.class);

	public Post process(Post post) {
		try {
			String md = post.getContent();
			String html = new Markdown4jProcessor().process(md);
			post.setContent(html);
		} catch (IOException e) {
			log.error("Error while process markdown, file "
					+ post.getRaw().getFileName(), e);
		}
		return post;
	}

}
