package enw;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;

    public ViewStats(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}
