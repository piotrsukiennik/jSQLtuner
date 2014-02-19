package pl.piotrsukiennik.tuner.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.service.DataSourceService;
import pl.piotrsukiennik.tuner.util.Collections3;

import java.util.*;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 10:18
 */
@Service
class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private Set<DataSource> dataSources;

    private Map<String, DataSource> rootDataSources = new LinkedHashMap<String, DataSource>();

    private class IdentifierPredicate implements Predicate<DataSource> {
        private String identifier;

        private IdentifierPredicate( String identifier ) {
            this.identifier = identifier;
        }

        @Override
        public boolean apply( DataSource dataSource ) {
            return identifier.equalsIgnoreCase( dataSource.getDataSourceIdentity().getIdentifier() );
        }
    }

    @Override
    public DataSource getRootDataSource( Query selectQuery ) {
        return rootDataSources.get( selectQuery.getHash() );
    }

    @Override
    public void setRootDataSource( Query query, DataSource dataSource ) {
        rootDataSources.put( query.getHash(), dataSource );
    }

    @Override
    public DataSource getDataSourceByIdentity( Query query, DataSourceIdentity dataSourceIdentity ) {
        return Collections3.first( getDataSourcesByIdentity( query, dataSourceIdentity ) );
    }

    public Collection<DataSource> getDataSources( Query selectQuery ) {
        Set<DataSource> out = new LinkedHashSet<>( dataSources );
        out.add( getRootDataSource( selectQuery ) );
        return out;
    }


    protected Collection<DataSource> getDataSourcesByIdentity( Query query, DataSourceIdentity dataSourceIdentity ) {
        if ( dataSourceIdentity != null ) {
            IdentifierPredicate identifierPredicate = new IdentifierPredicate( dataSourceIdentity.getIdentifier() );
            Collection<DataSource> dataSources = getDataSources( query );
            return new LinkedHashSet<DataSource>( Collections2.filter( dataSources, identifierPredicate ) );
        }
        return Collections.EMPTY_LIST;
    }


}