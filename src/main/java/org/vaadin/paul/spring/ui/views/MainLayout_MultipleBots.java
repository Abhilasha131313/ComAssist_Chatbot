package org.vaadin.paul.spring.ui.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.alicebot.ab.Bot;
import org.vaadin.artur.Avataaar;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.applayout.AppLayout.Section;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@CssImport("./styles/shared-styles1.css")
//@PWA(name = "Vaadin AI chat", shortName = "Vaadin AI chat")
//@Theme(value = Lumo.class, variant = Lumo.DARK)
@Push
public class MainLayout_MultipleBots extends AppLayout implements AfterNavigationObserver {

  private Span botNameContainer = new Span(new Text(""));
  private Tabs tabs = new Tabs();
  private Map<Tab, String> tabToBotNameMap = new HashMap<>();
  private Map<String, Tab> botNameToTabMap = new HashMap<>();
  private List<Bot> bots;
  

  public MainLayout_MultipleBots(List<Bot> bots) {
      if (VaadinSession.getCurrent().getAttribute("nickname") == null) {
          UI.getCurrent().navigate(WelcomeView.class);
          UI.getCurrent().getPage().reload();
          return;
      }
      

      this.bots = bots;
      setPrimarySection(Section.DRAWER);
      addToNavbar(new DrawerToggle(), botNameContainer);
      createMenuTabs();
      /*Image vaadinImage = new Image("images/vaadin.png", "Vaadin logo");
      vaadinImage.addClassName("vaadin");
      Anchor vaadin = new Anchor("https://vaadin.com", vaadinImage);
      Div poweredBy = new Div(new Span(new Text("Powered by")), vaadin);
      Div sourceCode = new Div(
              new Anchor("https://github.com/alejandro-du/vaadin-ai-chat/tree/advanced", "Browse the source code")
      );
      Div footer = new Div(poweredBy, sourceCode);
      footer.addClassName("footer");*/
      final String user_nickname= VaadinSession.getCurrent().getAttribute("nickname").toString();
      H3 hello = new H3("Welcome "+user_nickname+",");
      VerticalLayout menu = new VerticalLayout(hello,new H3("Available Bots:"), tabs);
      menu.addClassName("menu");
  	RouterLink Link2 =new RouterLink("Logout", LoginView.class);
  	Link2.setHighlightCondition(HighlightConditions.sameLocation());
  	addToDrawer(menu);
    addToDrawer(new VerticalLayout(Link2));
      
      
      // simple link to the logout endpoint provided by Spring Security
         //Element logoutLink = ElementFactory.createAnchor("logout", "Logout");
         //getElement().appendChild(logoutLink);
         
   
  }
  

  private void createMenuTabs() {
      tabs.setOrientation(Tabs.Orientation.VERTICAL);
      tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
      tabs.setId("tabs");
      tabs.add(getAvailableTabs());
      tabs.addSelectedChangeListener(event -> setBotName(tabToBotNameMap.get(event.getSelectedTab())));
  }

  private void setBotName(String botName) {
      botNameContainer.removeAll();
      botNameContainer.add(new Text("Chat with " + botName));
  }

  private Tab[] getAvailableTabs() {
      return bots.stream()
              .map(Bot::getName)
              .sorted()
              .map(name -> createTab(name))
              .collect(Collectors.toList()).toArray(new Tab[bots.size()]);
  }

  private Tab createTab(String botName) {
      final Tab tab = new Tab();
      Avataaar avataaar = new Avataaar(botName);
      RouterLink link = new RouterLink(botName, ChatView_MultipleBots.class, botName);
      link.addClassName("bot-link");
      tab.add(avataaar, link);
      tabToBotNameMap.put(tab, botName);
      botNameToTabMap.put(botName, tab);
      return tab;
  }

  @Override
  public void afterNavigation(AfterNavigationEvent event) {
      super.afterNavigation();
      ChatView_MultipleBots view = (ChatView_MultipleBots) getContent();
      String botName = view.getBotName();
      tabs.setSelectedTab(botNameToTabMap.get(botName));
      setBotName(botName);
  }
  
  

}
