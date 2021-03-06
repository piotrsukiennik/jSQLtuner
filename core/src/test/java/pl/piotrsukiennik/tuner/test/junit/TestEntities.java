package pl.piotrsukiennik.tuner.test.junit;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import pl.piotrsukiennik.tuner.test.model.MockData;
import pl.piotrsukiennik.tuner.test.model.MockDataModel;
import pl.piotrsukiennik.tuner.test.service.EntityService;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class TestEntities extends TestQueries {

    @Autowired
    private EntityService entityService;


    @Before
    public void setupBefore() {
        MockData t1 = entityService.save( "test1" );
        MockData t2 = entityService.save( "test2" );
    }

    @After
    public void setupAfter() {
        List<MockDataModel> mockDataList = new ArrayList<>();
        List<MockDataModel> t1 = entityService.getEntriesByEmail( "test1" );
        List<MockDataModel> t2 = entityService.getEntriesByEmail( "test2" );
        mockDataList.addAll( t1 );
        mockDataList.addAll( t2 );
        for ( MockDataModel t : mockDataList ) {
            entityService.deleteTestEntry( t );
        }
    }


    @Test
    public void testCacheClear() {
        List<MockDataModel> testEntities = entityService.getTestEntities();
        int size = testEntities.size();

        MockDataModel t3 = entityService.save( "test3" );

        List<MockDataModel> testEntities2 = entityService.getTestEntities();
        Assert.assertEquals( ( size + 1 ), testEntities2.size() );

        entityService.deleteTestEntry( t3 );

        List<MockDataModel> testEntities3 = entityService.getTestEntities();
        Assert.assertEquals( size, testEntities3.size() );
    }


    @Test
    public void testGetNull() {
        MockData mockData2 = entityService.getTestEntry( -1 );
        Assert.assertNull( mockData2 );
    }

    @Test
    public void testGetDifferentValues() {
        List<MockDataModel> testEntities = entityService.getEntriesByEmail( "test1" );
        MockData mockData = testEntities.get( 0 );
        Assert.assertNotNull( mockData );
        Assert.assertEquals( "test1", mockData.getEmail() );

        List<MockDataModel> testEntitiesEmpty = entityService.getEntriesByEmail( "test999" );
        Assert.assertEquals( 0, testEntitiesEmpty.size() );
    }

    public void testSingleSelect( final int runs ) {
        for ( int i = 0; i < runs; i++ ) {
            List<MockDataModel> testEntities = entityService.getEntriesWithNameAndEmailBegginingWithA();
            Assert.assertEquals( 19, testEntities.size() );
        }
    }

    @Test
    public void testSingleSelect1() {
        testSingleSelect( 1 );
    }

    @Test
    public void testSingleSelect10() {
        testSingleSelect( 10 );
    }


    @Test
    public void testSingleSelect100() {
        testSingleSelect( 100 );
    }


}
