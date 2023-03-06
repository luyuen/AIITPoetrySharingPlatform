package com.tw.model.member;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Optional<MemberBean> findByMemberId(String id) {
		return memberRepository.findById(id);

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
