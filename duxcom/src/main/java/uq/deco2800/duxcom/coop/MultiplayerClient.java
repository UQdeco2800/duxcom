package uq.deco2800.duxcom.coop;

import uq.deco2800.duxcom.auth.LoginData;
import uq.deco2800.duxcom.auth.LoginManager;
import uq.deco2800.duxcom.coop.exceptions.IncorrectGameState;
import uq.deco2800.duxcom.coop.exceptions.MultiplayerException;
import uq.deco2800.singularity.clients.restful.SingularityRestClient;
import uq.deco2800.singularity.common.ServerConstants;
import uq.deco2800.singularity.common.SessionType;
import uq.deco2800.singularity.common.representations.Token;
import uq.deco2800.singularity.common.representations.realtime.RealTimeSessionConfiguration;

import javax.ws.rs.WebApplicationException;
import java.io.IOException;

/**
 * Handles interactions needed to interact with multiplayer
 * <p>
 * Created by liamdm on 27/09/2016.
 */
public class MultiplayerClient extends SingularityRestClient {

    /**
     * The stored clients
     */
    private static MultiplayerClient multiplayerClient;
    private static GameplayClient gameplayClient;

    /**
     * Stores the multiplayer client for retrieval
     */
    public static void storeClient(MultiplayerClient mc, GameplayClient gc){
        MultiplayerClient.multiplayerClient = mc;
        MultiplayerClient.gameplayClient = gc;
    }

    /**
     * The logger used in the class
     */
    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MultiplayerClient.class);
    private final RealTimeSessionConfiguration configuration;
    private Token token;

    /**
     * If this client is admin
     */
    private boolean admin = false;

    public static MultiplayerClient getMultiplayerClient() {
        return multiplayerClient;
    }

    public static GameplayClient getGameplayClient() {
        return gameplayClient;
    }

    public RealTimeSessionConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Connects to an existing server
     *
     * @param serverID the server ID to use
     * @return the multiplayer client
     */
    public static MultiplayerClient connectToServer(String serverID) throws IncorrectGameState, IOException, MultiplayerException {
        return new MultiplayerClient(LoginManager.getData(), serverID, false);
    }

    /**
     * Create a new multiplayer server
     *
     * @param serverID the server ID to use
     * @return the multiplayer client
     */
    public static MultiplayerClient createServer(String serverID) throws IncorrectGameState, IOException, MultiplayerException {
        return new MultiplayerClient(LoginManager.getData(), serverID, true);
    }

    /**
     * Get the server session ID
     *
     * @return the server session ID
     */
    public String getServerName() {
        return String.valueOf(configuration.getSessionID());
    }

    /**
     * Creates a multiplayer client
     *
     * @throws IOException on fail
     */
    private MultiplayerClient(LoginData loginData, String serverID, boolean create) throws IOException, MultiplayerException, IncorrectGameState {
        super(SingularityTarget.getCurrentTarget(), ServerConstants.REST_PORT);

        admin = create;

        try {
            setupCredentials(loginData.getUsername(), loginData.getPassword());
            token = renewIfNeededAndGetToken();
        } catch (WebApplicationException ex) {
            throw new MultiplayerException(MultiplayerException.ErrorCode.CREDENTIAL_INVALID, ex);
        }

        RealTimeSessionConfiguration msc = getMultiplayerSession(serverID);
        boolean exists =
                msc != null;
        if (create && exists) {
            // tried to create something that did exist
            throw new IncorrectGameState(String.format("Tried to create server ID %s that already existed!", serverID));
        } else if (!create && !exists) {
            // tried to join something that didn't exist
            throw new IncorrectGameState(String.format("Tried to join server ID %s that doesn't exist!", serverID));
        }

        if (create) {
            logger.info(String.format("Gameplay server with ID %s does not exist. Creating...", serverID));
            configuration = requestMultiplayerGameSession(SessionType.DUXCOM, serverID); // create the session
        } else {
            configuration = msc;
        }
    }


    /**
     * If this client is an admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Set if this client has admin powers
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
