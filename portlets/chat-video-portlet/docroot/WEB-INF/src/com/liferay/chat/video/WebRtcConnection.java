package com.liferay.chat.video;

/**
 * WebRTC connection (thread safe)
 *
 * This represents a connection between two clients. It is owned by
 * both clients connected together, but still holds a reference to
 * the original caller so that we know it later.
 *
 * @author  Philippe Proulx <philippe.proulx@savoirfairelinux.com>
 */
public class WebRtcConnection {
	public enum State {
		INITIATED,
		CONNECTED,
		DISCONNECTED
	}
	private State currentState = State.DISCONNECTED;
	private final WebRtcClient caller;
    private long initiatedTsMs = -1;

	public WebRtcConnection(WebRtcClient caller) {
		this.caller = caller;
	}
	
	public synchronized State getState() {
		return this.currentState;
	}
	
	public synchronized void setState(State state) {
		this.currentState = state;
        if (state == State.INITIATED) {
            this.initiatedTsMs = System.currentTimeMillis();
        } else {
            this.initiatedTsMs = -1;
        }
	}
	
	public WebRtcClient getCaller() {
		return this.caller;
	}

    public long getInitatedTsMs() {
        if (this.initiatedTsMs == -1) {
            return -1;
        }
        return System.currentTimeMillis() - this.initiatedTsMs;
    }
}
