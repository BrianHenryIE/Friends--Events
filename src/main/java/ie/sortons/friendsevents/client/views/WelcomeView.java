package ie.sortons.friendsevents.client.views;

import ie.sortons.friendsevents.client.presenter.WelcomePresenter;
import ie.sortons.gwtfbplus.client.widgets.LoginButton;
import ie.sortons.gwtfbplus.client.widgets.LoginButton.Size;
import ie.sortons.gwtfbplus.client.widgets.buttons.GreenButton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class WelcomeView extends Composite implements WelcomePresenter.Display {

	private WelcomePresenter presenter;
	
	@Override
	public void setPresenter(WelcomePresenter presenter) {
		this.presenter = presenter;		
	}

	private static WelcomeViewUiBinder uiBinder = GWT.create(WelcomeViewUiBinder.class);

	interface WelcomeViewUiBinder extends UiBinder<Widget, WelcomeView> {
	}

	@UiField
	GreenButton addAppButton;
	
	@UiField
	LoginButton fbLoginButton;
	
	public WelcomeView() {
		initWidget(uiBinder.createAndBindUi(this));
		addAppButton.getElement().getStyle().setMargin(20, Unit.PX);
		fbLoginButton.setMaxRows(1);
		fbLoginButton.setShowFaces(true);
		fbLoginButton.setSize(Size.ICON);
	}

	@UiHandler("addAppButton")
	void onClick(ClickEvent e) {	
		if(presenter!=null){
			presenter.appLogin();
		}
	}

}
