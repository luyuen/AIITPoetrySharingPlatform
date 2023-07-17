package com.tw.model.member;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.model.exveption.memberNotFindEx;

@Service
@Transactional
public class MemberService {

	@Autowired

	private MemberRepository memberRepository;

	public MemberBean insertMember(MemberBean member) {
		return memberRepository.save(member);
	}

	public boolean deleteById(String id) {
		Object memberId = memberRepository.findById(id);

		if (memberId != null) {
			memberRepository.deleteById(id);
			return true;
		}
		return false;

	}
	
	public boolean findbyMember(String member) {
		 Optional<MemberBean> findMember = memberRepository.findById(member);
		 if(findMember.isEmpty()) {
			 return false;
		 }
		 return true ;
	}

	public MemberBean findByMemberForLogin(String member) {
		 Optional<MemberBean> findMember1 = memberRepository.findById(member);
		 if(member.isEmpty()) {
			 throw new memberNotFindEx("can't find member");
		 }
		 return findMember1.get();			 
	}

	public MemberBean updateMember(String id, MemberBean member) {
		Optional<MemberBean> memberId = memberRepository.findById(id);

		if (memberId != null) {
			return memberRepository.save(member);
		}
		return null;
	}

	public List<MemberBean> queryMember() {
		return memberRepository.findAll();
	}
}
