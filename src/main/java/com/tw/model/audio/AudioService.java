package com.tw.model.audio;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AudioService {

	@Autowired
	private AudioRepository audioRepository;

	public AudioBean insertAudio(AudioBean audio) {
		return audioRepository.save(audio);
	}

	public AudioBean updateAudio(Integer id, AudioBean audio) {
		Optional<AudioBean> audioId = audioRepository.findById(id);
		if (audioId != null) {
			return audioRepository.save(audio);
		}
		return null;
	}

	public boolean deleteAudio(Integer id) {
		audioRepository.deleteById(id);
		return true;
	}
	
	public List<AudioBean> queryAudioBeans() {
		return audioRepository.findAll();
	}
	public AudioBean queryById(Integer id) {
		Optional<AudioBean> audioId = audioRepository.findById(id);
		if(audioId.isPresent()) {
			return audioId.get();
		}
		return null;
	}
}
