package com.mzule.jayi.entity;

import java.util.Date;
import java.util.List;

public class RawPost {

	private Date lastModified;
	private String fileName;
	private List<String> lines;

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date modified) {
		this.lastModified = modified;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	@Override
	public String toString() {
		return "RawPost: " + fileName;
	}

}
