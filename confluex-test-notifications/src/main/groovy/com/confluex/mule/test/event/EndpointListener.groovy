package com.confluex.mule.test.event

import groovy.util.logging.Slf4j
import org.mule.api.MuleMessage
import org.mule.api.context.notification.EndpointMessageNotificationListener
import org.mule.context.notification.EndpointMessageNotification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * This can be useful for testing events without having to modify your config files or mock out
 * fake endpoints. You can attach this listener to endpoints and block until the required number
 * of messages have passed through the endpoint.
 */
@Slf4j
public class EndpointListener implements EndpointMessageNotificationListener<EndpointMessageNotification> {

    final String endpointName
    final CountDownLatch latch
    List<MuleMessage> messages = []
    List<Integer> actions = [EndpointMessageNotification.MESSAGE_DISPATCHED, EndpointMessageNotification.MESSAGE_SENT]

    /**
     * Creates a new listener and count down latch.
     *
     * @param endpointName the name of the GLOBAL endpoint
     * @param expectedCount the number of expected messages (default = 1)
     */
    public EndpointListener(String endpointName, Integer expectedCount = 1) {
        this.endpointName = endpointName;
        this.latch = new CountDownLatch(expectedCount)
    }

    /**
     * Will count down the latch if the notification action is in the configured actions list.
     *
     * @param notification generated by mule
     */
    @Override
    public void onNotification(EndpointMessageNotification notification) {
        if (endpointName == notification.immutableEndpoint.name && actions.contains(notification.action)) {
            messages << notification.source
            latch.countDown()
            log.debug("Latch count={}", latch.count)
        }
    }

    /**
     * Block until the count down latch has completed or time out.
     *
     * @param timeout the number of ms to wait until we give up
     * @return true if all expected messages have flowed through. false if timed out.
     */
    public Boolean waitForMessage(long timeout = 10000) {
        log.info("Waiting for latch to release with {} messages remaining", latch.count)
        return latch.await(timeout, TimeUnit.MILLISECONDS)
    }

}