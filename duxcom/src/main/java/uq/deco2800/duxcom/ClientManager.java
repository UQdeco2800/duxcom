package uq.deco2800.duxcom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.coop.SingularityTarget;
import uq.deco2800.singularity.clients.duxcom.DuxcomClient;
import uq.deco2800.singularity.clients.realtime.messaging.MessagingClient;
import uq.deco2800.singularity.clients.restful.SingularityRestClient;
import uq.deco2800.singularity.common.ServerConstants;
import uq.deco2800.singularity.common.SessionType;
import uq.deco2800.singularity.common.representations.realtime.RealTimeSessionConfiguration;

import java.io.IOException;

public class ClientManager {

	// Initialises the class logger
	private static Logger logger = LoggerFactory.getLogger(ClientManager.class);

	// Declares the instance variable
	private static ClientManager instance;

	// Declares the client variables
	private SingularityRestClient restClient;
	private MessagingClient messagingClient;
	private DuxcomClient duxcomClient;

	/**
	 * Returns the current instance or creates a new one
	 *
	 * @return an instance of ClientManager
	 */
	public static ClientManager getInstance() {
		if (instance == null) {
            instance = new ClientManager();
		}
		return instance;
	}

	/**
	 * Private constructor
	 */
	private ClientManager() {
		// Make constructor private so there cannot be numerous ClientManagers
		restClient = new SingularityRestClient(SingularityTarget.getCurrentTarget(), ServerConstants.REST_PORT);
		duxcomClient = new DuxcomClient(SingularityTarget.getCurrentTarget(), ServerConstants.REST_PORT);
	}

	/**
	 * Should only be called once the internal rest client has been setup with
	 * credentials
	 *
	 * Currently prints to standard output if messaging client cannot be initiated.
	 *
	 * TODO Use a logger or game variable to indicate whether client has been initiated
	 */
	public void initiateMessagingClient(){
		RealTimeSessionConfiguration configuration = new RealTimeSessionConfiguration();
		configuration.setPort(ServerConstants.MESSAGING_PORT);
		configuration.setSession(SessionType.MESSAGING);
		try {
			messagingClient = new MessagingClient(configuration, restClient, SessionType.DUXCOM);
		} catch (IOException exception) {
			logger.error("Could not initiate messaging Client", exception);
		}
		messagingClient.register();
	}

	/**
	 * Gets the stored {@link SingularityRestClient}
	 * 
	 * @return The client used to perform REST calls. The object returned may
	 *         already have credentials set up
	 */
	public SingularityRestClient getRestClient() {
		return restClient;
	}

	/**
	 * Retrieves the stored {@link MessagingClient}
	 * 
	 * @return The messaging client if {@link #initiateMessagingClient()} has
	 *         been called and did not cause an exception. Else it will return
	 *         null.
	 */
	public MessagingClient getMessagingClient() {
		return messagingClient;
	}

	/**
	 * Retrieves the stored {@link DuxcomClient}
	 *
	 * @return the DuxCom client
	 */
	public DuxcomClient getDuxcomClient() {
		return duxcomClient;
	}

}
