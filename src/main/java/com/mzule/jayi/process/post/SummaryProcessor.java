package com.mzule.jayi.process.post;

import com.mzule.jayi.entity.Post;
import com.mzule.jayi.util.HtmlUtils;

public class SummaryProcessor implements PostProcessor {

	private int summaryLength = 300;

	public Post process(Post post) {
		String summary = null;
		String plain = HtmlUtils.removeHtmlTags(post.getContent());
		if (plain.length() <= 300) {
			summary = plain;
		} else {
			summary = plain.substring(0, summaryLength);
		}
		post.setSummary(summary);
		return post;
	}

}
