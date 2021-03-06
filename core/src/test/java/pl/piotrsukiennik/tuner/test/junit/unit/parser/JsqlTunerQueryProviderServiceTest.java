package pl.piotrsukiennik.tuner.test.junit.unit.parser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.projection.StarProjection;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.model.source.TableSource;
import pl.piotrsukiennik.tuner.service.QueryProviderService;

import javax.annotation.Resource;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = { "classpath*:jsqltuner-test-context.xml" } )
public class JsqlTunerQueryProviderServiceTest {

    @Resource(name = "parsingQueryProviderService")
    private QueryProviderService queryProviderService;

    @Test
    public void testParseSelectQuery() throws QueryParsingNotSupportedException {
        String database = "testdb";
        String schema = "testschema";
        String query = "Select * from Test";

        Query queryParsed = queryProviderService.provide( database, schema, query );

        Assert.assertNotNull( queryParsed );
        Assert.assertTrue( queryParsed instanceof SelectQuery );

        Source source = ( (SelectQuery) queryParsed ).getSources().iterator().next();

        Assert.assertNotNull( source );
        Assert.assertTrue( source instanceof TableSource );

        Assert.assertTrue( "Test".equalsIgnoreCase( ( (TableSource) source ).getTable().getValue() ) );

        Expression projection = ( (SelectQuery) queryParsed ).getProjections().iterator().next();

        Assert.assertNotNull( projection );
        Assert.assertTrue( projection instanceof StarProjection );

        Source projectionSource = ( (StarProjection) projection ).getSource();
        Assert.assertSame( source, projectionSource );
    }


}
