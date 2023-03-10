package com.tw.model.briefImage;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BriefImageService {
	
	@Autowired
	private BriefImageRepository briefImageRepository;

	public BriefImageBean insertBriefImage(BriefImageBean briefImage) {
		return briefImageRepository.save(briefImage);
	}

	public BriefImageBean updateAudio(Integer id, BriefImageBean briefImage) {
		Optional<BriefImageBean> briefImageId = briefImageRepository.findById(id);
		if (briefImageId != null) {
			return briefImageRepository.save(briefImage);
		}
		return null;
	}

	public boolean deleteBriefImage(Integer id) {
		briefImageRepository.deleteById(id);
		return true;
	}
	
	public List<BriefImageBean> queryBriefImage() {
		return briefImageRepository.findAll();
	}
	public BriefImageBean queryById(Integer id) {
		Optional<BriefImageBean> briefImageId = briefImageRepository.findById(id);
		if(briefImageId.isPresent()) {
			return briefImageId.get();
		}
		return null;
	}

}
