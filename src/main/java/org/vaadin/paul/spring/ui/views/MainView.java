package org.vaadin.paul.spring.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.paul.spring.MessageBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Push
@Route

public class MainView extends VerticalLayout {
	public MainView() {
        addClassName(getClass().getSimpleName());

        H1 title = new H1("WELCOME TO COMASSIST CHATBOT PLATFORM");
        title.addClassName(getClass().getSimpleName() + "-title");

        final Button login = new Button("Log In", e -> {
        	UI.getCurrent().navigate("login"); 
        });
        add(new H1(" "));
        final Button signup = new Button("Sign Up", e -> {
        	UI.getCurrent().navigate("register");
        });
        login.setId("LoginButton");
        signup.setId("SignUpButton");
        
        VerticalLayout form = new VerticalLayout(title,login,signup);
        form.setSizeUndefined();
        form.addClassName(getClass().getSimpleName() + "-form");
        add(form);
    }

}
