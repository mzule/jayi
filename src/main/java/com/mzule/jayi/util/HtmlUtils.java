package com.mzule.jayi.util;

public class HtmlUtils {

	public static String removeHtmlTags(String html) {
		if (html != null) {
			return html.replaceAll("\\<[^>]*>", "");
		}
		return html;
	}

}
