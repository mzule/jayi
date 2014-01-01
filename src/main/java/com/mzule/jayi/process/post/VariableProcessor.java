package com.mzule.jayi.process.post;

import java.util.Map;
import java.util.Map.Entry;

import com.mzule.jayi.entity.Post;

public class VariableProcessor implements PostProcessor {

	public Post process(Post post) {
		String content = post.getContent();
		Map<String, String> keyValues = post.getKeyValues();
		// deal with key values
		for (Entry<String, String> kv : keyValues.entrySet()) {
			System.out.println(kv);
			content = content.replace("{" + kv.getKey() + "}", kv.getValue());
		}
		post.setContent(content);
		return post;
	}

}
