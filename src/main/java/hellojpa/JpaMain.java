package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
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

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);

            em.flush();
            em.clear();

/*

            Member findMember = em.getReference(Member.class, member1.getId()); //쿼리 안나감
            System.out.println("findMember = " + findMember.getClass()); //class hellojpa.Member$HibernateProxy
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.username = " + findMember.getUsername());  //이때 DB에 쿼리
*/
/*

            Member m1 = em.find(Member.class, member1.getId());  //영속성 컨테이너에 올라감
            Member m2 = em.find(Member.class, member2.getId());
            System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass()));  //true
*/

           /* Member m1 = em.find(Member.class, member1.getId());
            Member m2 = em.getReference(Member.class, member2.getId());
            System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass()));  //false
            System.out.println("m1 == m2 : " + (m2 instanceof Member)); //true
            //타입 체크시 instance of 사용

            Member reference = em.getReference(Member.class, member1.getId());
            //이미 영속성 컨테이너에 올라가 있기때문에 프록시가 아님 class hellojpa.Member
            System.out.println("reference = " + reference.getClass());
            System.out.println("a == a: " + (m1 == reference)); //true
*/

           /* Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());  //프록시
            //em.detach(refMember);  //준영속성 상태
            em.clear();
            //LazyInitializationException:could not initialize proxy [hellojpa.Member#1] - no Session
            System.out.println("refMember = " + refMember.getUsername());*/


            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());  //프록시
            //프록시 인스턴스의 초기화 여부확인
            System.out.println("isLoaded= "+ emf.getPersistenceUnitUtil().isLoaded(refMember)); //false
            //refMember.getUsername();  //강제 초기화
            Hibernate.initialize(refMember); //강제 초기화
            System.out.println("isLoaded2= "+ emf.getPersistenceUnitUtil().isLoaded(refMember)); //true

            /*Member member = new Member();
            member.setUsername("user1");
            member.setCreatedBy("kim");
            member.setCreateDate(LocalDateTime.now());*/

            /* 상속관계
            Movie movie = new Movie();
            movie.setDirector("a");
            movie.setActor("bbbb");
            movie.setName("바람과 함께 사라지다");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

            Movie findMove = em.find(Movie.class, movie.getId());
            System.out.println("findMove = " + findMove);*/
/*
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
            }*/

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
            e.printStackTrace();
        }finally {
            em.close();
        }

        emf.close();
    }

}
