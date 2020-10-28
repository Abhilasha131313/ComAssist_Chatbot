package org.vaadin.paul.spring.ui.views;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.paul.spring.MessageBean;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

@Tag("sa-Welcome-view")
@Route(value = WelcomeView.ROUTE, layout=MainLayout.class)
@PageTitle("welcome")
//@Route("join")
@CssImport("/styles/chat-view.css")
//@RouteAlias(value = "")

public class WelcomeView extends VerticalLayout {
	public static final String ROUTE = "welcome";

    public WelcomeView(@Autowired MessageBean bean) {
    	H1 title=new H1("CHOOSE YOUR BOT INTERFACE");
    	final Button button = new Button("Multiple Bot Mode", e -> {
            showChat_Multiple();
        });
        add(new H1(" "));
        final Button button1 = new Button("Single Bot Mode", e -> {
            showChat();
        });
        button.setId("SingleBotbutton1");
        button1.setId("SingleBotbutton2");

    	VerticalLayout botLayout = new VerticalLayout(title,button,button1);
    	botLayout.setWidth("250px");
    	botLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        botLayout.setSizeFull();
        botLayout.addClassName("botLayout");
        add(botLayout);
		 }

private void showChat_Multiple() {
		// TODO Auto-generated method stub
	UI.getCurrent().navigate("join");
		
	}


private void showChat() {
		// TODO Auto-generated method stub
	   UI.getCurrent().navigate("chat");
		
	}

    //button.addClickListener(clickEvent ->Notification.show("You have selected Single Bot for Conversation"));

    private void doHeavyStuff() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            // ignore
        }
    }
    
    
}
