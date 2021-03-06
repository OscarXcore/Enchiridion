package joshie.enchiridion.gui.book.buttons;

import joshie.enchiridion.api.EnchiridionAPI;
import joshie.enchiridion.api.book.IPage;
import joshie.enchiridion.gui.book.buttons.actions.ActionJumpPage;
import joshie.enchiridion.gui.book.features.FeatureButton;

public class ButtonInsertButton extends ButtonAbstract {
    public ButtonInsertButton() {
        super("arrow");
    }

    @Override
    public void performAction() {
        IPage current = EnchiridionAPI.book.getPage();
        FeatureButton feature = new FeatureButton(new ActionJumpPage().create());
        current.addFeature(feature, 0, current.getScroll(), 18D, 10D, false, false);
    }
}
