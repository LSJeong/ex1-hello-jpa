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
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5).setMaxResults(8)   //페이징
                    .getResultList();

            for(Member member : result){
                System.out.println("member.name = " + member.getName());
            }








            //트랜잭션 commit
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
