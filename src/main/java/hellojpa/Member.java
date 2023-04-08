package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity  //JPA가 관리할 객체
//@Table(name = "USER")  테이블명이 USER로 되어있을때 이렇게 사용
/*@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50)
*/
public class Member {

    @Id  //pk
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            ,generator = "MEMBER_SEQ_GENERATOR")
    //IDENTITY 기본키 생성을 DB에 위임, em.persist() 시점에 즉시 INSERT SQL 실행하고 DB에서 식별자를 조회
    //SEQUENCE 숫자타입으로 쓰면 오라클 시퀀스처럼 값 자동 생성
    private Long id;

    //@Column(name = "username") 컬럼명이 username일때 이렇게 사용
    //private String name;

    @Column(name = "name", nullable = false)
    private String username;

    private Integer age;

    //enum 타입 맵핑
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    //날짜타입 맵핑
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    //최신 지원
    private LocalDate testLocalDate;  //date 타입
    private LocalDateTime testLocalDateTime;  //timestamp 타입

    //BLOB, CLOB 매핑
    //문자면 알아서 CLOB 맵핑, 나머지는 BLOB
    @Lob
    private String description;

    //특정 필드를 컬럼에 매핑하지 않음(매핑 무시)
    @Transient
    private int temp;

    public Member(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
