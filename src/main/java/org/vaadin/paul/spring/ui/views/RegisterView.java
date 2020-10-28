package org.vaadin.paul.spring.ui.views;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
//import org.eclipse.jgit.transport.CredentialItem.Username;
import com.sun.el.stream.Stream;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
@Route("register")
public class RegisterView extends VerticalLayout {
	public static final String ROUTE = "register";
	private Grid<RegisterPage> registerPageGrid = new Grid<>(RegisterPage.class);
	 public RegisterView() {
		    add(
		        new H1("Register Here"),
		        buildForm(),
		        registerPageGrid
		        );
		  }
	
	private Component buildForm() {
		    // Create UI components
		    TextField usernameField = new TextField("Username");
		    TextField passwordField = new TextField("Password");
		    TextField emailIDField = new TextField("emailID");
		    TextField fullnameField = new TextField("Fullname");
		    Button registerButton = new Button("Register");
		    Div errorsLayout = new Div();
		    // Configure UI components
		    registerButton.setThemeName("primary");
		    Binder<RegisterPage> binder = new Binder<>(RegisterPage.class);
		   
		    binder.forField(usernameField)
	        .asRequired("User Name is required")
	        .bind(RegisterPage::getUsername, RegisterPage::setUsername);
		    binder.forField(passwordField)
	        .asRequired("Pass Word is required")
	        .bind(RegisterPage::getPassword, RegisterPage::setPassword);
		    binder.forField(emailIDField)
	        .asRequired("Email ID is required")
	        .bind(RegisterPage::getEmailID, RegisterPage::setEmailID);
		    binder.forField(fullnameField)
	        .asRequired("Full Name is required")
	        .bind(RegisterPage::getFullname, RegisterPage::setFullname);
		   
		    binder.addStatusChangeListener(status -> {
		        // Workaround for https://github.com/vaadin/flow/issues/4988
		      
		        registerButton.setEnabled(!status.hasValidationErrors());
		      }
		  );
		    registerButton.addClickListener(click -> {
		    	  try {
		    	    errorsLayout.setText("");
		    	    RegisterPage newregister = new RegisterPage();
		    	    binder.writeBean(newregister);
		    	    addOrder(newregister);
		    	    binder.readBean(new RegisterPage());
		    	    
		    	  } catch (ValidationException e) {
		    	    errorsLayout.add(new Html(e.getValidationErrors().stream()
		    	        .map(res -> "<p>" + res.getErrorMessage() + "</p>")
		    	        .collect(Collectors.joining("\n"))));
		    	  }
		    	});
		 
		   
		    // Wrap components in layouts
		    HorizontalLayout formLayout = new HorizontalLayout(usernameField, passwordField, emailIDField, fullnameField, registerButton);
		    Div wrapperLayout = new Div(formLayout, errorsLayout);
		    formLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
		    wrapperLayout.setWidth("100%");
		    return wrapperLayout;
		   
		 
		   
		 
		       
		  }
	private List<RegisterPage> registry = new LinkedList<>();
	private void addOrder(RegisterPage newregister) {
		registry.add(newregister);
		  registerPageGrid.setItems(registry);
		  final Button Log = new Button("Login In", e -> {
			  UI.getCurrent().navigate("login");;
	        });
		  add(new H2("User successfully Registered"));
		  add(Log);
	}
	
	
	  }
