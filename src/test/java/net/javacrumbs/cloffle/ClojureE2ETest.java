package net.javacrumbs.cloffle;

import static org.assertj.core.api.Assertions.assertThat;

public class ClojureE2ETest extends  AbstractE2ETest {
    protected void doRun(String expression, Object expected) {
        assertThat(mikera.cljutils.Clojure.eval(expression)).isEqualTo(expected);
    }
}