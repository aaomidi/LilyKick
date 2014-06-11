package com.aaomidi.lilykick;


import lilypad.client.connect.api.request.impl.RedirectRequest;

public class LilyConnection {
    private final LilyKick _plugin;

    public LilyConnection(LilyKick plugin) {
        _plugin = plugin;
    }

    public void teleportRequest(final String playerName) {
        RedirectRequest request = new RedirectRequest(_plugin.getHubName(), playerName);
        try {
            _plugin.getConnect().request(request);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
