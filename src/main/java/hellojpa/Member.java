package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  //JPA가 관리할 객체
//@Table(name = "USER")  테이블명이 USER로 되어있을때 이렇게 사용
public class Member {

    @Id  //pk
    private Long id;
    //@Column(name = "username") 컬럼명이 username일때 이렇게 사용
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
