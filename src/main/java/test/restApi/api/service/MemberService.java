package test.restApi.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.restApi.api.entity.Member;
import test.restApi.api.respository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }
    
    //회원전체조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원단건조회
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }

    //중복회원검증
    private void validateDuplicateMember(Member member){
        List<Member> findResult = memberRepository.findByName(member.getName());

        if(!findResult.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public void update(Long id, String name) {
        Member findMember = memberRepository.findOne(id);
        findMember.setName(name);
    }
}
