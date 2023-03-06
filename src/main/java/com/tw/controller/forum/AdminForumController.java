package com.tw.controller.forum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tw.model.forum.ForumBean;
import com.tw.model.forum.ForumService;

@Controller
@RequestMapping("/admin")
public class AdminForumController {
	@Autowired
	ForumService forumService;


	@Autowired
	private HttpServletRequest request;
	

	@GetMapping("/forum/forum_home")
//	@ResponseBody
	public String queryAll(Model model) {
		model.addAttribute("forumlist", forumService.queryForum());
		System.out.println("hello");
		return "admin/forum/forum_home.html";
	}

	@GetMapping("/insert_forum")
	public String insertStart() {
		return "admin/forum/insert_forum.html";
	}

	@PostMapping("/insert_forum/controller")
	public String insertForum(@RequestParam("forum_name") String forum_name, @RequestParam("image") MultipartFile image,
			@RequestParam("forum_content") String forum_content, Model model)
			throws IllegalStateException, IOException {
//		String fileName = (String) String.format("%s\\%s.%s", "forum", Instant.now().toEpochMilli(),
//				image.getContentType().split("/")[1]);
////		if (file == null) {
////			file == "noimage_s.jpg";
////		}
//
//
//		System.out.println("originalFileName:" + fileName);
//		System.out.println(fileName);
//		String pathname = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\images\\" + fileName;
////		 File saveFile = new File(pathname);
////			file.transferTo(saveFile);
////			System.out.println(saveFile);
//
////		File saveTempDirFile = new File(saveTempFile);
////		saveTempDirFile.mkdirs();
//
////		String saveFilePath = pathname + fileName;
//		File saveFile1 = new File(pathname);
//		if (!saveFile1.getParentFile().exists()) {
//			saveFile1.getParentFile().mkdirs();
//		}

		String imageNameString = image.getOriginalFilename();
		System.out.println("image: " + imageNameString);

		String saveTempDir = request.getSession().getServletContext().getRealPath("/") + "imageTempDir\\";
		File save = new File(saveTempDir);
		save.mkdirs();

		String saveImagePath = saveTempDir + imageNameString;

		File saveImage = new File(saveImagePath);
		image.transferTo(saveImage);

		System.out.println("save:" + saveImage);

		try (FileInputStream image1 = new FileInputStream(saveImagePath)) {
			byte[] b = new byte[image1.available()];
			
			image1.read();

			if (imageNameString != null && imageNameString.length() != 0) {

				ForumBean bean = new ForumBean();
				bean.setForum_name(forum_name);
				bean.setForum_image(b);
				bean.setForum_imageName(imageNameString);
				bean.setForum_content(forum_content);
				bean.setForum_state('A');
				forumService.insertForun(bean);
				
				return "redirect:/admin/forum/forum_home";
			}
		}
		return null;

	}

	@GetMapping("/preview_forum/{forum_id}")
 String previewOpusController(@PathVariable("forum_id") int forum_id, Model model) {
		ForumBean findbyid = forumService.queryForumById(forum_id);
		model.addAttribute("findbyforumid", findbyid);
		
		return "admin/forum/preview_forum.html";
		
	}

	@GetMapping("/upate_forum/{forum_id}")
	public String updateForumController(@PathVariable("forum_id") int forum_id, Model model) {
		ForumBean findbyid = forumService.queryForumById(forum_id);
		model.addAttribute("findbyforumid", findbyid);

		return "admin/forum/update_forum.html";

	}

	@PostMapping("/update_forum/controller/{forum_id}")
//	@ResponseBody
	public String updateForum(@PathVariable("forum_id") int forum_id, @RequestParam("forum_name") String forum_name,
			@RequestParam("forum_image") MultipartFile forum_image, @RequestParam("forum_content") String fourm_content,
			Model model) throws IllegalStateException, IOException {
		String imageNameString = forum_image.getOriginalFilename();

		System.out.println("image: " + imageNameString);
		String saveTempDir = request.getSession().getServletContext().getRealPath("/") + "imageTempDir\\";
		File save = new File(saveTempDir);
		save.mkdirs();

		String saveImagePath = saveTempDir + imageNameString;

		File saveImage = new File(saveImagePath);
		forum_image.transferTo(saveImage);

		System.out.println("save:" + saveImage);

		try (FileInputStream image1 = new FileInputStream(saveImagePath)) {
			byte[] b = new byte[image1.available()];
			image1.read();

			if (imageNameString != null && imageNameString.length() != 0) {
				ForumBean bean = new ForumBean();
				bean.setForum_name(forum_name);
				bean.setForum_image(b);
				bean.setForum_imageName(imageNameString);
				bean.setForum_content(fourm_content);
				forumService.updateForun(forum_id, bean);
				System.out.println("inside");
			}
		}
		return "admin/forum/success.html";
	}

	@GetMapping("/delete_forum/{forum_id}")
	public String deleteForumController(@PathVariable("forum_id") int forum_id, Model model) {
		ForumBean findid = forumService.queryForumById(forum_id);
		if (findid != null) {
			model.addAttribute("findbyforumid", findid);
			System.out.println(findid);
			return "admin/forum/delete_forum.html";
		}
		return null;

	}

	@PostMapping("/delete_forum/controller/{forum_id}")
	public String deleteForum(@PathVariable("forum_id") int forum_id, Model model) {
		forumService.deleteById(forum_id);
		System.out.println(forum_id);
		return "admin/forum/success.html";

	}


}
