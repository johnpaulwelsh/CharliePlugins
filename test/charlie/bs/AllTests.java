package charlie.bs;

import charlie.bs.section1.*;
import charlie.bs.section2.*;
import charlie.bs.section3.*;
import charlie.bs.section4.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
*
* @author John Paul Welsh
*/
@RunWith(Suite.class)
@Suite.SuiteClasses({
    // Section 1
    Test00_12_2.class,
    Test00_12_7.class,
    Test01_12_2.class,
    Test01_12_7.class,
    
    // Section 2
    Test00_5_2.class,
    Test00_5_7.class,
    Test01_5_2.class,
    Test01_5_7.class,
    
    // Section 3
    Test00_A2_6.class,
    Test00_A2_7.class,
    Test01_A2_6.class,
    Test01_A2_7.class,
    
    // Section 4
    Test00_22_2.class,
    Test00_22_7.class,
    Test01_22_2.class,
    Test01_22_7.class
})

public class AllTests {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}