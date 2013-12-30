package com.mzule.jayi;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.mzule.jayi.context.Context;
import com.mzule.jayi.entity.Post;
import com.mzule.jayi.reader.IndexReader;
import com.mzule.jayi.reader.PostReader;

public class Main {
	private static final Logger log = Logger.getLogger(Main.class);

	public static void main(String[] args) throws IOException {
		Context.setBaseDir("/home/jason/Desktop/jayi");
		List<Post> posts = PostReader.read();
		// compile index page.
		IndexReader ir = new IndexReader(posts);
		String index = ir.read();
		final String targetFolderName = "_target";
		File target = new File(Context.getBaseDir(), targetFolderName);
		if (!target.exists() || !target.isDirectory()) {
			target.mkdir();
		}
		File ifile = new File(target, "index.html");
		ifile.createNewFile();
		FileUtils.write(ifile, index);
		// compile posts
		File postDir = new File(target, "_post");
		postDir.mkdir();
		for (Post p : posts) {
			String content = p.compile();
			File pFile = new File(postDir, p.getFileName());
			FileUtils.write(pFile, content);
		}
		// previous and next post
		Collections.sort(posts);
		// copy static resources, such as css, js.
		File baseDir = new File(Context.getBaseDir());
		File[] files = baseDir.listFiles(new FileFilter() {
			public boolean accept(File f) {
				String name = f.getName();
				return name.equals("_posts") || name.equals("index.html")
						|| name.equals("_templates")
						|| name.equals(targetFolderName) ? false : true;
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
	}
}
