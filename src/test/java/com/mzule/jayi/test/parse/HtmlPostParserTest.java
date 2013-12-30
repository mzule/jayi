package com.mzule.jayi.test.parse;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.mzule.jayi.context.Context;
import com.mzule.jayi.entity.Post;

public class HtmlPostParserTest {

	private Post post;

	@Before
	public void setup() {
		Context.setBaseDir(Config.BASE_DIR);
		post = new Post();
		post.addKeyValue("template", "post.html");
		post.setContent("<p>Hello world</p>");
	}

	@Test
	public void testParseHtmlPost() throws IOException {
		String html = post.compile();
		System.out.println(html.trim().replace("\n", ""));
		assertThat(
				html.trim().replace("\n", ""),
				equalTo("<html>	<body>		<nav>home</nav>		<p>Hello world</p>	</body></html>"));
	}

}
