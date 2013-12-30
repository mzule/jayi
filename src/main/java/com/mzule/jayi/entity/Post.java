package com.mzule.jayi.entity;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.mzule.jayi.context.Context;
import com.mzule.jayi.util.HtmlUtils;
import com.petebevin.markdown.MarkdownProcessor;

public class Post implements Comparable<Post> {

	private static final Logger log = Logger.getLogger(Post.class);

	private Map<String, String> keyValues = new HashMap<String, String>();
	private String content;
	private Date time;
	private String fileName;

	public Map<String, String> getKeyValues() {
		return keyValues;
	}

	public void addKeyValue(String key, String value) {
		keyValues.put(key, value);
		// set time
		if (key.equals("time")) {
			try {
				time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
			} catch (ParseException e) {
				log.error("Error in parsing post time " + value, e);
			}
		}
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public String compile() throws IOException {
		String template = keyValues.get("template");
		File file = new File(Context.getBaseDir() + "/_templates", template);
		log.debug("compile post with template " + file.getName());
		String templateContent = FileUtils.readFileToString(file);
		templateContent = templateContent.replace("{content}", toHtml(content));
		// deal with includes
		File includes = new File(Context.getBaseDir(), "_includes");
		File[] files = includes.listFiles();
		if (files != null) {
			for (File f : files) {
				templateContent = templateContent.replace("{" + f.getName()
						+ "}", FileUtils.readFileToString(f));
			}
		}
		// deal with key values
		for (Entry<String, String> kv : keyValues.entrySet()) {
			templateContent = templateContent.replace("{" + kv.getKey() + "}",
					kv.getValue());
		}
		return templateContent;
	}

	/**
	 * Convert post content to HTML. Support markdown and html syntax.
	 * 
	 * @param content
	 * @return
	 */
	private String toHtml(String content) {
		String fName = getFileName().toLowerCase();
		if (fName.endsWith(".md") || fName.endsWith(".markdown")) {
			return new MarkdownProcessor().markdown(content);
		} else {
			return content;
		}
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public Date getTime() {
		if (time == null) {
			log.error("Missing time in post " + fileName);
		}
		return time;
	}

	public String generateSummary() {
		String plain = HtmlUtils.removeHtmlTags(getContent());
		int minLen = 300;
		if (plain == null || plain.length() < minLen) {
			return plain;
		}
		return plain.substring(0, minLen);
	}

	public int compareTo(Post o) {
		return this.getTime().after(o.getTime()) ? -1 : 1;
	}

	public String getSavedFileName() {
		String name = getFileName();
		return name.split("\\.")[0] + ".html";
	}

}
