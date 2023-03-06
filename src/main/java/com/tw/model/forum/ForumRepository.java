package com.tw.model.forum;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<ForumBean, Integer> {
	
//	@Query(value = "from forum where forum_id = ?")
//	public String findById(int forum_id);
//	

}
