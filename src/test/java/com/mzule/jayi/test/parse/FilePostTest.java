package com.mzule.jayi.test.parse;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import com.mzule.jayi.entity.Post;
import com.mzule.jayi.process.post.FilePost;

public class FilePostTest {

	@Test
	public void testReadPosts() throws Exception {
		Post post = new FilePost(new File(
				"src/test/resources/jayi/_posts/sample1.html")).post();
		String fileName = post.getRaw().getFileName();
		assertThat(fileName, equalTo("sample1.html"));
		assertThat(post.getBaseDir(), equalTo(new File(
				"src/test/resources/jayi/").getAbsolutePath()));
	}
}
