package explorewithme.ewm.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "compilation", schema = "public")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compilation {

    @Id
    @Column (name = "compilation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String title;
    @Column
    private boolean pinned;

    public Compilation(String title, boolean pinned){
        this.title = title;
        this.pinned = pinned;
    }

}
