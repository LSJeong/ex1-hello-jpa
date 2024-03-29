package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity  //JPA가 관리할 객체
//@Table(name = "USER")  테이블명이 USER로 되어있을때 이렇게 사용
/*@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50)
*/
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    /*@Column(name = "TEAM_ID")
    private Long teamId;*/

    @ManyToOne(fetch = FetchType.LAZY)  //지연로딩. Team을 프록시로 조회
    //@ManyToOne(fetch = FetchType.EAGER) //즉시로딩, 실무에서는 가급적 지연 로딩만 사용
    //즉시로딩=> JPQL에서 N+1 문제를 일으킴
    //@ManyToOne, @OneToOne 은 기본이 즉시 로딩이므로 LAZY로 설정
    //@OneToMany, @ManyToMany는 기본이 지연 로딩
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    //일대일
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    //다대다 //연결테이블 생성됨
    //실무에서 쓰기는 어려움, 연결테이블용 엔티티 추가 해서 쓰자
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();


    @Embedded  //값 타입을 사용하는 곳에 정의
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns =
//        @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressHistory = new ArrayList<>();
    //값타입 컬렉션 대신에 일대다 관계를 고려
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

    //한 엔티티에서 같은 값 타입을 사용하면? => 칼럼 중복
    //@AttributeOverrides, @AttributeOverride를 사용해서 컬러 명 속성을 재정의
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "WORK_ZIPCODE"))
    })
    private Address workAddress;

    public Team getTeam() {
        return team;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
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

    public void setTeam(Team team) {
        this.team = team;
    }

/* @Id  //pk
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
    private int temp;*/



}
