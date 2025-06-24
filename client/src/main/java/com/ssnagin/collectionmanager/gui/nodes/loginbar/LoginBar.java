package com.ssnagin.collectionmanager.gui.nodes.loginbar;


import com.ssnagin.collectionmanager.session.SessionKeyManager;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginBar {

    protected Node loginBar;

    protected Node logoutBar;

    public LoginBar(Node loginBar, Node logoutBar) {
        this.loginBar = loginBar;
        this.logoutBar = logoutBar;

        showLogin();
    }

    public void switchPane() {
        if (loginBar.isVisible() == logoutBar.isVisible()) {
            showLogin(); // Kostyl nebolshoi ;)
            return;
        }

        if (loginBar.isVisible()) {
            showLogout();
            return;
        }

        showLogin();
    }

    public void showLogin() {
        loginBar.setVisible(true);
        logoutBar.setVisible(false);
    }

    public void showLogout() {
        loginBar.setVisible(false);
        logoutBar.setVisible(true);
    }
}
