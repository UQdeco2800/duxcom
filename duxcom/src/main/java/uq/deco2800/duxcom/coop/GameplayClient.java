package uq.deco2800.duxcom.coop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.singularity.clients.duxcom.GameplayEventListener;
import uq.deco2800.singularity.clients.duxcom.GameplayListener;
import uq.deco2800.singularity.clients.realtime.RealTimeClient;
import uq.deco2800.singularity.clients.realtime.messaging.MessagingClient;
import uq.deco2800.singularity.clients.restful.SingularityRestClient;
import uq.deco2800.singularity.common.SessionType;
import uq.deco2800.singularity.common.representations.Token;
import uq.deco2800.singularity.common.representations.duxcom.gamestate.*;
import uq.deco2800.singularity.common.representations.realtime.RealTimeSessionConfiguration;

import java.io.IOException;

/**
 * Gameplay client for duxcom
 *
 * @author liamdm
 */
public class GameplayClient extends RealTimeClient {

    private static final String CLASS = MessagingClient.class.getName();
    private static final Logger LOGGER = LoggerFactory.getLogger(CLASS);

    private GameplayListener gameplayListener = new GameplayListener();

    /**
     * @param configuration
     * @throws IOException
     */
    public GameplayClient(RealTimeSessionConfiguration configuration, SingularityRestClient client) throws IOException {
        super(configuration, client, SingularityTarget.getCurrentTarget());
        realTimeClient.addListener(gameplayListener);
        register();
    }

    @Override
    public SessionType getSessionType() {
        return SessionType.DUXCOM;
    }

    /**
     * @param listener
     */
    public void addListener(GameplayEventListener listener) {
        gameplayListener.addListener(listener);
    }

    /**
     * @param listener
     */
    public void removeListener(GameplayEventListener listener) {
        gameplayListener.removeListener(listener);
    }

    /**
     * Sends a game state message
     *
     * @param message
     */
    public void sendMessage(AbstractGameStateMessage message) {
        Token token = restClient.renewIfNeededAndGetToken();
        message.attachInformation(restClient.getUsername(), token.getUserId(), token.getTokenId());
        LOGGER.info("Sending message: [{}]", message);
        realTimeClient.sendTCP(message);
    }

}
