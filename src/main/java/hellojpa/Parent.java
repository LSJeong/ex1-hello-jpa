package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    //cascade, orphanRemoval는 참조하는 곳이 하나일 때 사용해야함(특정 엔티티가 개인 소유할때)
    //ex member에서 child 보고있으면 쓰면 X
    //orphanRemoval는 parent 지워지면 child도 제거, 고아 객체
    //cascade, orphanRemoval 둘다 활성화 하면 부모 엔티티를 통해서 자식의 생명주기를 관리할 수 있음
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> childList = new ArrayList<>();

    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
    }

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

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
