package gc.cafe.aop.querycounter;

import lombok.Getter;

@Getter
public class QueryCounter {

    private int count;

    public void increase() {
        count++;
    }

    public boolean isWarn() {
        return count > 10;
    }
}
