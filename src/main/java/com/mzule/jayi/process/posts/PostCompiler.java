package com.mzule.jayi.process.posts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mzule.jayi.entity.Post;
import com.mzule.jayi.process.post.FilePost;
import com.mzule.jayi.process.post.PostProcessor;

public class PostCompiler {

	private List<PostListProcessor> postListProcessors = new ArrayList<PostListProcessor>();
	private List<PostProcessor> preparingProcessors = new ArrayList<PostProcessor>();
	private List<PostProcessor> postingProcessors = new ArrayList<PostProcessor>();

	public List<Post> compile(String baseDir) {
		List<Post> posts = new ArrayList<Post>();
		File dir = new File(baseDir, "_posts");
		File[] files = dir.listFiles();
		for (File file : files) {
			Post post = new FilePost(file).post();
			for (PostProcessor processor : preparingProcessors) {
				processor.process(post);
			}
			posts.add(post);
		}
		for (PostListProcessor processor : postListProcessors) {
			processor.process(posts);
		}
		for (Post post : posts) {
			for (PostProcessor processor : postingProcessors) {
				processor.process(post);
			}
		}
		return posts;
	}

	public void addPreparingProcessor(PostProcessor processor) {
		this.preparingProcessors.add(processor);
	}

	public void addPostingProcessor(PostProcessor processor) {
		this.postingProcessors.add(processor);
	}

	public void addPostListProcessor(PostListProcessor processor) {
		this.postListProcessors.add(processor);
	}

	// new TargetFileNameProcessor().process(post);
	// new DefaultKeyValueProcessor().process(post);
	// new KeyValueProcessor().process(post);
	// new MarkdownProcessor().process(post);
	// new SummaryProcessor().process(post);
	// new TemplateProcessor().process(post);
	// new IncludeProcessor().process(post);
}
