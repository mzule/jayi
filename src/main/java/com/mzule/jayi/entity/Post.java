package com.mzule.jayi.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class Post implements Comparable<Post> {

	private static final Logger log = Logger.getLogger(Post.class);

	public static final String KEY_TITLE = "title";
	public static final String KEY_TIME = "time";
	public static final String KEY_TEMPLATE = "template";
	public static final String KEY_NEXT_POST_TITLE = "next_post_title";
	public static final String KEY_NEXT_POST_LINK = "next_post_link";
	public static final String KEY_PREVIOUS_POST_TITLE = "previous_post_title";
	public static final String KEY_PREVIOUS_POST_LINK = "previous_post_link";
	public static final String KEY_SUMMARY = "summary";
	public static final String KEY_LINK = "link";
	public static final String KEY_STICK = "stick";
	public static final String KEY_REGULAR_POST = "regular";

	private RawPost raw;

	private Map<String, String> keyValues = new HashMap<String, String>();
	private String content;
	private Date time;
	private String htmlFileName;

	private String baseDir;

	public String getValue(String key) {
		return keyValues.get(key);
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

	public void setHtmlFileName(String fileName) {
		this.htmlFileName = fileName;
	}

	public String getHtmlFileName() {
		return htmlFileName;
	}

	public Date getTime() {
		if (time == null) {
			log.error("Missing time in post " + raw.getFileName());
		}
		return time;
	}

	public int compareTo(Post o) {
		return this.getTime().after(o.getTime()) ? -1 : 1;
	}

	public Map<String, String> getKeyValues() {
		return keyValues;
	}

	public RawPost getRaw() {
		return raw;
	}

	public void setRaw(RawPost raw) {
		this.raw = raw;
	}

	public void setSummary(String summary) {
		addKeyValue(Post.KEY_SUMMARY, summary);
	}

	public String getTemplate() {
		return getValue(KEY_TEMPLATE);
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public String getBaseDir() {
		return this.baseDir;
	}

	public boolean isRegular() {
		return keyValues.get(KEY_REGULAR_POST).trim().equalsIgnoreCase("true");
	}

}
