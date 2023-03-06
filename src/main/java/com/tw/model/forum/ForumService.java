package com.tw.model.forum;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ForumService {

	@Autowired
	private ForumRepository forumRepository;

	public ForumBean insertForun(ForumBean forum) {
		return forumRepository.save(forum);
	}

	public ForumBean updateForun(Integer id, ForumBean forum) {
		forumRepository.findById(id);
		if(id != null) {
			System.out.println("forumService" + forum);
			return forumRepository.save(forum);
		}
		return null;
		
	}

	public boolean deleteById(int id) {
		forumRepository.deleteById(id);
		return true;
	}

	public List<ForumBean> queryForum() {
		return forumRepository.findAll();
	}

	public ForumBean queryForumById(int id) {
		Optional<ForumBean> optional = forumRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();

		}
		return null;

	}
}
