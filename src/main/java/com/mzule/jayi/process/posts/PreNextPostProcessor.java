package com.mzule.jayi.process.posts;

import java.util.List;

import com.mzule.jayi.entity.Post;

public class PreNextPostProcessor implements PostListProcessor {

	public List<Post> process(List<Post> posts) {
		for (int i = 0; i < posts.size(); i++) {
			if (i == 0) {
				posts.get(i).addKeyValue(Post.KEY_PREVIOUS_POST_TITLE, "");
				posts.get(i).addKeyValue(Post.KEY_PREVIOUS_POST_LINK, "");
			} else {
				posts.get(i).addKeyValue(Post.KEY_PREVIOUS_POST_TITLE,
						(posts.get(i - 1).getValue("title")));
				posts.get(i).addKeyValue(Post.KEY_PREVIOUS_POST_LINK,
						(posts.get(i - 1).getHtmlFileName()));
			}
			if (i == posts.size() - 1) {
				posts.get(i).addKeyValue(Post.KEY_NEXT_POST_TITLE, "");
				posts.get(i).addKeyValue(Post.KEY_NEXT_POST_LINK, "");
			} else {
				posts.get(i).addKeyValue(Post.KEY_NEXT_POST_TITLE,
						(posts.get(i + 1).getValue(Post.KEY_TITLE)));
				posts.get(i).addKeyValue(Post.KEY_NEXT_POST_LINK,
						(posts.get(i + 1).getHtmlFileName()));
			}
		}
		return posts;
	}
}
