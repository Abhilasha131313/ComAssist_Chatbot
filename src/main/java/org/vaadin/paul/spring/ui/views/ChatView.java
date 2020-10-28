package org.vaadin.paul.spring.ui.views;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.configuration.BotConfiguration;
import org.vaadin.artur.Avataaar;

import com.vaadin.flow.theme.lumo.Lumo;

//import reactor.core.publisher.Flux;
//import reactor.core.publisher.UnicastProcessor;

@Route(value="chat", layout=MainLayout.class)
@CssImport("./styles/chat-view.css")
//@ParentLayout(MainLayout.class)
public class ChatView extends VerticalLayout implements RouterLayout{
	private final UI ui;
	private final MessageList messageList = new MessageList();
	private final TextField message = new TextField();
	private final Chat chatSession;
	private final ScheduledExecutorService executorService;
	public ChatView(Bot alice, ScheduledExecutorService executorService) {
		    this.executorService = executorService;
		    ui = UI.getCurrent();
		    chatSession = new Chat(alice);
		    message.setPlaceholder("Enter a message...");
		    message.setId("entermsg");
		    message.setSizeFull();
		
		    Button send = new Button(VaadinIcon.ENTER.create(), event -> sendMessage());
		  send.setId("sendkey");
		    send.addClickShortcut(Key.ENTER);
		
		    HorizontalLayout inputLayout = new HorizontalLayout(message,send);
		    inputLayout.addClassName("inputLayout");
		    add(messageList, inputLayout);
		    expand(messageList);
		    setSizeFull();
		}

	
	private void sendMessage() {
		    String text = message.getValue();
		    messageList.addMessage("You", new Avataaar("Name"), text, true);
		    message.clear();
		    ScheduledExecutorService executorService=Executors.newScheduledThreadPool(1);
		        executorService.schedule(()->{
		            String answer = chatSession.multisentenceRespond(text);
		            ui.access(() -> messageList.addMessage(
		                    "Alice", new Avataaar("Alice"), answer, false));
		        },new Random().ints(1000, 3000).findFirst().getAsInt(),
		                                 TimeUnit.MILLISECONDS);
		}

}
