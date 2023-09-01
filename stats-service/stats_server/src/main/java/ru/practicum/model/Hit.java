package ru.practicum.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "hits", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Long id;
    @Column(name = "app", nullable = false, length = 250)
    String app;
    @Column(name = "uri", nullable = false, length = 250)
    String uri;
    @Column(name = "ip", nullable = false, length = 25)
    String ip;
    @Column(name = "time_stamp", nullable = false)
    LocalDateTime timestamp;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HibernateProxy hibernateproxy = o instanceof HibernateProxy ? (HibernateProxy) o : null;
        Class<?> oEffectiveClass = hibernateproxy != null ? hibernateproxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = hibernateproxy != null ? hibernateproxy.getHibernateLazyInitializer().getPersistentClass() : getClass();
        if (!thisEffectiveClass.equals(oEffectiveClass)) return false;
        Hit hit = (Hit) o;
        return getId() != null && Objects.equals(getId(), hit.getId());
    }

    @Override
    public final int hashCode() {
        HibernateProxy hibernateproxy = (HibernateProxy) (this instanceof HibernateProxy ? this : null);
        return hibernateproxy != null ? hibernateproxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
