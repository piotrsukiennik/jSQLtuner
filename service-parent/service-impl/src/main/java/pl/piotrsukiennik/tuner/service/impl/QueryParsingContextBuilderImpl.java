package pl.piotrsukiennik.tuner.service.impl.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.service.*;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Piotr Sukiennik
 * @date 21.02.14
 */
@Service
public class QueryParsingContextBuilderImpl<C extends QueryParsingContext> implements QueryParsingContextBuilder<C> {

    private class CacheKey {
        private String database;

        private String schema;

        private CacheKey( String database, String schema ) {
            this.database = database;
            this.schema = schema;
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) {
                return true;
            }
            if ( o == null || getClass() != o.getClass() ) {
                return false;
            }

            CacheKey cacheKey = (CacheKey) o;

            if ( database != null ? !database.equals( cacheKey.database ) : cacheKey.database != null ) {
                return false;
            }
            if ( schema != null ? !schema.equals( cacheKey.schema ) : cacheKey.schema != null ) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = database != null ? database.hashCode() : 0;
            result = 31 * result + ( schema != null ? schema.hashCode() : 0 );
            return result;
        }
    }

    @Autowired
    private SchemaService schemaService;

    @Autowired
    private QueryElementService queryElementService;

    @Resource(name = "pl.piotrsukiennik.tuner.service.QueryElementsParsingContextBuilder.impl")
    private QueryElementsParsingContextBuilder<C> queryParsingContextBuilder;


    private LoadingCache<CacheKey, QueryElements> servicesCache = CacheBuilder.newBuilder().
     expireAfterAccess( 30, TimeUnit.DAYS ).
     maximumSize( 1000 ).
     build( new CacheLoader<CacheKey, QueryElements>() {
         @Override
         public QueryElements load( CacheKey o ) throws Exception {
             return new QueryElementsImpl( schemaService, queryElementService, o.database, o.schema );
         }
     } );


    @Override
    public C getQueryContext( String database, String schema ) {
        QueryElements queryElements = null;
        try {
            queryElements = servicesCache.get( new CacheKey( database, schema ) );
            return queryParsingContextBuilder.getQueryContext( queryElements );
        }
        catch ( ExecutionException e ) {
            e.printStackTrace();
            return null;
        }
    }
}