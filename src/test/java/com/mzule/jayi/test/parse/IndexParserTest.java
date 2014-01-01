package com.mzule.jayi.test.parse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mzule.jayi.entity.Post;
import com.mzule.jayi.process.index.IndexCompiler;

public class IndexParserTest {

	@Before
	public void setup() {
	}

	@Test
	public void testParseIndex() throws IOException {
		String index = new IndexCompiler(getPosts()).compile();
		System.out.println(index);
	}

	private List<Post> getPosts() {
		Post post = new Post();
		post.addKeyValue("time", "2012-11-11 12:23:21");
		post.addKeyValue("title", "do you know me?");
		Post post1 = new Post();
		post1.addKeyValue("time", "2012-12-11 12:23:21");
		post1.addKeyValue("title", "do you love?");
		return Arrays.asList(post, post1);
	}
}
