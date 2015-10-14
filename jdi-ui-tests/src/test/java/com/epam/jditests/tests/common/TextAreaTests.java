package com.epam.jditests.tests.common;

import static com.epam.jditests.entities.User.DEFAULT_USER;
import static com.epam.jditests.enums.Preconditions.CONTACT_PAGE_FILLED;
import static com.epam.jditests.pageobjects.EpamJDISite.contactFormPage;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.*;

import java.io.IOException;
import java.lang.reflect.Method;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Preconditions;
import com.epam.jditests.tests.common.utils.AttributeTests;
import com.epam.jditests.tests.common.utils.ContainsTextTests;
import com.epam.jditests.tests.common.utils.IElementable;
import com.epam.jditests.tests.common.utils.InputTests;
import com.epam.jditests.tests.common.utils.IInputable;
import com.epam.jditests.tests.common.utils.MatchTextTests;
import com.epam.jditests.tests.common.utils.SimpleTextTests;
import com.epam.jditests.tests.common.utils.ITexstable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextArea;

public class TextAreaTests extends InitTests {
	final String[] IN = { "line1", "line2", "line3" };
	public static final String TEXT = DEFAULT_USER.description;
	private Preconditions _onPage = null;
	
	public TextAreaTests() {
		_onPage = CONTACT_PAGE_FILLED;
	}

	private ITextArea getElement() {
		return contactFormPage.description;
	}
	
	private ITexstable getTextable()  { return () -> { return getElement(); }; }
	private IInputable getInputable() { return () -> { return getElement(); }; }
	private IElementable gete() 		 { return () -> { return (IElement) getElement(); }; }

	public ITextArea input() {
		return (ITextArea) getInputable().getInputElement();
	}
	
	@BeforeMethod
	public void before(final Method method) throws IOException {
		isInState(_onPage, method);
	}
	
	@AfterMethod
	public void after(){
		_onPage.open();
	}
	
	// LINES
	@Test
	public void inputLinesTest() throws Exception {
		getJSExecutor().executeScript("arguments[0].value=\"\"", input().getWebElement());
		input().inputLines(IN[0], IN[1], IN[2]);
		final String[] out = getJSExecutor().executeScript("return arguments[0].value", input().getWebElement()).toString().split("\n");
		Assert.assertEquals(IN, out);
	}

	@Test
	public void addNewLineTest() throws Exception {
		getJSExecutor().executeScript("arguments[0].value=\"line1\\nline2\\nline3\"", input().getWebElement());
		final String textLine = "newLine";
		input().addNewLine(textLine);
		final String[] out = getJSExecutor().executeScript("return arguments[0].value", input().getWebElement()).toString().split("\n");
		Assert.assertEquals(textLine, out[3]);
	}

	@Test
	public void getLinesTest() throws Exception {
		getJSExecutor().executeScript("arguments[0].value=\"line1\\nline2\\nline3\"", input().getWebElement());
		Assert.assertEquals(input().getLines(), IN);
	}

	@Test
	public void getLinesLengthTest() throws Exception {
		getJSExecutor().executeScript("arguments[0].value=\"line1\\nline2\\nline3\"", input().getWebElement());
		Assert.assertTrue(input().getLines().length == 3);
	}
	// !LINES
	
	@Factory
	public Object[] factory() {
		return new Object[] { new SimpleTextTests(TEXT, _onPage, getTextable()),
				new MatchTextTests(TEXT, "Descr.*", _onPage, getTextable()),
				new ContainsTextTests(TEXT, "escr", _onPage, getTextable()),
				new AttributeTests("testAttribute", "testValue", _onPage, gete()),
				new InputTests("inputText", _onPage, getInputable()),
				new InputTests("1234567890", _onPage, getInputable()),
				new InputTests("!@#^&*()_", _onPage, getInputable()) };
	}
}