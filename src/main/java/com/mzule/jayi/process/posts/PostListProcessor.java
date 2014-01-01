package com.mzule.jayi.process.posts;

import java.util.List;

import com.mzule.jayi.entity.Post;

public interface PostListProcessor {

	public List<Post> process(List<Post> posts);

}
