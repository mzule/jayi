package com.mzule.jayi.process.posts;

import java.util.Collections;
import java.util.List;

import com.mzule.jayi.entity.Post;

public class OrderProcessor implements PostListProcessor {

	public List<Post> process(List<Post> posts) {
		Collections.sort(posts);
		return posts;
	}

}
