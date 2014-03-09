package pl.piotrsukiennik.tuner.test.junit.benchmark.entities;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.carrotsearch.junitbenchmarks.h2.H2Consumer;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrsukiennik.tuner.test.junit.benchmark.H2ConsumerFactory;
import pl.piotrsukiennik.tuner.test.junit.unit.entities.JsqlTunerWrapperTest;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart
public class JsqlTunerWrapperBenchmark extends JsqlTunerWrapperTest {

    protected static final H2Consumer H2_CONSUMER = H2ConsumerFactory.getH2Consumer( JsqlTunerWrapperBenchmark.class );

    @Rule
    public TestRule benchmarkRun = new BenchmarkRule( H2_CONSUMER );

}