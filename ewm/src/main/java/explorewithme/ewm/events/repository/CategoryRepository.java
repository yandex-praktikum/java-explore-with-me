package explorewithme.ewm.events.repository;

import explorewithme.ewm.events.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Modifying
    @Query("update Category c set c.name = ?1 where c.id = ?2")
    int updateCategory(String name, long id);

}
