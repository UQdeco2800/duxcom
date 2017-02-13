package uq.deco2800.duxcom.savegame;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.WebApplicationException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import com.fasterxml.jackson.core.JsonProcessingException;

import uq.deco2800.singularity.clients.duxcom.DuxcomClient;
import uq.deco2800.singularity.clients.restful.SingularityRestClient;
import uq.deco2800.singularity.common.representations.User;
import uq.deco2800.singularity.common.representations.duxcom.PlayerStats;

@RunWith(MockitoJUnitRunner.class)
public class SavePlayerStatsTest {
    
    // Expected Exception
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Rule
    public ExpectedException fileExists = ExpectedException.none();
    
    
    @Mock
    SingularityRestClient restClient;
    
    @Before
    public void setUp() {
        reset(restClient);
    }
    
    /**
     * Test typical PlayerStats
     * @throws WebApplicationException
     * @throws IOException 
     */
    @Test
    public void saveTypicalTest() throws WebApplicationException, IOException {
        reset(restClient);
        
        String home = System.getProperty("user.home");
        File testFile = new File(home+"/Documents/My Games/Duxcom/Test/TestFile.dux");   
        File testDirectory = new File (home+"/Documents/My Games/Duxcom/Test");
        
        SavePlayerStats savePlayerStats = new SavePlayerStats();
        savePlayerStats.setRestClient(restClient);
        
        String userId = "55889c05-b3ca-4bb7-9906-976d6c787fc1";
        
        User newUser = new User("Potato", "anonymous", "anonymous", "anonymous", "anonymous");
        newUser.setUserId(userId);
        
        PlayerStats playerStats = new PlayerStats();
        playerStats.setScoreId("1");
        playerStats.setUserId(userId);
        playerStats.setNewTimestamp();
        playerStats.setKills("1");
        playerStats.setScore("1");
        
        when(restClient.getUsername()).thenReturn("Potato");
        when(restClient.getUserInformationByUserName(anyString())).thenReturn(newUser);
        
        savePlayerStats.save(testDirectory, testFile, playerStats);
        
        List<PlayerStats> retrieved = savePlayerStats.getAllScores(testFile);
        System.out.println(retrieved.size());
        
        assertTrue("There should only be one playerStats ", retrieved.size() == 1);
        
        testFile.delete();
        testDirectory.delete();
        
    }

    

}
