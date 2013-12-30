package com.mzule.jayi.test.parse;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.mzule.jayi.context.Context;
import com.mzule.jayi.entity.Post;
import com.mzule.jayi.reader.PostReader;

public class PostsReaderTest {

	@Before
	public void setup() {
		Context.setBaseDir(Config.BASE_DIR);
	}

	@Test
	public void testReadPosts() throws Exception {
		List<Post> posts = PostReader.read();
		assertThat(posts.size(), is(2));
		Post post = posts.get(1);
		Map<String, String> kvs = post.getKeyValues();
		assertThat(kvs.get("title"), equalTo("this is first post"));
		assertThat(kvs.get("time"), equalTo("2013-11-12 12:12:12"));
		assertThat(kvs.get("template"), equalTo("post.html"));
		assertThat(post.getTime().getTime(), is(1384229532000L));
		assertThat(post.getFileName(), equalTo("sample1.html"));
	}
}
