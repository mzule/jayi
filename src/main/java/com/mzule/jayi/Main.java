package com.mzule.jayi;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.mzule.jayi.entity.Post;
import com.mzule.jayi.process.index.IndexCompiler;
import com.mzule.jayi.process.post.DefaultKeyValueProcessor;
import com.mzule.jayi.process.post.IncludeProcessor;
import com.mzule.jayi.process.post.KeyValueProcessor;
import com.mzule.jayi.process.post.MarkdownProcessor;
import com.mzule.jayi.process.post.SummaryProcessor;
import com.mzule.jayi.process.post.TargetFileNameProcessor;
import com.mzule.jayi.process.post.TemplateProcessor;
import com.mzule.jayi.process.post.VariableProcessor;
import com.mzule.jayi.process.posts.OrderProcessor;
import com.mzule.jayi.process.posts.PostCompiler;
import com.mzule.jayi.process.posts.PreNextPostProcessor;

public class Main {
	private static final Logger log = Logger.getLogger(Main.class);
	private static final String target = "/home/jason/Desktop/jayi";

	public static void main(String[] args) {
		String baseDir = "/home/jason/Desktop/jayi/_src/";
		PostCompiler compiler = new PostCompiler();
		// before list process
		compiler.addPreparingProcessor(new TargetFileNameProcessor());
		compiler.addPreparingProcessor(new DefaultKeyValueProcessor());
		compiler.addPreparingProcessor(new KeyValueProcessor());
		compiler.addPreparingProcessor(new MarkdownProcessor());
		compiler.addPreparingProcessor(new SummaryProcessor());
		compiler.addPreparingProcessor(new TemplateProcessor());
		compiler.addPreparingProcessor(new IncludeProcessor());
		// list process
		compiler.addPostListProcessor(new OrderProcessor());
		compiler.addPostListProcessor(new PreNextPostProcessor());
		// after list process
		compiler.addPostingProcessor(new VariableProcessor());
		List<Post> posts = compiler.compile(baseDir);

		String index = new IndexCompiler(posts).compile();

		savingPosts(posts);
		savingIndex(index);
		// copy static resources, such as css, js.
		copyStaticResources(baseDir);
	}

	private static void savingIndex(String index) {
		File file = new File(target, "index.html");
		save(index, file);
	}

	private static void save(String index, File file) {
		try {
			file.delete();
			file.createNewFile();
			FileUtils.write(file, index);
			log.debug("saving " + file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void savingPosts(List<Post> posts) {
		String dirName = target + "/posts";
		new File(dirName).mkdirs();
		for (Post post : posts) {
			save(post.getContent(), new File(dirName, post.getHtmlFileName()));
		}
	}

	private static void copyStaticResources(String baseDir) {
		try {
			File dir = new File(baseDir);
			File[] files = dir.listFiles(new FileFilter() {
				public boolean accept(File f) {
					String name = f.getName();
					return name.equals("_posts") || name.equals("index.html")
							|| name.equals("_templates")
							|| name.equals("_includes") ? false : true;
				}
			});
			for (File f : files) {
				if (f.isDirectory()) {
					log.debug("copying folder " + f.getAbsolutePath());
					FileUtils.copyDirectory(f, new File(target, f.getName()));
				} else {
					log.debug("copying file " + f.getAbsolutePath());
					FileUtils.copyFile(f, new File(target, f.getName()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
