package com.epam.ui_test_framework.elements.base;

import com.epam.ui_test_framework.elements.interfaces.base.ISelect;
import com.epam.ui_test_framework.elements.common.ClickableText;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public class SelectElement extends ClickableText implements ISelect {
    public SelectElement() { }
    public SelectElement(By byLocator) { super(byLocator); }

    public void select() { click(); }
    protected boolean isSelectedAction() { return getWebElement().isSelected(); }
    public final boolean isSelected() {
        return doJActionResult("Is Selected", this::isSelectedAction);
    }
}
