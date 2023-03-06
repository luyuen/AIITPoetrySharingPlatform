package com.tw.controller.opus;

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

import com.tw.model.member.MemberService;
import com.tw.model.opus.OpusBean;
import com.tw.model.opus.OpusService;

@Controller
@RequestMapping("/admin")
public class AdminOpusController {
	@Autowired
	OpusService opusService;

	@Autowired
	MemberService memberService;

	@Autowired
	private HttpServletRequest request;

	@GetMapping("/opus/opus_home")
//	@ResponseBody
	public String queryAll(Model model) {
		model.addAttribute("opuslist", opusService.queryOpus());
		System.out.println("hello");
		return "admin/opus/opus_home.html";
	}

	@GetMapping("/insert_opus")
	public String insertStart() {
		return "admin/opus/insert_opus.html";
	}

	@PostMapping("/insert_opus/controller")
	public String insertOpus(@RequestParam("opus_name") String opus_name,
			@RequestParam("opus_member") String opus_member, @RequestParam("imagefile") MultipartFile imagefile,
			@RequestParam("audiofile") MultipartFile audiofile, @RequestParam("opus_content") String opus_content,
			Model model) throws IllegalStateException, IOException {
		if (memberService.findByMemberId(opus_member).isEmpty()) {
			return "redirect:/singin.html";
		}
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

		String imageFileNameString = imagefile.getOriginalFilename();
		String saveImageFileTempDir = request.getSession().getServletContext().getRealPath("/") + "imageFileTempDir\\";
		File imageFile = new File(saveImageFileTempDir);
		imageFile.mkdirs();

		String saveImageFilePath = saveImageFileTempDir + imageFileNameString;
		File saveImageFile = new File(saveImageFilePath);
		imagefile.transferTo(saveImageFile);

		String audioFileNameString = audiofile.getOriginalFilename();
		String saveAudioFileTempDir = request.getSession().getServletContext().getRealPath("/") + "audioFileTempDir\\";
		File audioFile = new File(saveAudioFileTempDir);
		audioFile.mkdirs();

		String saveAudioFilePath = saveAudioFileTempDir + audioFileNameString;
		File saveAudioFile = new File(saveAudioFilePath);
		audiofile.transferTo(saveAudioFile);

		System.out.println("save:" + saveImageFile);
		System.out.println("save:" + saveAudioFile);

		try (FileInputStream image = new FileInputStream(saveImageFilePath)) {
			try (FileInputStream audio = new FileInputStream(saveAudioFilePath)) {

				byte[] imageByte = new byte[image.available()];
				byte[] audioByte = new byte[audio.available()];

				image.read();
				audio.read();

				if (imageByte != null && audioByte != null) {

					OpusBean opus = new OpusBean();
					opus.setOpus_name(opus_name);
					opus.setOpus_member(opus_member);
					opus.setOpus_imageName(imageFileNameString);
					opus.setOpus_audioName(audioFileNameString);
					opus.setOpus_image(imageByte);
					opus.setOpus_audio(audioByte);
					opus.setOpus_content(opus_content);
					opus.setOpus_state('A');
					opusService.insertOpus(opus);

					return "redirect:/admin/opus/opus_home";
				}
			}
		}
		return null;

	}

	@GetMapping("/preview_opus/{opus_id}")
	public String previewOpusController(@PathVariable("opus_id") int opus_id, Model model) {
		OpusBean findbyid = opusService.queryById(opus_id);
		model.addAttribute("findbyopusid", findbyid);

		return "admin/opus/preview_opus.html";

	}

	@GetMapping("/upate_opus/{opus_id}")
	public String updateOpusController(@PathVariable("opus_id") int opus_id, Model model) {
		OpusBean findbyid = opusService.queryById(opus_id);
		model.addAttribute("findbyopusid", findbyid);

		return "admin/opus/update_opus.html";

	}

	@PostMapping("/update_opus/controller/{opus_id}")
//	@ResponseBody
	public String updateOpus(@PathVariable("opus_id") int forum_id, @RequestParam("opus_name") String opus_name,
			@RequestParam("opus_image") MultipartFile opus_image, @RequestParam("opus_content") String opus_content,
			Model model) throws IllegalStateException, IOException {
		String imageNameString = opus_image.getOriginalFilename();

		System.out.println("image: " + imageNameString);
		String saveTempDir = request.getSession().getServletContext().getRealPath("/") + "imageTempDir\\";
		File save = new File(saveTempDir);
		save.mkdirs();

		String saveImagePath = saveTempDir + imageNameString;

		File saveImage = new File(saveImagePath);
		opus_image.transferTo(saveImage);

		System.out.println("save:" + saveImage);

		try (FileInputStream image1 = new FileInputStream(saveImagePath)) {
			byte[] b = new byte[image1.available()];
			image1.read();

			if (imageNameString != null && imageNameString.length() != 0) {
				OpusBean opus = new OpusBean();
				opus.setOpus_name(opus_name);
				opus.setOpus_image(b);
				opus.setOpus_imageName(imageNameString);
				opus.setOpus_content(opus_content);
				opusService.updateOpus(opus);
				System.out.println("inside");
			}
		}
		return "admin/forum/success.html";
	}

	@GetMapping("/delete_opus/{opus_id}")
	public String deleteOpusController(@PathVariable("opus_id") int opus_id, Model model) {
		OpusBean findid = opusService.queryById(opus_id);
		if (findid != null) {
			model.addAttribute("findbyforumid", findid);
			System.out.println(findid);
			return "admin/opus/delete_opus.html";
		}
		return null;

	}

	@PostMapping("/delete_opus/controller/{opus_id}")
	public String deleteOpus(@PathVariable("opus_id") int opus_id, Model model) {
		opusService.deleteOpusById(opus_id);
		System.out.println(opus_id);
		return "admin/forum/success.html";

	}

	@GetMapping("state_opus/01/{id}")
	public String stateOpus01(@PathVariable("id") int opus_id) {
		OpusBean opus = opusService.queryById(opus_id);
		opus.setOpus_state('B');
		opusService.updateOpus(opus);
		return "redirect:/admin/opus/opus_home.html";

	}

	@GetMapping("state_opus/02/{id}")
	public String stateOpus02(@PathVariable("id") int opus_id) {
		OpusBean opus = opusService.queryById(opus_id);
		opus.setOpus_state('C');
		opusService.updateOpus(opus);
		return "redirect:/admin/opus/opus_home.html";

	}

	@GetMapping("state_opus/03/{id}")
	public String stateOpus03(@PathVariable("id") int opus_id) {
		OpusBean opus = opusService.queryById(opus_id);
		opus.setOpus_state('B');
		opusService.updateOpus(opus);
		return "redirect:/admin/opus/opus_home.html";

	}

}
