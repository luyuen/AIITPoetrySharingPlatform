package com.tw.model.member;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberBean, String> {
	
//	public Optional<MemberBean> findByMemberName(String name);

}
