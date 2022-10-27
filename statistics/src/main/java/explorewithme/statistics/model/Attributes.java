package explorewithme.statistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attributes implements Serializable {

    private String uri;
    private String ip;

    @Override
    public String toString() {
        return "{" +
                "uri='" + uri + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }

}
