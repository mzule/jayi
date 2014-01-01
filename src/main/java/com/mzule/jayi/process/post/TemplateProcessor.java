package com.mzule.jayi.process.post;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.mzule.jayi.entity.Post;

public class TemplateProcessor implements PostProcessor {
	private static final Logger log = Logger.getLogger(TemplateProcessor.class);
	private String templateFold = "_templates/";

	public Post process(Post post) {
		try {
			String templateFileName = post.getTemplate();
			if (templateFileName == null
					|| templateFileName.trim().length() == 0) {
				return post;
			}
			File file = new File(post.getBaseDir(), templateFold
					+ templateFileName);
			log.debug("compile post " + post.getRaw().getFileName()
					+ " with template " + file.getAbsolutePath());
			String templateContent = FileUtils.readFileToString(file);
			templateContent = templateContent.replace("{content}",
					post.getContent());
			post.setContent(templateContent);
		} catch (IOException e) {
			log.error("Error while process template "
					+ post.getRaw().getFileName(), e);
		}
		return post;
	}

}
