package com.tw.controller.audio;

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

import com.tw.model.audio.AudioBean;
import com.tw.model.audio.AudioService;

@Controller
@RequestMapping("/admin")
public class AdminAudioController {

	@Autowired
	AudioService audioService;
	@Autowired
	HttpServletRequest request;

	@GetMapping("audio/audio_home")
	public String audioHome(Model model) {
		model.addAttribute("audiolist", audioService.queryAudio());
		System.out.println("---------helloaudio------------");
		return "admin/audio/audio_home.html";
	}

	@GetMapping("/insert_audio")
	public String insertAudioStart() {
		return "admin/audio/audio_insert.html";
	}

	@PostMapping("/insert_audio/controller")
//	@ResponseBody
	public String insertAudioController(
			@RequestParam("audio_originalName") String audio_originalName,
			@RequestParam("audio_type") String audio_type, @RequestParam("audio_member") String audio_member,
			@RequestParam("audio_file") MultipartFile audio_file, @RequestParam("audio_content") String audio_content,
			Model model) throws IllegalStateException, IOException {
		String audioNameString = audio_file.getOriginalFilename();

		System.out.println("image: " + audioNameString);
		String saveAudioTempDir = request.getSession().getServletContext().getRealPath("/") + "audioFileTempDir\\";
		File save = new File(saveAudioTempDir);
		save.mkdir();

		String saveAudioPath = saveAudioTempDir + audioNameString;

		File saveAudioFile = new File(saveAudioPath);
		audio_file.transferTo(saveAudioFile);

		System.out.println("save:" + saveAudioFile);

		try (FileInputStream audio = new FileInputStream(saveAudioFile)) {
			byte[] audioByte = new byte[audio.available()];
			audio.read();

			if (audioNameString != null) {
				AudioBean audioBean = new AudioBean();
				audioBean.setAudio_name(audioNameString);
				audioBean.setAudio_originalName(audio_originalName);
				audioBean.setAudio_type(audio_type);
				audioBean.setAudio_member(audio_member);
				audioBean.setAudio_file(audioByte);
				audioBean.setAudio_content(audio_content);
				audioBean.setAudio_state('A');
				audioService.insertAudio(audioBean);
				System.out.println("inside");
			}
		}
		return "redirect:/admin/audio/audio_home";
	}

	@GetMapping("/update_audio/{audio_id}")
	public String updateAudioStert(@PathVariable("audio_id") int audio_id, Model model) {
		AudioBean findbyid = audioService.queryById(audio_id);
		model.addAttribute("findbyaudioid", findbyid);

		return "admin/audio/audio_update.html";

	}

	@PostMapping("/update_audio/controller/{audio_id}")
//	@ResponseBody
	public String updateOpus(@PathVariable("audio_id") int audio_id,
			@RequestParam("audio_file") MultipartFile audio_file, @RequestParam("audio_content") String audio_content,
			@RequestParam("audio_state") char audio_state, Model model) throws IllegalStateException, IOException {
		String audioNameString = audio_file.getOriginalFilename();

		System.out.println("image: " + audioNameString);
		String saveAudioTempDir = request.getSession().getServletContext().getRealPath("/") + "audioTempDir\\";
		File save = new File(saveAudioTempDir);
		save.mkdirs();

		String saveAudioPath = saveAudioTempDir + audioNameString;

		File saveAudioFile = new File(saveAudioPath);
		audio_file.transferTo(saveAudioFile);

		System.out.println("save:" + saveAudioFile);

		try (FileInputStream audio = new FileInputStream(saveAudioFile)) {
			byte[] audioByte = new byte[audio.available()];
			audio.read();

			if (audioNameString != null) {
				AudioBean audioBean = new AudioBean();
				audioBean.setAudio_name(audioNameString);
				audioBean.setAudio_file(audioByte);
				audioBean.setAudio_content(audio_content);
				audioService.updateAudio(audio_id, audioBean);
				System.out.println("inside");
			}
		}
		return "admin/audio/audio_home.html";
	}

	@GetMapping("/preview_audio/{audio_id}")

	public String audioPreviewController(@PathVariable("audio_id") int audio_id, Model model) {
		AudioBean findbyid = audioService.queryById(audio_id);

		model.addAttribute("findbyaudioid", findbyid);
		return "admin/audio/audio_preview.html";
	}

	@GetMapping("/delete_audio/{audio_id}")
	public String deleteAudio(@PathVariable("audio_id") int audio_id, Model model) {
		AudioBean findid = audioService.queryById(audio_id);
		if (findid != null) {
			model.addAttribute("findbyaudioid", findid);
			System.out.println(findid);
			return "admin/audio/audio_delete.html";
		}
		return null;

	}

	@PostMapping("/delete_audio/controller/{audio_id}")
	public String deleteAudioController(@PathVariable("audio_id") int audio_id, Model model) {
		audioService.deleteAudio(audio_id);
		System.out.println(audio_id);
		return "admin/audio/audio/audio_home.html";

	}

	@GetMapping("/state_audio/01/{id}")
	public String stateAudio01(@PathVariable("id") int audio_id) {
		AudioBean audio = audioService.queryById(audio_id);
		audio.setAudio_state('B');
		audioService.updateAudio(audio_id, audio);
		return "redirect:/admin/audio/audio_home.html";

	}

	@GetMapping("state_audio/02/{id}")
	public String stateAudio02(@PathVariable("id") int audio_id) {
		AudioBean audio = audioService.queryById(audio_id);
		audio.setAudio_state('C');
		audioService.updateAudio(audio_id, audio);
		return "redirect:/admin/audio/audio_home.html";

	}

	@GetMapping("state_audio/03/{id}")
	public String stateAudio03(@PathVariable("id") int audio_id) {
		AudioBean audio = audioService.queryById(audio_id);
		audio.setAudio_state('B');
		audioService.updateAudio(audio_id, audio);
		return "redirect:/admin/audio/audio_home.html";

	}

}