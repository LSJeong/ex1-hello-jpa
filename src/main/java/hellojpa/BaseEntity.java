package hellojpa;


import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@MappedSuperclass //전체적, 공통적으로 사용할 속성 내려줄때 사용
//엔티티X, 테이블과 매핑X
//조회, 검색불가(em.find(BaseEntity) 불가)
//직접 생성해서 사용할 일이 없으므로 추상클래스로 사용
public abstract class BaseEntity {

    private String createdBy;
    private LocalDateTime createDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
