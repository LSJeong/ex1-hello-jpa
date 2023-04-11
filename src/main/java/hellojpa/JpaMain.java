package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //persistence.xml => persistence-unit name에 적어준 이름
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        //DB 커넥션이라고 생각하면 됨
        EntityTransaction tx = em.getTransaction();
        //트랜잭션 시작
        tx.begin();
        try {

            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            //member.setTeamId(team.getId());
            //member.setTeam(team);  //연관관계 주인에 값 설정
            member.changeTeam(team);

            em.persist(member);

            //team.getMembers().add(member);  //양쪽에 값 설정

            em.flush();
            em.clear();

            //조회
            Member findMember = em.find(Member.class, member.getId());
            //Team findTeam = findMember.getTeam();
            //System.out.println("findTeam = " + findTeam.getName());
            List<Member> members = findMember.getTeam().getMembers();  //양방향 연관관계
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

            /*Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");*/

            /* //member 저장
            em.persist(member);*/

            //member 찾기
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());

            //member 삭제
            //em.remove(findMember);

            //member 이름 수정
            //findMember.setName("HelloJPA");

            //JPQL : 객체를 대상으로 검색하는 객체 지향 쿼리, 객체 지향 SQL
            /*List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5).setMaxResults(8)   //페이징
                    .getResultList();

            for(Member member : result){
                System.out.println("member.name = " + member.getName());
            }*/
/*

            //비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            //영속
            System.out.println("=== BEFORE ===");
            em.persist(member);  //1차 캐시에 저장됨
            System.out.println("=== AFTER ===");

            em.flush(); //commit전에 바로 DB반영
            //영속성 컨텍스트를 비우지않음
            //영속성 컨텍스트의 변경내용을 데이터베이스에 동기화

            Member findMember = em.find(Member.class, 101L);  //1차 캐시에서 조회
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());


            //수정? update?
            Member findMember1 = em.find(Member.class, 150L);
            findMember1.setName("ZZZZZ");
            //그냥 이렇게하면 update 쿼리 날려줌

            //영속성 컨텍스트에서 관리하지마라라는 뜻(준영속 상태로 전환)
            //update 쿼리를 날리지않음
            em.detach(findMember1);

            //영속성 컨텍스트를 완전 초기화
            em.clear();
*/

            //트랜잭션 commit
            //commit하는 순간 DB에 insert
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
