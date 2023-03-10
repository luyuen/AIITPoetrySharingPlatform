package com.tw.controller.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/audio/audio_home")
	public String audioHome(Model model) {
		model.addAttribute("audioList", audioService.queryAudioBeans());
		return "admin/audio/audio_home.html";
	}

	@GetMapping("/audio/audio_insert")
	public String insertAudioStart() {
		return "/audio/audio_insert.html";
	}

	@GetMapping("/audio/audio_insert/controller")
	public String insertAudio(@PathVariable("audio_id") int audio_id, @RequestParam("audio_name") String audio_name,
			@RequestParam("audio_member") String audio_member,
			@RequestParam("audio_file") MultipartFile audio_file,
			@RequestParam("audio_content") String audio_content, @RequestParam("audio_state") char audio_state) throws IllegalStateException, IOException {

		if(audio_member.isEmpty()) {
			return "redirect:/singin.html";
		}
		
		 String audioFileNameString =audio_file.getOriginalFilename();
		String saveAudioFileTempDir = request.getSession().getServletContext().getRealPath("/") + "audioFileTemDir\\";
		File audioFile = new File(saveAudioFileTempDir);
		if(audioFile == null ) {
			audioFile.mkdir();
		}
		String saveAudioFilePath = saveAudioFileTempDir + audioFileNameString;
		File saveAudioFile = new File(saveAudioFilePath);
		audio_file.transferTo(saveAudioFile);
		try(FileInputStream audio = new FileInputStream(saveAudioFilePath)) {
			byte[] audioByte = new byte[audio.available()];
			audio.read();
			
			if(audioByte!= null) {
				AudioBean audioBean = new AudioBean();
				
				audioBean.setAudio_name(audio_name);
				audioBean.setAudio_file(audioByte);
				audioBean.setAudio_content(audio_content);
				audioBean.setAudio_member(audio_member);
				audioBean.setAudio_state('A');
				audioService.insertAudio(audioBean);
				
				return "audio/audio_home.html";
				
				
			}
			return null; 
		}
		
	}
}
