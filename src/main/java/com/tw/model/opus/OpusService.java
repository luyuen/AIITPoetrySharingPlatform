package com.tw.model.opus;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OpusService {

	@Autowired
	private OpusRepository opusRepository;

	public OpusBean insertOpus(OpusBean opus) {

		return opusRepository.save(opus);

	}

	public OpusBean updateOpus(OpusBean opus) {

		return opusRepository.save(opus);

	}

	public boolean deleteOpusById(int id) {
		Optional<OpusBean> findbyid = opusRepository.findById(id);

		if (findbyid.isPresent()) {
			opusRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public List<OpusBean> queryOpus() {
		return opusRepository.findAll();
	}

	public OpusBean queryById(int opus_id) {
		Optional<OpusBean> findbyid = opusRepository.findById(opus_id);
		if (findbyid.isPresent()) {
			return findbyid.get();

		}
		return null;
	}
}
