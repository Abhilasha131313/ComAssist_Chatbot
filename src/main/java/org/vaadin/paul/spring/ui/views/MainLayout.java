package org.vaadin.paul.spring.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout; 
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.History;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
@CssImport("./styles/chat-view.css")
@Push
public class MainLayout extends AppLayout {
	History history = UI.getCurrent().getPage().getHistory();
public MainLayout() {
	
	createHeader();
	createDrawer();
}
private void createHeader() {
	H1 logo=new H1("Hello User");
	logo.addClassName("logo");

	HorizontalLayout header=new HorizontalLayout(new DrawerToggle(), logo);
	header.addClassName("header");
	header.setWidth("100%");
	header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
	addToNavbar(header);
}
private void createDrawer() {
	//RouterLink listLink =new RouterLink("End Session", MainView.class);
final Button end_Session = new Button("End Session", e -> {
		
	UI.getCurrent().navigate(" ");
      
});
end_Session.setId("endsession");
final Button restart = new Button("Restart Session", e -> {
		
		history.go(0);
      
});
restart.setId("restartsession");
	final Button logout = new Button("Logout", e -> {
		
		UI.getCurrent().navigate("login");
	      
	});
logout.setId("logoutsession");
VerticalLayout options=new VerticalLayout(end_Session, restart, logout); 

    addToDrawer(options);
}


}
